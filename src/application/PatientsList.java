package application;

import java.sql.Connection;
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
				String query = "SELECT vital1, vital2, vital3, vital4 FROM rooms WHERE ID = '"+temp_room_id+"'";    				
				ResultSet rs;
				
				System.out.println("refreshVital of patient in room " + temp_room_id );
				try {
					rs = con.createStatement().executeQuery(query);
					rs.first();   				  		
					tempPatient.setVital1(rs.getInt("vital1"));
					tempPatient.setVital2(rs.getInt("vital2"));
					tempPatient.setVital3(rs.getInt("vital3"));
					tempPatient.setVital4(rs.getInt("vital4"));
				} catch (SQLException e) {
					System.out.println("Loading Vital failed!");
					e.printStackTrace();
				}
			}
			else {	
			}
		}
	}
    
	// to do ---------------------------------------------------------
	
   	@FXML private void editProblem() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        
        if (selectedPatient != null) {
           	System.out.println("edit");

        }
        
        else {
        	System.out.println("nothing selected");
        }
    }

	public void refreshTable() {
		
		patientData.clear();
		
		int temp_id = Main.getClassID();
		
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
	    	  }
	    	    	  
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		patientTable.setItems(this.getPatientData());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		showPatientDetails(null);
	}
	
	
		
    @FXML
    private void initialize() {
    	refreshTable();
    	refreshVitals();
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
    		//	vitalSignPane.setVisible(true);
    		}
    		else {
    			status = "Outpatient";
    			//vitalSignPane.setVisible(false);
    		}
    		
    		statusLabel.setText(status);
    		String room;
    		if (patient.getRoom() != null) room = patient.getRoom().toString();
    		else room = "None";
    		roomLabel.setText(room);
    		problemLabel.setText(patient.getProblem());
    		
    		if (patient.getStatus() == true) {
    		vital1lbl.setText(patient.getVital1().toString());
    		vital2lbl.setText(patient.getVital2().toString());
    		vital3lbl.setText(patient.getVital3().toString());
    		vital4lbl.setText(patient.getVital4().toString());
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
}
