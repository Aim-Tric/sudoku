package game;

import java.awt.*;

/**
 * 数独格子
 *
 * @author Vent
 * @date 2020/5/17
 */
public class Cell {

    public static final int CELL_WIDTH = 30;
    public static final int CELL_HEIGHT = 30;

    private Game game;
    private int x;
    private int y;

    public String value;
    public boolean immutable;

    public Cell(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public void paint(Graphics g) {
        g.drawRect(x, y, CELL_WIDTH, CELL_HEIGHT);
        if (value != null) {
            int positionX = x + CELL_WIDTH / 2 - 2;
            int positionY = y + CELL_HEIGHT / 2 + 2;
            g.drawString(value, positionX, positionY);
        }
    }

    public void click(int x, int y) {
        if(immutable) {
            return;
        }
        boolean isXInArea = this.x < x && x < this.x + CELL_WIDTH;
        boolean isYInArea = this.y < y && y < this.y + CELL_HEIGHT;
        if (isXInArea && isYInArea) {
            this.value = game.getCurrent();
        }
    }

}
