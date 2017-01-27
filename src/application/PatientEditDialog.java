package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import application.model.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientEditDialog {
	
    private Stage dialogStage;
    private Patient patient;
    private boolean patientMode;
    private boolean okClicked = false;
	
    @FXML private RadioButton inPatient;
    @FXML private RadioButton outPatient;
    @FXML private RadioButton maleBtn;
    @FXML private RadioButton femaleBtn;
    
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField birthdayField;
      
    @FXML private ComboBox<String> doctorCombo;
    
    @FXML private ComboBox<String> roomCombo;
   
    @FXML private TextArea problemField;

    @FXML private Label headerlbl;

    @FXML private Button okayBtn;
    
    @FXML private Label roomLabel;
    @FXML private Label doctorLabel;
    
    Connection con;

    /* Fill ComboBox with all FREE rooms */ 
    
    public void fillComboRoom() {
    	
    	ResultSet rs;
    	String query;
    	
    	try {
	    	con = Main.getCon();	    	
	    	query = "SELECT id FROM rooms WHERE room_status =' + 0 + '";
			rs = con.createStatement().executeQuery(query);
			
		  	  while(rs.next()){
		  	  	  
		  		  String tempRoom = rs.getString("id");
		  		  roomCombo.getItems().add(tempRoom);	  		  
		  	  }
			  	  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /* Fill ComboBox with all doctors */
    
    public void fillComboDoc() {						
    	
    	ResultSet rs;
    	String query;
    	
    	try {
	    	con = Main.getCon();	    	
	    	query = "SELECT fname, lastname FROM doctor";
			rs = con.createStatement().executeQuery(query);
			
		  	  while(rs.next()){		  	  	  
		  		  String name = rs.getString("fname");
		  		  String lastname = rs.getString("lastname");			  		  
		  		  name = lastname + ", " + name;		  		  
		  		  doctorCombo.getItems().add(name);	  		  
		  	  }
			  	  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
 
    /* Initialize when Dialog pops up */
    
    @FXML private void initialize() {
   	
    	fillComboRoom();
    	fillComboDoc();
    	//doctorField.setDisable(true);
    	//roomField.setDisable(true);
    }    	
    
    /* Get Values of radioBtns */
    
    @FXML protected void getInPatient(ActionEvent e) { 	
    	
    	headerlbl.setText("Add new Inpatient");
    	outPatient.setSelected(false);
    	roomCombo.setDisable(false);
    	doctorCombo.setDisable(false);
    	patientMode = true;
    	okayBtn.setDisable(false);
    }
      
    @FXML protected void getOutPatient(ActionEvent e) { 	
    	headerlbl.setText("Add new Outpatient");
    	patient.setRoom(0);
    	//roomField.setText("");
    	inPatient.setSelected(false); 
    	roomCombo.setDisable(true);
    	//roomField.setDisable(true);
    	doctorCombo.setDisable(false);
    	patientMode = false;
    	okayBtn.setDisable(false);
    	roomLabel.setText("");
    }
    
    @FXML protected void getMale(ActionEvent e) { 	
    	femaleBtn.setSelected(false);
    	}
    
    @FXML protected void getFemale(ActionEvent e) { 	
    	maleBtn.setSelected(false);
    }
 
    /* Get selected values of comboBars */
    
    @FXML protected void getDoc(ActionEvent e) {
   // 	doctorField.setText(doctorCombo.getValue());
    	doctorLabel.setText(doctorCombo.getValue());
    }
         
    @FXML protected void getRoom(ActionEvent e) {
    //	roomField.setText(roomCombo.getValue());
    	roomLabel.setText(roomCombo.getValue());
    //	roomCombo.setPromptText("Room:");
    }

    
    /* Set Up dialogStage */
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /* show given patient data*/
    
    public void setPatient(Patient patient) {
        this.patient = patient;
                  
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
                 
    	if(firstNameField.getText().equals("new") && lastNameField.getText().equals("Patient")) {

    		okayBtn.setDisable(true);   		
    		inPatient.setSelected(false);
        	outPatient.setSelected(false);
         	maleBtn.setSelected(false);
         	femaleBtn.setSelected(false);        	
            firstNameField.setText("");
            lastNameField.setText("");
        	firstNameField.setText("");
            lastNameField.setText("");
            birthdayField.setText("");         
            birthdayField.setPromptText("YYYY-MM-DD");
            roomLabel.setText("");
            doctorLabel.setText("");  	
    	}
        
    	else {
    		
    	    headerlbl.setText("Edit Patient Information");
    		okayBtn.setDisable(false);
        	
    		String formattedDate = patient.getBirthday().toString();
    		birthdayField.setText(formattedDate);
		
    		if (patient.getStatus() == true) {
    			inPatient.setSelected(true);	
    			outPatient.setSelected(false);
    			roomCombo.setDisable(false);
    		}		
    		else {			
    			inPatient.setSelected(false);	
    			outPatient.setSelected(true);
    			roomCombo.setDisable(true);
    		}
				
    		if (patient.getGender().equals("Male")) {
    			maleBtn.setSelected(true);	
    			femaleBtn.setSelected(false);
    		}
    		else {	
    			maleBtn.setSelected(false);	
    			femaleBtn.setSelected(true);
    		}
		
    		String room;
    		if (patient.getRoom() != null) room = patient.getRoom().toString();
    		else room = "None";
			//roomField.setText(room);
        	roomLabel.setText(room);
			problemField.setText(patient.getProblem());
			doctorLabel.setText(patient.getAssignedDoctor());
			System.out.println("my doc" + patient.getAssignedDoctor());
    	}
    }

    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /* (OK Button) if user input is valid-> user input - temp patient object. */
    
    @FXML private void handleOk() {
        if (isInputValid()) {
        	
           if (inPatient.isSelected()) {
        	   patient.setStatus(true);
               //patient.setRoom(Integer.parseInt(roomField.getText()));
               patient.setRoom(Integer.parseInt(roomLabel.getText()));
           }
           else patient.setStatus(false);         
          
           if (maleBtn.isSelected()) patient.setGender("Male");
           else patient.setGender("Female");           
           
           patient.setFirstName(firstNameField.getText());
           patient.setLastName(lastNameField.getText());
           patient.setBirthday(birthdayField.getText());          
           patient.setProblem(problemField.getText());            
           DecimalFormat mFormat = new DecimalFormat("00");
           Calendar cal = Calendar.getInstance();			
           int year = cal.get(Calendar.YEAR);
           int month = cal.get(Calendar.MONTH);
           int day = cal.get(Calendar.DAY_OF_MONTH);
           String inDate = mFormat.format(Double.valueOf(year))+"."+mFormat.format(Double.valueOf(month+1))+"."+mFormat.format(Double.valueOf(day));
           patient.setInDate(inDate);            
             
      //     @FXML private Label roomLabel;
       //    @FXML private Label doctorLabel;
           
           
           patient.setAssignedDoctor(doctorLabel.getText());
           
                  
		 okClicked = true;
		 dialogStage.close();
        }
    }
    
    @FXML private void handleCancel() {
        dialogStage.close();
    }
     
    /* check if user input is valid*/
    
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid last Birthday!\n"; 
        }      
        
        /*
        if (doctorField.getText() == null || doctorField.getText().length() == 0) {
            errorMessage += "No valid Doctor!\n"; 
        }
        */
        if(patientMode == true) {
        
        	if (roomLabel.getText() == null || roomLabel.getText().length() == 0) {
        		errorMessage += "No valid Room! (Inpatient)\n"; 
        	}
        }
       
        if (maleBtn.isSelected() == false && femaleBtn.isSelected() == false) {
        	errorMessage += "No valid Gender!\n"; 
        }
        
        if (problemField.getText() == null || problemField.getText().length() == 0) {
            errorMessage += "No valid problem!\n"; 
        }

        
        if (errorMessage.length() == 0) {
            System.out.println("A");
        	return true;
        
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
