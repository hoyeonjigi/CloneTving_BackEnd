package site.hoyeonjigi.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.entity.ProfileImage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ProfileImageRepositoryTest {

    @Autowired
    ProfileImageRepository profileImageRepository;

    ProfileImage profileImage;

    @BeforeEach
    void initData(){
        profileImage = new ProfileImage("testName1","TestUrl1","TestCategory1");
        profileImageRepository.save(profileImage);

        profileImage = new ProfileImage("testName2","TestUrl2","TestCategory2");
        profileImageRepository.save(profileImage);
    }
    @Test
    @DisplayName("프로필 이미지 리스트 조회")
    void findProfileImageByAllTest(){
        List<ProfileImage> profileImageByAll = profileImageRepository.findProfileImageByAll();
        assertThat(profileImageByAll.size()).isEqualTo(2);
    }
}
