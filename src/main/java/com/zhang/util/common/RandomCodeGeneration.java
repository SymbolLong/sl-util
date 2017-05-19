package com.zhang.util.common;

import java.io.Serializable;
import java.util.Random;

public final class RandomCodeGeneration implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int RANDOM_CODE_MAX = 10;

	private RandomCodeGeneration() {

	}

	public static String generate(int codeLength) {
		StringBuilder code = new StringBuilder("");
		int getNum = 0;
		Random rd = new Random();
		do {
			getNum = Math.abs(rd.nextInt(RANDOM_CODE_MAX)); // 产生数字0-9的随机数
			code.append(getNum);
		} while (code.length() < codeLength);
		return code.toString();
	}
}
