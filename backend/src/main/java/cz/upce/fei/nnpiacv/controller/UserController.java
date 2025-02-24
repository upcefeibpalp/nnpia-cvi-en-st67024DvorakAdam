package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;


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
        return userService.findUser(id);
    }

    /*
    //Původní endpoint users
    @GetMapping("/users")
    public String findusers(){
        return userService.findUsers().toString();
    }
     */

    // Přepsaný endpoint pro vrácení všech uživatelů
    @GetMapping("/users")
    public Collection<? extends Object> findUsers(@RequestParam(required = false) String email) {
        if(email != null) {
            return userService.findUsers();
        }else{
            Optional<User> user = userService.findbyEmail(email);
            if(user == null) {
                return Collections.emptyList();
            }else {
                return Collections.singletonList(user);
            }
        }
    }
}
