package site.hoyeonjigi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.dto.ProfileImageDto;
import site.hoyeonjigi.entity.ProfileImage;
import site.hoyeonjigi.repository.ProfileImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileImageService {
    private final ProfileImageRepository profileImageRepository;

    public List<ProfileImageDto> profileImages(){
        List<ProfileImage> profileImages = profileImageRepository.findProfileImageByAll();
        return profileImages.stream().map(ProfileImageDto::new).toList();
    }
}
