package site.hoyeonjigi.common;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = {"local"})
class RedisUtilsTest {

    @Autowired
    RedisUtils redisUtils;

    @Test
    @DisplayName("레디스 데이터베이스에 값 저장 및 조회.")
    void setDataAndGetData() throws InterruptedException {
        // given
        String key = "testKey";
        String  value = "testValue";
        Long expiredTime = 1000L; //유효기간: 10초

        // when
        redisUtils.setData(key, value, expiredTime);

        // then
        //데이터 조회
        Assertions.assertThat(redisUtils.getData(key)).isEqualTo(value);
        Thread.sleep(1100);

        //유효기간 경과 후, 데이터 조회
        Assertions.assertThat(redisUtils.getData(key)).isNull();
    }

    @Test
    @DisplayName("레디스 데이터베이스 값 삭제")
    void deleteData() {
        // given
        String key = "testKey";
        String  value = "testValue";
        Long expiredTime = 1000L; //유효기간: 10초

        redisUtils.setData(key, value, expiredTime);
        // when
        redisUtils.deleteData(key);
        // then
        Assertions.assertThat(redisUtils.getData(key)).isNull();
    }
}