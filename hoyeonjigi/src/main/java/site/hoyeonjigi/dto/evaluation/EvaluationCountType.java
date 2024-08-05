package site.hoyeonjigi.dto.evaluation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EvaluationCountType {
    GOOD("GOOD", "좋아요"), BAD("BAD", "싫어요");

    private final String name;
    @JsonValue
    private final String value;

    @JsonCreator
    public static EvaluationCountType from(String s) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(s))
                .findAny()
                .orElse(null);
    }
}
