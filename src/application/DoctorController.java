package application;

import javafx.fxml.FXML;

public class DoctorController {

	private String currentScreen;
	
	@FXML
    private void initialize() {
		Main.showPatientsList();
		currentScreen = "patients";
	}

	@FXML
    private void handleBtn1() {
		if(currentScreen.equals("patients")) return;
		Main.showPatientsList();
		currentScreen = "patients";
    }
	
	@FXML
    private void handleBtn2() {
		if(currentScreen.equals("alertsroom")) return;
       Main.showAlertsList();
       currentScreen = "alertsroom";
    }
	
	@FXML
    private void handleBtn3() {
		if(currentScreen.equals("allpatients")) return;
       Main.showAllPatientsList();
       currentScreen = "allpatients";
    }
	
	@FXML
    private void handleBtn4() {
		if(currentScreen.equals("alertsstaff")) return;
       Main.showAlertsStaff();
       currentScreen = "alertsstaff";
    }
	
	
}
