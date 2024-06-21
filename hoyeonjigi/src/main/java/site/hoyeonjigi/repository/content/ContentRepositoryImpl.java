package site.hoyeonjigi.repository.content;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.entity.content.QContent;
import site.hoyeonjigi.entity.content.QDrama;
import site.hoyeonjigi.entity.content.QMovie;

import java.util.List;

import static site.hoyeonjigi.entity.content.QContent.content;
import static site.hoyeonjigi.entity.content.QDrama.drama;
import static site.hoyeonjigi.entity.content.QMovie.movie;

public class ContentRepositoryImpl implements ContentCustomRepository{
    QContent qContent = content;
    QMovie qMovie = movie;
    QDrama qDrama = drama;
    private final JPAQueryFactory queryFactory;
    public ContentRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Content> findContentsByTypeAndSort(String type, String sort, Pageable pageable) {
        QueryResults<Content> results = queryFactory.selectFrom(content)
                .where(content.dType.eq(type))
                .orderBy(getOrderBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Content> content  = results.getResults();
        long totalCount = results.getTotal();
        return new PageImpl<>(content, pageable, totalCount);
    }

    private OrderSpecifier<?>[] getOrderBy(String sort) {
        OrderSpecifier<?>[] orders;
        if (sort.equals("popular")) {
            orders = new OrderSpecifier<?>[]{content.viewCount.desc()};
        } else if (sort.equals("latest")) {
            orders = new OrderSpecifier<?>[]{content.releaseDate.desc()};
        }
        else{
            orders = new OrderSpecifier<?>[]{content.id.desc()};
        }
        return orders;
    }
}
