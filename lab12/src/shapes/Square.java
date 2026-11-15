package shapes;

import tileengine.TETile;
import tileengine.Tileset;

/**
 * A hollow square outline made of WALL tiles. (x, y) is its bottom-left corner, and each
 * side is SIDE tiles long. The tiles inside the outline are left unchanged.
 *
 * For example, new Square(2, 3, 4) covers x = 2..5 and y = 3..6, and draws:
 *
 *   # # # #
 *   #     #
 *   #     #
 *   # # # #
 *
 * PARTNER B: this file is yours. Partner A should not edit it.
 */
public class Square implements Shape {
    public static final TETile TILE = Tileset.WALL;

    private final int x;
    private final int y;
    private final int side;

    public Square(int x, int y, int side) {
        this.x = x;
        this.y = y;
        this.side = side;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int width() {
        return side;
    }

    @Override
    public int height() {
        return side;
    }

    @Override
    public void draw(TETile[][] world) {
        //  The bottom edge is y, the top edge is y + side - 1,
        //  the left edge is x, and the right edge is x + side - 1.
        //  Do not change any tile inside the outline.
        for (int i = y; i < y + side; i++) {
            world[x][i] = TILE;
            world[x + side - 1][i] = TILE;
        }
        for (int i = x; i < x + side; i++) {
            world[i][y] = TILE;
            world[i][y + side - 1] = TILE;
        }
    }
}
