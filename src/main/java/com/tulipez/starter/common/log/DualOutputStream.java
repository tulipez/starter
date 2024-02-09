package com.tulipez.starter.common.log;

import java.io.IOException;
import java.io.OutputStream;

public class DualOutputStream extends OutputStream {
        private OutputStream o1;
        private OutputStream o2;
        public DualOutputStream(OutputStream o1, OutputStream o2) {
            this.o1 = o1;
            this.o2 = o2;
        }
        public void write(int b) throws IOException{
            if(o1!=null) o1.write(b);
            if(o2!=null) o2.write(b);
        }
        public void write(byte b[]) throws IOException {
            if(o1!=null) o1.write(b);
            if(o2!=null) o2.write(b);
        }
        public void write(byte b[], int off, int len) throws IOException {
            if(o1!=null) o1.write(b, off, len);
            if(o2!=null) o2.write(b, off, len);
        }
        public void flush() throws IOException {
            if(o1!=null) o1.flush();
            if(o2!=null) o2.flush();
        }
        public void close() throws IOException {
            if(o1!=null) o1.close();
            if(o2!=null) o2.close();
        }
    }
