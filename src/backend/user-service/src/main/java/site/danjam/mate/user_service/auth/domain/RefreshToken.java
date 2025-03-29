package site.danjam.mate.user_service.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String refresh;

    private String expiration;

    public RefreshToken(Long userId, String refreshTokenValue, Long expiredMs){
        this.userId = userId;
        this.refresh = refreshTokenValue;
        this.expiration = (new Date(System.currentTimeMillis() + expiredMs)).toString();
    }
}
