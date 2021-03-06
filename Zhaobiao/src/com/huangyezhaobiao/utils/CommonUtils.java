package com.huangyezhaobiao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;

import android.util.Log;

/**
 * 一般性的工具类
 * @author shenzhixin
 *
 */
public class CommonUtils {
	public static String message = "";
	/**
	 * 把内容转成md5
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String ToMd5(String content) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("md5");
	
		byte [] bytes =  digest.digest(content.getBytes());
		StringBuffer buffer = new StringBuffer();
		for(byte b: bytes){
			int number = b & 0xff;//加盐
			String hex = Integer.toHexString(number);
			if(hex.length()==1){
				buffer.append("0");
			}
			
			buffer.append(hex);
		}
     	return buffer.toString();
	}
	
	/**
	 * 比较两个字符串大小的，如果第一个比第二个大，就返回true
	 * @return
	 */
	public static boolean compareTwoNumbers(String number1,String number2){
		Log.e("ashenVersion", "number1:"+number1+",number2:"+number2);
		number1 = number1.replace(".", "");
		number2 = number2.replace(".", "");
		Integer n1 = Integer.valueOf(number1);
		Integer n2 = Integer.valueOf(number2);
		Log.e("ashenVersion", "n1:"+n1+",n2:"+n2);
		return n1>=n2;
	}
}
