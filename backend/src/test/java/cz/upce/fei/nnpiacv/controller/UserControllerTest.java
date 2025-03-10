package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.exceptions.UserNotFoundException;
import cz.upce.fei.nnpiacv.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void findUser() throws Exception {
        // Nastavení mocku pro UserService
        Long userId = 1L;
        User mockUser = new User(userId, "test@example.com", "password");
        when(userService.findUser(userId)).thenReturn(mockUser);

        // Volání GET endpointu a ověření správnosti odpovědi
        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk()) // Ověření, že status kód je 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Ověření, že odpověď je JSON
                .andExpect(jsonPath("$.id").value(userId)) // Ověření, že ID v odpovědi je správné
                .andExpect(jsonPath("$.email").value(mockUser.getEmail())) // Ověření, že email v odpovědi je správný
                .andExpect(jsonPath("$.password").value(mockUser.getPassword())); // Ověření, že password je správný
    }

    @Test
    void findUser_NotFound() throws Exception {
        // Nastavení mocku tak, aby při hledání uživatele s id 99 vyhodil UserNotFoundException
        Long nonExistentUserId = 99L;
        when(userService.findUser(nonExistentUserId)).thenThrow(new UserNotFoundException("User with id " + nonExistentUserId + " not found"));

        // Volání GET endpointu a ověření, že status je 404 Not Found
        mockMvc.perform(get("/api/v1/users/{id}", nonExistentUserId))
                .andExpect(status().isNotFound()) // Ověření, že status kód je 404
                .andExpect(content().string("User with id 99 not found")); // Ověření, že zpráva odpovídá výjimce
    }


    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

}
