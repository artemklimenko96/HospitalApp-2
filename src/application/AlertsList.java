package application;

import application.model.AlertRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AlertsList {
	
	@FXML private TableView<AlertRoom> alertRoomTable;
    @FXML private TableColumn<AlertRoom, Number> roomNbrColumn;
    @FXML private TableColumn<AlertRoom, String> descColumn;
    
    public static ObservableList<AlertRoom> alertRoomData = FXCollections.observableArrayList();
	
	public static ObservableList<AlertRoom> getAlertRoomData() {
        return alertRoomData;
    }

	public static void setAlertRoomData(ObservableList<AlertRoom> alertRoomData) {
		AlertsList.alertRoomData = alertRoomData;
	}

	@FXML
	    private void initialize() {
	    	System.out.println("init AlertsList");
	    	// Add some sample data
	        alertRoomData.add(new AlertRoom(12, "Low blood pressure"));
	        alertRoomData.add(new AlertRoom(123, "High breathing rate"));
	        // Add observable list data to the table
	        alertRoomTable.setItems(this.getAlertRoomData());

	        // Initialize the alertRoom table with the two columns.
	        roomNbrColumn.setCellValueFactory(cellData -> cellData.getValue().roomNbrProperty());
	        descColumn.setCellValueFactory(cellData -> cellData.getValue().descProperty());
	        //Listen for selection changes and show the person details when changed.
	        //alertRoomTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));
	    }

}
