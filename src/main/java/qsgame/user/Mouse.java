package qsgame.user;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import qsgame.constant.GameConstant;
import qsgame.game.*;

import java.util.ArrayList;
import java.util.List;

public class Mouse extends PointerValidator implements UserInterface {
    private final Game game;
    private final List<CoverTile> tiles = new ArrayList<>();
    private Timeline timer;

    public Mouse(Game game) {
        super(game);
        this.game = game;
    }

    @Override
    public void pressTile(IconField field, MouseEvent event) {
        CoverTile coverTile;

        if (game.getGameState() != GameState.RUN) return;
        if (tiles.size() >= 2) return;

        coverTile = getCoverTile(event);
        if (coverTile == null) return;

        if (tiles.size() == 0) {
            openFirstTile(coverTile);
        } else if (tiles.size() == 1) {
            openSecondTile(coverTile);
        }
    }

    private CoverTile getCoverTile(MouseEvent event) {
        if (!isPointerOnField(event)) return null;

        int x = ((int)event.getX() - game.getFieldMargin()) / GameConstant.ICON_STEP_PIX;
        int y = ((int)event.getY() - game.getFieldMargin()) / GameConstant.ICON_STEP_PIX;

        if (!isPointerOnTile(event, x, y)) return null;

        CoverTile coverTile = game.getCoverTiles().get(y * game.getDimension() + x);

        return !coverTile.isOpened() ? coverTile : null;
    }

    private void openFirstTile(CoverTile coverTile) {
        tiles.add(coverTile);
        coverTile.startAnimation(GameConstant.OPEN_TIME_DURATION);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(GameConstant.TOTAL_ANIM_TIME), e -> endTimerDifferentTiles());
        startTimer(keyFrame);
    }

    private void openSecondTile(CoverTile coverTile) {
        if (!tiles.get(0).equals(coverTile) && checkOpenTime()) {
            tiles.add(coverTile);
            coverTile.startAnimation(GameConstant.OPEN_TIME_DURATION - (long)timer.getCurrentTime().toMillis());

            if (isTheSameIcons(tiles)) {
                timer.stop();
                KeyFrame keyFrame = new KeyFrame(Duration.millis(GameConstant.OPENING_TIME), e -> endTimerEqualTiles());
                startTimer(keyFrame);
                game.updateResult();
            }
        }
    }

    private void startTimer(KeyFrame keyFrame) {
        timer = new Timeline();
        timer.getKeyFrames().add(keyFrame);
        timer.play();
    }

    private void endTimerDifferentTiles() {
        tiles.clear();
    }

    private void endTimerEqualTiles() {
        if (tiles.size() >= 2) {
            tiles.get(0).resetAnimation();
            tiles.get(1).resetAnimation();
        }
        endTimerDifferentTiles();
    }

    private Boolean checkOpenTime() {
        return GameConstant.OPEN_TIME_DURATION - (long)timer.getCurrentTime().toMillis() > 2 * GameConstant.OPENING_TIME;
    }
}

