package site.danjam.mate.user_service.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.danjam.mate.user_service.global.common.entity.BaseTimeEntity;


@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deletedAt is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private Integer gender;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT")
    private String authImgUrl;
    private LocalDateTime deletedAt;

    @Builder
    public User(String username, Integer gender, String password,
                String nickname, String email, String authImgUrl, LocalDateTime deletedAt) {
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.authImgUrl = authImgUrl;
        this.deletedAt = deletedAt;
    }
}
