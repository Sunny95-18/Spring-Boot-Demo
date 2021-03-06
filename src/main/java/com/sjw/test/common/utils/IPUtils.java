package com.sjw.test.common.utils;

import com.sjw.test.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangyonglong
 * @date 2019/5/20 20:16
 *
 * IP地址
 */
public class IPUtils {
	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }
        
//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
        
        return ip;
    }public static byte[] memset(int c, int length) {
	    System.out.println("c:"+c);
        byte[] buffer = new byte[length];

        int i = 0;

        for (i = 0; i < length; i++) {
            buffer[i] = (byte) c;
        }

        return buffer;
    }

public static void test1(int a){
	    System.out.println("a:"+a);
}
    public static void main(String[] args) {
//        test1(0x00010100);
//        System.out.println(Math.pow(2,8)+Math.pow(2,16));
        byte[] pucData = new byte[10];
        System.out.println(pucData.length);
        for(int i=0;i<pucData.length;i++){
            System.out.println(pucData[i]);
        }
//        int a=0;
//        int b='\0';
//        System.out.println((byte)a==(byte)b);
//        System.out.println();
    }
}
