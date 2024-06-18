package site.hoyeonjigi.entity.content;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DiscriminatorValue(value = "drama")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drama extends Content{
}
