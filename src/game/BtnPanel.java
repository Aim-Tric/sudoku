package game;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 数字按钮面板
 *
 * @author Vent
 * @date 2020/5/17
 */
public class BtnPanel {

    private String[] btns = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "清除",
    };
    private int x, y;
    private Game game;
    private BtnCell[] btnCells;

    public BtnPanel(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
        btnCells = new BtnCell[btns.length];
        for (int i = 0; i < btns.length; i++) {
            btnCells[i] = new BtnCell(x + (i + 1) * 20, y, btns[i], game);
        }
    }

    public void paint(Graphics g) {
        for(int i = 0; i < btnCells.length; i++) {
            btnCells[i].paint(g);
        }
    }

    public void handleMouseReleasedEvent(MouseEvent e) {
        for (int i = 0; i < btnCells.length; i++) {
            btnCells[i].click(e.getX(), e.getY());
        }
    }
}
