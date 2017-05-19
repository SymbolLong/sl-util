package com.zhang.util.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.yscredit.util.StringUtil;

/**
 * 
 * @author senzhu
 * 
 */
public final class DateUtil implements Serializable {

	private static final long serialVersionUID = 5L;
	private static DateUtil instance;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private DateUtil() {

	}
	
	public static String getStrFromDateExceptTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date==null){
			return "";
		}
		return sdf.format(date);
	}

	/**
	 * format date to String.
	 * 
	 * @param date
	 * @return pattern : 'yyyy-MM-dd'.
	 */
	public String formatDateToString(Date date) {
		if (date == null) {
			return "";
		}
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public Date parse(String date){
		if (date == null) {
			return null;
		}
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 转换成某天的零点整
	 * @param currentDate
	 * @return
	 */
	public Date converterStartDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		Date converteredStartDate = cal.getTime();
		return converteredStartDate;
	}
	
	/**
	 * 转换成某天的23:59:59
	 * @param currentDate
	 * @return
	 */
	public Date converterEndDate(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		Date converteredEndDate = cal.getTime();
		return converteredEndDate;
	}

	/**
	 * 获得所在月的第一天和最后一天.
	 * @param currentDate
	 * @return
	 */
	public List<Date> getStartDateAndEndDateInCurrentMonth(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		Date startDate = cal.getTime();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), days, 23, 59, 59);
		Date endDate = cal.getTime();
		List<Date> dates = new ArrayList<Date>();
		dates.add(startDate);
		dates.add(endDate);
		return dates;
	}
	
	/**
	 * 获得所在月的上个月的第一天和最后一天.
	 * @param currentDate
	 * @return
	 */
	public List<Date> getStartDateAndEndDateInPreviousMonth(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
		Date startDate = cal.getTime();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), days, 23, 59, 59);
		Date endDate = cal.getTime();
		List<Date> dates = new ArrayList<Date>();
		dates.add(startDate);
		dates.add(endDate);
		return dates;
	}
	
	public static synchronized DateUtil getInstance() {
		if (instance == null) {
			synchronized (DateUtil.class) {
				/** for double-checked if it is created. **/
				if (instance == null) {
					instance = new DateUtil();
				}
			}
		}
		return instance;
	}
}
