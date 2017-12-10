package by.epam.maksim.movietheater.web;

import by.epam.maksim.movietheater.entity.Ticket;
import by.epam.maksim.movietheater.service.BookingService;
import by.epam.maksim.movietheater.service.EventService;
import by.epam.maksim.movietheater.web.view.pdf.TicketsForEventPdfView;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequestMapping("/pdf")
@AllArgsConstructor
public class PdfDataController {

    private final BookingService bookingService;
    private final EventService eventService;

    // Example of request http://localhost:8080/pdf/tickets-for-event?eventName=tempEvent&dateTime=2018-05-03T15:30
    @GetMapping(value = "/tickets-for-event", consumes = "application/pdf")
    public ModelAndView getTicketsByEvent(@RequestParam(name = "eventName", required = false) String eventName,
            @RequestParam(name = "dateTime", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(eventService.getByName(eventName), dateTime);
        return new ModelAndView(new TicketsForEventPdfView(), "tickets", tickets);
    }

}