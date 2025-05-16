package com.venkat.expense.tracker.user;

import com.venkat.expense.tracker.entities.User;
import com.venkat.expense.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with IDP");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email: {}", userEmail);
            Optional<User> isUserExists = userRepository.findByEmail(userEmail);
            if (isUserExists.isEmpty()) {
                User user = userMapper.fromTokenAttribute(token.getClaims());
                userRepository.saveAndFlush(user);
                log.info("Added new user");
            } else
                log.info("User already exists: {}", isUserExists.get().getId());

        });
    }

    public Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> attributes = token.getClaims();
        if (attributes.containsKey("email"))
            return Optional.of(attributes.get("email").toString());

        return Optional.empty();
    }
}
