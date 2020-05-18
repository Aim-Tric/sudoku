package game;

import game.config.GameConfig;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 游戏棋盘类
 *
 * @author Vent
 * @date 2020/5/17
 */
public class Board {

    private int row;
    private int col;

    private Game game;
    private int gameLevel = 0;
    private Cell[][] cells;
    List<Level> levels;

    public Board(int row, int col, Game game) {
        this.row = row;
        this.col = col;
        this.game = game;
        levels = GameConfig.getInstance().getLevels();
        loadLevel();
    }

    public void paint(Graphics g) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j].paint(g);
            }
        }
    }

    public boolean check() {
        for (int i = 0; i < row; i++) {

            int rank = i / 3 + 1;

            int[] rCheck = new int[row];
            int[] cCheck = new int[row];
            int[] cellCheck = new int[row];

            for (int j = 0; j < col; j++) {
                if (i == j) {
                    // 横向检查
                    boolean isRCheck = check(rCheck, i, j);
                    // 纵向检查
                    boolean isCCheck = check(cCheck, j, i);
                    if (!isRCheck || !isCCheck) {
                        return false;
                    }
                }
            }

            for (int x = (i % 3) * 3; x < (i % 3 + 1) * 3; x++) {
                for (int y = (rank - 1) * 3; y < rank * 3; y++) {
                    boolean isCellCheck = check(cellCheck, x, y);
                    if (!isCellCheck) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean check(int[] check, int i, int j) {
        String r = cells[i][j].value;
        if (!"".equals(r) && r != null) {
            int rNum = Integer.valueOf(r);
            if (check[rNum - 1] == 0) {
                check[rNum - 1] = rNum;
                return true;
            }
        }
        return false;
    }

    public void reset() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (cells[i][j].immutable) {
                    continue;
                }
                cells[i][j].value = "";
            }
        }
    }

    public void loadLevel() {
        this.cells = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Cell cell = new Cell((i + 1) * Cell.CELL_WIDTH, (j + 1) * Cell.CELL_HEIGHT, game);
                cells[i][j] = cell;
            }
        }
        Level currentLevel = levels.get(gameLevel);
        List<Level.CellInfo> cellInfos = currentLevel.getCellInfos();
        for (int i = 0; i < cellInfos.size(); i++) {
            Level.CellInfo cellInfo = cellInfos.get(i);
            int x = cellInfo.getX();
            int y = cellInfo.getY();
            cells[y][x].value = String.valueOf(cellInfo.getValue());
            cells[y][x].immutable = true;
        }
    }

    public void nextLevel() {
        gameLevel++;
        if (gameLevel >= levels.size()) {
            System.out.println("111");
            game.setEnd(true);
            return;
        }
        loadLevel();
    }

    public void handleMouseReleasedEvent(MouseEvent e) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j].click(e.getX(), e.getY());
            }
        }
        if (check()) {
            game.setWin(true);
        }
    }

    public void restart() {
        gameLevel = 0;
    }
}
