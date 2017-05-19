package com.zhang.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNoUtil {

	public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
	public final static Random random = new Random();

	public synchronized static String getOrderNo() {
		StringBuilder str = new StringBuilder();
		for (int i=0; i<4; i++) {
			str.append(random.nextInt(9));
		}
		return dateFormat.format(new Date()) + str;
	}

	public static void main(String[] args) {
		System.out.println(getOrderNo());
	}

}