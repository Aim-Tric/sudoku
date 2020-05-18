package game.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import game.Level;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏数据配置
 *
 * @author Vent
 * @date 2020/5/18
 */
public class GameConfig {

    private static GameConfig gameConfig;

    private List<Level> levels;

    public static GameConfig getInstance() {
        if (gameConfig == null) {
            gameConfig = new GameConfig();
        }
        return gameConfig;
    }

    private GameConfig() {
        URL url = GameConfig.class.getClassLoader().getResource("gameConfig.json");
        if (url != null) {
            try {
                File file = new File(url.toURI());
                String json = FileUtils.readFileToString(file, Charset.forName("utf-8"));
                JSONObject bean = (JSONObject) JSONObject.parse(json);
                JSONArray levelJsonArray = bean.getJSONArray("levels");
                levels = new ArrayList<>();
                for (int i = 0; i < levelJsonArray.size(); i++) {
                    JSONArray array = levelJsonArray.getJSONArray(i);
                    List<Level.CellInfo> cellInfos = new ArrayList<>();
                    for (int j = 0; j < array.size(); j++) {
                        JSONObject cellInfoJsonObject = (JSONObject) array.get(j);
                        Integer x = cellInfoJsonObject.getInteger("x");
                        Integer y = cellInfoJsonObject.getInteger("y");
                        Integer value = cellInfoJsonObject.getInteger("value");
                        Level.CellInfo cellInfo = new Level.CellInfo(x, y, value);
                        cellInfos.add(cellInfo);
                    }
                    Level l = new Level();
                    l.setCellInfos(cellInfos);
                    levels.add(l);
                }
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Error("Error 404: The game config not found! please check your file is intact");
        }
    }

    public List<Level> getLevels() {
        return levels;
    }

    public static void main(String[] args) {
        GameConfig instance = GameConfig.getInstance();
    }

}
