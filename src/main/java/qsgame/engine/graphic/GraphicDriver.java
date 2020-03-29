package qsgame.engine.graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import qsgame.constant.GameConstant;
import qsgame.game.Game;
import qsgame.game.Icon;

import java.util.List;
import java.util.stream.IntStream;

public class GraphicDriver {
    private final Game game;

    public GraphicDriver(Game game) {
        this.game = game;
    }

    public void drawAllIcons() {
        IntStream.iterate(0, x -> x +1)
                .limit(game.getDimension())
                .forEach(this::drawOneRow);
    }

    private void drawOneRow(int x) {
        GraphicsContext gc = game.getIconField().getGraphicsContext2D();
        List<Image> iconList = game.getIconList();
        Icon[][] icons = game.getGameField();

        IntStream.iterate(0, y -> y + 1)
                .limit(game.getDimension())
                .forEach(y -> gc.drawImage(iconList.get(icons[x][y].getIconNumber()),
                        game.getFieldMargin() + x * GameConstant.ICON_STEP_PIX,
                        game.getFieldMargin() + y * GameConstant.ICON_STEP_PIX,
                        GameConstant.ICON_SIZE_PIX,
                        GameConstant.ICON_SIZE_PIX));
    }

}
