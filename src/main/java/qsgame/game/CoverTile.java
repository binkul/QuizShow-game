package qsgame.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import qsgame.constant.GameConstant;
import qsgame.engine.file.PathDriver;
import qsgame.game.animation.AnimSequence;
import qsgame.game.animation.AnimState;
import qsgame.user.UserInterface;

@Getter
@Setter
public class CoverTile {
    private final int id;
    private final int x;
    private final int y;
    private final UserInterface userInterface;

    private boolean opened = false;
    private AnimSequence animSequence;
    private AnimState animState = AnimState.CLOSED;
    private ImageView imageView;

    public CoverTile(int id, int x, int y, UserInterface userInterface) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.userInterface = userInterface;
        this.opened = false;
        initImage();
     }

    public void startAnimation(long openTime) {
        animSequence.startAnimation(openTime);
    }

    private void initImage() {
        Image image = new Image(PathDriver.getInstance().getPath(GameConstant.TILE_PATH),
                GameConstant.ICON_SIZE_PIX, GameConstant.ICON_SIZE_PIX, false, true);
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setUserData(id);
        imageView.setMouseTransparent(true);
        animSequence = new AnimSequence(this);
    }

    public void resetAnimation() {
        animSequence.resetAnim();
        opened = true;
        imageView.setVisible(false);
    }
}
