package com.tulipez.starter.common.log;

import java.io.OutputStream;
import java.io.PrintStream;

import com.tulipez.starter.common.FreeMobileSmsSender;
import com.tulipez.starter.util.DateUtil;

public class TimePrintStream extends PrintStream {
	
	private FreeMobileSmsSender freeMobileSmsSender;
//	private SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	//buffer
	private Long lastSmsTime;
	private long minSmsTimeInterval = 24L * 60L *60L * 1000L;
	
	public TimePrintStream(OutputStream out) {
		this(out, null);
	}
	
	public TimePrintStream(OutputStream out, FreeMobileSmsSender freeMobileSmsSender) {
		super(out, true);
		this.freeMobileSmsSender = freeMobileSmsSender;
	}
	
	public @Override void println(Object o) {
		
		if(o!=null) {
			String s = String.valueOf(o);
			if(!s.startsWith("\tat ")) {

				long currentTimeMillis = System.currentTimeMillis();
				print(DateUtil.dateFormat_millis.format(currentTimeMillis) + " ");

				if(freeMobileSmsSender!=null) {
					if(lastSmsTime==null || currentTimeMillis - lastSmsTime > minSmsTimeInterval) {
						lastSmsTime = currentTimeMillis;
						try {
							freeMobileSmsSender.send(s);
						} catch (Exception e) {}
					}
				}
			}
		}
		
		super.println(o);
	}
	public @Override void println(String s) {
		
		if(s!=null) {
			if(!s.startsWith("\tat ")) {

				long currentTimeMillis = System.currentTimeMillis();
				print(DateUtil.dateFormat_millis.format(currentTimeMillis) + " ");

				if(freeMobileSmsSender!=null) {
					if(lastSmsTime==null || currentTimeMillis - lastSmsTime > minSmsTimeInterval) {
						lastSmsTime = currentTimeMillis;
						try {
							freeMobileSmsSender.send(s);
						} catch (Exception e) {}
					}
				}
			}
		}
		super.println(s);
	}
}
