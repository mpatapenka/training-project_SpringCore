package by.epam.maksim.movietheater.web;

import by.epam.maksim.movietheater.entity.Event;
import by.epam.maksim.movietheater.entity.User;
import by.epam.maksim.movietheater.service.EventService;
import by.epam.maksim.movietheater.service.UserService;
import by.epam.maksim.movietheater.util.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@RequestMapping("/batch-upload")
@AllArgsConstructor
public class BatchUploadController {
    private final EventService eventService;
    private final UserService userService;

    @PostMapping("/events")
    public String uploadEvents(@RequestParam MultipartFile events) {
        try {
            List<Event> eventList = JsonParser.convertJsonStringToObject(
                    new BufferedReader(new InputStreamReader(events.getInputStream())),
                    new TypeToken<List<Event>>() { }.getType());
            eventList.forEach(eventService::save);
        } catch (IOException e) {
            throw new RuntimeException("Events can't be read.", e);
        }
        return "redirect:/";
    }

    @PostMapping("/users")
    public String uploadUsers(@RequestParam MultipartFile users) {
        try {
            List<User> userList = JsonParser.convertJsonStringToObject(
                    new BufferedReader(new InputStreamReader(users.getInputStream())),
                    new TypeToken<List<User>>() { }.getType());
            userList.forEach(userService::save);
        } catch (IOException e) {
            throw new RuntimeException("Users can't be read.", e);
        }
        return "redirect:/";
    }
}