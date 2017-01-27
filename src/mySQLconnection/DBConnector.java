package mySQLconnection;


import java.sql.*;

public class DBConnector {
	private static String url = "jdbc:mysql://localhost:3306/hospitaldata";
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String username = "root";
	private static String password = "";
	private static Connection conn;

	public static Connection getConnection() throws Exception {

		try{

			Class.forName(driverName);
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