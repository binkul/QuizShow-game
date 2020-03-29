package qsgame.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import qsgame.engine.graphic.GraphicDriver;
import qsgame.gui.GameForm;
import qsgame.user.Mouse;
import qsgame.user.UserInterface;

import java.util.List;

@Getter
@Setter
public class Game extends Init {
    private final int WAIT_TIME_MS = 3000;
    private final int INTERVAL_TIME_MS = 1000;

    private final GameForm gameForm;
    private final IconField iconField;
    private final GraphicDriver graphicDriver;
    private final UserInterface userInterface;
    private final Settings settings;

    private Timeline introTimer;
    private Timeline timer;
    private GameState gameState;
    private List<Image> iconList;
    private Icon[][] gameField;
    private List<CoverTile> coverTiles;
    private int time;
    private int result;

    public Game() {
        gameForm = new GameForm(this);
        graphicDriver = new GraphicDriver(this);
        userInterface = new Mouse(this);
        settings = new Settings(this);
        iconField = new IconField(userInterface, this);
        gameState = GameState.STOP;
    }

    public void initGame() {
        iconList = createIcons();
        gameForm.open();
    }

    public void startGame() {
        removeCovers();
        iconField.clear();

        prepareField();
        startIntroTimer();
        startCounter();

        gameForm.setDisableStartMenu();
        gameForm.setDisableContinueMenu();
        gameForm.setEnableStopMenu();
        gameForm.setDisableSettingsMenu();
        gameForm.resetVisibleLabelEndGame();
    }

    public void continueGame() {
        gameForm.setEnableStopMenu();
        gameForm.setDisableStartMenu();
        gameForm.setDisableContinueMenu();

        timer.play();
        gameState = GameState.RUN;
    }

    public void pauseGame() {
        gameForm.setDisableStopMenu();
        gameForm.setEnableStartMenu();
        gameForm.setEnableContinueMenu();

        timer.pause();
        gameState = GameState.PAUSED;
    }

    private void endGame() {
        gameForm.setEnableStartMenu();
        gameForm.setDisableContinueMenu();
        gameForm.setDisableStopMenu();
        gameForm.setEnableSettingsMenu();
        gameForm.setVisibleLabelEndGame();

        timer.stop();
        gameState = GameState.STOP;
    }

    private void prepareField() {
        gameField = createGameField(this);
        arrangeIconsOnField(gameField, this);
        coverTiles = createCoverTiles(userInterface, this);
        graphicDriver.drawAllIcons();
    }

    private void startIntroTimer() {
        introTimer = new Timeline();
        KeyFrame introKeyFrame = new KeyFrame(Duration.millis(WAIT_TIME_MS), e -> endIntro());
        introTimer.getKeyFrames().add(introKeyFrame);
        introTimer.play();
    }

    private void startCounter() {
        result = 0;
        time = -1 * (WAIT_TIME_MS / 1000);
        gameForm.printTimer(time);
        timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        KeyFrame timerKeyFrame = new KeyFrame(Duration.millis(INTERVAL_TIME_MS), e -> countTimer());
        timer.getKeyFrames().add(timerKeyFrame);
        timer.playFromStart();
    }

    public Canvas getIconCanvas() {
        return iconField.getCanvas();
    }

    public Icon getIcon(int row, int column) {
        return gameField[row][column];
    }

    public int getDimension() {
        return settings.mapToValue();
    }

    public int getIconCount() {
        return settings.getIconsCount();
    }

    public int getFieldDimension() {
        return settings.getFieldDimension();
    }

    public int getFieldMargin() {
        return settings.getFieldMargin();
    }

    private void endIntro() {
        gameForm.putCoverTiles();
        gameState = GameState.RUN;
    }

    private void countTimer() {
        time++;
        gameForm.printTimer(time);
    }

    public void updateResult() {
        result++;
        gameForm.printResult(result);

        if (result >= getIconCount()) endGame();
    }

    private void removeCovers() {
        if (coverTiles != null && coverTiles.size() > 0) {
            gameForm.removeCoverTiles();
            coverTiles = null;
        }
    }
}
