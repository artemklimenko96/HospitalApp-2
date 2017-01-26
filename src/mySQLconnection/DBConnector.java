package mySQLconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	/*String url = "jdbc:mysql://localhost:3306/hospital";
	String username = "hugo";
	String password = "passwort";*/
	public static Connection getConnection() throws Exception {
		
		try{

			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/HospitalData";
			String username = "projectoop";
			String password = "password";
			Class.forName(driver);						
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection established!");			
			return conn;
			
		} catch(Exception e) {
			System.out.println("Connection failed!");
		}
		
		return null;
	}
	
	
    public static ResultSet runMyQuery(Statement statement, Connection connection, String query){
    	
    	ResultSet resultSet = null;
    	 try {
			statement = connection.createStatement();  //Usage of statement makes your code prone to SQL injection attacks
			resultSet = statement
                    .executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return resultSet;   	
    }
  
    
    public static void writeData(ResultSet resultSet) throws SQLException {

    	ResultSetMetaData rsmd = resultSet.getMetaData();
    	
    	int columnsNumber = rsmd.getColumnCount();
    	
    	for (int i = 1; i <= columnsNumber; i++){
    		
    		System.out.print(rsmd.getColumnName(i)+",\t");
    		if(i==columnsNumber)
    		{
    			System.out.print("\r\n");
    		}
        	
    	}
    	   	
    	while (resultSet.next()) {
    	    for (int i = 1; i <= columnsNumber; i++) {
    	        if (i > 1) System.out.print(",  ");
    	        String columnValue = resultSet.getString(i);
    	        System.out.print(columnValue + " " );
    	    }
    	    System.out.println("");
    	}
    }
}