package site.danjam.mate.mate_service.mate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import site.danjam.mate.mate_service.mate.enums.MateType;
import site.danjam.mate.mate_service.global.common.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class MateProfile extends BaseTimeEntity {

    @Column(nullable = false, unique = true)
    protected Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected MateType mateType;

}
