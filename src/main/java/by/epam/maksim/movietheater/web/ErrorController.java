package by.epam.maksim.movietheater.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/exception")
    public void throwException(@RequestParam(name = "message", required = false) String message) throws Exception {
        message = StringUtils.isNotEmpty(message) ? message : "Exception was thrown";
        throw new Exception(message);
    }

    @GetMapping("/runtime-exception")
    public void throwRuntimeException(@RequestParam(name = "message", required = false) String message) {
        message = StringUtils.isNotEmpty(message) ? message : "RuntimeException was thrown";
        throw new RuntimeException(message);
    }
}