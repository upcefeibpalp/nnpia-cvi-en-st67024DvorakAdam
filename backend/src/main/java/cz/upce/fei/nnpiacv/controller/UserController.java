package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    //prvni verze
    @GetMapping("/user")
    public String findUser() {
        return userService.findUser();
    }
    */

    /*
    //druha verze
    @GetMapping("/user")
    public User findUser(@RequestParam("id") Long id) {
        return userService.findUserById(id);
    }
    */

    //treti verze
    @GetMapping("/user/{id}")
    public User findUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    /*
    //Původní endpoint users
    @GetMapping("/users")
    public String findusers(){
        return userService.findUsers().toString();
    }
     */

    // Přepsaný endpoint pro vrácení všech uživatelů
    @GetMapping("/user")
    public String findUsers() {
        return userService.findUsers().toString();
    }
}
