package com.zhang.util.excel;

public enum KeyType {

	ENTERPRISE_NAME("企业名称", "2"), REGISTERED_NO("工商注册号", "3");

	private String label;

	private String code;

	private KeyType(String label, String code) {
		this.label = label;
		this.code = code;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
}
