package application;

import javafx.fxml.FXML;

public class StaffController {

	private String currentScreen;
	
	@FXML
    private void initialize() {
		Main.showRoomList();
		currentScreen = "roomList";
	}
	
	@FXML
    private void handleBtn1() {
		if(currentScreen.equals("roomList")) return;
		Main.showRoomList();
		currentScreen = "roomList";
    }
		
	@FXML
    private void handleBtn2() {
		if(currentScreen.equals("allpatients")) return;
	      Main.showAllPatientsList();
	      currentScreen = "allpatients";
    }
	
	@FXML
    private void handleBtn3() {
		if(currentScreen.equals("alertsroom")) return;
       Main.showAlertsList();
       currentScreen = "alertsroom";
    }
	
}
