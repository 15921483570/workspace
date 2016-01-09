/*
 * 文件名：StringUtils.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月25日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.gop.rpc.util;

public class StringUtils
{

    public static boolean isDouble(String str)
    {
        return str.matches("^[0-9]+(\\.[0-9]+)?$");

    }

    public static boolean isInt(String str)
    {
        return str.matches("^[1-9]\\d*$");
    }

    public static void main(String[] args)
    {
        System.out.println(isDouble("1"));

    }

}
