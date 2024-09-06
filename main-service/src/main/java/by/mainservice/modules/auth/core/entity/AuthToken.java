package by.mainservice.modules.auth.core.entity;

import by.mainservice.common.core.entity.BaseUpdatingEntity;
import by.mainservice.modules.user.core.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "complitech", name = "auth_token")
public class AuthToken extends BaseUpdatingEntity {

    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    @Column(name = "is_revoked", nullable = false)
    private Boolean isRevoked;

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token_expiry", nullable = false)
    private OffsetDateTime refreshTokenExpiry;
}
