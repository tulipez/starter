package com.tulipez.starter.common.log;

import java.io.OutputStream;
import java.io.PrintStream;

import com.tulipez.starter.common.FreeMobileSmsSender;

public class SmsPrintStream extends PrintStream {
	
	private FreeMobileSmsSender freeMobileSmsSender;
	
	//buffer
	private Long lastSmsTime;
	private long minSmsTimeInterval = 24L * 60L *60L * 1000L;
	
	public SmsPrintStream(OutputStream out) {
		this(out, null);
	}
	
	public SmsPrintStream(OutputStream out, FreeMobileSmsSender freeMobileSmsSender) {
		super(out, true);
		this.freeMobileSmsSender = freeMobileSmsSender;
	}
	
	public @Override void println(Object o) {
		
		if(o!=null) {
			String s = String.valueOf(o);
			if(!s.startsWith("\tat ")) {
				if(freeMobileSmsSender!=null) {
					long currentTimeMillis = System.currentTimeMillis();
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
				if(freeMobileSmsSender!=null) {
					long currentTimeMillis = System.currentTimeMillis();
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
