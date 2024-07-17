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
import site.hoyeonjigi.dto.GenreDto;
import site.hoyeonjigi.service.GenreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"local"})
@WebMvcTest(GenreController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class GenreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GenreService genreService;

    @Autowired
    Gson gson;

    @Test
    @DisplayName("장르 조회 테스트")
    @WithMockUser(username = "모든 사용자")
    void getGenreInfoTest() throws Exception {
        GenreDto genreDto = new GenreDto(1L, "스릴러");

        given(genreService.genreInfo(1L)).willReturn(genreDto);

        ResultActions actions =
                mockMvc.perform(get("/genre/{genreId}",1L)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}
