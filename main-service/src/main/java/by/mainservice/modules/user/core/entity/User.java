package by.mainservice.modules.user.core.entity;

import by.mainservice.common.core.entity.BaseUpdatingEntity;
import by.mainservice.modules.user.core.entity.converter.GenderTypeByIdConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(schema = "complitech", name = "user")
public class User extends BaseUpdatingEntity implements UserDetails {

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "password", length = 1024)
    private String password;

    @Column(name = "full_name", length = 256)
    private String fullName;

    @Convert(converter = GenderTypeByIdConverter.class)
    @Column(name = "gender_id", nullable = false, columnDefinition = "int2")
    private GenderType genderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 50)
    private UserRole userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(userRole);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
