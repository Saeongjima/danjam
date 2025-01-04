package site.danjam.mate.user_service.domain.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;
import site.danjam.mate.user_service.domain.school.domain.School;
import site.danjam.mate.user_service.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deletedAt is null")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "my_profile_id")
    private MyProfile myProfile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "school_id")
    private School school;

    @Builder
    public User(String username, Integer gender, String password,
                String nickname, String email, String authImgUrl) {
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.authImgUrl = authImgUrl;
    }

    public void createDefaultMyProfile(MyProfile myProfile) {
        this.myProfile = myProfile;
    }

    public void createDefaultSchool(School school) {
        this.school = school;
    }

    public void deleteUser() {
        this.deletedAt = LocalDateTime.now();
    }
}
