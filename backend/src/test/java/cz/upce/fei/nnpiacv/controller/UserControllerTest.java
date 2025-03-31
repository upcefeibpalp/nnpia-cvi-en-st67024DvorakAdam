package cz.upce.fei.nnpiacv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.exceptions.*;
import cz.upce.fei.nnpiacv.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

//    @Test
//    void findUser() throws Exception {
//        // Nastavení mocku pro UserService
//        Long userId = 1L;
//        User mockUser = new User(userId, "test@example.com", "password");
//        when(userService.findUser(userId)).thenReturn(mockUser);
//
//        // Volání GET endpointu a ověření správnosti odpovědi
//        mockMvc.perform(get("/api/v1/users/{id}", userId))
//                .andExpect(status().isOk()) // Ověření, že status kód je 200 OK
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Ověření, že odpověď je JSON
//                .andExpect(jsonPath("$.id").value(userId)) // Ověření, že ID v odpovědi je správné
//                .andExpect(jsonPath("$.email").value(mockUser.getEmail())) // Ověření, že email v odpovědi je správný
//                .andExpect(jsonPath("$.password").value(mockUser.getPassword())); // Ověření, že password je správný
//    }

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


    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        // Arrange - připravíme mock uživatele a jeho DTO
        UserRequestDto userRequestDto = new UserRequestDto("test@example.com", "password123");

        // Create a mock User entity
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password123");

        // Mock the userService to return the mock User entity
        when(userService.createUser(any(User.class))).thenReturn(mockUser);

        // Convert userRequestDto to JSON
        String userJson = objectMapper.writeValueAsString(userRequestDto);

        // Act - zavoláme POST požadavek na /api/v1/users
        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated()) // Očekáváme status 201 Created
                .andExpect(jsonPath("$.id").value(1)) // Očekáváme, že id bude 1
                .andExpect(jsonPath("$.email").value(mockUser.getEmail())) // Očekáváme správný email
                .andExpect(jsonPath("$.password").value(mockUser.getPassword())); // Očekáváme správné heslo
    }


    @Test
    void createUserConflict() throws Exception {
        // Arrange - připravíme mock uživatele a jeho DTO
        UserRequestDto userRequestDto = new UserRequestDto("test@example.com", "password123");

        // Mock the userService to throw UserAlreadyExistsException when the email already exists
        when(userService.createUser(any(User.class))).thenThrow(new UserAlreadyExistsException(userRequestDto.getEmail()));

        // Convert userRequestDto to JSON
        String userJson = objectMapper.writeValueAsString(userRequestDto);

        // Act - zavoláme POST požadavek na /api/v1/users
        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isConflict()) // Očekáváme status 409 Conflict
                .andExpect(content().string("User with email "+ userRequestDto.getEmail()
                        +" already exists.")); // Očekáváme chybovou zprávu
    }


}
