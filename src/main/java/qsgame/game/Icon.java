package qsgame.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Icon {
    private int iconNumber;
    private boolean hidden;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Icon)) return false;
        Icon icon = (Icon) o;
        return iconNumber == icon.iconNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iconNumber);
    }
}
