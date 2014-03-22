package BL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.Consts;
import Common.Logger;

public class RepositoryManager {
	private Connection con;
	private PreparedStatement statement;
	
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
			Logger.Write(ex);
			return null;
		}
	}
	
	public int updateQuery(String updateQuery) throws Exception {
		//Assumption: con is initialized. User should close connection outside this function
		try {
			statement = con.prepareStatement(updateQuery);
			return statement.executeUpdate();  //return number of rows which were updated
		} catch(Exception ex) {
			Logger.Write(ex);
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
			Logger.Write(e);
			System.err.println(e.toString());
		} finally {
			closeConnection();
		}
	}
	
	public void dropAllTables() throws Exception {
		//DropDbTable("nl_word");
		//DropDbTable("described_object");
		System.out.println("All tables were dropped successfully!");
	}
	
}
