package transaction;

import utils.StrUtil;

/**
 * @Date: 23:56 2019/6/7
 * @Author: Kermit Sun
 * @Description: 数据库表明对应实体类表名转换
 */
public class TableNameTransaction {
    /**
     *@Date: 22:45 2019/6/7
     *@Author: Kermit Sun
     *@Description: 定义数据库表名与实体类参数tableName的转换规则
     */
    public static String trans(String dbTableName){
        return new StrUtil(dbTableName).wipeTitle()._Up().firstUp().getStr();
    }
}