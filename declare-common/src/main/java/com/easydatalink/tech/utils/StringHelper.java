/**
 * 2009-10-12 21:47:02
 */
package com.easydatalink.tech.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;

/**
 * @author bin_liu
 */
public class StringHelper {

	private static String phoneMatcher;

	static {
		StringBuffer query = new StringBuffer();
		query.append("147\\d{9}");
		query.append("|1[3,8]\\d{9}");
		query.append("|15\\[0,1,2,3,5,6,7,8,9]");
		query.append("|17[3,6,7,8]\\d{8}");
		query.append("|170[0,1,2,5,7,8,9]\\d{7}");

		phoneMatcher = query.toString();
	}

	/**
	 * 把原始MAC替换成连续的字符串
	 * 
	 * @param mac
	 * @return String
	 */
	public static String getMac(String mac) {
		return mac.replaceAll(":", "").replaceAll("-", "")
				.replaceAll("\\.", "").toUpperCase();
	}

	private StringHelper() {

	}

	public static Long toUnixTimestamp(Long timestamp) {
		Long r = timestamp / 1000;
		return r;
	}

	public static Long unixTimestampToJAVA(Long timestamp) {
		Long r = timestamp * 1000;
		return r;
	}

	public static boolean isIP(String addr) {
		if (isNull(addr) || addr.length() < 7 || addr.length() > 15
				|| "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();

		return ipAddress;
	}

	public static int compareCharterEqualsCount(String input, String compare) {
		int flag = 0;

		for (int i = 0; i < input.length(); i++) {
			if (i >= compare.length())
				break;

			if (input.charAt(i) == compare.charAt(i))
				flag++;

		}
		return flag;
	}

	public static String convertUnicode(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	/**
	 * 判断一个字符串是否是由英文，数字，下划线组成
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isAlphanumeric(String str) {
		int flag = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			int n = (int) c;

			if (n >= 0 && n <= 9) {
				// num
				continue;
			} else if (n >= 65 && n <= 90) {
				// en b
				continue;
			} else if (n == 95) {
				// _
				continue;
			} else if (n >= 97 && n <= 122) {
				// en s
				continue;
			}
			flag++;
		}
		if (flag == 0)
			return true;
		else
			return false;
	}

	/**
	 * 得到系统当前时间
	 * 
	 * @return String
	 */
	public static String getSystime() {
		DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dft.format(new Date());
	}

	/**
	 * 按格式获取系统当前时间
	 * 
	 * @param format
	 * @return String
	 */
	public static String getSystime(String format) {
		DateFormat dft = new SimpleDateFormat(format);
		return dft.format(new Date());
	}

	/**
	 * 按格式获取系统当前时间
	 * 
	 * @param format
	 * @return String
	 */
	public static List<String> getTimeDistance(String format, int distance) {
		List<String> dateList = new ArrayList<String>();

		for (int i = 0; i < distance; i++) {
			Calendar curCal = Calendar.getInstance();
			SimpleDateFormat datef = new SimpleDateFormat(format);
			curCal.add(Calendar.MONTH, -i);
			Date beginTime = curCal.getTime();
			String time = datef.format(beginTime);
			dateList.add(time);
		}

		return dateList;
	}

	/**
	 * 按格式获取系统当前时间
	 * 
	 * @param format
	 * @return String
	 */
	public static String getSystime(String format, long timestamp) {
		DateFormat dft = new SimpleDateFormat(format);
		return dft.format(new Date(timestamp));
	}

	/**
	 * 得到上月的这一天0点，返回格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getPreMonth() {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

		curCal.add(Calendar.MONTH, -1);
		Date beginTime = curCal.getTime();
		String time = datef.format(beginTime) + " 00:00:00";
		return time;
	}

	/**
	 * 得到下月的这一天0点，返回格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getNextMonth() {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

		curCal.add(Calendar.MONTH, 1);
		Date beginTime = curCal.getTime();
		String time = datef.format(beginTime) + " 00:00:00";
		return time;
	}

	/**
	 * 指定时间的月份第一天
	 * 
	 * @param time
	 * @return String
	 */
	public static String getFirstDay(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		String sTime = datef.format(beginTime) + " 00:00:00";

		return sTime;
	}

	/**
	 * 指定时间的月份第一天
	 * 
	 * @param time
	 * @return String
	 */
	public static Long getFirstDayForTime(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		String sTime = datef.format(beginTime) + " 00:00:00";
		Long res = null;
		try {
			res = datef.parse(sTime).getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 指定时间的月份第一天
	 * 
	 * @param time
	 * @return String
	 */
	public static Timestamp getFirstDayForTimestamp(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		String sTime = datef.format(beginTime) + " 00:00:00";
		Long res = null;
		try {
			res = datef.parse(sTime).getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(res);
	}

	/**
	 * 指定时间的月份最后一天
	 * 
	 * @param time
	 * @return String
	 */
	public static String getEndDay(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DATE, 1);
		curCal.roll(Calendar.DATE, -1);
		Date endTime = curCal.getTime();
		String eTime = datef.format(endTime) + " 23:59:59";

		return eTime;
	}

	/**
	 * 指定时间的月份最后一天
	 * 
	 * @param time
	 * @return Long
	 */
	public static Long getEndDayForTime(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DATE, 1);
		curCal.roll(Calendar.DATE, -1);
		Date endTime = curCal.getTime();
		String eTime = datef.format(endTime) + " 23:59:59";
		Long res = null;
		try {
			res = datef.parse(eTime).getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 指定时间的月份最后一天
	 * 
	 * @param time
	 * @return Long
	 */
	public static Timestamp getEndDayForTimestamp(Long time) {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		curCal.setTime(new Date(time));
		curCal.set(Calendar.DATE, 1);
		curCal.roll(Calendar.DATE, -1);
		Date endTime = curCal.getTime();
		String eTime = datef.format(endTime) + " 23:59:59";
		Long res = null;
		try {
			res = datef.parse(eTime).getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Timestamp(res);
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param
	 * @return
	 */
	public static String getCurrYearFirst() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currentYear);
		Date currYearFirst = calendar.getTime();
		return sdf.format(currYearFirst);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param
	 * @return
	 */
	public static String getCurrYearLast() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currentYear);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return sdf.format(currYearLast);
	}

	public static String getLastMonthFirstDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = sdf.format(calendar.getTime());
		return firstday;
	}

	public static String getLastMonthLastDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calenda = Calendar.getInstance();
		calenda.set(Calendar.DAY_OF_MONTH, 1);
		calenda.add(Calendar.DATE, -1);
		String lasttday = sdf.format(calenda.getTime());
		return lasttday;
	}

	// 指定时间的上月第一天
	public static String getLastMonthFirstDay(String time)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String firstday = sdf.format(calendar.getTime());
		return firstday;
	}

	// 指定时间的上月最后一天
	public static String getLastMonthLastDay(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(time);
		Calendar calenda = Calendar.getInstance();
		calenda.setTime(date);
		calenda.set(Calendar.DAY_OF_MONTH, 1);
		calenda.add(Calendar.DATE, -1);
		String lasttday = sdf.format(calenda.getTime());
		return lasttday;
	}

	/**
	 * 得到默认日期格式的时间:yyyy-MM-dd HH:mm:ss
	 * 
	 * @param time
	 * @return Long
	 * @throws java.text.ParseException
	 */
	public static Long getTime(String time) throws java.text.ParseException {
		return getTime(time, "yyyy-MM-dd HH:mm:ss");
	}

	public static Timestamp getTimestamp(String time, String formart)
			throws java.text.ParseException {
		return new Timestamp(getTime(time, formart));
	}

	public static Timestamp getTimestamp(String time)
			throws java.text.ParseException {
		return new Timestamp(getTime(time));
	}

	/**
	 * 得到指定日期格式的时间
	 * 
	 * @param time
	 * @param formart
	 * @return Long
	 * @throws java.text.ParseException
	 */
	public static Long getTime(String time, String formart)
			throws java.text.ParseException {
		SimpleDateFormat datef = new SimpleDateFormat(formart);
		return datef.parse(time).getTime();
	}

	/**
	 * 当月第一天
	 * 
	 * @return String
	 */
	public static String getFirstDay() {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

		curCal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginTime = curCal.getTime();
		String sTime = datef.format(beginTime) + " 00:00:00";

		return sTime;
	}

	/**
	 * 当月最后一天
	 * 
	 * @return String
	 */
	public static String getEndDay() {
		Calendar curCal = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");

		curCal.set(Calendar.DATE, 1);
		curCal.roll(Calendar.DATE, -1);
		Date endTime = curCal.getTime();
		String eTime = datef.format(endTime) + " 23:59:59";

		return eTime;
	}

	/**
	 * 获得星期几
	 * 
	 * @return String
	 */
	public static String getWeek(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week < 0)
			week = 0;
		return weekDays[week];
	}

