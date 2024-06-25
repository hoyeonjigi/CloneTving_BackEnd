package site.hoyeonjigi.repository.evaluation;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import site.hoyeonjigi.common.exception.IncorretSortTypeException;
import site.hoyeonjigi.dto.evaluation.*;

import java.util.List;

import static site.hoyeonjigi.entity.QEvaluation.*;
import static site.hoyeonjigi.entity.QProfile.profile;

@Slf4j
public class EvaluationRepositoryCustomImpl implements EvaluationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public EvaluationRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReviewRetrieveDto> findAllEvaluationsByContentId(EvaluationSearchCondition condition, Pageable pageable) {

        QueryResults<ReviewRetrieveDto> results = queryFactory
                .select(new QReviewRetrieveDto(
                        evaluation.id.as("evaluationId"),
                        evaluation.profile.profileName.as("profileName"),
                        evaluation.review,
                        evaluation.ratingTime,
                        evaluation.goodCount,
                        evaluation.badCount,
                        evaluation.rating))
                .from(evaluation)
                .leftJoin(evaluation.profile, profile)
                .orderBy(setSortType(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); //count쿼리가 추가로 실행.

        List<ReviewRetrieveDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public double findAvgRatingScoreByContentId(Long contentId) {
        try {
            return queryFactory
                    .select(MathExpressions.round(evaluation.rating.avg(), 1))
                    .from(evaluation)
                    .where(evaluation.content.id.eq(contentId))
                    .fetchOne().doubleValue();

        } catch (Exception e) {
            throw new IllegalArgumentException("해당 컨텐츠의 리뷰가 존재하지 않습니다.");
        }
    }

    private OrderSpecifier<?> setSortType(EvaluationSearchCondition condition) {

        OrderSpecifier<?> orderSpecifier;
        EvaluationSortType sortType = condition.getSortType();

        if (sortType.equals(EvaluationSortType.DEFAULT)) {
            orderSpecifier = evaluation.ratingTime.asc().nullsLast();
        } else if (sortType.equals(EvaluationSortType.LATEST)) {
            orderSpecifier = evaluation.ratingTime.desc().nullsLast();
        } else if (sortType.equals(EvaluationSortType.POPULARITY)) {
            orderSpecifier = evaluation.goodCount.desc().nullsLast();
        } else {
            throw new IncorretSortTypeException("올바르지 않은 타입 종류입니다.");
        }

        return orderSpecifier;
    }
}
