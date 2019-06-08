package utils;

import com.alibaba.fastjson.JSON;
import entity.Config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @Date: 17:59 2019/6/7
 * @Author: Kermit Sun
 * @Description: 读取配置文件
 */
public class ConfigUtil {
    /**
     *@Date: 18:00 2019/6/7
     *@Author: Kermit Sun
     *@Description: 读取配置文件
     */
    public static String read(String path) throws Exception{
        StringBuffer sb = new StringBuffer();
        try(InputStream is = ConfigUtil.class.getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);){
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /**
     *@Date: 21:51 2019/6/7
     *@Author: Kermit Sun
     *@Description: 将params替换json中的参数
     */
    private static String injectParams(String json, Map<String, String> params){
        String key;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            key = "${"+entry.getKey()+"}";
            json = json.replaceAll(key, entry.getValue());
        }
        return json;
    }

    /**
     *@Date: 21:56 2019/6/7
     *@Author: Kermit Sun
     *@Description: 获取配置对象
     */
    public static Config createConfig(String json){
        return JSON.parseObject(json, Config.class);
    }
}