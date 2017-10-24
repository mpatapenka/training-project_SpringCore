package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.service.CounterService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static by.epam.maksim.movietheater.service.CounterService.DISCOUNT_PERCENTAGE_DOMAIN;
import static by.epam.maksim.movietheater.service.DiscountService.FIFTY_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.FIVE_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.NO_DISCOUNT;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class DiscountAspectTest {

    @Autowired
    private CounterService mockCounterService;

    @Autowired
    private DiscountStrategy mockDiscountStrategy1;

    @Autowired
    private DiscountStrategy mockDiscountStrategy2;

    @Autowired
    private DiscountService discountService;

    @Before
    public void beforeTest() {
        reset(mockCounterService);
    }

    @Test
    public void checkHowManyTimesEachDiscountWasAppliedPerUser() {
        User user1 = buildUser("user1@user.com");
        User user2 = buildUser("user2@user.com");
        User user3 = buildUser("user3@user.com");

        doReturn(FIVE_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(eq(user1), anyObject(), anyObject(), anyInt());
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(eq(user1), anyObject(), anyObject(), anyInt());
        doReturn(FIVE_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(eq(user2), anyObject(), anyObject(), anyInt());
        doReturn(FIFTY_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(eq(user2), anyObject(), anyObject(), anyInt());
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(eq(user3), anyObject(), anyObject(), anyInt());
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(eq(user3), anyObject(), anyObject(), anyInt());

        discountService.getDiscount(user1, new Event(), LocalDateTime.now(), 1);
        discountService.getDiscount(user1, new Event(), LocalDateTime.now(), 2);
        discountService.getDiscount(user2, new Event(), LocalDateTime.now(), 5);
        discountService.getDiscount(user3, new Event(), LocalDateTime.now(), 2);
        discountService.getDiscount(user3, new Event(), LocalDateTime.now(), 1);

        verify(mockCounterService, times(2)).increment(User.class, user1.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + FIVE_PERCENTAGE_DISCOUNT);
        verify(mockCounterService, times(1)).increment(User.class, user2.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + FIFTY_PERCENTAGE_DISCOUNT);
        verify(mockCounterService, times(0)).increment(User.class, user3.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + FIVE_PERCENTAGE_DISCOUNT);
        verify(mockCounterService, times(0)).increment(User.class, user3.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + FIFTY_PERCENTAGE_DISCOUNT);
    }

    private User buildUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }

}