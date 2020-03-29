package qsgame.game;

import javafx.scene.image.Image;
import qsgame.constant.FileAccess;
import qsgame.constant.GameConstant;
import qsgame.engine.file.PathDriver;
import qsgame.user.UserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class Init {
    private static final Random random = new Random();

    List<Image> createIcons() {
        String catalog = PathDriver.getInstance().getPath(FileAccess.ICON_PATH) + "/ico";

        return IntStream.range(1, 51)
                .mapToObj(i -> catalog + i + ".png")
                .map(i -> new Image(i, 64, 64, true, true))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    Icon[][] createGameField(Game game) {
        Icon[][] icons = new Icon[game.getDimension()][game.getDimension()];
        Arrays.setAll(icons, x -> {
            Arrays.setAll(icons[x], y -> new Icon(-1, false));
            return icons[x];
        });

        return icons;
    }

    List<CoverTile> createCoverTiles(UserInterface userInterface, Game game) {
        int step = GameConstant.ICON_GAP + GameConstant.ICON_SIZE_PIX;
        List<CoverTile> coverTiles = new ArrayList<>();
        int id = 0;

        for (int x = 0; x < game.getDimension(); x++) {
            for (int y = 0; y < game.getDimension(); y++){
                CoverTile coverTile = new CoverTile(id, game.getFieldMargin() + (y * step), game.getFieldMargin() + (x * step), userInterface);
                coverTiles.add(coverTile);
                id++;
            }
        }

        return coverTiles;
    }

    void arrangeIconsOnField(Icon[][] gameField, Game game) {
        mixIcons(gameField, game);
        mixIcons(gameField, game);
    }

    private void mixIcons(Icon[][] gameField, Game game) {
        int iconNumber = 0;
        int row;
        int column;
        while (iconNumber < game.getIconCount()) {
            column = random.nextInt(game.getDimension());
            row = random.nextInt(game.getDimension());
            if (gameField[row][column].getIconNumber() == -1) {
                gameField[row][column].setIconNumber(iconNumber);
                iconNumber++;
            }
        }
    }

}
