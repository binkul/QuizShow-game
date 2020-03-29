package qsgame.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import qsgame.constant.FileAccess;
import qsgame.constant.GameStyle;
import qsgame.engine.file.PathDriver;
import qsgame.game.Game;

@Getter
public class GameForm {
    private static final String END_GAME = "End Game";

    private static final int GAP_TEN = 20;
    private static final int GAP_ZERO = 0;
    private static final int MARGIN = 40;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;

    private final Game game;
    private final Label lblResult = new Label("000");
    private final Label lblTimer = new Label("00:00");
    private final Pane mainPane = new Pane();
    private final MenuItem menuStart = new MenuItem("Start new game");
    private final MenuItem menuContinue = new MenuItem("Continue game");
    private final MenuItem menuStop = new MenuItem("Pause game");
    private final MenuItem menuSettings = new MenuItem("Settings");
    private Label lblEndGame;

    public GameForm(Game game) {
        this.game = game;
    }

    public void open() {
        Stage window = new Stage();
        Image icon = new Image(PathDriver.getInstance().getPath(FileAccess.MAIN_FORM_ICO));
        window.getIcons().add(icon);
        window.setScene(setScene());
        window.initStyle(StageStyle.DECORATED);
        window.setTitle("QuizShow game");
        window.show();
    }

    private Scene setScene() {

        // main scene
        BorderPane root  = new BorderPane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.setStyle(GameStyle.MAIN_VIEW);

        // top - Menu
        root.setTop(createTopContainer());

        // left, right
        VBox leftBox = new VBox();
        leftBox.setPrefWidth(MARGIN);
        root.setLeft(leftBox);

        VBox rightBox = new VBox();
        rightBox.setPrefWidth(MARGIN);
        root.setRight(rightBox);

        // bottom

        // center
        root.setCenter(createCenterContainer());

        //        scene.setOnKeyPressed(e -> game.getComputerBattleField().enterKeyPressed(e));

        return new Scene(root);
    }

    private VBox createTopContainer() {
        VBox vBoxContainer = new VBox();
        vBoxContainer.setStyle(GameStyle.TOP_BOX_STYLE);

        // Menu
        Menu gameMenu = new Menu("Game");
        SeparatorMenuItem[] sep = new SeparatorMenuItem[2];
        sep[0] = new SeparatorMenuItem();
        sep[1] = new SeparatorMenuItem();

        setDisableStopMenu();
        setDisableContinueMenu();
        MenuItem menuExit = new MenuItem("Exit");
        gameMenu.getItems().addAll(menuStart, menuStop, menuContinue, sep[0], menuSettings, sep[1], menuExit);

        menuExit.setOnAction(e -> System.exit(0));
        menuStart.setOnAction(e -> game.startGame());
        menuContinue.setOnAction(e -> game.continueGame());
        menuStop.setOnAction(e -> game.pauseGame());
        menuSettings.setOnAction(e -> new SettingsForm(game.getSettings()).open());

        MenuBar menu = new MenuBar();
        menu.setStyle(GameStyle.MAIN_MENU);
        menu.getMenus().addAll(gameMenu);

        vBoxContainer.getChildren().addAll(menu);

        return vBoxContainer;
    }

    private GridPane createResultArea() {
        GridPane resultGrid = createTwoColumnGrid(320, 320, GAP_TEN);
        GridPane scoreGrid = createTwoColumnGrid(160, 160, GAP_ZERO);
        GridPane timeGrid = createTwoColumnGrid(160, 160, GAP_ZERO);
        Label lblResultString = new Label("Result");
        Label lblTimerString = new Label("Time");

        HBox hBoxScore = new HBox();
        HBox hBoxTime = new HBox();
        hBoxScore.setStyle(GameStyle.ROUND_BOX_STYLE);
        hBoxTime.setStyle(GameStyle.ROUND_BOX_STYLE);

        HBox hBoxScoreText = new HBox();
        HBox hBoxScoreValue = new HBox();
        HBox hBoxTimeText = new HBox();
        HBox hBoxTimeValue = new HBox();
        hBoxScoreText.setAlignment(Pos.CENTER_LEFT);
        hBoxScoreValue.setAlignment(Pos.CENTER_RIGHT);
        hBoxTimeText.setAlignment(Pos.CENTER_LEFT);
        hBoxTimeValue.setAlignment(Pos.CENTER_RIGHT);

        lblResult.setStyle(GameStyle.LABEL_VALUE_STYLE);
        lblTimer.setStyle(GameStyle.LABEL_VALUE_STYLE);
        lblResultString.setStyle(GameStyle.LABEL_NAME_STYLE);
        lblTimerString.setStyle(GameStyle.LABEL_NAME_STYLE);

        hBoxScoreText.getChildren().addAll(lblResultString);
        hBoxScoreValue.getChildren().addAll(lblResult);
        hBoxTimeText.getChildren().addAll(lblTimerString);
        hBoxTimeValue.getChildren().addAll(lblTimer);

        scoreGrid.add(hBoxScoreText, 0, 0, 1,1);
        scoreGrid.add(hBoxScoreValue, 1, 0, 1,1);
        timeGrid.add(hBoxTimeText,0,0,1,1);
        timeGrid.add(hBoxTimeValue,1,0,1,1);

        hBoxScore.getChildren().addAll(scoreGrid);
        hBoxTime.getChildren().addAll(timeGrid);

        resultGrid.add(hBoxScore, 0, 0, 1,1);
        resultGrid.add(hBoxTime, 1,0,1,1);

        return resultGrid;
    }

