package entity;

import lombok.Data;

import java.util.List;

/**
 * @Date: 21:38 2019/6/7
 * @Author: Kermit Sun
 * @Description:
 */
@Data
public class DB {
        private String driver;
        private String url;
        private String username;
        private String password;
        private String catalog;
        private String schemaPattern;
        private String tableNamePattern;
        private String[] types;
        private List<String> tables;
}