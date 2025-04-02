package site.danjam.mate.user_service.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.common.utils.BaseTimeEntity;
import site.danjam.mate.user_service.domain.school.domain.School;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Integer gender;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "major_id")
    private Long majorId;

    @Column(nullable = false)
    private Integer entryYear;

    @Column(columnDefinition = "TEXT")
    String profileImgUrl;

    @Column
    String mbti;

    @Column(nullable = false)
    private String birth;

    @Column
    String greeting;

    @Builder
    public User(String nickname, Integer gender, String birth, Integer entryYear, Long majorId) {
        this.birth = birth;
        this.entryYear = entryYear;
        this.nickname = nickname;
        this.gender = gender;
        this.majorId = majorId;
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

    public void updateSchoolInfo(Integer entryYear, Long schoolId, Long majorId) {
        this.entryYear = entryYear;
        this.schoolId = schoolId;
        this.majorId = majorId;
    }

    public void updateSchool(School school) {
        this.schoolId = school.getId();
    }
}

