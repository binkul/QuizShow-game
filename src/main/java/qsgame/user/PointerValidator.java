package qsgame.user;

import javafx.scene.input.MouseEvent;
import qsgame.constant.GameConstant;
import qsgame.game.CoverTile;
import qsgame.game.Game;
import qsgame.game.Icon;

import java.util.List;

public class PointerValidator {
    private final Game game;

    PointerValidator(Game game) {
        this.game = game;
    }

    boolean isTheSameIcons(List<CoverTile> tiles) {
        if (tiles.size() < 2) return false;
        return getIconUnderCover(tiles.get(0)).equals(getIconUnderCover(tiles.get(1)));
    }

    private Icon getIconUnderCover(CoverTile coverTile) {
        int x = coverTile.getId() % game.getDimension();
        int y = coverTile.getId() / game.getDimension();
        return game.getIcon(x, y);
    }

    boolean isPointerOnField(MouseEvent event) {
        return isPointerOnX(event) && isPointerOnY(event);
    }

    boolean isPointerOnTile(MouseEvent event, int x, int y) {
        return isPointerOnTileX(event, x) && isPointerOnTileY(event, y);
    }

    private boolean isPointerOnX(MouseEvent event) {
        return !(event.getX() < game.getFieldMargin()) && !(event.getX() > (game.getFieldMargin() + game.getFieldDimension()));
    }

    private boolean isPointerOnY(MouseEvent event) {
        return !(event.getY() < game.getFieldMargin() || event.getY() > (game.getFieldMargin() + game.getFieldDimension()));
    }

    private boolean isPointerOnTileX(MouseEvent event, int x) {
        int outOfX = (int)event.getX() - x * (GameConstant.ICON_STEP_PIX) - game.getFieldMargin();
        return  outOfX >= 0 && outOfX <= GameConstant.ICON_SIZE_PIX - 1;
    }

    private boolean isPointerOnTileY(MouseEvent event, int y) {
        int outOfY = (int)event.getY() - y * (GameConstant.ICON_STEP_PIX) - game.getFieldMargin();
        return outOfY >= 0 && outOfY <= GameConstant.ICON_SIZE_PIX - 1;
    }

}
