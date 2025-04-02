package site.danjam.mate.user_service.domain.certification.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Filter;
import site.danjam.mate.common.enums.Role;
import site.danjam.mate.common.utils.BaseTimeEntity;
import site.danjam.mate.user_service.domain.user.domain.User;

@Entity
@Getter
@FilterDef(name = "deletedCertificationFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCertificationFilter", condition = "(:isDeleted = true OR is_deleted = false)")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "certification")
public class Certification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String authImgUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Certification(String name, String username, String password,
            String email, String authImgUrl, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authImgUrl = authImgUrl;
        this.role = role;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAuthImgUrl(String authImgUrl) {
        this.authImgUrl = authImgUrl;
    }

}
