package com.tulipez.starter.common.log;

import java.io.File;
import java.io.FileWriter;

public class FileLogger {
	
	private FileWriter writer;
	
	private boolean append = true;
	public boolean isAppend() { return append; }
	public void setAppend(boolean append) { this.append = append; }

	private File file;
	public File getFile() { return file; }
	public void setFile(File file) { this.file = file; }
	
	public FileLogger() {}
	public FileLogger(boolean append) { this.append = append; }
	public FileLogger(File file) { this.file = file; }
	public FileLogger(boolean append, File file) { this.append = append; this.file = file; }
	
	public void log(String s) {
		try {
			writer.append(s + "\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			writer = new FileWriter(file, append);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
