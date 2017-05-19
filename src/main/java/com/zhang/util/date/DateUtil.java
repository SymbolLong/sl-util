package com.zhang.util.date;

import com.zhang.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Administrator
 *
 */
public class DateUtil {
	private static final String YYYY_MM_DD = "yyyy-MM-dd HH:mm:ss";
	
	private static final String EMPTY_DATE = "1111-11-11 00:00:00";
	public static String getStrFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
		if(date==null|| StringUtil.isEmpty(date.toString())||EMPTY_DATE.equals(date.toString())){
			return "您是第一次登录";
		}
		return sdf.format(date);
	}
	
	public static String getStrFromDateExceptTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(date==null||StringUtil.isEmpty(date.toString())||EMPTY_DATE.equals(date.toString())){
			return "";
		}
		return sdf.format(date);
	}
	
	public static Date getDateFromStr(String dateStr){
		
	      return getFormatDate(dateStr, YYYY_MM_DD);
	}

	public static Date getFormatDate(String date,String format){
	    SimpleDateFormat sdf = new SimpleDateFormat(format);   
        Date d=null;
        try {
        	d= sdf.parse(date);
		} catch (ParseException e) {
			return d;
		}
        return d;
	}
	
	
	
	// 获得当天0点时间
	public static Date getTimesMorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得当天24点时间
	public static Date getTimesNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得传入时间的24点
	 * @param day
	 * @return
     */
	public static Date getTimesNight(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本周日24点时间
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}
	
	
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		
		return calendar.getTime();
	}

	/**    
     * 得到几天后的时间    
     *     
     * @param d    
     * @param day    
     * @return    
     */     
    public static Date getDateAfter(Date d, int day) {      
       Calendar now = Calendar.getInstance();      
        now.setTime(d);      
       now.set(Calendar.DATE, now.get(Calendar.DATE) + day);      
        return now.getTime();      
    } 
    
    /**
     * 获取几个月之后的时间
     * 
     * @return
     */
    public static Date getMonthAfter(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, n);
//        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
//        String lastMonth = dft.format(cal.getTime());
//        return lastMonth;
        return cal.getTime();
    }
    
    /**
     *  相差几个月 
     * 
     */
    public static int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = sFormat.parse(sFormat.format(start));
            end = sFormat.parse(sFormat.format(end));
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        startCalendar.add(Calendar.MONTH, 1);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        if(endCalendar.compareTo(startCalendar) <= 0){
            return 1;
        }
        return 0;
    }
    
	/**    
     * 得到一年前的时间    
     *     
     * @param d    
     * @return    
     */     
    public static Date getDateLastYear(Date d) {      
       Calendar cal = Calendar.getInstance();      
       cal.setTime(d);      
       cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) -1);      
       return cal.getTime();      
    } 
}
