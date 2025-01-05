package site.danjam.mate.user_service.domain.myProfile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.user_service.domain.user.domain.User;
import site.danjam.mate.user_service.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class MyProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String birth;
    @Column(nullable = false)
    private Integer entryYear;
    @Column(nullable = false)
    private String major;
    @Column(columnDefinition = "TEXT")
    String profileImgUrl;
    String mbti;
    String greeting;

    @OneToOne(mappedBy = "myProfile", fetch = FetchType.LAZY)
    private User user;

    @Builder
    public MyProfile(String birth, Integer entryYear, String major, User user) {
        this.birth = birth;
        this.entryYear = entryYear;
        this.major = major;
        this.user = user;
    }

    public void updateMbti(String mbti) {
        this.mbti = mbti;
    }

    public void updateGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void updateProfileImg(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }
}

