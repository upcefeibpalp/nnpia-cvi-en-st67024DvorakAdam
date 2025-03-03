package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import cz.upce.fei.nnpiacv.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
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
    @GetMapping
    public User findUser(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto user) {
        log.info("request for creating user obtained {}", user);

        User createdUser = userService.createUser(new User(user.getEmail(), user.getPassword()));
        /*return ResponseEntity.status(201).body(
                new UserResponseDto(createdUser.getId(), createdUser.getEmail(),
                        createdUser.getPassword())
        );

         */
        return ResponseEntity.status(201).body(
                UserResponseDto.builder().id(createdUser.getId())
                        .password(createdUser.getPassword())
                        .email(createdUser.getEmail())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("request for deleting user with id {} obtained", id);
        userService.deleteUser(id);
        return null;
    }

    /*
    //Původní endpoint users
    @GetMapping("/users")
    public String findusers(){
        return userService.findUsers().toString();
    }
     */
/*
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

 */
}
