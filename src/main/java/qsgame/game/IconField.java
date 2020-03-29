package qsgame.game;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import qsgame.constant.GameConstant;
import qsgame.user.UserInterface;

public class IconField extends Canvas {
    private UserInterface userInterface;
    private final Game game;

    public IconField(UserInterface userInterface, Game game) {
        super(game.getFieldDimension(), game.getFieldDimension());
        this.game = game;
        this.userInterface = userInterface;
        getCanvas().addEventHandler(MouseEvent.MOUSE_PRESSED, this::enterMousePressed);
    }

    @Override
    public GraphicsContext getGraphicsContext2D() {
        return super.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return super.getGraphicsContext2D().getCanvas();
    }

    private void enterMousePressed(MouseEvent event) {
        event.consume();
        if (userInterface == null) return;

        userInterface.pressTile(this, event);
    }

    public void clear() {
        super.getGraphicsContext2D().clearRect(0,0, GameConstant.MAX_SIZE, GameConstant.MAX_SIZE);
    }
}
