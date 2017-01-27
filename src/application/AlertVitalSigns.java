package application;

import application.model.AlertRoom;
import application.model.Patient;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Artem Klimenko on 27 Jan 2017.
 */
public class AlertVitalSigns implements Runnable {

    int vs1;
    int vs2;
    int vs3;
    int vs4;
    @Override
    public void run() {
        ArrayList<Patient> local = LoginController.p;
        ArrayList<Patient> updatedList = PatientsList.getAllPatients();
        // if(local.size()<updatedList.size()){
        while (true) {
            for (int i = 0; i < local.size(); i++) {
                if (local.get(i).getId() == updatedList.get(i).getId()) {
                    Boolean stateChanged = check(local.get(i), updatedList.get(i));
                    if (stateChanged) {
                        ObservableList<AlertRoom> alerts = AlertsList.getAlertRoomData();
                        AlertRoom alertRoom = new AlertRoom(local.get(i).getRoom(), "problem occured");
                        alerts.add(alertRoom);
                        AlertsList.setAlertRoomData(alerts);
                    }
                }

            }
            try{
            Thread.sleep(2000);
            }catch (InterruptedException e){e.printStackTrace();}
        }
    }
public Boolean check(Patient a, Patient b){
    if(a.getVital1() == b.getVital1()&& a.getVital2() == b.getVital2()&& a.getVital3() == b.getVital3() && a.getVital4() == b.getVital4()){
        return true;
    }
    else return false;
}
}
