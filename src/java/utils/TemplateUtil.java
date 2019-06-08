package utils;

import entity.TableParams;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 17:59 2019/6/7
 * @Author: Kermit Sun
 * @Description: 读取模板
 */
public class TemplateUtil {
    private static Map<String, Template> templateMap = new HashMap<>();
    /**
     *@Date: 20:22 2019/6/7
     *@Author: Kermit Sun
     *@Description: 初始化模板，读取templateNames模板内容
     */
    public static Map<String, Template> readTemplates(List<String> templateNames){
        for(String templateName:templateNames){
            Template template = templateMap.get(templateName);
            if(template == null){
                template = readTemplate(templateName);
                templateMap.put(templateName, template);
            }
        }
        return templateMap;
    }

    /**
     *@Date: 20:31 2019/6/7
     *@Author: Kermit Sun
     *@Description: 输出templateName文件
     */
    public static void write(Template template, TableParams params, FileWriter fw){
        try {
            template.process(params, fw);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *@Date: 20:23 2019/6/7
     *@Author: Kermit Sun
     *@Description: 读取templateName模板内容
     */
    private static Template readTemplate(String templateName){
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            // 设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(TemplateUtil.class, "/templates");
            // 在模板文件目录中找到名称为name的文件
            return cfg.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}