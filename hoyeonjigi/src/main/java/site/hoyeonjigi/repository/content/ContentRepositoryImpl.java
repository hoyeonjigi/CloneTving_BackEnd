package site.hoyeonjigi.repository.content;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import site.hoyeonjigi.entity.QContentGenre;
import site.hoyeonjigi.entity.QGenre;
import site.hoyeonjigi.entity.content.Content;
import site.hoyeonjigi.entity.content.QContent;
import site.hoyeonjigi.entity.content.QDrama;
import site.hoyeonjigi.entity.content.QMovie;

import java.util.List;

import static site.hoyeonjigi.entity.QContentGenre.*;
import static site.hoyeonjigi.entity.QGenre.genre;
import static site.hoyeonjigi.entity.content.QContent.content;
import static site.hoyeonjigi.entity.content.QDrama.drama;
import static site.hoyeonjigi.entity.content.QMovie.movie;

public class ContentRepositoryImpl implements ContentCustomRepository{
    QContent qContent = content;
    QMovie qMovie = movie;
    QDrama qDrama = drama;
    QContentGenre qContentGenre = contentGenre;
    QGenre qGenre = genre;
    private final JPAQueryFactory queryFactory;
    public ContentRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Content> findContentsByTypeAndSortOrGenre(String type, String sort,String genreName, String title, Pageable pageable) {
        QueryResults<Content> results = queryFactory.select(content).distinct().from(content)
                .join(content.contentGenres, contentGenre)
                .join(contentGenre.genre, genre)
                .where(filterType(type), filterGenreName(genreName), filterTitle(title))
                .orderBy(getOrderBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Content> content  = results.getResults();
        long totalCount = results.getTotal();
        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression filterType(String type){
        if(type==null){
            return null;
        }
        return content.dType.eq(type);
    }
    private BooleanExpression filterGenreName(String genreName){
        if(genreName == null){
            return null;
        }
        return genre.name.eq(genreName);
    }

    private BooleanExpression filterTitle(String title){
        if(title == null){
            return null;
        }
        return content.contentTitle.contains(title);
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
