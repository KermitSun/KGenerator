package entity;

import lombok.Data;

import java.util.Map;

/**
 * @Date: 21:37 2019/6/7
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class Config {
    private Map<String, String> params;
    private DB db;
    private Export export;
}