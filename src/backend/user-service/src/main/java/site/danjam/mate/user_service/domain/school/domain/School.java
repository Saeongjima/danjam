package site.danjam.mate.user_service.domain.school.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.danjam.mate.common.utils.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "school")
public class School extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String korName;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<College> collegeList;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Dormitory> dormitoryList;

    @Builder
    public School(String name, String korName) {
        this.name = name;
        this.korName = korName;
    }
}
