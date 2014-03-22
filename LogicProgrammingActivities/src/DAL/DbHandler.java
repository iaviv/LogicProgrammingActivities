package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Common.Consts;
import Common.Logger;
import Model.Question;

public class DbHandler {
	private Connection con = null;
	private PreparedStatement statement = null;
	
	public void startConnection() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Consts.DB_CONNECTION_STRING,Consts.DB_USERNAME, Consts.DB_PASSWORD);
		} catch (Exception ex) {
			System.err.println("Error in DbHandler.StartConnection():\n"+ex.toString());
		}
	}
	
	public void closeConnection() throws SQLException {
		if(statement != null) statement.close();
		if(con != null) con.close();
	}
	
	public void CreateDbTables() throws SQLException {
		try {
			startConnection(); 
			String queryRelevantQuestionsTable = String.format(
					"CREATE TABLE relevant_questions ("
					+ "id INT NOT NULL auto_increment primary key,"
					+ "title TEXT DEFAULT NULL,"
					+ "body TEXT DEFAULT NULL,"
					+ "score INT DEFAULT NULL)");
			if(updateQuery(queryRelevantQuestionsTable)<0) {
				throw new Exception("Error occured during DbHandler.CreateDbTables() on table: relevant_questions.\n"
						+ "See log file for more details.");
			}
			System.out.println("relevant_questions Table creted successfully");
		} catch (Exception ex) {
			Logger.write(ex);
			System.err.println(ex.toString());
		} finally {
			closeConnection();
		}
	}
	
	public ArrayList<String> selectQuery(String selectQuery, String columnName) throws ClassNotFoundException, SQLException {
		//Assumption: con is initialized. User should close connection outside this function.
		try {
			statement = con.prepareStatement(selectQuery);
			ResultSet result = statement.executeQuery();
			ArrayList<String> list = new ArrayList<String>();
			while(result.next()) {
				list.add(result.getString(columnName));
			}
			return list;
		} catch (Exception ex) {
			Logger.write(ex);
			return null;
		}
	}
	
	public int updateQuery(String updateQuery) throws Exception {
		//Assumption: con is initialized. User should close connection outside this function
		try {
			statement = con.prepareStatement(updateQuery);
			return statement.executeUpdate();  //return number of rows which were updated
		} catch(Exception ex) {
			Logger.write(ex);
			return -1;
		}
	}
	
	public void dropDbTable(String tableName) throws Exception {
		try {
			startConnection();
			String dropQuery = String.format("DROP TABLE `%s`.`%s`;",Consts.DB_NAME ,tableName);
			if(updateQuery(dropQuery)<0) {
				throw new Exception("Error occured during DbHandler.DropDbTable() on table: " + tableName
						+ ".\nSee log file for more details.");
			}
			System.out.println(String.format("%s table dropped successfully!", tableName));
		} catch(Exception e) {
			Logger.write(e);
			System.err.println(e.toString());
		} finally {
			closeConnection();
		}
	}
	
	public void dropAllTables() throws Exception {
		dropDbTable("relevant_questions");
		System.out.println("All tables were dropped successfully!");
	}
	
	public Map<Integer,Question> getQuestions() {
		//Assumption: con is initialized. User should close connection outside this function.
		String selectQuery = String.format("SELECT Id,Title, Body,Score FROM sofdb.posts WHERE ParentID is null and Score > %d;", Consts.QUESTION_MIN_SCORE);
		try {
			statement = con.prepareStatement(selectQuery);
			ResultSet result = statement.executeQuery();
			Map<Integer,Question> finalSet = new HashMap<Integer,Question>();
			while(result.next()) {
				Question q = new Question();
				q.setTitle(result.getString("Title"));
				q.setBosy(result.getString("Body"));
				q.setScore(result.getInt("Score"));
				finalSet.put(result.getInt("Id"), q);
			}
			return finalSet;
		} catch (Exception ex) {
			Logger.write(ex);
			return null;
		}
	}
}
