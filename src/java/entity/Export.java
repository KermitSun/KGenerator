package entity;

import lombok.Data;

import java.util.List;

/**
 * @Date: 21:45 2019/6/7
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class Export {
    private String outPath;
    private boolean clearDirs;
    private List<Item> items;
}