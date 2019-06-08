package transaction;

import entity.TableParams;

import java.util.Map;

/**
 * @Date: 0:44 2019/6/8
 * @Author: Kermit Sun
 * @Description: 将TableParams转换为模板识别的key-value
 */
public class ParamsTrans {
    public static Map<String, String> trans(TableParams tableParams){
        /*Map<String, String> tableMap = new HashMap();
        tableMap.putAll(tableParams.getParams());
        tableMap.put("tableName", tableParams.getName());
        tableMap.put("tableRemark", tableParams.getRemark());
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (ColumnParams column : tableParams.getColumns()) {
            Map<String, String> columnMap = new HashMap();
            columnMap.put("name",column.getColumnName());
            columnMap.put("package", column.getImportPackage());
            columnMap.put("typeNmae", column.getTypeName());
            columnMap.put("remark", column.getRemark());
            list.add(columnMap);
        }
        return tableMap;*/
        return null;
    }
}