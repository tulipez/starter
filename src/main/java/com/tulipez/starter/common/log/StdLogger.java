package com.tulipez.starter.common.log;

import java.io.File;
import java.io.FileOutputStream;

import com.tulipez.starter.common.FreeMobileSmsSender;

public class StdLogger {

	public final static String MODE_CONSOLE = "MODE_CONSOLE";
	public final static String MODE_FILE = "MODE_FILE";
	public final static String MODE_CONSOLE_FILE = "MODE_CONSOLE_FILE";

	public StdLogger() {
	}
	
	public StdLogger(String mode) {
		this.mode = mode;
	}
	
	public StdLogger(String mode, File file) {
		this.mode = mode;
		this.file = file;
	}
	
	private String mode; 
	public void setMode(String mode) { this.mode = mode; }

	private File file; 
	public void setFile(File file) { this.file = file; }
	public File getFile() { return file; }

	private FreeMobileSmsSender freeMobileSmsSender; 
	public void setFreeMobileSmsSender(FreeMobileSmsSender freeMobileSmsSender) { this.freeMobileSmsSender = freeMobileSmsSender; }
	
	public void start() {
		
		if(file!=null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		try {
			switch (mode) {
			
			case MODE_CONSOLE: 
				
				System.setOut(new TimePrintStream(System.out));
				System.setErr(new TimePrintStream(System.err));
				
				break;
			
			case MODE_FILE:
				
				FileOutputStream fos = new FileOutputStream(file, true);
				System.setOut(new TimePrintStream(fos));
				System.setErr(new TimePrintStream(fos, freeMobileSmsSender));

				break;
				
			case MODE_CONSOLE_FILE:

				FileOutputStream fos2 = new FileOutputStream(file, true);
				System.setOut(new TimePrintStream(new DualOutputStream(System.out, fos2)));
				System.setErr(new TimePrintStream(new DualOutputStream(System.err, fos2), freeMobileSmsSender));

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
