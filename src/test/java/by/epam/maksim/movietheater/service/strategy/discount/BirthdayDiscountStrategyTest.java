package by.epam.maksim.movietheater.service.strategy.discount;

import by.epam.maksim.movietheater.config.TestAppConfig;
import by.epam.maksim.movietheater.domain.Event;
import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.strategy.DiscountStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static by.epam.maksim.movietheater.service.DiscountService.FIVE_PERCENTAGE_DISCOUNT;
import static by.epam.maksim.movietheater.service.DiscountService.NO_DISCOUNT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
public class BirthdayDiscountStrategyTest {

    @Autowired
    @Qualifier("birthdayDiscountStrategy")
    private DiscountStrategy birthdayStrategy;

    @Test
    public void noDiscountInCaseNotRegisteredUser() {
        assertEquals(NO_DISCOUNT, birthdayStrategy.calculateDiscount(null, new Event(), LocalDateTime.now(), 0));
    }

    @Test
    public void noDiscountIfUserDidntSetBirthday() {
        assertEquals(NO_DISCOUNT, birthdayStrategy.calculateDiscount(new User(), new Event(), LocalDateTime.now(), 0));
    }

    @Test
    public void noDiscountIfUserBirthdayBeforeAirDateNotWithinDiscountDays() {
        LocalDate birthday = LocalDate.now();
        LocalDateTime airDate = birthday.atStartOfDay().minusDays(5);
        User user = new User();
        user.setBirthday(birthday);
        assertEquals(NO_DISCOUNT, birthdayStrategy.calculateDiscount(user, new Event(), airDate, 0));
    }

    @Test
    public void noDiscountIfUserBirthdayAfterAirDateNotWithinDiscountDays() {
        LocalDate birthday = LocalDate.now();
        LocalDateTime airDate = birthday.atStartOfDay().plusDays(5);
        User user = new User();
        user.setBirthday(birthday);
        assertEquals(NO_DISCOUNT, birthdayStrategy.calculateDiscount(user, new Event(), airDate, 0));
    }

    @Test
    public void discountIfUserBirthdayBeforeAirDateWithinDiscountDays() {
        LocalDate birthday = LocalDate.now();
        LocalDateTime airDate = birthday.atStartOfDay().plusDays(1);
        User user = new User();
        user.setBirthday(birthday);
        assertEquals(FIVE_PERCENTAGE_DISCOUNT, birthdayStrategy.calculateDiscount(user, new Event(), airDate, 0));
    }

    @Test
    public void discountIfUserBirthdayAfterAirDateWithinDiscountDays() {
        LocalDate birthday = LocalDate.now();
        LocalDateTime airDate = birthday.atStartOfDay().minusDays(1);
        User user = new User();
        user.setBirthday(birthday);
        assertEquals(FIVE_PERCENTAGE_DISCOUNT, birthdayStrategy.calculateDiscount(user, new Event(), airDate, 0));
    }

}