    private HBox createCenterContainer() {
        HBox mainBox = new HBox();
        mainBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);

        lblEndGame = createEndLabel();
        resetVisibleLabelEndGame();
        game.getIconCanvas().relocate(0,0);
        mainPane.getChildren().addAll(game.getIconCanvas(), lblEndGame);

        gridPane.add(mainPane, 0, 0, 1, 1);
        gridPane.add(createResultArea(), 0, 1, 1, 1);

        mainBox.getChildren().add(gridPane);
        return mainBox;
    }

    private GridPane createTwoColumnGrid(int colWidthLeft, int colWidthRight, int gap) {
        GridPane grid = new GridPane();
        grid.setHgap(gap);

        ColumnConstraints colTop1 = new ColumnConstraints();
        ColumnConstraints colTop2 = new ColumnConstraints();
        colTop1.setPrefWidth(colWidthLeft);
        colTop2.setPrefWidth(colWidthRight);
        RowConstraints rowTop = new RowConstraints();

        grid.getColumnConstraints().addAll(colTop1, colTop2);
        grid.getRowConstraints().addAll(rowTop);

        return grid;
    }

    private Label createEndLabel() {
        Text text = new Text(END_GAME);
        Font font = Font.font("Arial", FontWeight.BOLD, 48);
        text.setFont(font);
        double width = text.getLayoutBounds().getWidth();
        double height = text.getLayoutBounds().getHeight();

        Label label = new Label(text.getText());
        label.setStyle(GameStyle.LABEL_END_STYLE);
        double x_pos = ((game.getIconCanvas().getWidth() / 2) - width / 2);
        double y_pos = ((game.getIconCanvas().getHeight() / 2) - height /2);
        label.relocate(x_pos, y_pos);

        return label;
    }

    public void removeCoverTiles() {
        game.getCoverTiles().forEach(i -> mainPane.getChildren().remove(i.getImageView()));
    }

    public void putCoverTiles() {
        game.getCoverTiles().forEach(i -> mainPane.getChildren().add(i.getImageView()));
    }

    public void printTimer(int time) {
        String minutes;
        String second;

        int total = time % 3600;
        int min = Math.abs(total / 60);
        int sec = Math.abs(total % 60);
        minutes = min < 10 ? "0" + min : Integer.toString(min);
        second = sec < 10 ? "0" + sec : Integer.toString(sec);

        if (time >= 0) {
            lblTimer.setText(minutes + ":" + second);
        } else {
            lblTimer.setText("-" + minutes + ":" + second);
        }
    }

    public void printResult(int result) {
        String text = result >= 10 ? "0" + result : "00" + result;
        lblResult.setText(text);
    }

    public void setDisableStartMenu() {
        menuStart.setDisable(true);
    }

    public void setDisableStopMenu() {
        menuStop.setDisable(true);
    }

    public void setEnableStartMenu() {
        menuStart.setDisable(false);
    }

    public void setEnableStopMenu() {
        menuStop.setDisable(false);
    }

    public void setEnableContinueMenu() {
        menuContinue.setDisable(false);
    }

    public void setDisableContinueMenu() {
        menuContinue.setDisable(true);
    }

    public void setEnableSettingsMenu() {
        menuSettings.setDisable(false);
    }

    public void setDisableSettingsMenu() {
        menuSettings.setDisable(true);
    }

    public void setVisibleLabelEndGame() {
        getLblEndGame().setVisible(true);
    }

    public void resetVisibleLabelEndGame() {
        getLblEndGame().setVisible(false);
    }
}
