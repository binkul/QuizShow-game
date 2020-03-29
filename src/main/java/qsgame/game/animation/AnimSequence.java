package qsgame.game.animation;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import lombok.Getter;
import qsgame.constant.GameConstant;
import qsgame.game.CoverTile;

@Getter
public class AnimSequence {
    private final CoverTile coverTile;
    private long openTime;
    private AnimState animState = AnimState.CLOSED;
    private SequentialTransition sequentialTransition;

    public AnimSequence(CoverTile coverTile) {
        this.coverTile = coverTile;
    }

    public void startAnimation(long openTime) {
        if (animState != AnimState.CLOSED) return;
        this.openTime = openTime;
        processAnimation();
    }

    private void processAnimation() {
        animState = AnimState.OPENING;

        // open 200ms
        RotateTransition step1 = new RotateTransition(Duration.millis(GameConstant.OPENING_TIME), coverTile.getImageView());
        step1.setAxis(Rotate.Y_AXIS);
        step1.setByAngle(90);

        // wait 2000ms
        Timeline step2 = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(openTime));
        step2.getKeyFrames().add(keyFrame);

        // close 200ms
        RotateTransition step3 = new RotateTransition(Duration.millis(GameConstant.OPENING_TIME), coverTile.getImageView());
        step3.setOnFinished(e -> endAnimation());
        step3.setAxis(Rotate.Y_AXIS);
        step3.setByAngle(-90);

        sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(step1, step2, step3);
        sequentialTransition.play();
    }

    public void resetAnim() {
        sequentialTransition.stop();
    }

    private void endAnimation() {
        animState = AnimState.CLOSED;
    }
}
