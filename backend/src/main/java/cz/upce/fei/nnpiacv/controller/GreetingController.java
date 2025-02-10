package cz.upce.fei.nnpiacv.controller;


import cz.upce.fei.nnpiacv.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private GreetingService greetingService = new GreetingService();

    @GetMapping("/")
    public String sayGreeting() {
        return greetingService.sayGreeting();
    }
}
