package transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 23:57 2019/6/7
 * @Author: Kermit Sun
 * @Description: 数据库字段对应实体类字段转换
 */
public class DBTypeTransaction {
    private static String[] longs = new String[] { "SMALLINT", "MEDIUMINT", "BIGINT" };
    private static String[] strings = new String[] { "VARCHAR", "CHAR", "TEXT", "MEDIUMTEXT" };
    private static String[] ints = new String[] { "INTEGER","INT", "BIT", "BOOLEAN","TINYINT" };
    private static String[] bigdecimals = new String[] { "FLOAT", "DOUBLE", "DECIMAL" };
    private static String[] dates = new String[] { "DATE", "TIME", "DATETIME", "TIMESTAMP", "YEAR" };

    /**
     *@Date: 23:19 2019/6/7
     *@Author: Kermit Sun
     *@Description: 数据库对应实体类类型转换
     */
    public static Map<String, String> trans(String dbType, int columnSize) {
        Map<String, String> rs = new HashMap<String, String>();
        if (Arrays.asList(longs).contains(dbType)) {
            rs.put("importPackage", "java.lang.Long");
            rs.put("typeName", "Long");
        } else if (Arrays.asList(strings).contains(dbType)) {
            rs.put("importPackage", "java.lang.String");
            rs.put("typeName", "String");
        } else if (Arrays.asList(ints).contains(dbType)) {
            rs.put("importPackage", "java.lang.Integer");
            rs.put("typeName", "Integer");
        } else if (Arrays.asList(bigdecimals).contains(dbType)) {
            rs.put("importPackage", "java.math.mathBigDecimal");
            rs.put("typeName", "BigDecimal");
        } else if (Arrays.asList(dates).contains(dbType)) {
            rs.put("importPackage", "java.util.Date");
            rs.put("typeName", "Date");
        }  else {
            rs.put("importPackage", "java.lang.Object");
            rs.put("typeName", "Object");
        }
        return rs;
    }
}