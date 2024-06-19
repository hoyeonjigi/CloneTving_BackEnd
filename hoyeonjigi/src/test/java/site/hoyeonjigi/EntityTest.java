package site.hoyeonjigi;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"local"})
public class EntityTest {

    @Test
    public void test() {

    }
}
