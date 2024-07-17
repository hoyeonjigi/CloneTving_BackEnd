package site.hoyeonjigi.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import site.hoyeonjigi.dto.ContentDto;
import site.hoyeonjigi.service.ContentService;
import site.hoyeonjigi.service.ProfileService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentController.class)
@ActiveProfiles(value = {"local"})
@MockBean(JpaMetamodelMappingContext.class)
public class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @MockBean
    private ContentService contentService;

    @Test
    @DisplayName("컨텐츠 리스트 조회 테스트")
    @WithMockUser(username = "test")
    void contentsByOptionsTest() throws Exception {
        ContentDto contentDto = new ContentDto();
        contentDto.setContentId(1L);
        contentDto.setGrade(true);
        contentDto.setPoster("test");
        contentDto.setOverview("good");
        contentDto.setTitle("test");
        List<ContentDto> contentDtoList = new ArrayList<>();
        contentDtoList.add(contentDto);
        Page<ContentDto> contentDtos = new PageImpl<>(contentDtoList);
        given(contentService.findContentsByOptions("drama","latest","","쿵푸팬더",0))
                .willReturn((contentDtos));

        ResultActions actions =
                mockMvc.perform(
                        get("/contents")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        actions.andExpect(status().is2xxSuccessful());
    }


    @Test
    @DisplayName("컨텐츠 리스트 조회 테스트")
    @WithMockUser(username = "test")
    void contentsByIdTest() throws Exception {
        ContentDto contentDto = new ContentDto();
        contentDto.setContentId(1L);
        contentDto.setGrade(true);
        contentDto.setPoster("test");
        contentDto.setOverview("good");
        contentDto.setTitle("test");
        given(contentService.findContentById(1L))
                .willReturn((contentDto));

        ResultActions actions =
                mockMvc.perform(
                        get("/contents/{contentId}",1L)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        actions.andExpect(status().is2xxSuccessful());
    }
}
