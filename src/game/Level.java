package game;

import java.util.List;

/**
 * 关卡信息
 *
 * @author Vent
 * @date 2020/5/18
 */
public class Level {

    private List<CellInfo> cellInfos;

    public List<CellInfo> getCellInfos() {
        return cellInfos;
    }

    public void setCellInfos(List<CellInfo> cellInfos) {
        this.cellInfos = cellInfos;
    }

    public static class CellInfo {
        private int x;
        private int y;
        private int value;

        public CellInfo(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


}
