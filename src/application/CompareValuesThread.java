package application;

import application.LoginController;
import application.model.AlertRoom;
import application.model.Patient;

import java.util.ArrayList;

/**
 * Created by Artem Klimenko on 27 Jan 2017.
 */
public class CompareValuesThread implements Runnable {
    @Override
    public void run() {
        while(true) {
            AlertsList alerts = new AlertsList();
            ArrayList<Patient> patinets = new ArrayList<>();
            patinets.addAll(LoginController.changableValues);
            for (Patient p : patinets) {
                String bloodPressure = p.getBlood_pressure();
                int pulse = p.getPulse_rate();
                int breathRate = p.getBreathing_rate();
                double bodyTemp = p.getBody_temp();
                if (pulse > 100) {
                    alerts.getAlertRoomData().add(new AlertRoom(p.getRoom(), "Pulse has changed"));
                    System.out.println("ALeeert"+ p.getPulse_rate());
                }
                try {


                Thread.sleep(2000);
                }catch (InterruptedException e){e.printStackTrace();}
            }
        }
    }
}
