package com.tools.common.util;

/**
 * 代码生成工具类
 * 爪哇笔记：https://blog.52itstyle.vip
 */
public class GenUtils {

    /**
     * 由数据库表名生成实体类名
     * @param tableName
     * @return
     */
    public static String allInitialCapital(String tableName) {
        if (org.springframework.util.StringUtils.isEmpty(tableName)) {
            return null;
        }
        tableName = allLowerCase(tableName);
        String[] tableNameArray = splitName(tableName);
        StringBuffer entryName = new StringBuffer();
        for (String part : tableNameArray) {
            entryName.append(initialCapital(part));
        }
        return entryName.toString();
    }
    /**
     * 由数据库列名生成实体类属性名
     * @param columnName
     * @return
     */
    public static String secInitialCapital(String columnName) {
        if (org.springframework.util.StringUtils.isEmpty(columnName)) {
            return null;
        }
        columnName = allLowerCase(columnName);
        String[] columnNameArray = splitName(columnName);
        StringBuffer fieldName = new StringBuffer();
        for (int i = 0; i < columnNameArray.length; i++) {
            String part = columnNameArray[i];
            if (0 == i) {
                fieldName.append(part);
            } else {
                fieldName.append(initialCapital(part));
            }
        }
        return fieldName.toString();
    }
    /**
     * 所有字母转成小写
     * @return
     */
    public static String allLowerCase(String str) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return str;
        }
        return str.toLowerCase();
    }
    /**
     * 功能：将输入字符串的首字母改成大写
     * @param str
     * @return
     */
    public static String initialCapital(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
    /**
     * 分解名称
     * @param str
     * @return
     */
    public static String[] splitName(String str) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return null;
        }
        return str.split("_");
    }
    /**
     * 首字母转小写
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0))){
            return s;
        } else{
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}