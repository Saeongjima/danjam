package site.danjam.mate.user_service.domain.myProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.domain.myProfile.domain.MyProfile;

@Repository
public interface MyProfileJpaRepository extends JpaRepository<MyProfile, Long> {
}
