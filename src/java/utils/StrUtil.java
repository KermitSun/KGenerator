package utils;

/**
 * @Date: 23:23 2019/6/7
 * @Author: Kermit Sun
 * @Description: 字符处理类
 */
public class StrUtil {
    private String str;
    public StrUtil(String str){
        this.str = str;
    }
    public StrUtil wipeTitle(){
        if(str.indexOf("t_") == 0){
            str = str.substring(2);
        }
        return this;
    }
    public StrUtil _Up(){
        int idx = str.indexOf("_");
        if(idx>=0){
            //如果_后边有字母
            if(str.length() > (idx+2)){
                str = str.replace(str.substring(idx, idx+2),str.substring(idx+1,idx+2).toUpperCase());
            }else{
                str = str.replace(str.substring(idx,idx+1),"");
            }
            _Up();
        }
        return this;
    }
    public StrUtil firstUp() {
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        } else {
            str = str.toUpperCase();
        }
        return this;
    }

    public StrUtil firstLow() {
        if (str.length() > 1) {
            str = str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
        } else {
            str = str.toLowerCase();
        }
        return this;
    }
    public String getStr(){
        return str;
    }
}