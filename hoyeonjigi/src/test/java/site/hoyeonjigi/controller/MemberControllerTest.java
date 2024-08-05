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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import site.hoyeonjigi.dto.JsonWebTokenDto;
import site.hoyeonjigi.dto.member.MemberLoginDto;
import site.hoyeonjigi.dto.member.MemberRegisterDto;
import site.hoyeonjigi.entity.member.Member;
import site.hoyeonjigi.service.MemberService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ActiveProfiles(value = {"local"})
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @MockBean
    private MemberService memberService;
    
    @Test
    @DisplayName("POST 회원가입")
    @WithMockUser(username = "모든 사용자")
    void registerMember() throws Exception {
        //given
        MemberRegisterDto testRegisterDto = new MemberRegisterDto("test", "123", "test@gmail.com", true, true, true, true);
        Member testMember = Member.builder()
                .loginId("test")
                .password("123")
                .build();

        given(memberService.register(testRegisterDto))
                .willReturn(testMember.getId());
        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/member/register")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(testRegisterDto))
                );

        // then
        actions.andExpect(status().isCreated()).andDo(print());
    }

    @Test
    @DisplayName("POST 로그인")
    @WithMockUser(username = "모든 사용자")
    void login() throws Exception {
        //given
        MemberLoginDto testMemberLoginDto = new MemberLoginDto("test", "123");
        JsonWebTokenDto jsonWebTokenDto = new JsonWebTokenDto("testAccessToken", "testRefreshToken");
        given(memberService.login(any(MemberLoginDto.class)))
                .willReturn(jsonWebTokenDto);
        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/member/login")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(testMemberLoginDto))
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").value(jsonWebTokenDto.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(jsonWebTokenDto.getRefreshToken()))
                .andDo(print());
    }

    @Test
    @DisplayName("POST 토큰 재발급")
    @WithMockUser(username = "모든 사용자")
    void reissue() throws Exception {
        //given
        JsonWebTokenDto jsonWebTokenDto = new JsonWebTokenDto("testAccessToken", "testRefreshToken");
        given(memberService.reissue(anyString())).willReturn(jsonWebTokenDto);
        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/member/reissue")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Refresh-Token", "testRefreshToken")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").value(jsonWebTokenDto.getAccessToken()))
                .andExpect(jsonPath("$.refreshToken").value(jsonWebTokenDto.getRefreshToken()))
                .andDo(print());
    }

    @Test
    @DisplayName("GET 아이디 중복 체크")
    @WithMockUser(username = "모든 사용자")
    void duplicationCheck() throws Exception {
        //given
        given(memberService.duplicateCheck(anyString())).willReturn(true);
        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/member/duplication-check")
                                .with(csrf())
                                .param("loginId", "test")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE 아이디 삭제")
    @WithMockUser(username = "검증된 사용자")
    void deleteMember() throws Exception {
        //given
        given(memberService.delete(anyString())).willReturn(1L);
        // when
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.delete("/member/delete")
                                .with(csrf())
                                .param("loginId", "test")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}