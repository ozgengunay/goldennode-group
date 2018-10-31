package com.thingabled.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeSet;

public class DateTimeUtils {
	public static Date getGmtDate(Date date, TimeZone tz) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String sd1 = sdf.format(date);
			sdf.setTimeZone(tz);
			return sdf.parse(sd1);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getGmtDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String sd1 = sdf.format(new Date());
			sdf.setTimeZone(TimeZone.getDefault());
			return sdf.parse(sd1);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static long getGmtUnixTimestamp() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String sd1 = sdf.format(new Date());
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.parse(sd1).getTime())/1000;
		} catch (ParseException e) {
			return 0;
		}
	}

	public static Date getLocalDate(Date gmtDate, TimeZone tz) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			sdf.setTimeZone(tz);
			String sd1 = sdf.format(gmtDate);
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			return sdf.parse(sd1);
		} catch (ParseException e) {
			return null;
		}
	}
	public enum Type {
		MINUTELY, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY
	}

	public enum Period {
		THISHOUR, TODAY, THISWEEK, THISMONTH, THISYEAR, LAST3MONTHS, LAST6MONTHS, LAST12MONTHS
	}

	public static Interval getIntervalFromPeriod(Period period, TimeZone tz) {
		// LOGGER.debug("Period :" + period.toString());
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTime(getLocalDate(getGmtDate(), tz));
		endDate.setTime(getLocalDate(getGmtDate(), tz));
		if (period.equals(Period.THISHOUR)) {
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.TODAY)) {
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.THISWEEK)) {
			while (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				startDate.add(Calendar.DATE, -1);
			}
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.THISMONTH)) {
			startDate.set(Calendar.DATE, 1);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.LAST3MONTHS)) {
			startDate.set(Calendar.DATE, 1);
			startDate.add(Calendar.MONTH, -2);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.LAST6MONTHS)) {
			startDate.set(Calendar.DATE, 1);
			startDate.add(Calendar.MONTH, -5);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.THISYEAR)) {
			startDate.set(Calendar.DATE, 1);
			startDate.set(Calendar.MONTH, 0);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		if (period.equals(Period.LAST12MONTHS)) {
			startDate.set(Calendar.DATE, 1);
			startDate.add(Calendar.MONTH, -11);
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
		}
		// LOGGER.debug("Start Date :" + startDate.toString());
		// LOGGER.debug("End Date :" + endDate.toString());
		return new Interval(startDate.getTime(), endDate.getTime(), null);
	}

	public static TreeSet<Interval> getIntervals(Period period, Type type,
			TimeZone tz) {
		if (type == Type.MINUTELY) {
			return getMinutelyMap(getIntervalFromPeriod(period, tz));
		}
		if (type == Type.HOURLY) {
			return getHourlyMap(getIntervalFromPeriod(period, tz));
		}
		if (type == Type.DAILY) {
			return getDailyMap(getIntervalFromPeriod(period, tz));
		}
		if (type == Type.WEEKLY) {
			return getWeeklyMap(getIntervalFromPeriod(period, tz));
		}
		if (type == Type.MONTHLY) {
			return getMonthlyMap(getIntervalFromPeriod(period, tz));
		}
		if (type == Type.YEARLY) {
			return getYearlyMap(getIntervalFromPeriod(period, tz));
		}
		return null;
	}

	private static TreeSet<Interval> getMinutelyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.MINUTE, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(
						startDate.getTime(),
						endDate.getTime(),
						format(2, startDate.get(Calendar.DATE))
								+ "/"
								+ format(2, startDate.get(Calendar.MONTH) + 1)
								+ "/"
								+ format(2, startDate.get(Calendar.YEAR))
								+ "  "
								+ format(2, startDate.get(Calendar.HOUR_OF_DAY))
								+ ":"
								+ format(2, startDate.get(Calendar.MINUTE))
								+ ":00"));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					format(2, startDate.get(Calendar.DATE)) + "/"
							+ format(2, startDate.get(Calendar.MONTH) + 1)
							+ "/" + format(2, startDate.get(Calendar.YEAR))
							+ "  "
							+ format(2, startDate.get(Calendar.HOUR_OF_DAY))
							+ ":" + format(2, startDate.get(Calendar.MINUTE))
							+ ":00"));
			startDate.add(Calendar.MINUTE, 1);
		}
		return map;
	}

	private static TreeSet<Interval> getHourlyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.HOUR_OF_DAY, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(
						startDate.getTime(),
						endDate.getTime(),
						format(2, startDate.get(Calendar.DATE))
								+ "/"
								+ format(2, startDate.get(Calendar.MONTH) + 1)
								+ "/"
								+ new Integer(startDate.get(Calendar.YEAR))
										.toString()
								+ "  "
								+ format(2, startDate.get(Calendar.HOUR_OF_DAY))
								+ ":00:00"));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					format(2, startDate.get(Calendar.DATE))
							+ "/"
							+ format(2, startDate.get(Calendar.MONTH) + 1)
							+ "/"
							+ new Integer(startDate.get(Calendar.YEAR))
									.toString() + "  "
							+ format(2, startDate.get(Calendar.HOUR_OF_DAY))
							+ ":00:00"));
			startDate.add(Calendar.HOUR_OF_DAY, 1);
		}
		return map;
	}

	private static TreeSet<Interval> getDailyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.DATE, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(startDate.getTime(), endDate.getTime(),
						format(2, startDate.get(Calendar.DATE))
								+ "/"
								+ format(2, startDate.get(Calendar.MONTH) + 1)
										.toString()
								+ "/"
								+ new Integer(startDate.get(Calendar.YEAR))
										.toString()));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					format(2, startDate.get(Calendar.DATE))
							+ "/"
							+ format(2, startDate.get(Calendar.MONTH) + 1)
							+ "/"
							+ new Integer(startDate.get(Calendar.YEAR))
									.toString()));
			startDate.add(Calendar.DATE, 1);
		}
		return map;
	}

	private static TreeSet<Interval> getWeeklyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.WEEK_OF_YEAR, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(startDate.getTime(), endDate.getTime(),
						new Integer(startDate.get(Calendar.YEAR)).toString()
								+ " - "
								+ format(2,
										startDate.get(Calendar.WEEK_OF_YEAR))
								+ ".week"));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					new Integer(startDate.get(Calendar.YEAR)).toString()
							+ " - "
							+ format(2, startDate.get(Calendar.WEEK_OF_YEAR))
							+ ".week"));
			startDate.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return map;
	}

	private static TreeSet<Interval> getMonthlyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.MONTH, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(startDate.getTime(), endDate.getTime(),
						new Integer(startDate.get(Calendar.YEAR)).toString()
								+ " - "
								+ startDate.getDisplayName(Calendar.MONTH,
										Calendar.LONG, Locale.US)));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					new Integer(startDate.get(Calendar.YEAR)).toString()
							+ " - "
							+ startDate.getDisplayName(Calendar.MONTH,
									Calendar.LONG, Locale.US)));
			startDate.add(Calendar.MONTH, 1);
		}
		return map;
	}

	private static TreeSet<Interval> getYearlyMap(Interval interval) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(interval.getStartDate());
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(interval.getEndDate());
		TreeSet<Interval> map = new TreeSet<Interval>();
		Calendar tmpStartDate = (Calendar) startDate.clone();
		while (startDate.before(endDate)) {
			tmpStartDate.add(Calendar.YEAR, 1);
			if (tmpStartDate.after(endDate)) {
				map.add(new Interval(startDate.getTime(), endDate.getTime(),
						new Integer(startDate.get(Calendar.YEAR)).toString()));
				break;
			}
			Calendar tmpCalendar = (Calendar) tmpStartDate.clone();
			tmpCalendar.add(Calendar.MILLISECOND, -1);
			map.add(new Interval(startDate.getTime(), tmpCalendar.getTime(),
					new Integer(startDate.get(Calendar.YEAR)).toString()));
			tmpStartDate.add(Calendar.MILLISECOND, 1);
			startDate.add(Calendar.YEAR, 1);
		}
		return map;
	}

	private static String format(int pad, int number) {
		int p = pad - new Integer(number).toString().length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < p; i++) {
			sb.append("0");
		}
		sb.append(new Integer(number).toString());
		return sb.toString();
	}

}
