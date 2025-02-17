package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        User user = new User(0L, "onder@upce.cz", "Pass123");
        User user2 = new User(1L, "tomas@upce.cz", "ABC123");
        users.put(user.getId(), user);
        users.put(user2.getId(), user2);
    }

    public String findUser() {
        log.info("User requested: {}",users.get(0L).toString());
        return users.get(0L).toString();
    }

    public Collection<User> findUsers() {
        return users.values();
    }

    public User findUserById(Long id) {
        return users.get(id);
    }
}
