package Common;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	static {
		File folder = new File(Consts.JDA_FOLDER_PATH);
		if (folder.exists() == false) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				System.err
						.println("Error in Logger class. cannot create JDA's directory!");
			}
		}
		File file = new File(Consts.LOG_FILE_PATH);
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.out
						.println("Error in Logger class. cannot create JDA's log file!");
			}
		}
		System.out.println("Log file exists at: " + file.getPath());
	}
	private Logger() {
	}

	public static void write(String text) throws IOException {
		FileWriter out = new FileWriter(Consts.LOG_FILE_PATH, true);
		out.write(getCurrentDateTime() + ": " + text + "\n");
		out.write(breakingLine());
		out.close();
	}
	
	public static void writeStartTime() throws IOException {
		write("JavaDocsAnalysis Starting Time: " + getCurrentDateTime());
	}
	public static void writeEndTime() throws IOException {
		write("JavaDocsAnalysis End Time: " + getCurrentDateTime());
	}

	public static void write(Throwable e) {
		FileWriter out = null;
		try {
			out = new FileWriter(Consts.LOG_FILE_PATH, true);
			out.write(getCurrentDateTime() + ":\n");
			out.write("Exception Description: " + e.toString() + "\n");
			out.write("Stack Trace: " + getStackTrace(e));
			out.write(breakingLine());
			out.close();
		} catch (IOException e1) {
			System.err.println("Error while trying writing to the log file. \nStackTrace: ");
			e1.printStackTrace();
		}
	}

	private static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter print_writer = new PrintWriter(result);
		aThrowable.printStackTrace(print_writer);
		return result.toString();
	}

	private static String breakingLine() {
		return createLine(35);
	}
	
	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private static String createLine(int number) {
		String line = new String();
		for (int i = 0; i <= number; i++) {
			line += "-";
		}
		return line + "\n";
	}
}
