package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.ListIterator;

import application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PatientDataController {
	
	Connection con;
	
	@FXML private TextField filterField;
	@FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Number> idColumn;
    @FXML private TableColumn<Patient, String> firstNameColumn;
    @FXML private TableColumn<Patient, String> lastNameColumn;
    @FXML private TableColumn<Patient, String> inDateColumn;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label genderLabel;
    @FXML private Label birthdayLabel;
    @FXML private Label statusLabel;
    @FXML private Label roomLabel;
    @FXML private Label doctorLabel;
    @FXML private Label inDateLabel;
    
  
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
	
	public ObservableList<Patient> getPatientData() {
        return patientData;
    }

	/* select all patients & get them into patientData *list  */
	
	public void refreshTable() {
			
		   patientData.clear();
		
		try {
	    	con = Main.getCon();
	    	String query = "SELECT * FROM patient";
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
	   		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        patientTable.setItems(this.getPatientData());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        inDateColumn.setCellValueFactory(cellData -> cellData.getValue().inDateProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        showPatientDetails(null);
	}

	
    @FXML private void initialize() {
    	System.out.println("init Patient Database");
    	refreshTable();  	
        
    	/* give actionlistener and search and sort fuction */
    	
    	patientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));
        FilteredList<Patient> filteredData = new FilteredList<>(patientData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                /*if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else*/ if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Patient> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        patientTable.setItems(sortedData);
    }
    
    
    private void showPatientDetails(Patient patient) {
     	
    	if(patient != null) {
    		firstNameLabel.setText(patient.getFirstName());
    		lastNameLabel.setText(patient.getLastName());
    		genderLabel.setText(patient.getGender());
    		String formattedDate = patient.getBirthday().toString();
    		birthdayLabel.setText(formattedDate);
    		String status;
    		if (patient.getStatus()) status = "Inpatient";
    		else status = "Outpatient";
    		statusLabel.setText(status);
    		String room;
    		
    		if (patient.getRoom() == null || patient.getRoom().toString().equals("0")) { 
    			room = "None";
    		}
    				
    		else {
    			room = patient.getRoom().toString();
    		}
    		inDateLabel.setText(patient.getInDate());
    		roomLabel.setText(room);
    		doctorLabel.setText(patient.getAssignedDoctor());
    		
    	} else {
    		firstNameLabel.setText("");
    		lastNameLabel.setText("");
    		genderLabel.setText("");
    		birthdayLabel.setText("");
    		statusLabel.setText("");
    		roomLabel.setText("");
    		inDateLabel.setText("");
    		doctorLabel.setText("");
    	}
    }
  
    /*Call Dialog Stage / give patient */
    
    public boolean showPatientEditDialog(Patient patient) {
        try {
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PatientsList.class.getResource("view/PatientEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("theme/mainTheme.css").toExternalForm());
            PatientEditDialog controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /* New Patient Button / Check  */ 
    
    @FXML private void handleNewPatient() {
        Patient tempPatient = new Patient("new", "Patient");
        boolean okClicked = showPatientEditDialog(tempPatient);
       
        if (okClicked) {
            this.getPatientData().add(tempPatient);
            int tempDocInt = 0;
            Boolean patient_status = tempPatient.getStatus();
            Integer room_id = tempPatient.getRoom();
            String temp_doc = tempPatient.getAssignedDoctor();
            
            String[] result;
 	       
            result = temp_doc.split(", ");
  		       
             for(String s : result){
  		     System.out.println(s.trim());
             }
              	String lname = result[0];
             	String fname = result[1];
  			
             	String query = "SELECT * FROM DOCTOR WHERE lastname ='" + lname + "' AND fname = '" + fname + "'";
			
				try {
					ResultSet rs = con.createStatement().executeQuery(query);
					rs.first();   				  		
					String tempDocString = rs.getString("id");
					tempDocInt = Integer.parseInt(tempDocString);
					System.out.println(tempDocInt);
					System.out.println(tempDocInt);					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            try {
              	//con = Main.getCon();
              	
              	query = "INSERT INTO patient"
              			+ "(fname, lastname, gender, date_of_birth, patient_status, room_id, in_date, medical_history, assigned_doctor)" 
              			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
              	         	
              	PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, tempPatient.getFirstName());
				preparedStmt.setString(2, tempPatient.getLastName());
				preparedStmt.setString(3, tempPatient.getGender());
				preparedStmt.setString(4, tempPatient.getBirthday().toString());
				preparedStmt.setBoolean(5, tempPatient.getStatus());
				preparedStmt.setInt(6, tempPatient.getRoom());
				preparedStmt.setInt(9, tempDocInt);
				
				DecimalFormat mFormat = new DecimalFormat("00");
				Calendar cal = Calendar.getInstance();
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				String in_date = mFormat.format(Double.valueOf(year))+"."+mFormat.format(Double.valueOf(month+1))+"."+mFormat.format(Double.valueOf(day));
				
				preparedStmt.setString(7, in_date);
				preparedStmt.setString(8, tempPatient.getProblem());
				preparedStmt.setInt(9, tempDocInt);				
				preparedStmt.execute();
				
			} catch (SQLException e) {
				System.out.println("Failed!");
				e.printStackTrace();
			}
            
                
            if(patient_status == false) {
            	query = "UPDATE rooms SET room_status = 0 WHERE id ='" + room_id + "'";
	            System.out.println("change RoomStatus of Room -" + room_id + " to 0");
            }
            
            else {
            	query = "UPDATE rooms SET room_status = 1 WHERE id ='" + room_id + "'";
	            System.out.println("change RoomStatus of Room -" + room_id + " to 1");
            }
            
            
            PreparedStatement pst;
			try {
				pst = con.prepareStatement(query);
	            pst.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            refreshTable();
        }
    }
     
    
    
    
    @FXML private void handleEditPatient() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
        	
            Integer old_room_id = selectedPatient.getRoom();
          // Boolean old_patient_status = selectedPatient.getStatus();
            int pat_status;
            
            System.out.println(old_room_id);
            
            boolean okClicked = showPatientEditDialog(selectedPatient);
            
            if (okClicked) {
            	
                int tempDocInt = 0;
            	
                showPatientDetails(selectedPatient);
                
                String fname = selectedPatient.getFirstName();
                String lastname = selectedPatient.getLastName();
                String gender = selectedPatient.getGender();
                String date_of_birth = selectedPatient.getBirthday();
                Boolean patient_status = selectedPatient.getStatus();
                Integer room_id = selectedPatient.getRoom();
                String medical_history = selectedPatient.getProblem();
          
                String temp_doc = selectedPatient.getAssignedDoctor();
                
                String[] result;
     	       
                result = temp_doc.split(", ");
      		       
                 for(String s : result){
      		     System.out.println(s.trim());
                 }
      				        
                 	String lnameedit = result[0];
                 	String fnameedit = result[1];
      			
                 	String query = "SELECT * FROM DOCTOR WHERE lastname ='" + lnameedit + "' AND fname = '" + fnameedit + "'";
    			
    				try {
    					ResultSet rs = con.createStatement().executeQuery(query);
    					rs.first();   				  		
    					String tempDocString = rs.getString("id");
    					tempDocInt = Integer.parseInt(tempDocString);

    					
    				} catch (SQLException e) {
    					System.out.println("getting name of doc " + tempDocInt);
    					e.printStackTrace();
    				}
    				
             
                if(patient_status == true) {
                	pat_status = 1;
                }
                else {
                	pat_status = 0;
                }
                
                //update values in patientTable
                
                try {
                    con = Main.getCon();
                                
                    int patientID = selectedPatient.getId();
                    
                    System.out.println(patientID);
                  
                    query = "UPDATE patient SET fname = '"+fname+"' ,"
                    		+ " lastname = '"+lastname+"' ,"
                    		+ " gender = '"+gender+"' ,"
                    		+ " date_of_birth = '" +date_of_birth+ "',"
                    		+ " patient_status = '"+pat_status+"',"
                    		+ " room_id = '"+room_id+"' ,"
                    		+ " medical_history= '"+medical_history+"'"
                    		//+ " assigned_doctor = '"+assigned_doctor+"'"
                    		+ " WHERE id  ='" + patientID + "'";
                    		
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.execute();
                    
    			} catch (SQLException e) {
    				System.out.println("Failed!");
    				e.printStackTrace();
    			}
                  
                
                //update values in roomTable
               // TO DO verlgeich altraum mit neu wenn != edit
             
                if(patient_status == false) {
                	query = "UPDATE rooms SET room_status = 0  WHERE id ='" + old_room_id + "'";
    	            System.out.println("change RoomStatus of Room -" + old_room_id + "- to 0");
                }
                
                else {
                	query = "UPDATE rooms SET room_status = 1 WHERE id ='" + room_id + "'";
    	            System.out.println("change RoomStatus of Room -" + room_id + "- to 1");
                }
                
                
                PreparedStatement pst;
				try {
					pst = con.prepareStatement(query);
		            pst.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				refreshTable();              
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            //alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}

