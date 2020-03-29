package qsgame.user;

import javafx.scene.input.MouseEvent;
import qsgame.game.IconField;

public interface UserInterface {
    void pressTile(IconField field, MouseEvent event);
}
