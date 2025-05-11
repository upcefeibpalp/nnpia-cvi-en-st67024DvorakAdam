package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.exceptions.*;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        log.info("User : {}", user.get());
        return user.orElse(null);
    }

    public Collection<User> findUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        // Check if user with this email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public User updateUser(Long id, User updatedUserData) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return null;
        }

        User existingUser = existingUserOpt.get();

        if (updatedUserData.getEmail() != null) {
            existingUser.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getPassword() != null) {
            // Encode the new password before saving
            existingUser.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User activateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(true);
            return userRepository.save(user);
        }
        return null;
    }

    public User deactivateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(false);
            return userRepository.save(user);
        }
        return null;
    }

    // Additional method needed for authentication
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
