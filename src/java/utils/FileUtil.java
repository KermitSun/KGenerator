package utils;

import java.io.File;
import java.io.IOException;

/**
 * @Date: 18:01 2019/6/7
 * @Author: Kermit Sun
 * @Description: 文件工具
 */
public class FileUtil {
    /**
     * @Date: 18:02 2019/6/7
     * @Author: Kermit Sun
     * @Description: 创建文件或文件夹
     */
    public static File create(String filePath) throws IOException {
            File file = new File(filePath);
            File parentDir = new File(file.getParent());
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                if(file.isDirectory()){
                    file.mkdir();
                }else{
                    file.createNewFile();
                }
            }
            return file;
    }

    /**
     * @Date: 18:03 2019/6/7
     * @Author: Kermit Sun
     * @Description: 删除文件或文件夹
     */
    public static void delete(String filePath) throws IOException {
        delete(new File(filePath));
    }
    public static void empty(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.isDirectory()){
            for (File listFile : file.listFiles()) {
                delete(listFile);
            }
        }
    }

    private static void delete(File file){
        if(!file.exists()){
            return;
        }
        if(file.isFile()){
            file.delete();
            return;
        }
        for(File f:file.listFiles()){
            delete(f);
        }
        file.delete();
    }
}