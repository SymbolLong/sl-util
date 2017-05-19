package com.zhang.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yscredit.util.excel.ExcelSheetParser.CellValue;

/**
 * 企业信息excel解析器
 * 
 * @author Thinkpad
 * 
 */
public class EnterpriseParser implements ExcelParser<List<EnterpriseExcelData>> {

	@Override
	public List<EnterpriseExcelData> parse(InputStream is) throws IOException {
		List<List<Object>> rowData = new ExcelSheetParser(is).getDatasInSheet(
				0, 1);
		List<EnterpriseExcelData> result = new ArrayList<EnterpriseExcelData>();
		for (int i = 0; i < rowData.size(); i++) {
			List<Object> colunmData = rowData.get(i);
			if (!colunmData.isEmpty()) {
//				EnterpriseExcelData enterpriseExcelData = new EnterpriseExcelData();
//				String key = colunmData.get(0).toString();
//				// 判断key是名称还是工商注册号
//				if (NumberUtils.isDigits(key)) {
//					enterpriseExcelData.setKey(key);
//					enterpriseExcelData.setKeyType(KeyType.REGISTERED_NO);
//					result.add(enterpriseExcelData);
//				} else if (StringUtils.isNotBlank(key)) {
//					enterpriseExcelData.setKey(key);
//					enterpriseExcelData.setKeyType(KeyType.ENTERPRISE_NAME);
//					result.add(enterpriseExcelData);
//				}
			}
		}
		return result;
	}

	public List<EnterpriseExcelData> parse2CellValue(InputStream is)
			throws IOException {
		List<List<CellValue>> rowData = new ExcelSheetParser(is)
				.getDatasInSheet2CellValue(0, 1);
		List<EnterpriseExcelData> result = new ArrayList<EnterpriseExcelData>();
		for (int i = 0; i < rowData.size(); i++) {
			List<CellValue> colunmData = rowData.get(i);
			if (!colunmData.isEmpty()) {
				EnterpriseExcelData enterpriseExcelData = new EnterpriseExcelData();
				boolean exists = false;
				for (CellValue cellValue : colunmData) {
					if (cellValue.getIndex() == 0
							&& StringUtils.isNotBlank(cellValue.getValue()
									.toString())) {
						enterpriseExcelData.setEntName(cellValue.getValue()
								.toString());
						exists = true;
					} else if (cellValue.getIndex() == 1
							&& StringUtils.isNotBlank(cellValue.getValue()
									.toString())) {
						enterpriseExcelData.setRegisteredNo(cellValue
								.getValue().toString());
						exists = true;
					}
				}
				if (exists) {
					result.add(enterpriseExcelData);
				}
			}
		}
		return result;
	}
	
	
	public List<EnterpriseExcelData> parse2CellValueForAlibaba(InputStream is) throws IOException {
		List<List<CellValue>> rowData = new ExcelSheetParser(is).getDatasInSheet2CellValue(0, 1);
		List<EnterpriseExcelData> result = new LinkedList<EnterpriseExcelData>();
		for (int i = 0; i < rowData.size(); i++) {

			List<CellValue> colunmData = rowData.get(i);
			if (!colunmData.isEmpty()) {
				EnterpriseExcelData enterpriseExcelData = new EnterpriseExcelData();
				CellValue cellValue = colunmData.get(0);
				String regNo="";
				String entName = cellValue.getValue().toString();
				CellValue cellValue2 = colunmData.get(1);
				if(cellValue2==null||cellValue2.getValue()==null||StringUtils.isBlank(cellValue2.getValue().toString())){
					regNo="";
				}else{
					regNo=cellValue2.getValue().toString();
				}
				enterpriseExcelData.setEntName(entName);
				enterpriseExcelData.setRegisteredNo(regNo);
				result.add(enterpriseExcelData);
			}

		}
		return result;
	}

	@Override
	public boolean supports(QueryType type) {
		if (type == null) {
			return false;
		}
		if (QueryType.ALL_QY_QUERY == type) {
			return true;
		}
		return false;
	}
}
