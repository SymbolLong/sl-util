package com.zhang.util.excel;

/**
 * 查询类型
 * @author senzhu
 *
 */
public enum QueryType {

	ALL_QUERY("全部", "-1"), 
	QY_QUERY("企业基本信息查询", "1"), 
	QYDWTZ_QUERY("企业对外投资查询", "2"),
	GRDWTZ_QUERY("个人投资任职查询", "3"),
//	BATCH_QUERY("批量查询", "4");
	ALL_QY_QUERY("企业综合信息查询", "5"),
	SFRZ_QUERY("身份认证信息查询", "6");

	private String label;

	private String code;

	private QueryType(String label, String code) {
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
