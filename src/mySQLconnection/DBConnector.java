package mySQLconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnector {

	Connection Con;
	
	public static Connection getConnection() throws Exception {
		
		try{
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/HospitalData";
			String username = "root";
			String password = "";
			Class.forName(driver);						
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection established!");			
			return conn;
			
		} catch(Exception e) {
			System.out.println("Connection failed!");
		}
		
		return null;
	}
	
	
	public static ObservableList<Patient> getPatientDataDoctorUser(Connection con, int doc_id) {
		
		ObservableList<Patient> patientData = FXCollections.observableArrayList();
			   	
    	String query = "SELECT * FROM patient WHERE assigned_doctor = '"+doc_id+"'";
		patientData.clear();
		ResultSet rs = null;;
		
			try {			
		    	rs = con.createStatement().executeQuery(query);
		    	
		    	  while(rs.next()){
		    	  
		    		  Patient tempPatient = new Patient();
		    		  tempPatient.setFirstName(rs.getString("fname"));
		    		  tempPatient.setLastName(rs.getString("lastname"));
		    		  tempPatient.setGender(rs.getString("gender"));		  
		    		  tempPatient.setBirthday(rs.getString("date_of_birth"));
		    		  tempPatient.setStatus(rs.getBoolean("patient_status"));
		    		  tempPatient.setRoom(rs.getInt("room_id"));
		    		  tempPatient.setInDate(rs.getString("in_date"));
		    		  tempPatient.setProblem(rs.getString("medical_history"));
		    		  tempPatient.setId(rs.getInt("id"));
		    		  tempPatient.setDoctorID(rs.getInt("assigned_doctor"));
		    		  patientData.add(tempPatient);	    		  
		    	  }
		    	  
		    		ListIterator<Patient> iterator = patientData.listIterator();
		    		
		    		while(iterator.hasNext()) {	    			
		    		
		    			Patient tempPatient = iterator.next();
		    			int temp_doc_id = tempPatient.getDoctorID();
		    			
		    			query = "SELECT fname, lastname FROM doctor WHERE id = '"+temp_doc_id+"'";    				
		    			
		    			rs = con.createStatement().executeQuery(query);
		    		  	rs.first();   				  		
		    		  	String fname = rs.getString("fname");
		    		  	String lastname = rs.getString("lastname");    		  	
		    		  	String fullname = lastname + ", " + fname;
		    		  	tempPatient.setAssignedDoctor(fullname);
		 	    }
		   		
				rs.close();
				System.out.println("... done!");
		    		
			}catch (SQLException e) {
				System.out.println("Failed loading PatientData (SQL)");
				e.printStackTrace();
			}	
			return patientData;
	}
		
	public static ObservableList<Patient> getVitals(ObservableList<Patient> patientData, Connection con) {

		ListIterator<Patient> iterator = patientData.listIterator();

		while(iterator.hasNext()) {	    			
			Patient tempPatient = iterator.next();
			System.out.println(tempPatient.getStatus() + " Patient is " + tempPatient.getFirstName());
				
				try {
					
					if(tempPatient.getStatus()) {	
						int temp_room_id = tempPatient.getRoom();
						String query = "SELECT vital1, vital2, vital3, vital4 FROM rooms WHERE ID = '"+temp_room_id+"'";    				
						ResultSet rs;
				
						System.out.println("refreshing Vitals of Patient in room " + temp_room_id );
						rs = con.createStatement().executeQuery(query);
						rs.first();   				  		
						tempPatient.setBody_temp(rs.getDouble("vital1"));
						tempPatient.setBreathing_rate(rs.getInt("vital2"));
						tempPatient.setPulse_rate(rs.getInt("vital3"));
						tempPatient.setBlood_pressure(rs.getString("vital4"));
						rs.close();
					}

				} catch (SQLException e) {
						System.out.println("Loading Vital failed!");
					e.printStackTrace();
				}					
		}	

		return patientData;		
	}
}