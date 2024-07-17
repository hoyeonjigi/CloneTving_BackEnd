package site.hoyeonjigi.controller;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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
import site.hoyeonjigi.dto.profile.ProfileDto;
import site.hoyeonjigi.dto.profile.ProfileEditDto;
import site.hoyeonjigi.dto.profile.ProfileRegisterDto;
import site.hoyeonjigi.service.ProfileService;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(value = {"local"})
@WebMvcTest(ProfileController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Autowired
    Gson gson;

    @Test
    @DisplayName("프로필 등록 테스트")
    @WithMockUser(username = "test")
    void registerTest() throws Exception {
        ProfileDto profileDto = new ProfileDto(1L, "Test", "TestUrl", "TestImgName", false);
        ProfileRegisterDto profileRegisterDto = new ProfileRegisterDto("TestProfile", 1L, true);
        ResultActions actions =
                mockMvc.perform(
                        post("/profiles/register")
                            .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(profileRegisterDto))
                );
        actions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("프로필 리스트 조회 테스트")
    @WithMockUser(username = "test")
    void profilesByMemberTest() throws Exception {
        List<ProfileDto> profileDtoList = new ArrayList<>();
        profileDtoList.add(new ProfileDto(1L, "test", "testUrl", "testImgName", true));
        given(profileService.profiles("test")).willReturn(profileDtoList);
        ResultActions actions =
                mockMvc.perform(
                        get("/profiles")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        actions.andExpect(status().is2xxSuccessful()).andDo(print());
    }
}
