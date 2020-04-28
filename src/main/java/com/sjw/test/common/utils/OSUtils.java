package com.sjw.test.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
public class OSUtils {
    /**
     * 功能：获取Linux系统cpu使用率
     * */
    public static int cpuUsage() {
        try {
            Map<?, ?> map1 = OSUtils.cpuinfo();
            Thread.sleep(3 * 1000);
            Map<?, ?> map2 = OSUtils.cpuinfo();

            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());
            log.info("user1:{},nice1:{},system1:{},idle1:{}",user1,nice1,system1,idle1);
            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());
            log.info("user2:{},nice2:{},system2:{},idle2:{}",user2,nice2,system2,idle2);
            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;
            log.info("total1:{},total2:{},total:{}",total1,total2,total);
            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;
             log.info("totalIdle1:{},totalIdel2:{},totalidel:{}",totalIdle1,totalIdle2,totalidle);
            float cpusage = (total / totalidle) * 100;

            log.info("cpusage:{}",cpusage);
            return (int) cpusage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 功能：CPU使用信息
     * */
    public static Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	if(buffer != null){
            		buffer.close();
            	}
                if(inputs != null){
                	inputs.close();
                }
                
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 功能：内存使用率
     * */
    public static int memoryUsage() {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null)
                    break;
                int beginIndex = 0;
                int endIndex = line.indexOf(":");
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace("kB", "").trim();
                    map.put(key, value);
                }
            }

            long memTotal = Long.parseLong(map.get("MemTotal").toString());
            long memFree = Long.parseLong(map.get("MemFree").toString());
            long memused = memTotal - memFree;
            long buffers = Long.parseLong(map.get("Buffers").toString());
            long cached = Long.parseLong(map.get("Cached").toString());
            log.info("memTotal:{},memFree:{},menused:{},buffers:{},cached:{}",memTotal,memFree,memused,buffers,cached);
            double usage = (double) (memused - buffers - cached) / memTotal * 100;
            log.info("usage:{}",usage);
            return (int) usage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }
    
    public static String exeCmd(String commandStr) {
    	 
        String result = null;
        try {
            String[] cmd = new String[]{"/bin/sh", "-c",commandStr};
            Process ps = Runtime.getRuntime().exec(cmd);
 
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                //执行结果加上回车
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
