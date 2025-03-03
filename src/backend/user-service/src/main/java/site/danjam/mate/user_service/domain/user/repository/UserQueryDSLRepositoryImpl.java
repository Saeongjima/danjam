package site.danjam.mate.user_service.domain.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.myProfile.domain.QMyProfile;
import site.danjam.mate.user_service.domain.school.domain.QSchool;
import site.danjam.mate.user_service.domain.user.domain.QUser;
import site.danjam.mate.user_service.domain.user.dto.UserFilterInputDTO;
import site.danjam.mate.user_service.domain.user.dto.UserFilterOutputDTO;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Repository
@RequiredArgsConstructor
public class UserQueryDSLRepositoryImpl implements UserQueryDSLRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    @MethodDescription(description = "필터링 진행 메서드")
    public List<UserFilterOutputDTO> filterUsers(UserFilterInputDTO filter) {
        QUser user = QUser.user;
        QMyProfile profile = QMyProfile.myProfile;
        QSchool school = QSchool.school;

        // 요청자용 별칭 생성
        QUser requester = new QUser("requestUser");

        return queryFactory.select(Projections.constructor(UserFilterOutputDTO.class,
                    user.id,
                    user.nickname,
                    profile.mbti,
                    profile.major,
                    profile.entryYear.stringValue(),
                    profile.birth,
                    profile.profileImgUrl,
                    user.gender
                    ))
                    .from(user)
                    .join(user.myProfile, profile)
                    .join(user.school, school)
                    .where(
                            //1. 요청자와 학교가 동일해야함
                            user.school.id.eq(
                                    JPAExpressions.select(requester.school.id)
                                            .from(requester)
                                            .where(requester.id.eq(filter.getRequesterId()))
                            ),

                            //2. 후보 userIds 필터링
                            filter.getUserIds() != null && !filter.getUserIds().isEmpty()
                                    ? user.id.in(filter.getUserIds())
                                    : null,

                            //3, mbti 필터링
                            filter.getMbti() != null && !filter.getMbti().isEmpty()
                                    ? profile.mbti.likeIgnoreCase(filter.getMbti())
                                    : null,

                            //4. 성별 필터
                            filter.getGender() != null
                                    ? user.gender.eq(filter.getGender())
                                    : null,

                            //5. 생년월일 필터: "YYYY-MM-DD"에서 앞 4자리(연도)를 기준으로 비교
                            filter.getMinBirthYear() != null && !filter.getMinBirthYear().isEmpty()
                                    ? profile.birth.substring(0,4).goe(filter.getMinBirthYear())
                                    : null,


                            filter.getMaxBirthYear() != null && !filter.getMaxBirthYear().isEmpty()
                                    ? profile.birth.substring(0, 4).loe(filter.getMaxBirthYear())
                                    : null,

                            //6. 입학년도 필터
                            filter.getMinEntryYear() != null
                                    ? profile.entryYear.goe(Integer.parseInt(filter.getMinEntryYear()))
                                    : null,
                            filter.getMaxEntryYear() != null
                                    ? profile.entryYear.loe(Integer.parseInt(filter.getMaxEntryYear()))
                                    : null

    //  todo - colleges필터도 추가되어야함. 아직 school관련 colleges데이터가 없음. 논의 해봐야함.
    //                        // 7. colleges 필터
                    ).fetch();
    }
}
