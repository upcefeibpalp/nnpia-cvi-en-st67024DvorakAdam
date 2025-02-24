package cz.upce.fei.nnpiacv.component;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner { //to co bude v run se spustí při spuštění backendu
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;


    @Override
    public void run(String... args) {
        User user = new User(0L,"admin@upce.cz", "ABC123");

        log.debug("Admin user created" + user);
        if(!userRepository.existsById(user.getId())) {
            userRepository.save(user);
        }
    }
}
