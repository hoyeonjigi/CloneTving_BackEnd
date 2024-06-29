package site.hoyeonjigi.controller;

import com.google.gson.Gson;
import io.lettuce.core.GeoArgs;
import jakarta.validation.Valid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;
import site.hoyeonjigi.dto.evaluation.*;
import site.hoyeonjigi.service.EvaluationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = {"local"})
@WebMvcTest(EvaluationController.class)
@MockBean(JpaMetamodelMappingContext.class)
class EvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @MockBean
    private EvaluationService evaluationService;

    @Test
    @DisplayName("POST 해당 컨텐츠의 모든 리뷰 조회")
    @WithMockUser(username = "검증된 사용자")
    void evaluationsByContentId() throws Exception {
        //given
        List<ReviewRetrieveDto> content = new ArrayList<>();
        Pageable pageable = (Pageable) PageRequest.of(0, 20);
        Page<ReviewRetrieveDto> reviewRetrieveDtos = new PageImpl<>(content, pageable, 20L);
        EvaluationSearchCondition evaluationSearchCondition = new EvaluationSearchCondition(1L, EvaluationSortType.DEFAULT);
        given(evaluationService.getAllEvaluationsByContentId(any(Long.class), any(EvaluationSortType.class), any(Pageable.class)))
                .willReturn(reviewRetrieveDtos);
        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/evaluation/retrieve")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(evaluationSearchCondition))
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("GET 해당 컨텐츠의 모든 리뷰 조회")
    @WithMockUser(username = "검증된 사용자")
    void evaluationByProfileId() throws Exception {
        //given
        ReviewRetrieveDto reviewRetrieveDto = new ReviewRetrieveDto(1L, "testProfile", "testReview", LocalDateTime.now(), 0L, 0L, 3.0);
        given(evaluationService.getEvaluationByProfileId(any(Long.class), any(Long.class)))
                .willReturn(reviewRetrieveDto);
        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/evaluation/retrieve/by-profile")
                                .with(csrf())
                                .param("contentId", "1")
                                .param("profileId", "1")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("POST 리뷰 등록")
    @WithMockUser(username = "검증된 사용자")
    void registerEvaluation() throws Exception {
        //given
        EvaluationRegisterDto evaluationRegisterDto = new EvaluationRegisterDto(1L, 1L, "testReview", 3.0);
        given(evaluationService.register(any(EvaluationRegisterDto.class)))
                .willReturn(1L);
        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/evaluation/register")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(evaluationRegisterDto))
                );

        // then
        actions.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Patch 리뷰 수정")
    @WithMockUser(username = "검증된 사용자")
    void editEvaluation() throws Exception {
        //given
        ReviewEditDto reviewEditDto = new ReviewEditDto(1L, "editReview", 4.0);
        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/evaluation/edit")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(reviewEditDto))
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("Delete 평가 삭제")
    @WithMockUser(username = "검증된 사용자")
    void deleteEvaluation() throws Exception {
        //given
        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/evaluation/delete")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("evaluationId", "1")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("GET 평균 평점 조회")
    @WithMockUser(username = "검증된 사용자")
    void averageRatingScore() throws Exception {
        //given
        given(evaluationService.getAverageRatingScore(any(Long.class)))
                .willReturn(3.0);
        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/evaluation/average-rating")
                                .with(csrf())
                                .param("contentId", "1")
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @DisplayName("PATCH 좋아요, 싫어요 업데이트")
    @WithMockUser(username = "검증된 사용자")
    void updatePopularity() throws Exception {
        //given
        EvaluationPopularitySetDto evaluationPopularitySetDto = new EvaluationPopularitySetDto(1L, EvaluationCountType.GOOD, true);
        // when
        ResultActions actions =
                mockMvc.perform(
                        patch("/evaluation/popularity")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(evaluationPopularitySetDto))
                );

        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}