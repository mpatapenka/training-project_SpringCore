package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.CounterService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static by.epam.maksim.movietheater.service.CounterService.DISCOUNT_PERCENTAGE_DOMAIN;

@Aspect
@Component
@AllArgsConstructor
public class DiscountAspect {

    @Autowired
    private CounterService counterService;

    @AfterReturning(value = "execution(* by.epam.maksim.movietheater.*.DiscountService.getDiscount(..)) && args(user, ..)",
            returning = "discount")
    public void calculateOveralDiscounts(User user, byte discount) {
        if (user != null && discount > 0) {
            counterService.increment(User.class, user.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + discount);
        }
    }

}