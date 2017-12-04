package by.epam.maksim.movietheater.web.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Throwable.class)
    public String handleNoHandlerFoundException(Throwable e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}