	/**
	 * 一 周 周日为第一天 获取一周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getSundayStateWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, 0);
		}
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		return imptimeBegin;
	}

	/**
	 * 一周 周日为第一天 获取一周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getSundayEndWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, 0);
		}
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		return imptimeEnd;
	}

	/**
	 * 一周 周一为第一天 获取一周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMondayStateWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeEnd = sdf.format(cal.getTime());
		return imptimeEnd;
	}

	/**
	 * 一周 周一为第一天 获取一周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMondayEndWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		return imptimeEnd;
	}

	/**
	 * 一周以周一为第一天
	 * 
	 * @param n
	 *            n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
	 * @return
	 */
	public static String getLastMonday(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar call = Calendar.getInstance();
		String monday;
		call.add(Calendar.DATE, n * 7);
		call.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		monday = sdf.format(call.getTime());
		return monday;
	}

	/**
	 * 得到指定类型时间间隔的之前日期
	 * 
	 * @param timeType
	 *            1-天，2-月<br>
	 * @param intervalNumber
	 *            间隔几天/几个月<br>
	 * @param currentTime
	 *            指定时间<br>
	 * @return Long
	 * @throws java.text.ParseException
	 */
	public static Long getBeforeTimeLong(int timeType, int intervalNumber,
			long currentTime) throws java.text.ParseException {
		String time = getBeforeTimeStr(timeType, intervalNumber, currentTime);
		return getTime(time);
	}

	/**
	 * 得到指定类型时间间隔的之前日期
	 * 
	 * @param timeType
	 *            1-天，2-月<br>
	 * @param intervalNumber
	 *            间隔几天/几个月<br>
	 * @param currentTime
	 *            指定时间<br>
	 * @return String
	 */
	public static String getBeforeTimeStr(int timeType, int intervalNumber,
			long currentTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(currentTime));

