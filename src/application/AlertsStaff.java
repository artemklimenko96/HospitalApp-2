package application;

import application.model.AlertStaff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertsStaff {
	
	@FXML private TableView<AlertStaff> alertStaffTable;
    @FXML private TableColumn<AlertStaff, String> firstNameColumn;
    @FXML private TableColumn<AlertStaff, String> lastNameColumn;
    @FXML private TableColumn<AlertStaff, String> descColumn;
    
    private ObservableList<AlertStaff> alertStaffData = FXCollections.observableArrayList();
	
	public ObservableList<AlertStaff> getAlertStaffData() {
        return alertStaffData;
    }
    
	 @FXML
	    private void initialize() {
	    	System.out.println("init AlertsStaff");
	    	// Add some sample data
	        alertStaffData.add(new AlertStaff("Jean","Bon","do this"));
	        alertStaffData.add(new AlertStaff("Doc","Tür","do that"));
	        // Add observable list data to the table
	        alertStaffTable.setItems(this.getAlertStaffData());
	        // Initialize the alertRoom table with the two columns.
	        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	        descColumn.setCellValueFactory(cellData -> cellData.getValue().descProperty());
	    }
	 
	 public boolean showNewAlertDialog(AlertStaff alert) {
	        try {
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AlertsStaff.class.getResource("view/NewAlertDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("New Alert");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            //dialogStage.initOwner(primaryStage);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            NewAlertDialog controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            controller.setAlertStaff(alert);

	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();

	            return controller.isOkClicked();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	 
	 @FXML
	    private void handleNewAlert() {
	        AlertStaff tempAlert = new AlertStaff();
	        boolean okClicked = showNewAlertDialog(tempAlert);
	        if (okClicked) {
	            this.getAlertStaffData().add(tempAlert);
	        }
	    }

}
