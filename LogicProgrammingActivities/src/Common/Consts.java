package Common;

public class Consts {
	/*
	 * Analysis - API
	 */
	public static int QUESTION_MIN_SCORE = 0;
	public static String QUESTION_MAY_CONTAINS_LIST = "i need to;how to;how can i;i'm trying;i am trying;i want to;how do i";
	
	/*
	 * DB Connection
	 */
	public static String DB_NAME = "sofdb";
	public static String DB_CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
	public static String DB_USERNAME = "admin";  
	public static String DB_PASSWORD = "admin";
	
	/*
	 * Logger
	 */
	public static String JDA_FOLDER_NAME = "LogicProgrammingActivities";
	public static String JDA_FOLDER_PATH = "c:\\" + JDA_FOLDER_NAME + "\\";
	public static String LOG_FILE_PATH = JDA_FOLDER_PATH + "log.txt";
}
