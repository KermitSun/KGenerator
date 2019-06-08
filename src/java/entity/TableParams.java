package entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Date: 23:50 2019/6/7
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class TableParams {
    //config.json中params的配置
    Map<String, String> params;
    //表名
    private String tableName;
    //数据库表名称
    private String dbTableName;
    //表注释
    private String tableRemark;
    //表注释的时间
    private String formatterTime;
    //主键
    private List<String> dbPrimaryNames;
    //需要导入的包
    Set<String> importPackages;
    //所有列
    List<ColumnParams> columns;
    //主键列
    List<ColumnParams> primarys;

    public ColumnParams getColumnParams(String dbColumnName){
        List<ColumnParams> list = columns.stream().filter(item -> dbColumnName.equals(item.getDbColumnName())).collect(Collectors.toList());
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}
