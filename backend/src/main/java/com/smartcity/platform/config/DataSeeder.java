package com.smartcity.platform.config;

import com.smartcity.platform.user.Role;
import com.smartcity.platform.user.User;
import com.smartcity.platform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@smartcity.local");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRoles(Set.of(Role.ADMIN, Role.TRAFFIC_OPERATOR, Role.EMERGENCY_STAFF));
        userRepository.save(admin);

        User operator = new User();
        operator.setUsername("operator");
        operator.setEmail("operator@smartcity.local");
        operator.setPassword(passwordEncoder.encode("operator123"));
        operator.setFirstName("Traffic");
        operator.setLastName("Operator");
        operator.setRoles(Set.of(Role.TRAFFIC_OPERATOR));
        userRepository.save(operator);

        User emergency = new User();
        emergency.setUsername("emergency");
        emergency.setEmail("emergency@smartcity.local");
        emergency.setPassword(passwordEncoder.encode("emergency123"));
        emergency.setFirstName("Emergency");
        emergency.setLastName("Staff");
        emergency.setRoles(Set.of(Role.EMERGENCY_STAFF));
        userRepository.save(emergency);

        User citizen = new User();
        citizen.setUsername("citizen");
        citizen.setEmail("citizen@smartcity.local");
        citizen.setPassword(passwordEncoder.encode("citizen123"));
        citizen.setFirstName("City");
        citizen.setLastName("Citizen");
        citizen.setRoles(Set.of(Role.CITIZEN));
        userRepository.save(citizen);
    }
}
