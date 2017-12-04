package by.epam.maksim.movietheater.web;

import by.epam.maksim.movietheater.entity.Counter;
import by.epam.maksim.movietheater.service.AuditoriumService;
import by.epam.maksim.movietheater.service.CounterService;
import by.epam.maksim.movietheater.service.DiscountService;
import by.epam.maksim.movietheater.service.EventService;
import by.epam.maksim.movietheater.service.UserService;
import by.epam.maksim.movietheater.util.ReflectionUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/retrieve")
@AllArgsConstructor
public class DataRetrieveController {

    private static final List<String> ALL_COUNTER_DOMAINS = Arrays.asList(
            CounterService.GET_BASE_PRICE_DOMAIN,
            CounterService.GET_NAME_DOMAIN,
            CounterService.DISCOUNT_PERCENTAGE_DOMAIN + DiscountService.FIVE_PERCENTAGE_DISCOUNT,
            CounterService.DISCOUNT_PERCENTAGE_DOMAIN + DiscountService.FIFTY_PERCENTAGE_DISCOUNT,
            CounterService.BOOK_TICKET_FOR_EVENT_DOMAIN);

    private final AuditoriumService auditoriumService;
    private final CounterService counterService;
    private final UserService userService;
    private final EventService eventService;

    @ModelAttribute(name = "counterDomains")
    public List<String> retrievePageCounterDomains() {
        return ALL_COUNTER_DOMAINS;
    }

    @GetMapping("/")
    public String getStartPage() {
        return "retrieve";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "retrieve";
    }

    @GetMapping("/events")
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getAll());
        return "retrieve";
    }

    @PostMapping("/auditorium")
    public String getAuditoriumByName(@RequestParam String name, Model model) {
        model.addAttribute("auditorium", auditoriumService.getByName(name));
        return "retrieve";
    }

    @PostMapping("/statistic")
    public String getAuditoriumByName(@RequestParam(required = false) String type, @RequestParam(required = false) String name,
            @RequestParam(required = false) String domain, Model model) {
        Collection<Counter> counters = Collections.emptyList();
        if (StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(domain)) {
            counters = Collections.singletonList(counterService.get(ReflectionUtils.loadClassForName(type), name, domain));
        } else if (StringUtils.isNotEmpty(type)) {
            counters = counterService.getByType(ReflectionUtils.loadClassForName(type));
        } else if (StringUtils.isNotEmpty(name)) {
            counters = counterService.getByName(name);
        } else if (StringUtils.isNotEmpty(domain)) {
            counters = counterService.getByDomain(domain);
        }
        model.addAttribute("counters", counters);
        return "retrieve";
    }

}