package site.hoyeonjigi.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import site.hoyeonjigi.dto.profile.ProfileImageDto;
import site.hoyeonjigi.service.ProfileImageService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"local"})
@WebMvcTest(ProfileImageController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProfileImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @MockBean
    private ProfileImageService profileImageService;

    @Test
    @DisplayName("프로필 이미지 리스트 조회")
    @WithMockUser(username = "검증된 사용자")
    void profileImageListTest() throws Exception {
        List<ProfileImageDto> profileImageDtos = new ArrayList<>();
        profileImageDtos.add(new ProfileImageDto(1L,"testUrl","testName","testCategory"));
        given(profileImageService.profileImages()).willReturn(profileImageDtos);
        ResultActions actions =
                mockMvc.perform(
                        get("/profileImages")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
