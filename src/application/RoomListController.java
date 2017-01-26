package application;

import application.model.AlertRoom;
import application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    @FXML private TableColumn<Patient, String> lastCheckColumn;
       
	@FXML private TableView<AlertRoom> alertTable;
	@FXML private TableColumn<AlertRoom, Number> alertRoomColumn; 
    @FXML private TableColumn<AlertRoom, String> timeStamp;
    @FXML private TableColumn<AlertRoom, String> sendByColumn;
			
    @FXML private Label firstnamelbl;
    @FXML private Label lastnamelbl;
    @FXML private Label genderlbl;
    @FXML private Label dateOfBirthlbl;
    @FXML private Label doctorlbl;

    @FXML private Label vital1lbl;
    @FXML private Label vital2lbl;
    @FXML private Label vital3lbl;
    @FXML private Label vital4lbl;
    
    @FXML private TextArea descriptionText;
       
    private ObservableList<Patient> roomData = FXCollections.observableArrayList();
	
	public ObservableList<Patient> getPatientData() {
        return roomData;
    }
    	
	@FXML private void checkedBtn(ActionEvent e) {
		System.out.println("checked");
	}

    @FXML
    private void initialize() {
    	System.out.println("init PatientsList");
    	// Add some sample data
    	roomData.add(new Patient(16, "Jean", "Pierre", "Doctor John"));
    	roomData.add(new Patient(29, "Leopoldo", "Zuniga", "Doctor Shivago"));
    	roomData.add(new Patient(4, "Werner", "Herzog", "Doctor Stevensen"));
    	roomData.add(new Patient(11, "Marie", "Le Pen", "Doctor Death"));
        // Add observable list data to the table
    	roomTable.setItems(this.getPatientData());
        // Initialize the person table with the two columns.
    	roomColumn.setCellValueFactory(cellData -> cellData.getValue().roomProperty());
    	firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        //Clear person details.
        showRoomDetails(null);
        //Listen for selection changes and show the person details when changed.
        roomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showRoomDetails(newValue));
    }
    
    private void showRoomDetails(Patient patient) {
    	if(patient != null) {
    		firstnamelbl.setText(patient.getFirstName());
    		lastnamelbl.setText(patient.getLastName());
    		genderlbl.setText(patient.getGender());

    		String formattedDate = patient.getBirthday().toString();
    		dateOfBirthlbl.setText(formattedDate);

    		doctorlbl.setText(patient.getAssignedDoctor());
    		
    	} else {
    		firstnamelbl.setText("");
    		lastnamelbl.setText("");
    		genderlbl.setText("");
    		dateOfBirthlbl.setText("");
    		doctorlbl.setText("");
    	}
    }
         
}
