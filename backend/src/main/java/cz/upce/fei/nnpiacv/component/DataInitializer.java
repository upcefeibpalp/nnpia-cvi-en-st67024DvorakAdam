package cz.upce.fei.nnpiacv.component;

import cz.upce.fei.nnpiacv.domain.Profile;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.ProfileRepository;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    public void run(String... args) {
        // Vytvoření nového uživatele
        User user = new User(0L, "admin@upce.cz", "ABC123");

        log.debug("Admin user created: " + user);

        // Kontrola, zda uživatel existuje, pokud ne, vytvoříme ho
        if (!userRepository.existsById(user.getId())) {
            userRepository.save(user);
            log.debug("User saved to database: " + user);
        }

        // Vytvoření profilu pro tohoto uživatele
        Profile profile = new Profile();
        profile.setUser(user);  // Propojení uživatele s profilem
        profile.setFirstName("Admin");
        profile.setLastName("User");
        profile.setBio("This is the admin user profile");

        // Uložení profilu do databáze
        profileRepository.save(profile);

        log.debug("Profile for user created: " + profile);
    }
}
