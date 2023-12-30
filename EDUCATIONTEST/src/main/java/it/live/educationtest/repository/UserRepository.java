package it.live.educationtest.repository;


import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumberAndPhoneCode(String phoneNumber, Integer phoneCode);

    Optional<User> findByIdAndSystemRoleName(Long id, SystemRoleName systemRoleName);

    List<User> findAllBySystemRoleName(SystemRoleName systemRoleName);

    List<User> findAllByGroupIdAndSystemRoleName(Long group_id, SystemRoleName systemRoleName);

    List<User> findAllByEducationLocationIdAndSystemRoleName(Long educationLocation_id, SystemRoleName systemRoleName);

    List<User> findAllByGroupIdAndSystemRoleNameOrderByBallDesc(Long group_id, SystemRoleName systemRoleName);

    @Query(value = "select count(*) from users where phone_number=:phoneNumber and id != :userid", nativeQuery = true)
    Long phoneNumberCheck(@Param("phoneNumber") String phoneNumber, @Param("userid") Long userid);
}
