package transaction;

import utils.StrUtil;

/**
 * @Date: 0:11 2019/6/8
 * @Author: Kermit Sun
 * @Description:
 */
public class ColumnNameTransaction {
    public static String trans(String dbTableName){
        return new StrUtil(dbTableName).wipeTitle()._Up().firstLow().getStr();
    }
}