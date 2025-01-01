package site.danjam.mate.user_service.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.user_service.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String birth;
    @Column(nullable = false)
    private Integer entryYear;
    @Column(nullable = false)
    private String major;
    @Column(columnDefinition = "TEXT")
    private String profileImgUrl;
    private String mbti;
    private String greeting;

    @Builder
    public MyProfile(Long id, String birth, Integer entryYear, String major, String profileImgUrl) {
        this.id = id;
        this.birth = birth;
        this.entryYear = entryYear;
        this.major = major;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateMbti(String mbti) {
        this.mbti = mbti;
    }

    public void updateGreeting(String greeting) {
        this.greeting = greeting;
    }
}

