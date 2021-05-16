package api.controllers;

import bot.Bot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @PostMapping("/message")
    public String test(@RequestParam String server, @RequestParam String channel, @RequestParam String message) {
        Bot bot = Bot.getInstance();
        bot.sendMessage(server, channel, message);
        return "success";
    }

    @GetMapping("/")
    public String index() {
        return "success";
    }
}
