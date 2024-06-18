package site.hoyeonjigi.entity.content;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue(value = "movie")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Content{
}
