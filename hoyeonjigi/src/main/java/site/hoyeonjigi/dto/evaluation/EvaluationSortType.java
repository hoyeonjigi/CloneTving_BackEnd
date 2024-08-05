package site.hoyeonjigi.dto.evaluation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EvaluationSortType {

    DEFAULT("DEFAULT", "기본값"),
    LATEST("LATEST", "최신순"),
    POPULARITY("POPULARITY", "인기순");

    private final String name;
    @JsonValue
    private final String value;

    @JsonCreator
    public static EvaluationSortType from(String s) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equals(s))
                .findAny()
                .orElse(null);
    }
}
