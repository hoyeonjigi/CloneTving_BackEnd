package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hoyeonjigi.entity.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
