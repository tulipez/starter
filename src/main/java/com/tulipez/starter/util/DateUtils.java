package com.tulipez.starter.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateUtils {
	
	public static final SimpleDateFormat dateFormat_millis = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	public static final SimpleDateFormat dateFormat_millis_UTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	public static final SimpleDateFormat dateFormat_millis_GMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	public static final SimpleDateFormat dateFormat_seconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat dateFormat_seconds_file = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	public static final SimpleDateFormat dateFormat_seconds_file_concat = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat dateFormat_day = new SimpleDateFormat("yyyy-MM-dd");

	static {
		dateFormat_millis_UTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateFormat_millis_GMT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	public static String getDureeStringShort(long duree) {
		String result = "";
		if(duree < 60L*1000L) {
			//en secondes
			result += (duree/1000L) + "s";
		}
		else if(duree < 60L*60L*1000L) {
			//en minutes
			result += (duree/1000L/60L) + "m";
		}
		else if(duree < 24L*60L*60L*1000L) {
			//en heures
			result += (duree/1000L/60L/60L) + "h";
		}
		else {
			//en jours
			result += (duree/1000L/60L/60L/24L) + "j";
		}
		return result;
	}
	
	
	public static String getDureeString(long duree) {

		long s = 0;
		long m = 0;
		long h = 0;
		long j = 0;
		
		long dureeJ = 24L * 60L * 60L * 1000L;
		if(duree >= dureeJ) {
			j = duree / dureeJ;
			duree -= j * dureeJ;
		}

		long dureeH = 60L * 60L * 1000L;
		if(duree >= dureeH) {
			h = duree / dureeH;
			duree -= h * dureeH;
		}
		
		long dureeM = 60L * 1000L;
		if(duree >= dureeM) {
			m = duree / dureeM;
			duree -= m * dureeM;
		}
		
		long dureeS = 1000L;
		if(duree >= dureeS) {
			s = duree / dureeS;
		}
		
		String resultValue = "";

		if(j!=0L) resultValue += j + " jour"+(j==1L?"":"s");
		if(h!=0L) resultValue += " " + h + " heure"+(h==1L?"":"s");
		if(m!=0L) resultValue += " " + m + " minute"+(m==1L?"":"s");
		if(s!=0L) resultValue += " " + s + " seconde"+(s==1L?"":"s");

		return resultValue;
	}
	
	public static String getDureeStringConcat(long duree) {

		long s = 0;
		long m = 0;
		long h = 0;
		long j = 0;
		
		long dureeJ = 24L * 60L * 60L * 1000L;
		if(duree >= dureeJ) {
			j = duree / dureeJ;
			duree -= j * dureeJ;
		}

		long dureeH = 60L * 60L * 1000L;
		if(duree >= dureeH) {
			h = duree / dureeH;
			duree -= h * dureeH;
		}
		
		long dureeM = 60L * 1000L;
		if(duree >= dureeM) {
			m = duree / dureeM;
			duree -= m * dureeM;
		}
		
		long dureeS = 1000L;
		if(duree >= dureeS) {
			s = duree / dureeS;
		}
		
		String resultValue = "";

		if(j!=0L) resultValue += j + "j";
		if(h!=0L) resultValue += h + "h";
		if(m!=0L) resultValue += m + "m";
		if(s!=0L) resultValue += s + "s";

		return resultValue;
	}
	
	public static String getDureeStringConcatSimple(long duree) {

		long s = 0;
		long m = 0;
		long h = 0;
		long j = 0;
		
		long dureeJ = 24L * 60L * 60L * 1000L;
		if(duree >= dureeJ) {
			j = duree / dureeJ;
			duree -= j * dureeJ;
		}

		long dureeH = 60L * 60L * 1000L;
		if(duree >= dureeH) {
			h = duree / dureeH;
			duree -= h * dureeH;
		}
		
		long dureeM = 60L * 1000L;
		if(duree >= dureeM) {
			m = duree / dureeM;
			duree -= m * dureeM;
		}
		
		long dureeS = 1000L;
		if(duree >= dureeS) {
			s = duree / dureeS;
		}
		
		String resultValue = "";

		if(j!=0L) resultValue += j + "j";
		if(h!=0L) resultValue += h + "h";
		if(m!=0L) resultValue += m + "m";
		if(s!=0L) resultValue += s + "s";

		return resultValue;
	}
	
}
