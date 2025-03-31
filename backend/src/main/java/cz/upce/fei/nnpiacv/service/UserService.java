package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.exceptions.*;
        import cz.upce.fei.nnpiacv.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        // Ověřujeme, zda uživatel existuje
        if (user.isEmpty()) {
            // Pokud neexistuje, vyhodíme výjimku
            throw new UserNotFoundException("User with id " + id + " not found");
        }

        log.info("User : {}",user.get());
        return user.orElse(null);
    }

    public Collection<User> findUsers() {
        return userRepository.findAll();
    }

    /*
        public Optional<User> findbyEmail(String email) {
            return userRepository.findByEmail(email);
        }
    */
    public User createUser(User user) {
        // Zkontrolujeme, zda uživatel s tímto emailem již existuje
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        return userRepository.save(user);
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Nová metoda pro aktualizaci uživatele
    public User updateUser(Long id, User updatedUserData) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            return null;  // Pokud uživatel neexistuje, vrátíme null
        }

        User existingUser = existingUserOpt.get();

        // Aktualizujeme pouze požadované atributy
        if (updatedUserData.getEmail() != null) {
            existingUser.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getPassword() != null) {
            existingUser.setPassword(updatedUserData.getPassword());
        }

        // Uložíme aktualizovaného uživatele zpět do databáze
        return userRepository.save(existingUser);
    }

    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Metoda pro aktivaci uživatele (stav 'active' na true)
    public User activateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(true);  // Nastavení 'active' na true
            return userRepository.save(user);  // Uložení změny do databáze
        }
        return null;  // Pokud uživatel nebyl nalezen, vrátíme null
    }

    // Metoda pro deaktivaci uživatele (stav 'active' na false)
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(false);  // Nastavení 'active' na false
            return userRepository.save(user);  // Uložení změny do databáze
        }
        return null;  // Pokud uživatel nebyl nalezen, vrátíme null
    }
/*
    public User findUserById(Long id) {
        return users.get(id);
    }*/
}
