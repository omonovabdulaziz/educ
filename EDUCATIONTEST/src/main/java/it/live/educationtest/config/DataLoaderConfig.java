package it.live.educationtest.config;

import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.entity.Group;
import it.live.educationtest.entity.Status;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.repository.GroupRepository;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataLoaderConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;
    private final EducationLocationRepository educationLocationRepository;
    private final GroupRepository groupRepository;

    @Value("${spring.sql.init.mode}")
    private String sqlInitMode;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(sqlInitMode, "always")) {
            userRepository.save(User.builder().name("SuperAdmin").surname("SuperAdmin").ball(0).systemRoleName(SystemRoleName.ROLE_SUPER_ADMIN).enabled(true).isAccountNonExpired(true).isAccountNonLocked(true).isCredentialsNonExpired(true).phoneNumber("+0").password(passwordEncoder.encode("superadmin")).build());
            System.out.println("_______Super Admin saqlandi______");
            EducationLocation educationLoc = educationLocationRepository.save(EducationLocation.builder().name("IT LIVE").IsValid(true).build());
            System.out.println("_______Education saqlandi_____");
            userRepository.save(User.builder().name("Admin").surname("Admin").ball(0).educationLocation(educationLoc).systemRoleName(SystemRoleName.ROLE_ADMIN).enabled(true).isAccountNonExpired(true).isAccountNonLocked(true).isCredentialsNonExpired(true).phoneNumber("+00").password(passwordEncoder.encode("admin")).build());
            System.out.println("_______Admin saqlandi______");
            Status status = statusRepository.save(Status.builder().isValid(true).educationLocation(educationLoc).name("FOUNDATION").build());
            System.out.println("_________Status saqlandi______");
            User teacher = userRepository.save(User.builder().name("Teacher").educationLocation(educationLoc).surname("Teacher").status(status).ball(0).systemRoleName(SystemRoleName.ROLE_TEACHER).enabled(true).isAccountNonExpired(true).isAccountNonLocked(true).isCredentialsNonExpired(true).phoneNumber("+000").password(passwordEncoder.encode("teacher")).build());
            System.out.println("_______Teacher saqlandi______");
            groupRepository.save(Group.builder().teacher(teacher).isValid(true).status(status).name("FOUNDATION GRUPPA 1").build());
            System.out.println("_________Gruppa saqlandi______");
        }
    }
}