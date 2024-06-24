package site.hoyeonjigi.common;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"local"})
class RedisUtilsTest {

    @Autowired
    RedisUtils redisUtils;

    @Test
    @DisplayName("레디스 : 값 저장 후 조회")
    void setDataAndGetData() throws InterruptedException {
        // given
        String key = "redisKey";
        String value = "redisValue";
        Long expirationTime = 1000L; // 10초

        // when
        redisUtils.setData(
                key,
                value,
                expirationTime);

        // then
        Assertions.assertThat(redisUtils.getData(key)).isEqualTo(value);
    }

    @Test
    void getData() {
    }

    @Test
    void deleteData() {
    }
}