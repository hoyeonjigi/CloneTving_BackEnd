package site.hoyeonjigi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.hoyeonjigi.repository.content.ContentRepository;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;
    @InjectMocks
    private ContentService contentService;

    @Test
    @DisplayName("콘텐츠가 존재하지 않을때 NoSuchElementException 발생")
    void findContentByIdTest(){
        assertThatThrownBy(()->contentService.findContentById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Content");
    }

    @Test
    @DisplayName("콘텐츠가 존재하지 않을때 NoSuchElemntException 발생")
    void addViewCount(){
        assertThatThrownBy(() -> contentService.addViewCount(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Content");
    }
}
