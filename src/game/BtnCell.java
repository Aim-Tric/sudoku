package game;

import java.awt.*;

/**
 * 功能按钮
 *
 * @author Vent
 * @date 2020/5/17
 */
public class BtnCell {

    private int x, y;
    private String label;
    private Game game;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    public BtnCell(int x, int y, String label, Game game) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.game = game;
    }

    public void paint(Graphics g) {
        int width = label.length() * WIDTH;
        g.drawRect(x, y, width, HEIGHT);
        g.drawString(label, x + WIDTH / 2 - 2, y + HEIGHT / 2 + 2);
    }

    public void click(int x, int y) {
        boolean isXInArea = this.x < x && x < this.x + WIDTH;
        boolean isYInArea = this.y < y && y < this.y + HEIGHT;
        if (isXInArea && isYInArea) {
            if (label.equals("清除")) {
                game.setCurrent("");
            } else {
                game.setCurrent(label);
            }
        }
    }
}
