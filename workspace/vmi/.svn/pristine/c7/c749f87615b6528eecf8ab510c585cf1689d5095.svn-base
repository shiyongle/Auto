package Com.Base.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class WrapperResponse extends HttpServletResponseWrapper {
	
	private WrapperWriter nWriter;
	
	public WrapperResponse(HttpServletResponse response) {
		super(response);
	}

	public String getContent() {
		if(nWriter == null){
			return "";
		}
		return nWriter.getContent();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		
		if(nWriter == null){
			PrintWriter writer = super.getWriter();
			nWriter = new WrapperWriter(writer);
		}
		return nWriter;
	}
	
	private class WrapperWriter extends PrintWriter{

		private String content;
		
		private PrintWriter out;
		
		public String getContent() {
			return content;
		}


		public WrapperWriter(PrintWriter out) {
			super(out);
			this.out = out;
		}
		
		@Override
		public void write(String s) {
			if(s.length()<50){
				content = s;
			}
			out.write(s);
		}

		@Override
		public void flush() {
			out.flush();
		}

		@Override
		public void close() {
			
			out.close();
		}

		@Override
		public boolean checkError() {
			
			return out.checkError();
		}


		@Override
		public void write(int c) {
			
			out.write(c);
		}

		@Override
		public void write(char[] buf, int off, int len) {
			
			out.write(buf, off, len);
		}

		@Override
		public void write(char[] buf) {
			
			out.write(buf);
		}

		@Override
		public void write(String s, int off, int len) {
			
			out.write(s, off, len);
		}


		@Override
		public void print(boolean b) {
			
			out.print(b);
		}

		@Override
		public void print(char c) {
			
			out.print(c);
		}

		@Override
		public void print(int i) {
			
			out.print(i);
		}

		@Override
		public void print(long l) {
			
			out.print(l);
		}

		@Override
		public void print(float f) {
			
			out.print(f);
		}

		@Override
		public void print(double d) {
			
			out.print(d);
		}

		@Override
		public void print(char[] s) {
			
			out.print(s);
		}

		@Override
		public void print(String s) {
			
			out.print(s);
		}

		@Override
		public void print(Object obj) {
			
			out.print(obj);
		}

		@Override
		public void println() {
			
			out.println();
		}

		@Override
		public void println(boolean x) {
			
			out.println(x);
		}

		@Override
		public void println(char x) {
			
			out.println(x);
		}

		@Override
		public void println(int x) {
			
			out.println(x);
		}

		@Override
		public void println(long x) {
			
			out.println(x);
		}

		@Override
		public void println(float x) {
			
			out.println(x);
		}

		@Override
		public void println(double x) {
			
			out.println(x);
		}

		@Override
		public void println(char[] x) {
			
			out.println(x);
		}

		@Override
		public void println(String x) {
			
			out.println(x);
		}

		@Override
		public void println(Object x) {
			
			out.println(x);
		}

		@Override
		public PrintWriter printf(String format, Object... args) {
			
			return out.printf(format, args);
		}

		@Override
		public PrintWriter printf(Locale l, String format, Object... args) {
			
			return out.printf(l, format, args);
		}

		@Override
		public PrintWriter format(String format, Object... args) {
			
			return out.format(format, args);
		}

		@Override
		public PrintWriter format(Locale l, String format, Object... args) {
			
			return out.format(l, format, args);
		}

		@Override
		public PrintWriter append(CharSequence csq) {
			
			return out.append(csq);
		}

		@Override
		public PrintWriter append(CharSequence csq, int start, int end) {
			
			return out.append(csq, start, end);
		}

		@Override
		public PrintWriter append(char c) {
			
			return out.append(c);
		}
		
		
	}
}