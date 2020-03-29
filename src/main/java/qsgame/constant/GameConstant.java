package qsgame.constant;

public class GameConstant {
    public static final int MAX_SIZE = 1000;
    public static final int INITIAL_DIMENSION = 10;
    public static final int ICON_SIZE_PIX = 64;
    public static final int ICON_GAP = 2;
    public static final int ICON_STEP_PIX = ICON_SIZE_PIX + ICON_GAP;

    public static final int OPENING_TIME = 200;
    public static final long OPEN_TIME_DURATION = 2000;
    public static final long TOTAL_ANIM_TIME = 2 * OPENING_TIME + OPEN_TIME_DURATION + 20;

    public static final String TILE_PATH = "tile/tile.png";
}
