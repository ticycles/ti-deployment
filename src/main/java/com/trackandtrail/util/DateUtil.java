package com.trackandtrail.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String[] getCurrentWeekStartEndDate() {		
		// Get calendar set to current date and time
	      Calendar c = Calendar.getInstance();
	     // Set the calendar to monday of the current week
	     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	     // Print dates of the current week starting on Monday
	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       String firstDay = df.format(c.getTime());
	        c.add(Calendar.DATE, 6);
	        String lastDay = df.format(c.getTime());		
		return new String[] {firstDay, lastDay};
	}
	
	public static String[] getLastWeekStartEndDate() {		
		// Get calendar set to current date and time
	      Calendar c = Calendar.getInstance();
	     // Set the calendar to monday of the current week
	     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    // calculate monday week ago (moves cal 7 days back)
	      c.add(Calendar.DATE, -7);
	     // Print dates of the current week starting on Monday
	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       String firstDay = df.format(c.getTime());
	        c.add(Calendar.DATE, 6);
	        String lastDay = df.format(c.getTime());		
		return new String[] {firstDay, lastDay};
	}
	
	
	public static String getCurrentMonthStartDate() {		
		LocalDate startDate =  LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());		
//		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	     return df.format(startDate);		
	}

	
	public static String getCurrentMonthEndDate() {		
		LocalDate endDate =  LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());		
//		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	     return df.format(endDate);		
	}
	
	
	public static String getLastMonthStartDate() {		
		LocalDate startDate =  LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());		
//		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	     return df.format(startDate);		
	}

	
	public static String getLastMonthEndDate() {		
		LocalDate endDate =  LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());		
//		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	     return df.format(endDate);		
	}
	

	public static Date convertStringToDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}
	
//	public static Date currentDate() {
//		Date date = new Date();   
//	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");  
//	    return String todayDate= formatter.format(date);
//	}
//	
	
}
