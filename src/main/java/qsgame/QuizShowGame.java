package qsgame;

import javafx.application.Application;
import javafx.stage.Stage;
import qsgame.game.Game;

public class QuizShowGame extends Application {
    Game game = new Game();

    @Override
    public void start(Stage primaryStage) {
        game.initGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
