package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class PatientsList {
	
	@FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> firstNameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;
    @FXML private Label birthdayLabel;
    @FXML private Label statusLabel;
    @FXML private Label roomLabel;
    @FXML private TextArea problemLabel;
    @FXML private Label vital1lbl;
    @FXML private Label vital2lbl;
    @FXML private Label vital3lbl;
    @FXML private Label vital4lbl; 
    @FXML private AnchorPane vitalSignPane;
    
    Connection con;
  	public static ArrayList<Patient> staticVitalList = new ArrayList();
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
	
	public ObservableList<Patient> getPatientData() {
        return patientData;
    }
    	
	public void refreshVitals() {
				
		ListIterator<Patient> iterator = patientData.listIterator();

		while(iterator.hasNext()) {	    			
		
		Patient tempPatient = iterator.next();
		System.out.println(tempPatient.getStatus() + " Patient is " + tempPatient.getFirstName());
			
			if(tempPatient.getStatus()) {			
				int temp_room_id = tempPatient.getRoom();
				String query = "SELECT body_temp, breathing_rate, pulse_rate, blood_pressure FROM rooms WHERE ID = '"+temp_room_id+"'";    				
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
					System.out.println("Loading Vitals failed!");
					e.printStackTrace();
				}
			}
		}
	}
    
   	@FXML private void editProblem() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        
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
   	
	public void refreshTable() {
		
		patientData.clear();
		
		/*
		try {
			System.out.println("Get Patient Data for Doctor with ID: " + Main.getClassID());
			con = Main.getCon();
			patientData = DBConnector.getPatientDataDoctorUser(con, Main.getClassID());
			patientData = DBConnector.getVitals(patientData, con);
			con.close();
			
		} catch (Exception e1) {
			System.out.println("failed refreshing table");
			e1.printStackTrace();
		}
		 */
		//getPatientDataDoctorUser*/

		int temp_id = Main.getClassID();
		System.out.println("Get table for Doctor with ID: " + temp_id);
		
		try {
	    	con = Main.getCon();
	    	String query = "SELECT * FROM patient WHERE assigned_doctor = '"+temp_id+"'";
	    	ResultSet rs = con.createStatement().executeQuery(query);
	    	
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
	    		  patientData.add(tempPatient);
				  staticVitalList.add(tempPatient);
	    	  }
	    	    	  
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void setTable(){
		patientTable.setItems(this.getPatientData());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		showPatientDetails(null);
}

		
    @FXML
    private void initialize() {
    	refreshTable();
    	refreshVitals();
		setTable();
    	System.out.println("init PatientsList");
        showPatientDetails(null);
        patientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));
    }
    
    private void showPatientDetails(Patient patient) {
    	if(patient != null) {
    		firstNameLabel.setText(patient.getFirstName());
    		lastNameLabel.setText(patient.getLastName());
    		genderLabel.setText(patient.getGender());
    		String formattedDate = patient.getBirthday().toString();
    		birthdayLabel.setText(formattedDate);
    		String status;
    		
    		if (patient.getStatus()) {
    			status = "Inpatient";
    		}
    		else {
    			status = "Outpatient";
    		}
    		
    		statusLabel.setText(status);
    		String room;
    		if (patient.getRoom() != null) room = patient.getRoom().toString();
    		else room = "None";
    		roomLabel.setText(room);
    		problemLabel.setText(patient.getProblem());
    		
    		if (patient.getStatus() == true) {
    		vital1lbl.setText(patient.getBody_temp().toString() + "C");
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
    		statusLabel.setText("");
    		roomLabel.setText("");
    		problemLabel.setText("");
    		vital1lbl.setText("");
    		vital2lbl.setText("");
    		vital3lbl.setText("");
    		vital4lbl.setText("");
    	}
    }

	public static ArrayList<Patient> getStaticVitalList() {

		return staticVitalList;

	}
}
