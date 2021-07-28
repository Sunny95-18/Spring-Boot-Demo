package com.sjw.test.common.utils;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/9/14 14:32
 */
public class TestUtils {

    public static void main(String[] args) {
//        Integer a=1;
//        Integer b=2;
//       System.out.println("a="+a);
//        System.out.println("b="+b);
//        change(a,b);
//        System.out.println("a="+a);
//        System.out.println("b="+b);

        int[] arr = { 1, 2, 3, 4, 5 };
        System.out.println(arr[0]);
        change(arr);
        System.out.println(arr[0]);
    }
    public static void change(int[] array) {
        // 将数组的第一个元素变为0
        array[0] = 0;
    }
    public static void change(Integer m,Integer n){
        Integer temp=m;
        m=n;
        n=temp;
    }
}
