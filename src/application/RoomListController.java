package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class RoomListController {
	
	@FXML private TableView<Patient> roomTable;
	@FXML private TableColumn<Patient, Number> roomColumn; 
    @FXML private TableColumn<Patient, String> firstNameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;    
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;
    @FXML private Label birthdayLabel;
    @FXML private Label doctorLabel;
    @FXML private Label roomLabel;
    @FXML private TextArea problemLabel;
    @FXML private Label vital1lbl;
    @FXML private Label vital2lbl;
    @FXML private Label vital3lbl;
    @FXML private Label vital4lbl; 

    Connection con = Main.getCon();
    
    private ObservableList<Patient> roomData = FXCollections.observableArrayList();
	
	public ObservableList<Patient> getRoomData() {
        return roomData;
    }
    
	public void refreshVitals() {
		
		ListIterator<Patient> iterator = roomData.listIterator();

		while(iterator.hasNext()) {	    			
		
		Patient tempPatient = iterator.next();
		System.out.println(tempPatient.getStatus() + " Patient is " + tempPatient.getFirstName());
			
			if(tempPatient.getStatus()) {			
				int temp_room_id = tempPatient.getRoom();
				String query = "SELECT  body_temp, breathing_rate, pulse_rate, blood_pressure FROM rooms WHERE ID = '"+temp_room_id+"'";    				
				ResultSet rs;
				
				System.out.println("refreshVital of patient in room " + temp_room_id );
				try {
					rs = con.createStatement().executeQuery(query);
					rs.first();   				  		
					tempPatient.setBody_temp(rs.getDouble("body_temp"));
					tempPatient.setBreathing_rate(rs.getInt("breathing_rate"));
					tempPatient.setPulse_rate(rs.getInt("pulse_rate"));
					tempPatient.setBlood_pressure(rs.getString("blood_pressure"));
				} catch (SQLException e) {
					System.out.println("Loading Vital failed!");
					e.printStackTrace();
				}
			}
		}
	}

	public void refreshTable() {
		
		roomData.clear();
		
		int temp_id = Main.getClassID();
		System.out.println("Get table for Staff with ID: " + temp_id);
		
		try {
			
	    	con = Main.getCon();
	    	String query = "SELECT * FROM rooms,patient,staff WHERE (rooms.ID=room_id AND staff_id=staff.ID)";
	    	ResultSet rs = con.createStatement().executeQuery(query);
	    	   	
	    	  while(rs.next()){	    
	    		if(temp_id == rs.getInt("staff.ID")) {
	    			
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
	    		//  int tempDoc_id =  (rs.getInt("assigned_doctor"));
	    		//  System.out.println("patient mit dr: " + tempDoc_id);
	    		  tempPatient.setDoctorID(rs.getInt("assigned_doctor"));
	    		  roomData.add(tempPatient);
	    	  }
			}
		
	    	  ListIterator<Patient> iterator = roomData.listIterator();
	    		
	    		while(iterator.hasNext()) {	    
	    			System.out.println("HALLO");
	    		
	    			Patient tempPatient = iterator.next();
	    			
	    			int temp_doc_id = tempPatient.getDoctorID();

	    			try {
	    			query = "SELECT fname, lastname FROM doctor WHERE id = '"+temp_doc_id+"'";    				
	    			rs = con.createStatement().executeQuery(query);
	    		  	rs.first();   				  		
	    		  	String fname = rs.getString("fname");
	    		  	String lastname = rs.getString("lastname");    		  	
	    		  	String fullname = lastname + ", " + fname;
	    		  	tempPatient.setAssignedDoctor(fullname);
	    			}catch (SQLException e) {
	    				System.out.println("failed creating doctorname");
	    				e.printStackTrace();
	    			}
	 	    }
	    	  
		}catch (SQLException e) {
			System.out.println("failed refreshing patientData");
			e.printStackTrace();
		}
		
		roomTable.setItems(this.getRoomData());
		roomColumn.setCellValueFactory(cellData -> cellData.getValue().roomProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		showRoomDetails(null);
	}
	
	@FXML private void editProblem() {
        Patient selectedPatient = roomTable.getSelectionModel().getSelectedItem();
        
        if (selectedPatient != null) {
           	System.out.println("edit");
           	String newProblem = problemLabel.getText();
           	int patient_id = selectedPatient.getId();
      	
           	System.out.println("Change med_history of Patient " + patient_id + " to "+ newProblem);
           	String query = "UPDATE patient SET medical_history = '"+newProblem+"' WHERE id = '"+patient_id+"'";

           	try{
                PreparedStatement pst = con.prepareStatement(query);
                pst.execute();
           		
    		}catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}        	
           	refreshTable();
           	refreshVitals();
        }
 
        else {
        	System.out.println("nothing selected");
        } 		
	}

   	@FXML private void refreshPatientData() {
   		System.out.println("Refresh PatientData");
       	refreshTable();
       	refreshVitals();
   	}
	
    @FXML
    private void initialize() {
    	
    	refreshTable();
    	refreshVitals();
    	
    	System.out.println("init PatientsList");
        showRoomDetails(null);
        roomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoomDetails(newValue));
    }
    
    private void showRoomDetails(Patient patient) {
    	if(patient != null) {
    		firstNameLabel.setText(patient.getFirstName());
    		lastNameLabel.setText(patient.getLastName());
    		genderLabel.setText(patient.getGender());
    		String formattedDate = patient.getBirthday().toString();
    		birthdayLabel.setText(formattedDate);

    		
    		doctorLabel.setText(patient.getAssignedDoctor());
    		String room;
    		if (patient.getRoom() != null) room = patient.getRoom().toString();
    		else room = "None";
    		roomLabel.setText(room);
    		problemLabel.setText(patient.getProblem());
    		
    		if (patient.getStatus() == true) {
    		vital1lbl.setText(patient.getBody_temp().toString() + "°C");
    		vital2lbl.setText(patient.getBreathing_rate().toString());
    		vital3lbl.setText(patient.getPulse_rate().toString());
    		vital4lbl.setText(patient.getBlood_pressure());
    		}
    		   		
    		else {
        		vital1lbl.setText("-");
        		vital2lbl.setText("-");
        		vital3lbl.setText("-");
        		vital4lbl.setText("-");
    		}
    		
    	} else {
    		firstNameLabel.setText("");
    		lastNameLabel.setText("");
    		genderLabel.setText("");
    		birthdayLabel.setText("");
    		doctorLabel.setText("");
    		roomLabel.setText("");
    		problemLabel.setText("");
    		vital1lbl.setText("");
    		vital2lbl.setText("");
    		vital3lbl.setText("");
    		vital4lbl.setText("");
    	}
    }
         
}
