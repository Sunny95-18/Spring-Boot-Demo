package com.sjw.test.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/8/13 9:26
 */
public class StringUtils {
    public static int StrToInt(String str) {
        if (str.length() == 0)
            return 0;
        char[] chars = str.toCharArray();
        // 判断是否存在符号位
        int flag = 0;
        if (chars[0] == '+')
            flag = 1;
        else if (chars[0] == '-')
            flag = 2;
        int start = flag > 0 ? 1 : 0;
        int res = 0;// 保存结果
        for (int i = start; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {// 调用Character.isDigit(char)方法判断是否是数字，是返回True，否则False
                int temp = chars[i] - '0';
                res = res * 10 + temp;
            } else {
                return 0;
            }
        }
        return flag != 2 ? res : -res;

    }
    public static void main(String[] args) {
//        String s = "-12312312a";
////        System.out.println("使用库函数转换：" + Integer.valueOf(s));
//        int res =StrToInt(s);
//        System.out.println("使用自己写的方法转换：" + res);
        int length=10000000;
        List<LogData> list=new ArrayList<LogData>(length);

        System.out.println("start:"+new Date().toString());
        for(int i=0;i<length;i++){
            LogData logData=new LogData();
            logData.setId(i);
            logData.setType("用户模块");
            logData.setContent("用户登录了该系统");
            logData.setDate(new Date().toString());
            list.add(logData);
        }
        System.out.println("end:"+new Date().toString());
    }

}
 class LogData{

    public int id;
    public String type;
    public String content;
    public String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
