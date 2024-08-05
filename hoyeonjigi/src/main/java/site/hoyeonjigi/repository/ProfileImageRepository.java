package site.hoyeonjigi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.hoyeonjigi.entity.ProfileImage;

import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    @Query("select pi from ProfileImage  pi order by pi.profileImageCategory")
    List<ProfileImage> findProfileImageByAll();
}
