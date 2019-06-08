package utils;

import com.alibaba.fastjson.JSON;
import entity.*;
import freemarker.template.Template;
import transaction.ColumnNameTransaction;
import transaction.DBTypeTransaction;
import transaction.TableNameTransaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 22:09 2019/6/7
 * @Author: Kermit Sun
 * @Description: 构建工具
 */
public class BuildUtil {
    public static void start() {
        try {
            //获取所有配置文件
            String configJson = ConfigUtil.read("/config/config.json");
            Map<String, List<String>> map = JSON.parseObject(configJson, Map.class);
            List<String> items = map.get("configs");
            //逐一处理每个config.json文件
            for (String item : items) {
                String itemConfigJson = ConfigUtil.read(item);
                //文本转换为配置对象
                Config config = ConfigUtil.createConfig(itemConfigJson);
                //处理配置对象
                disposeConfig(config);
                System.out.println("Build config '"+item+"' success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Date: 23:47 2019/6/7
     * @Author: Kermit Sun
     * @Description: 处理单个Congfig
     */
    private static void disposeConfig(Config config) {
        Connection conn = null;
        try {
            //获取数据库连接
            conn = JDBCUtil.getConn(config.getDb());
            disposeConnection(config, conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn);
        }
    }

    private static void disposeConnection(Config config, Connection conn) {
        ResultSet tableRs = null;
        try {
            //获取需要解析的模板
            List<String> templateNames = config.getExport().getItems().stream().map(item -> item.getTemplateName()).collect(Collectors.toList());
            Map<String, Template> templateMap = TemplateUtil.readTemplates(templateNames);
            //获取需要解析的表
            List<String> dbTableNames = config.getDb().getTables();
            DatabaseMetaData metaData = conn.getMetaData();
            //获取表
            DB db = config.getDb();
            tableRs = metaData.getTables(db.getCatalog(), db.getSchemaPattern(), db.getTableNamePattern(), db.getTypes());

            //获取格式化时间
            String timeFormatter = config.getParams().get("timeFormatter");
            String formatterTime = DateTimeFormatter.ofPattern(timeFormatter).format(LocalDateTime.now());
            config.getParams().put("formatterTime", formatterTime);
            while (tableRs.next()) {
                String dbTableName = tableRs.getString("TABLE_NAME");
                if (dbTableNames.isEmpty() || dbTableNames.contains(dbTableName)) {
                    disposeTable(config, metaData, tableRs, dbTableName, templateMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResultSet(tableRs);
        }
    }

    private static void disposeTable(Config config, DatabaseMetaData metaData, ResultSet tableRs, String dbTableName, Map<String, Template> templateMap) throws SQLException, IOException {
        //获取表数据
        TableParams tableParams = new TableParams();
        tableParams.setFormatterTime(config.getParams().get("formatterTime"));
        tableParams.setParams(config.getParams());
        String tableName = TableNameTransaction.trans(dbTableName);
        tableParams.setTableName(tableName);
        tableParams.setDbTableName(dbTableName);
        tableParams.setTableRemark(tableRs.getString("REMARKS"));
        ResultSet columnRs = metaData.getColumns(null, null, dbTableName, null);
        //获取列数据
        Set<String> importPackages = new HashSet();
        List<ColumnParams> columnParams = new ArrayList<>();
        while (columnRs.next()) {
            ColumnParams columnParam = new ColumnParams();
            String dbColumnName = columnRs.getString("COLUMN_NAME");
            columnParam.setColumnName(ColumnNameTransaction.trans(dbColumnName));
            columnParam.setDbColumnName(dbColumnName);
            String dbColumnType = columnRs.getString("TYPE_NAME");
            int columnSize = columnRs.getInt("COLUMN_SIZE");
            Map<String, String> typeInfo = DBTypeTransaction.trans(dbColumnType, columnSize);
            importPackages.add(typeInfo.get("importPackage"));
            columnParam.setTypeName(typeInfo.get("typeName"));
            columnParam.setDbTypeName(dbColumnType);
            columnParam.setColumnRemark(columnRs.getString("REMARKS"));
            columnParams.add(columnParam);
        }
        tableParams.setColumns(columnParams);
        tableParams.setImportPackages(importPackages);
        JDBCUtil.closeResultSet(columnRs);
        ResultSet primaryKeys = metaData.getPrimaryKeys(config.getDb().getCatalog(), null, dbTableName);
        List<ColumnParams> primarys = new ArrayList();
        while(primaryKeys.next()){
            String dbColumnName = primaryKeys.getString("COLUMN_NAME");
            ColumnParams columnParam = tableParams.getColumnParams(dbColumnName);
            primarys.add(columnParam);
        }
        tableParams.setPrimarys(primarys);
        JDBCUtil.closeResultSet(primaryKeys);

        String outPath = config.getExport().getOutPath();
        if (config.getExport().isClearDirs()) {
            FileUtil.empty(outPath);
            System.out.println("Empty Path:"+outPath);
        }
        //逐个生成模板
        for (Item item : config.getExport().getItems()) {
            //模板
            Template template = templateMap.get(item.getTemplateName());
            //路径替换并生成8
            String filePath = (outPath + item.getOutPath() + item.getFileName()).replaceAll("\\$\\{tableName\\}", tableName);
            //生成文件
            File file = FileUtil.create(filePath);
            FileWriter fw = new FileWriter(file);
            //模板内容写入文件
            TemplateUtil.write(template, tableParams, fw);
        }
        System.out.println("Build table '"+dbTableName+"' success!");
    }
}