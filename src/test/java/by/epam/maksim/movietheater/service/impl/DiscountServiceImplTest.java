package by.epam.maksim.movietheater.service.impl;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static by.epam.maksim.movietheater.service.DiscountService.FIFTY_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.FIVE_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.NO_DISCOUNT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class DiscountServiceImplTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountStrategy mockDiscountStrategy1;

    @Autowired
    private DiscountStrategy mockDiscountStrategy2;

    @Test
    public void noDiscountInCaseNoOneAppliedDiscountStrategy() {
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());

        assertEquals(NO_DISCOUNT, discountService.getDiscount(new User(), new Event(), LocalDateTime.now(), 5));
    }

    @Test
    public void discountAccordingToOneOfDiscountStrategy() {
        doReturn(NO_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(FIVE_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());

        assertEquals(FIVE_PERCENTAGE_DISCOUNT, discountService.getDiscount(new User(), new Event(), LocalDateTime.now(), 5));
    }

    @Test
    public void maximalDiscountAccordingToAllDiscountStrategies() {
        doReturn(FIFTY_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy1).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());
        doReturn(FIVE_PERCENTAGE_DISCOUNT).when(mockDiscountStrategy2).calculateDiscount(anyObject(), anyObject(), anyObject(), anyInt());

        assertEquals(FIFTY_PERCENTAGE_DISCOUNT, discountService.getDiscount(new User(), new Event(), LocalDateTime.now(), 5));
    }

}