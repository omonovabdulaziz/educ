package it.live.educationtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbsLongEntity implements UserDetails {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    private Group group;
    @ManyToOne
    private EducationLocation educationLocation;
    @ManyToOne
    private Status status;
    @Column(nullable = false)
    private Integer ball;
    private Integer phoneCode;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;
    @JsonIgnore
    private Boolean enabled;
    @JsonIgnore
    private Boolean isAccountNonExpired;
    @JsonIgnore
    private Boolean isAccountNonLocked;
    @JsonIgnore
    private Boolean isCredentialsNonExpired;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(systemRoleName.name());
        return Collections.singleton(simpleGrantedAuthority);
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }
}