		if (timeType == 1)
			calendar.add(Calendar.DATE, -intervalNumber); // 得到前一天
		else if (timeType == 2)
			calendar.add(Calendar.MONTH, -intervalNumber); // 得到前一个月
		else if (timeType == 3)
			calendar.add(Calendar.HOUR, -intervalNumber); // 得到前一个月

		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR)).append("-");
		sb.append(calendar.get(Calendar.MONTH) + 1).append("-");
		sb.append(calendar.get(Calendar.DATE)).append(" ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
		sb.append(calendar.get(Calendar.MINUTE)).append(":");
		sb.append(calendar.get(Calendar.SECOND));

		return sb.toString();
	}

	/**
	 * 得到指定类型时间间隔的之后日期
	 * 
	 * @param timeType
	 *            1-天，2-月<br>
	 * @param intervalNumber
	 *            间隔几天/几个月<br>
	 * @param currentTime
	 *            指定时间<br>
	 * @return Long
	 * @throws java.text.ParseException
	 */
	public static Long getNextTimeLong(int timeType, int intervalNumber,
			long currentTime) throws java.text.ParseException {
		String time = getNextTimeStr(timeType, intervalNumber, currentTime);
		return getTime(time);
	}

	/**
	 * 得到指定类型时间间隔的之后日期
	 * 
	 * @param timeType
	 *            1-天，2-月<br>
	 * @param intervalNumber
	 *            间隔几天/几个月<br>
	 * @param currentTime
	 *            指定时间<br>
	 * @return String
	 */
	public static String getNextTimeStr(int timeType, int intervalNumber,
			long currentTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(currentTime));

		if (timeType == 1)
			calendar.add(Calendar.DATE, +intervalNumber); // 得到前一天
		else if (timeType == 2)
			calendar.add(Calendar.MONTH, +intervalNumber); // 得到前一个月

		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR)).append("-");
		sb.append(calendar.get(Calendar.MONTH) + 1).append("-");
		sb.append(calendar.get(Calendar.DATE)).append(" ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
		sb.append(calendar.get(Calendar.MINUTE)).append(":");
		sb.append(calendar.get(Calendar.SECOND));

		return sb.toString();
	}

	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}

	/**
	 * 得到UNICODE码
	 * 
	 * @param str
	 * @return String
	 */
	public static String getUnicode(String str) {

		if (str == null)
			return "";
		String hs = "";

		try {
			byte b[] = str.getBytes("UTF-16");
			for (int n = 0; n < b.length; n++) {
				str = (java.lang.Integer.toHexString(b[n] & 0XFF));
				if (str.length() == 1)
					hs = hs + "0" + str;
				else
					hs = hs + str;
				if (n < b.length - 1)
					hs = hs + "";
			}
			str = hs.toUpperCase().substring(4);
			char[] chs = str.toCharArray();
			str = "";
			for (int i = 0; i < chs.length; i = i + 4) {
				str += "\\u" + chs[i] + chs[i + 1] + chs[i + 2] + chs[i + 3];
			}
			return str;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return str;
	}

	public int compare(Object o1, Object o2) {
		String a = (String) o1;
		String b = (String) o2;

		if (!isDigit(a) || !isDigit(b))
			throw new IllegalArgumentException("the object must a digit");

		long aa = Long.valueOf(a).longValue();
		long bb = Long.valueOf(b).longValue();

		if (aa > bb)
			return 1;
		else if (aa < bb)
			return -1;

		return 0;
	}

	/**
	 * 去空格并将其替换成指定字符
	 * 
	 * @param content
	 * @return String
	 */
	public static String alterSpace(String content, String character) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			String c = new String(new char[] { content.charAt(i) });
			if (c.trim().length() == 0) {
				sb.append(character);
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * @param line
	 * @return boolean
	 */
	public static boolean isEmail(String line, int length) {
		return line.matches("\\w+[\\w.]*@[\\w.]+\\.\\w+$")
				&& line.length() <= length;
	}

	public static boolean isEmail(String line) {
		return line.matches("\\w+[\\w.]*@[\\w.]+\\.\\w+$");
	}

	/**
	 * 判断输入是否全是中文
	 * 
	 * @param value
	 * @param length
	 * @return boolean
	 */
	public static boolean isChineseName(String value, int length) {
		return value.matches("^[\u4e00-\u9fa5]+$") && value.length() <= length;
	}

	/**
	 * 判断字符串是否含有HTML标签
	 * 
	 * @param value
	 * @return boolean
	 */

	public static boolean isHaveHtmlTag(String value) {
		return value.matches("<(\\S*?)[^>]*>.*?</\\1>|<.*? />");
	}

	/**
	 * 检查URL是否合法
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isURL(String value) {
		return value.matches("[a-zA-z]+://[^\\s]*");
	}

	/**
	 * 检查IP是否合法
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean iskIP(String value) {
		return value.matches("\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}");
	}

	/**
	 * 检查QQ是否合法，必须是数字，且首位不能字幕
	 * 
	 * @param value
	 * @return boolean
	 */

	public static boolean isQQ(String value) {
		return value.matches("[1-9][0-9]{4,13}");
	}

	/**
	 * 检查邮编是否合法
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isPostCode(String value) {
		return value.matches("[1-9]\\d{5}(?!\\d)");
	}

	/**
	 * 检查身份证是否合为15位或18位
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isIDCard(String value) {
		return value.matches("\\d{15}|\\d{18}");
	}

	/**
	 * 检查输入的字符串是否为手机号
	 * 
	 * @param line
	 * @return List
	 */
	public static boolean isPhone(String line) {

		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);// 匹配移动手机号码
		m = p.matcher(line);
		if (m.matches())
			return true;
		return false;
	}

	/**
	 * 去掉手机号码前面的86和+86符号
	 * 
	 * @param phoneno
	 * @return String
	 */
	public static String fixPhoneno(String phoneno) {
		if (phoneno.length() > 11 && phoneno.startsWith("86"))
			return phoneno.substring(2);
		else if (phoneno.length() > 11 && phoneno.startsWith("+86"))
			return phoneno.substring(3);
		return phoneno;
	}

	/**
	 * 是否包含手机号
	 * 
	 * @param line
	 * @return boolean
	 */
	public static boolean hasPhone(String line) {
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);// 匹配移动手机号码
		m = p.matcher(line);
		if (m.find())
			return true;
		return false;
	}

	/**
	 * 从一行字符串中提取号码由左至右,11为数字符合手机号规则;
	 * 
	 * @param line
	 * @return String
	 */
	public static String getPhone(String line) {
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);// 匹配手机号码

		for (int i = 0; i < line.length(); i++) {

			m = p.matcher(line);
			if (m.find()) {
				String str = line.substring(m.start(), m.end());
				return str;
			}
		}

		return "";
	}

	/**
	 * 获取重复的手机号列表
	 * 
	 * @param content
	 * @return List<String>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getPhoneListByList(String content) {
		Collection c = getPhoneList(content, true);
		if (c == null)
			return null;
		return (List) c;
	}

	/**
	 * 获取不重复的手机号列表
	 * 
	 * @param content
	 * @return Set<String>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set<String> getPhoneListBySet(String content) {
		Collection c = getPhoneList(content, false);
		if (c == null)
			return null;
		return (Set) c;
	}

	public static Collection<String> getPhoneList(String content, boolean repeat) {
		Collection<String> res = null;
		if (repeat)
			res = new ArrayList<String>();
		else
			res = new HashSet<String>();
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);// 匹配手机号码
		m = p.matcher(content);
		while (m.find()) {
			res.add(content.substring(m.start(), m.end()));
		}
		return res;
	}

	public static Collection<String> getPhoneLists(String content,
			boolean repeat) {
		Collection<String> res = null;
		if (repeat)
			res = new ArrayList<String>();
		else
			res = new HashSet<String>();
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		phoneMatcher += "|[0-9]{1,11}";
		p = Pattern.compile(phoneMatcher);// 匹配手机号码
		m = p.matcher(content);
		while (m.find()) {
			res.add(content.substring(m.start(), m.end()));
		}
		return res;
	}

	/**
	 * 一段内容里总计有多少手机号
	 * 
	 * @param content
	 * @return int
	 */
	public static int getPhoneCount(String content) {
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);
		m = p.matcher(content);
		int flag = 0;
		while (m.find()) {
			flag++;
		}
		return flag;
	}

	/**
	 * 获取有多少不是手机号的总行数
	 * 
	 * @param content
	 * @return int
	 */
	public static int getNotPhoneCount(String content) {
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile("(?m)^.*$");// 以换行符切割，判断文本有多少行
		m = p.matcher(content);
		int flag = 0;
		while (m.find()) {
			flag++;
		}
		int phoneCount = getPhoneCount(content);
		return flag - phoneCount;
	}

	/**
	 * 按规定条件得到一个文本里的字符
	 * 
	 * @param text
	 * @param compile
	 * @return Set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set getTextBlock(String text, String compile) {

		Set set = new HashSet();
		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(compile);// 匹配条件
		m = p.matcher(text);
		while (m.find()) {
			// System.out.println(strMail.substring(m.start(),m.end()));
			String str = text.substring(m.start(), m.end());
			set.add(str);
		}
		return set;
	}

	/**
	 * 返回所有手机号码
	 * 
	 * @param strMail
	 * @return Set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set getCode(String strMail) {
		Set set = new HashSet();
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern.compile(phoneMatcher);// 匹配移动手机号码
		m = p.matcher(strMail);
		while (m.find()) {
			// System.out.println(strMail.substring(m.start(),m.end()));
			String str = strMail.substring(m.start(), m.end());
			set.add(str);
		}
		return set;
	}

	/**
	 * get email
	 * 
	 * @param content
	 * @return Set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set getMail(String content) {
		Set set = new HashSet();
		Pattern p = null; // 正则表达??
		Matcher m = null; // 操作的字符串
		p = Pattern
				.compile("(?i)(?<=\\b)[a-z0-9][-a-z0-9_.]+[a-z0-9]@([a-z0-9][-a-z0-9]+\\.)+[a-z]{2,4}(?=\\b)");// 匹配移动手机号码
		m = p.matcher(content);
		while (m.find()) {
			// System.out.println(strMail.substring(m.start(),m.end()));
			String str = content.substring(m.start(), m.end());
			set.add(str);
		}
		return set;
	}

	/**
	 * 生成指定位之间的随机数
	 * 
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int getRandom(int min, int max) {
		return (int) ((double) min + (int) (max - min) * Math.random());
	}

	/**
	 * 生成length位数字
	 * 
	 * @param length
	 * @return int
	 */
	public static int getRandom(int length) {
		return Integer.valueOf(getRand(length)).intValue();
	}// end...

	/**
	 * 生成length位数字
	 * 
	 * @param length
	 * @return long
	 */
	public static long getRandomL(int length) {
		return Long.valueOf(getRand(length)).longValue();
	}// end...

	/**
	 * 生成length位数字
	 * 
	 * @param length
	 * @return String
	 */
	public static String getRandomStr(int length) {
		return Long.toString(getRandomL(length));
	}// end...

	/**
	 * 生成随机数字的字符串
	 * 
	 * @param length
	 * @return String
	 */
	private static String getRand(int length) {
		StringBuffer t = new StringBuffer();
		for (int j = 0; j < length; j++) {
			double d = Math.random() * 10;
			int c = (int) d;
			t.append(c);
		}
		String result = t.toString();
		if (result.substring(0, 1).equalsIgnoreCase("0")) {
			result = result.replaceAll("0", "1");
		}

		if (result.length() > length) {
			result = result.substring(0, length);
		} else if (result.length() < length) {
			result = result + StringHelper.getRand(length - result.length());
		}

		return result;
	}// end...

	public static String getRandomChar(int length) {
		String defaultContent = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return getRandomCharForInput(defaultContent, length);
	}

	@SuppressWarnings("unused")
	public static String getRandomCharForInput(String inputContent, int length) {
		String chars = inputContent;
		String res = "";
		for (int i = 0; i <= length; i++) {
			double d = Math.random() * inputContent.length();
			int pos = new Double(d).intValue();
			res += new Character(inputContent.charAt(pos));
		}
		return res;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String[] str) {
		for (int i = 0; i < str.length; i++) {
			if (isNull(str[i]))
				return true;
		}
		return false;
	}

	/**
	 * 字符串是否为数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return (str == null || str.trim().length() == 0);
	}// end....

	/**
	 * 判断字符串中的每个字符是否都是数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDigit(String[] str) {
		for (int i = 0; i < str.length; i++) {
			if (!isDigit(str[i]))
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否都是数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDigit(String str) {
		if (isNull(str))
			throw new NullPointerException();
		for (int i = 0, size = str.length(); i < size; i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}// end....

	/**
	 * 得到STACK中信息
	 * 
	 * @param e
	 * @return String
	 */
	public static String getStackInfo(Throwable e) {
		StringBuffer info = new StringBuffer("Found Exception: ");

		info.append("\n");
		info.append(e.getClass().getName());
		info.append(" : ").append(e.getMessage() == null ? "" : e.getMessage());
		StackTraceElement[] st = e.getStackTrace();
		for (int i = 0; i < st.length; i++) {
			info.append("\t\n").append("at ");
			info.append(st[i].toString());
		}
		return info.toString();
	}// end..

	/**
	 * 将输入的字符按指定的正则式转换
	 * 
	 * @param str
	 * @param regEx
	 * @param code
	 * @return String
	 */
	private static String insteadCode(String str, String regEx, String code) {
		if (isNull(str))
			return "";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		String s = m.replaceAll(code);
		return s;
	}// end insteadCode method

	/**
	 * 将HTML的关键字替换输出
	 * 
	 * @param sourceStr
	 * @return String
	 */
	public static String toHtml(String sourceStr) {
		if (isNull(sourceStr))
			return "";
		String targetStr;
		targetStr = insteadCode(sourceStr, ">", "&gt;");
		targetStr = insteadCode(targetStr, "<", "&lt;");
		targetStr = insteadCode(targetStr, "\n", "<br>");
		targetStr = insteadCode(targetStr, " ", "&nbsp;");
		return targetStr.trim();
	}// end toHTML method

	/**
	 * 转义传参中的特殊字符问题 <li>+ URL ??号表示空??%2B<br/> <li>空格 URL中的空格可以??号或者编??%20<br/>
	 * <li>/ 分隔目录和子目录 %2F <br/> <li>? 分隔实际??URL 和参??%3F<br/> <li>% 指定特殊字符 %25
	 * <br/> <li># 表示书签 %23 <br/> <li>& URL 中指定的参数间的分隔??%26 <br/> <li>=URL
	 * 中指定参数的??%3D <br/>
	 * 
	 * @param parameter
	 * @return String
	 */
	public static String sendGetParameter(String parameter) {
		parameter = insteadCode(parameter, "&", "%26");
		parameter = insteadCode(parameter, " ", "%20");
		parameter = insteadCode(parameter, "%", "%25");
		parameter = insteadCode(parameter, "#", "%23");

		return parameter.trim();
	}

	/**
	 * 按指定的起始和终止字符，切割字符??
	 * 
	 * @param content
	 * @param start
	 * @param end
	 * @return String
	 */
	public static String spiltStr(String content, String start, String end) {
		if (!(content.indexOf(start) > -1) || !(content.indexOf(end) > -1))
			throw new IndexOutOfBoundsException(
					"[start Character or end Character,isn't exist in the specified content]");

		int s = content.indexOf(start);

		int e = start.equals(end) ? content.substring(s + 1).indexOf(end)
				: content.indexOf(end);

		if (s >= e)
			throw new IndexOutOfBoundsException(
					"[the Character end is smallness Character start]");
		else
			content = new String(content.substring(s + 1, e));

		return content.trim();
	}// end...

	/**
	 * 得到????中按指定分割符切好的????元素
	 * 
	 * @param content
	 * @param split
	 * @return String[]
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] splitStr(String content, String split) {

		if (content.indexOf(split) < 0) {
			return new String[] { content };
		}

		int s = 0;
		int e = content.indexOf(split);

		List list = new ArrayList();

		while (e <= content.length()) {
			if (content.indexOf(split) == -1 && list.size() != 0) {
				list.add(content);
				break;
			}
			list.add(content.substring(s, e));
			content = content.substring(e + 1, content.length());
			e = s + content.indexOf(split);
		}
		return (String[]) list.toArray(new String[0]);
	}// end...

	/**
	 * 按指定的字符位切割字符串(用于页面显示),剩余位字符用变量end中的字符表示 此方法带过滤转意字符功能
	 * 
	 * @param str
	 * @param num
	 * @param end
	 * @return String
	 * @throws Cm2Exception
	 */
	public static String splitStr(String str, int num, String end) {
		StringBuffer sb = new StringBuffer();
		if (str == null || end == null)
			throw new NullPointerException();
		if (str.length() > num)
			str = sb.append(str.substring(0, num)).append(end).toString();
		return toHtml(str);
	}// end of splitStr()

	/**
	 * 按左补零右对齐的规则格式化内??
	 * 
	 * @param content
	 * @param count
	 * @return String
	 */
	public static String completeText(String content, int count) {
		StringBuffer sb = new StringBuffer();
		if (count > content.length()) {
			for (int i = count - content.length(); content.length() < count
					&& i != 0; i--)
				sb.append("0");
		}
		sb.append(content);
		return sb.toString();
	}// end..

	/**
	 * 按左补零右对齐的规则格式化内??
	 * 
	 * @param content
	 * @param count
	 * @return String
	 */
	public static String completeText(int content, int count) {
		String c = Integer.toString(content);
		StringBuffer sb = new StringBuffer();
		if (count > c.length()) {
			for (int i = count - c.length(); c.length() < count && i != 0; i--)
				sb.append("0");
		}
		sb.append(content);
		return sb.toString();
	}// end..

	/**
	 * 按右补空格左对齐的规则格式化内容
	 * 
	 * @param content
	 * @param count
	 * @return String
	 */
	public static String completeTextSpace(String content, int count) {
		StringBuffer sb = new StringBuffer();

		sb.append(content);
		if (count > content.length()) {
			for (int i = 0; i < count - content.length(); i++)
				sb.append(" ");
		}

		return sb.toString();
	}// end..

	/**
	 * 按右补空格左对齐的规则格式化内容
	 * 
	 * @param content
	 * @param count
	 * @return String
	 */
	public static String completeTextSpace(int content, int count) {
		StringBuffer sb = new StringBuffer();
		String c = Integer.toString(content);
		sb.append(content);
		if (count > c.length()) {
			for (int i = 0; i < count - c.length(); i++)
				sb.append(" ");
		}
		return sb.toString();
	}// end..

	/**
	 * 特殊处理号码
	 * 
	 * @param phone
	 * @return String
	 */
	public static String processPhone(String phone) {
		StringBuffer sb = new StringBuffer();
		sb.append(phone.substring(0, 3)); // 3
		sb.append(phone.substring(5, 6)); // 6
		sb.append(phone.substring(4, 5)); // 5
		sb.append(phone.substring(3, 4)); // 4
		sb.append(phone.substring(6, 7)); // 7
		sb.append(phone.substring(9, 10)); // 10
		sb.append(phone.substring(7, 8)); // 8
		sb.append(phone.substring(8, 9)); // 9
		sb.append(phone.substring(10, 11)); // 11

		return sb.toString();
	}

	public static String toHex(String phoneno) {
		if (isNull(phoneno))
			return "";
		long i = new Long(phoneno).longValue();
		String i_16 = Long.toHexString(i);
		return i_16;
	}

	/**
	 * 判断字符是否是中文
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}

	/**
	 * @param b
	 * @return String
	 */
	public static String getBASE64(byte[] b) {
		String s = null;
		if (b != null) {
			s = new sun.misc.BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * @param s
	 * @return byte[]
	 */
	public static byte[] getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 把链接的中文转成可识别链
	 * 
	 * @param content
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getUrlEncode(String content)
			throws UnsupportedEncodingException {
		char[] a = content.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			if (isChinese(a[i])) {
				sb.append(URLEncoder.encode(a[i] + "", "GBK"));
			} else {
				sb.append(a[i]);
			}
		}

		return sb.toString();
	}

	public static String getUrlEncode(String content, String encode)
			throws UnsupportedEncodingException {
		char[] a = content.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			if (isChinese(a[i])) {
				sb.append(URLEncoder.encode(a[i] + "", encode));
			} else {
				sb.append(a[i]);
			}
		}

		return sb.toString();
	}

	public static String getUrlDecode(String content)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append(URLDecoder.decode(content, "GBK"));
		return sb.toString();
	}

	public static String getUrlDecode(String content, String encode)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append(URLDecoder.decode(content, encode));
		return sb.toString();
	}

	/**
	 * 转换十六进制编码为字符串
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String toStringHex(String bytes) {

		String hexString = "0123456789ABCDEF ";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static String fromUnicode(String str) {
		if (str.indexOf("\\u") < 0)
			return str;
		return fromUnicode(str.toCharArray(), 0, str.length(), new char[1024]);

	}

	public static String fromUnicode(char[] in, int off, int len,
			char[] convtBuf) {

		if (convtBuf.length < len) {

			int newLen = len * 2;

			if (newLen < 0) {

				newLen = Integer.MAX_VALUE;

			}

			convtBuf = new char[newLen];

		}

		char aChar;

		char[] out = convtBuf;

		int outLen = 0;

		int end = off + len;

		while (off < end) {

			aChar = in[off++];

			if (aChar == '\\') {

				aChar = in[off++];

				if (aChar == 'u') {

					// Read the xxxx

					int value = 0;

					for (int i = 0; i < 4; i++) {

						aChar = in[off++];

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							value = (value << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							value = (value << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							value = (value << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

							"Malformed \\uxxxx encoding.");

						}

					}

					out[outLen++] = (char) value;

				} else {

					if (aChar == 't') {

						aChar = '\t';

					} else if (aChar == 'r') {

						aChar = '\r';

					} else if (aChar == 'n') {

						aChar = '\n';

					} else if (aChar == 'f') {

						aChar = '\f';

					}

					out[outLen++] = aChar;

				}

			} else {

				out[outLen++] = (char) aChar;

			}

		}

		return new String(out, 0, outLen);

	}

	public static String removeSpace(String content) {
		String unicode = toUnicode(content);
		unicode = unicode.replaceAll("\\\\u0020", "");
		unicode = unicode.replaceAll("\\\\u3000", "");
		return fromUnicode(unicode);
	}

	/**
	 * 字符转16进制，以指定字符分割
	 * 
	 * @param str
	 * @return String
	 */
	public static String strToHex(String str, String split) {
		String res = "";
		byte[] b = str.getBytes();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			res += hex.toUpperCase();
			if (i < b.length - 1)
				res += split;
		}
		return res;
	}

	/**
	 * 字符转16进制，不分割
	 * 
	 * @param str
	 * @return String
	 */
	public static String strToHex(String str) {
		return strToHex(str, "");
	}

	/**
	 * 16进制转
	 * 
	 * @param bytes
	 * @return String
	 */
	public static String hexToStr(String bytes) {
		String hexString = "0123456789ABCDEF";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));

		return new String(baos.toByteArray());
	}

	/**
	 * 将字符串转成unicode
	 * 
	 * @param str
	 *            待转字符串
	 * @return unicode字符串
	 */
	public static String toUnicode(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	/**
	 * 转换为unicode的10进制字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String toUnicodeHexString(String str) {
		if (StringHelper.isNull(str))
			return "";

		String hexString = "0123456789ABCDEF ";
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();

	}

	/**
	 * 取一个整数的指定百分比的整数
	 * 
	 * @param max
	 * @param perc
	 * @return Integer
	 */
	public static Integer findPerc(Integer max, Integer perc) {
		Double a = max * (perc * 0.01);
		return new Long(Math.round(a)).intValue();
	}

	public static Double formartDecimalToDouble(Double d) {
		return new Double(formartDecimalToStr(d));
	}

	public static String formartDecimalToStr(Double d) {
		return formartDecimalToStr(d, "0.00");
	}

	public static String formartDecimalToStr(Float d) {
		return formartDecimalToStr(d.doubleValue(), "0.00");
	}

	public static Double formartDecimalToDouble(Double d, String formart) {
		return new Double(formartDecimalToStr(d, formart));
	}

	public static String formartDecimalToStr(Double d, String formart) {
		DecimalFormat decimalFormat = new DecimalFormat(formart);
		String resultStr = decimalFormat.format(d);
		return resultStr;
	}

	public static Integer[] strToInt(String[] str) {
		List<Integer> l = new ArrayList<Integer>();
		for (String e : str) {
			l.add(new Integer(e));
		}
		return (Integer[]) l.toArray(new Integer[0]);
	}

	public static String[] intToStr(String[] in) {
		List<String> l = new ArrayList<String>();
		for (String e : in) {
			l.add(new Integer(e).toString());
		}
		return (String[]) l.toArray(new String[0]);
	}

	public static int getDiffMinute(Date td1, Date td2) throws Exception {
		long lBeginTime = td1.getTime();
		long lEndTime = td2.getTime();

		int iminte = (int) ((lEndTime - lBeginTime) / (60 * 1000));
		return iminte;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static String arrayToString(Object[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			if (i < array.length - 1)
				sb.append(",");
		}
		return sb.toString();
	}

	public static String arrayToStringVarchar(Object[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append("'").append(array[i]).append("'");
			if (i < array.length - 1)
				sb.append(",");
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(Object[] obj) {
		if (obj == null || obj.length == 0)
			return null;
		List l = new ArrayList();
		for (Object o : obj) {
			l.add(o);
		}
		return l;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 按长度分割字符串
	 * 
	 * @param content
	 * @param len
	 * @return
	 */
	public static String[] split(String content, int len) {
		if (content == null || content.equals("")) {
			return null;
		}
		int len2 = content.length();
		if (len2 <= len) {
			return new String[] { content };
		} else {
			int i = len2 / len + 1;
			System.out.println("i:" + i);
			String[] strA = new String[i];
			int j = 0;
			int begin = 0;
			int end = 0;
			while (j < i) {
				begin = j * len;
				end = (j + 1) * len;
				if (end > len2)
					end = len2;
				strA[j] = content.substring(begin, end);
				// System.out.println(strA[j]+"<br/>");
				j = j + 1;
			}
			return strA;
		}
	}

	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}


	/**
	 * 验证汉字为true
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isLetterorDigit(String s) {
		if (s.equals("") || s == null) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				// if (!Character.isLetter(s.charAt(i))){
				return false;
			}
		}
		// Character.isJavaLetter()
		return true;
	}

	/**
	 * 判断某字符串是否为null,如果长度大于256,则返回256长度的子字符串,反之返回原串
	 * 
	 * @param str
	 * @return
	 */
	public static String checkStr(String str) {
		if (str == null) {
			return "";
		} else if (str.length() > 256) {
			return str.substring(256);
		} else {
			return str;
		}
	}

	/**
	 * 验证是不是Int validate a int string
	 * 
	 * @param str
	 *            the Integer string.
	 * @return true if the str is invalid otherwise false.
	 */
	public static boolean validateInt(String str) {
		if (str == null || str.trim().equals(""))
			return false;

		char c;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				return false;
		}

		return true;
	}


	/**
	 * 字节码转换成自定义字符串 内部调试使用
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2string(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static byte[] string2byte(String hs) {
		byte[] b = new byte[hs.length() / 2];
		for (int i = 0; i < hs.length(); i = i + 2) {
			String sub = hs.substring(i, i + 2);
			byte bb = new Integer(Integer.parseInt(sub, 16)).byteValue();
			b[i / 2] = bb;
		}
		return b;
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean empty(String param) {
		return param == null || param.trim().length() < 1;
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean empty(Date param) {
		return param == null;
	}
	/**
	 * 验证数字是否为空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean empty(BigDecimal param) {
		return param == null || "".equals(param);
	}
	/**
	 * 验证数字是否为空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean empty(Integer param) {
		return param == null || "".equals(param);
	}



	public static String encode(String str, String code) {
		try {
			return URLEncoder.encode(str, code);
		} catch (Exception e) {
			return "";
		}
	}

	public static String decode(String str, String code) {
		try {
			return URLDecoder.decode(str, code);
		} catch (Exception e) {
			return "";
		}
	}

	public static String nvl(String param) {
		return param != null ? param.trim() : "";
	}

	public static int parseInt(String param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(param);
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseInt(String param) {
		return parseInt(param, 0);
	}

	public static long parseLong(String param) {
		long l = 0L;
		try {
			l = Long.parseLong(param);
		} catch (Exception e) {
		}
		return l;
	}

	public static double parseDouble(String param) {
		return parseDouble(param, 1.0);
	}

	public static double parseDouble(String param, double t) {
		double tmp = 0.0;
		try {
			tmp = Double.parseDouble(param.trim());
		} catch (Exception e) {
			tmp = t;
		}
		return tmp;
	}

	public static boolean parseBoolean(String param) {
		if (empty(param))
			return false;
		switch (param.charAt(0)) {
		case 49: // '1'
		case 84: // 'T'
		case 89: // 'Y'
		case 116: // 't'
		case 121: // 'y'
			return true;

		}
		return false;
	}

	/**
	 * public static String replace(String mainString, String oldString, String
	 * newString) { if(mainString == null) return null; int i =
	 * mainString.lastIndexOf(oldString); if(i < 0) return mainString;
	 * StringBuffer mainSb = new StringBuffer(mainString); for(; i >= 0; i =
	 * mainString.lastIndexOf(oldString, i - 1)) mainSb.replace(i, i +
	 * oldString.length(), newString);
	 * 
	 * return mainSb.toString(); }
	 * 
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final String[] split(String str, String delims) {
		StringTokenizer st = new StringTokenizer(str, delims);
		ArrayList list = new ArrayList();
		for (; st.hasMoreTokens(); list.add(st.nextToken()))
			;
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String substring(String str, int length) {
		if (str == null)
			return null;

		if (str.length() > length)
			return (str.substring(0, length - 2) + "...");
		else
			return str;
	}

	public static boolean validateDouble(String str) throws RuntimeException {
		if (str == null)
			// throw new RuntimeException("null input");
			return false;
		char c;
		int k = 0;
		for (int i = 0, l = str.length(); i < l; i++) {
			c = str.charAt(i);
			if (!((c >= '0') && (c <= '9')))
				if (!(i == 0 && (c == '-' || c == '+')))
					if (!(c == '.' && i < l - 1 && k < 1))
						// throw new RuntimeException("invalid number");
						return false;
					else
						k++;
		}
		return true;
	}

	public static boolean validateMobile(String str, boolean includeUnicom) {
		if (str == null || str.trim().equals(""))
			return false;
		str = str.trim();

		if (str.length() != 11 || !validateInt(str))
			return false;

		if (includeUnicom
				&& (str.startsWith("130") || str.startsWith("133") || str
						.startsWith("131")))
			return true;

		if (!(str.startsWith("139") || str.startsWith("138")
				|| str.startsWith("137") || str.startsWith("136") || str
					.startsWith("135")))
			return false;
		return true;
	}

	public static boolean validateMobile(String str) {
		return validateMobile(str, false);
	}

	/**
	 * delete file
	 * 
	 * @param fileName
	 * @return -1 exception,1 success,0 false,2 there is no one directory of the
	 *         same name in system
	 */
	public static int deleteFile(String fileName) {
		File file = null;
		int returnValue = -1;
		try {
			file = new File(fileName);
			if (file.exists())
				if (file.delete())
					returnValue = 1;
				else
					returnValue = 0;
			else
				returnValue = 2;

		} catch (Exception e) {
			System.out.println("Exception:e=" + e.getMessage());
		} finally {
			file = null;
			// return returnValue;
		}
		return returnValue;
	}

	public static String gbToIso(String s) throws UnsupportedEncodingException {
		return covertCode(s, "GB2312", "ISO8859-1");
	}

	public static String covertCode(String s, String code1, String code2)
			throws UnsupportedEncodingException {
		if (s == null)
			return null;
		else if (s.trim().equals(""))
			return "";
		else
			return new String(s.getBytes(code1), code2);
	}

	public static String transCode(String s0)
			throws UnsupportedEncodingException {
		if (s0 == null || s0.trim().equals(""))
			return null;
		else {
			s0 = s0.trim();
			return new String(s0.getBytes("GBK"), "ISO8859-1");
		}
	}

	public static String GBToUTF8(String s) {
		try {
			StringBuffer out = new StringBuffer("");
			byte[] bytes = s.getBytes("unicode");
			for (int i = 2; i < bytes.length - 1; i += 2) {
				out.append("\\u");
				String str = Integer.toHexString(bytes[i + 1] & 0xff);
				for (int j = str.length(); j < 2; j++) {
					out.append("0");
				}
				out.append(str);
				String str1 = Integer.toHexString(bytes[i] & 0xff);
				for (int j = str1.length(); j < 2; j++) {
					out.append("0");
				}
				out.append(str1);
			}
			return out.toString();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	public static final String[] replaceAll(String[] obj, String oldString,
			String newString) {
		if (obj == null) {
			return null;
		}
		int length = obj.length;
		String[] returnStr = new String[length];
		String str = null;
		for (int i = 0; i < length; i++) {
			returnStr[i] = replaceAll(obj[i], oldString, newString);
		}
		return returnStr;
	}

	public static String replaceAll(String s0, String oldstr, String newstr) {
		if (s0 == null || s0.trim().equals(""))
			return null;
		StringBuffer sb = new StringBuffer(s0);
		int begin = 0;
		// int from = 0;
		begin = s0.indexOf(oldstr);
		while (begin > -1) {
			sb = sb.replace(begin, begin + oldstr.length(), newstr);
			s0 = sb.toString();
			begin = s0.indexOf(oldstr, begin + newstr.length());
		}
		return s0;
	}


	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static final String escapeHTMLTags(String input) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}
		// Use a StringBuffer in lieu of String concatenation -- it is
		// much more efficient this way.
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static String formatHTMLOutput(String s) {
		if (s == null)
			return null;

		if (s.trim().equals(""))
			return "";

		String formatStr;
		formatStr = replaceAll(s, " ", "&nbsp;");
		formatStr = replaceAll(formatStr, "\n", "<br />");

		return formatStr;
	}

	/*
	 * 4舍5入 @param num 要调整的数 @param x 要保留的小数位
	 */
	public static double myround(double num, int x) {
		BigDecimal b = new BigDecimal(num);
		return b.setScale(x, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/*
	 * public static String getSubstring(String content,int i){ int varsize=10;
	 * String strContent=content; if(strContent.length()<varsize+1){ return
	 * strContent; }else{ int
	 * max=(int)Math.ceil((double)strContent.length()/varsize); if(i<max-1){
	 * return strContent.substring(i*varsize,(i+1)*varsize); }else{ return
	 * strContent.substring(i*varsize); } } }
	 */

	/**
	 * liuqs
	 * 
	 * @param param
	 * @param d
	 * @return
	 */
	public static int parseLongInt(Long param, int d) {
		int i = d;
		try {
			i = Integer.parseInt(String.valueOf(param));
		} catch (Exception e) {
		}
		return i;
	}

	public static int parseLongInt(Long param) {
		return parseLongInt(param, 0);
	}

	public static String returnString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 修改敏感字符编码
	 * 
	 * @param value
	 * @return
	 */
	public static String htmlEncode(String value) {
		String re[][] = { { "<", "&lt;" }, { ">", "&gt;" }, { "\"", "&quot;" },
				{ "\\′", "&acute;" }, { "&", "&amp;" } };

		for (int i = 0; i < 4; i++) {
			value = value.replaceAll(re[i][0], re[i][1]);
		}
		return value;
	}

	/**
	 * 防SQL注入
	 * 
	 * @param str
	 * @return
	 */
	public static boolean sql_inj(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_stra[] = inj_str.split("|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 正则表达式判断字符串是否包含中文
	 * */
	public static boolean isContainChinese(String str) {
		if(str == null || "".equals(str)){
			return false;
		}
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 正则表达式判断字符串是否数字
	 * */
	public static boolean isContainNumber(String str) {
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 正则判断是否是浮点数或者整数
	 * */
	public static boolean isNumber(String str) {
		if(str == null || "".equals(str)){
			return false;
		}
		// 采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
		// 可以判断正负、整数小数
		boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
		boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();
		return isInt || isDouble;
	}
	
	/**
	 * 统计某个字符串中字符出现次数
	 * jie.qin
	 * 2018.05.15
	 * */
	public static int countInStr(String str, String split){
		int count = 0;
		Pattern pattern = Pattern.compile(split);
		Matcher m = pattern.matcher(str);
		while(m.find()){
			count++;
		}
		return count;
	}

	
}
