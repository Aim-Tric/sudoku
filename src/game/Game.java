package game;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏启动类
 *
 * @author Vent
 * @date 2020/05/17
 */
public class Game extends Frame {

    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 600;

    public static final int ROW = 9;
    public static final int COL = 9;

    private Board board;
    private BtnPanel btnPanel;

    private String current = "";
    private boolean pause = false;
    private boolean win = false;
    private boolean end = false;

    public Game() {
        board = new Board(ROW, COL, this);
        btnPanel = new BtnPanel(50, GAME_HEIGHT - 50, this);

        setTitle("Sudoku");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setVisible(true);

        addWindowListener(new WindowEventListener());

        addMouseListener(new MouseEventListener());

        addKeyListener(new KeyEventListener());

    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public void paint(Graphics g) {
        if (!pause) {
            if (win) {
                if (end) {
                    pause = true;
                    Dialog dialog = new Dialog(this, "游戏结束");
                    Button button = new Button("确定");
                    button.addActionListener(e -> dialog.setVisible(false));
                    dialog.add(button);
                    dialog.setVisible(true);
                } else {
                    board.nextLevel();
                }
            }
            board.paint(g);
            btnPanel.paint(g);
        }
    }

    private Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color color = gOffScreen.getColor();
        gOffScreen.setColor(Color.WHITE);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(color);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        Game g = new Game();
        while (!Thread.currentThread().isInterrupted()) {
            g.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    class WindowEventListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class MouseEventListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (!pause) {
                board.handleMouseReleasedEvent(e);
                btnPanel.handleMouseReleasedEvent(e);
            }
        }
    }

    class KeyEventListener extends KeyAdapter {
        private Map<Integer, String> mapping = new HashMap<>();

        public KeyEventListener() {
            mapping.put(KeyEvent.VK_0, "");
            mapping.put(KeyEvent.VK_1, "1");
            mapping.put(KeyEvent.VK_2, "2");
            mapping.put(KeyEvent.VK_3, "3");
            mapping.put(KeyEvent.VK_4, "4");
            mapping.put(KeyEvent.VK_5, "5");
            mapping.put(KeyEvent.VK_6, "6");
            mapping.put(KeyEvent.VK_7, "7");
            mapping.put(KeyEvent.VK_8, "8");
            mapping.put(KeyEvent.VK_9, "9");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_F4:
                    pause = !pause;
                    break;
                case KeyEvent.VK_F6:
                    board.restart();
                    pause = false;
                    break;
                case KeyEvent.VK_F5:
                    board.reset();
                    pause = false;
                    break;
                default:
                    current = mapping.get(e.getKeyCode());
                    break;
            }
        }
    }
}
