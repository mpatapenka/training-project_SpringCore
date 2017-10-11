package by.epam.maksim.movietheater.aspect;

import by.epam.maksim.movietheater.domain.User;
import by.epam.maksim.movietheater.service.CounterService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @SneakyThrows
    @Around("execution(* *.getDiscount(..)) && within(by.epam.maksim.movietheater.*.DiscountService)")
    public Object calculateOveralDiscounts(ProceedingJoinPoint proceedingJoinPoint) {
        User userArg = (User) proceedingJoinPoint.getArgs()[0];
        Object discount = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        counterService.increment(User.class, userArg.getEmail(), DISCOUNT_PERCENTAGE_DOMAIN + discount);
        return discount;
    }

}