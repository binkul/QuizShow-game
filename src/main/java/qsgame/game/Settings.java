package qsgame.game;

import lombok.Getter;
import lombok.Setter;
import qsgame.constant.GameConstant;

@Getter
@Setter
public class Settings {
    private final Game game;
    private String name;
    private String dimension;

    public Settings(Game game) {
        this.game = game;
        this.name = "None";
        mapToDimension(GameConstant.INITIAL_DIMENSION);
    }

    public void mapToDimension(int fieldDimension) {
        switch (fieldDimension) {
            case 4:
                dimension = "4x4";
                break;
            case 6:
                dimension = "6x6";
                break;
            case 8:
                dimension = "8x8";
                break;
            default:
                dimension = "10x10";
        }
    }

    public int mapToValue() {
        switch (dimension) {
            case "4x4":
                return 4;
            case "6x6":
                return 6;
            case "8x8":
                return 8;
            default:
                return 10;
        }
    }

    public int getIconsCount() {
        int tmp = mapToValue();
        return (tmp * tmp) / 2;
    }

    public int getFieldDimension() {
        return GameConstant.ICON_STEP_PIX * mapToValue() + GameConstant.ICON_GAP;
    }

    public int getFieldMargin() {
        int diff = (10 - mapToValue()) / 2;
        return GameConstant.ICON_GAP + diff * GameConstant.ICON_SIZE_PIX;
    }
}
