package com.zhang.util.excel;

import java.io.IOException;
import java.io.InputStream;



public interface ExcelParser<T> {

	T parse(InputStream is) throws IOException;
	
	boolean supports(QueryType type);
}
