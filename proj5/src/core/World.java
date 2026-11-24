package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.awt.Color;

public class World {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 45;
    private static final int MIN_ROOM_WIDTH = 5;
    private static final int MAX_ROOM_WIDTH = 10;
    private static final int MIN_ROOM_HEIGHT = 4;
    private static final int MAX_ROOM_HEIGHT = 8;
    private static final int MIN_HALLWAY_LENGTH = 4;
    private static final int MAX_HALLWAY_LENGTH = 6;
    private static final int SEED = 42;
    private static final Random RANDOM = new Random(SEED);
    private static final int FAIL_LIMIT = 10;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private Direction getRandomDirections() {
        int choice = RANDOM.nextInt(4);
        if (choice == 0) {
            return Direction.UP;
        } else if (choice == 1) {
            return Direction.DOWN;
        } else if (choice == 2) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
    }

    record RoomWidthAndHeight(int width, int height) {
    }

    private RoomWidthAndHeight getRandomRoomWidthAndHeight() {
        int width = RANDOM.nextInt(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
        int height = RANDOM.nextInt(MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);
        return new RoomWidthAndHeight(width, height);
    }

    private int getRandomHallwayLength() {
        return RANDOM.nextInt(MIN_HALLWAY_LENGTH, MAX_HALLWAY_LENGTH);
    }

    private boolean checkTile(TETile[][] tiles, int x, int y, int width, int height) {
        if (x <= 0 || x >= WIDTH || y <= 0 || y >= HEIGHT) {
            return false;
        }
        if (x + width >= WIDTH || y + height >= HEIGHT) {
            return false;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[x + i][y + j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillTile(TETile[][] tiles, TETile tetile, int x, int y, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[x + i][y + j] = tetile;
            }
        }
    }

    private void generateHallwayAndRoom(TETile[][] tiles, int room_x, int zoom_y, int room_width, int room_height,
            HashSet<Direction> blocked_direction) {
        int hallway_fail_count = 0;
        while (hallway_fail_count < FAIL_LIMIT && blocked_direction.size() < 3) {
            Direction to = getRandomDirections();
            if (blocked_direction.contains(to)) {
                continue;
            }
            int hallway_width, hallway_height;
            if (to == Direction.UP || to == Direction.DOWN) {
                hallway_width = 1;
                hallway_height = getRandomHallwayLength();
            } else {
                hallway_width = getRandomHallwayLength();
                hallway_height = 1;
            }
            int hallway_x, hallway_y;
            if (to == Direction.UP) {
                hallway_x = RANDOM.nextInt(room_x + 1, room_x + room_width - 2);
                hallway_y = zoom_y + room_height;
            } else if (to == Direction.DOWN) {
                hallway_x = RANDOM.nextInt(room_x + 1, room_x + room_width - 2);
                hallway_y = zoom_y - hallway_height;
            } else if (to == Direction.LEFT) {
                hallway_x = room_x - hallway_width;
                hallway_y = RANDOM.nextInt(zoom_y + 1, zoom_y + room_height - 2);
            } else {
                hallway_x = room_x + room_width;
                hallway_y = RANDOM.nextInt(zoom_y + 1, zoom_y + room_height - 2);
            }

            if (checkTile(tiles, hallway_x, hallway_y, hallway_width, hallway_height)) {
                int room_fail_count = 0;
                while (room_fail_count < FAIL_LIMIT) {
                    var r = getRandomRoomWidthAndHeight();
                    int new_zoom_x, new_zoom_y;
                    if (to == Direction.UP) {
                        new_zoom_x = hallway_x - RANDOM.nextInt(r.width);
                        new_zoom_y = hallway_y + hallway_height;
                    } else if (to == Direction.DOWN) {
                        new_zoom_x = hallway_x - RANDOM.nextInt(r.width);
                        new_zoom_y = hallway_y - r.height;
                    } else if (to == Direction.LEFT) {
                        new_zoom_x = hallway_x - r.width;
                        new_zoom_y = hallway_y - RANDOM.nextInt(r.height);
                    } else {
                        new_zoom_x = hallway_x + hallway_width;
                        new_zoom_y = hallway_y - RANDOM.nextInt(r.height);
                    }

                    if (checkTile(tiles, new_zoom_x, new_zoom_y, r.width, r.height)) {
                        fillTile(tiles, Tileset.FLOOR, hallway_x, hallway_y, hallway_width, hallway_height);
                        fillTile(tiles, Tileset.FLOOR, new_zoom_x, new_zoom_y, r.width, r.height);
                        blocked_direction.add(to);
                        HashSet<Direction> new_blocked_direction = new HashSet<>();
                        new_blocked_direction.add(to);
                        generateHallwayAndRoom(tiles, new_zoom_x, new_zoom_y, r.width, r.height,
                                new_blocked_direction);
                        break;
                    } else {
                        room_fail_count += 1;
                    }
                }
            } else {
                hallway_fail_count += 1;
            }
        }
    }

    private void generateFloor(TETile[][] tiles) {
        // choose a random coordinate
        int x = RANDOM.nextInt((int) (WIDTH * 0.8));
        int y = RANDOM.nextInt((int) (HEIGHT * 0.8));
        // randomly generate the first room
        int try_count = 0;
        while (try_count < FAIL_LIMIT) {
            var r = getRandomRoomWidthAndHeight();
            if (checkTile(tiles, x, y, r.width, r.height)) {
                fillTile(tiles, Tileset.FLOOR, x, y, r.width, r.height);
                HashSet<Direction> blocked_direction = new HashSet<>();
                blocked_direction.add(getRandomDirections());
                generateHallwayAndRoom(tiles, x, y, r.width, r.height, blocked_direction);
                return;
            } else {
                try_count += 1;
            }
        }
    }

    private void generateWall(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    if (y + 1 < HEIGHT && tiles[x][y + 1] == Tileset.NOTHING) {
                        tiles[x][y + 1] = Tileset.WALL;
                    }
                    if (0 <= y - 1 && tiles[x][y - 1] == Tileset.NOTHING) {
                        tiles[x][y - 1] = Tileset.WALL;
                    }
                    if (0 <= x - 1 && tiles[x - 1][y] == Tileset.NOTHING) {
                        tiles[x - 1][y] = Tileset.WALL;
                    }
                    if (x + 1 < WIDTH && tiles[x + 1][y] == Tileset.NOTHING) {
                        tiles[x + 1][y] = Tileset.WALL;
                    }
                }
            }
        }
    }

    public void run() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

        this.generateFloor(tiles);
        this.generateWall(tiles);

        StdDraw.clear(new Color(0, 0, 0));
        ter.drawTiles(tiles);
        StdDraw.show();
    }
}
