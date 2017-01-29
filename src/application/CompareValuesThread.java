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
           try{ Thread.sleep(1000);
           }catch (InterruptedException e){e.printStackTrace();}
            ArrayList<Patient> patinets = new ArrayList<>();
            if(Main.userClass){
            patinets.addAll(LoginController.changableValues);
            }else {patinets.addAll(LoginController.roomList);
            }
            for (Patient p : patinets) {
                String bloodPressure = p.getBlood_pressure();
                String[] bloodPrep = bloodPressure.split("/");
                int upperBound = Integer.parseInt(bloodPrep[0]);
                int lowerBound = Integer.parseInt(bloodPrep[1]);
                int pulse = p.getPulse_rate();
                int breathRate = p.getBreathing_rate();
                double bodyTemp = p.getBody_temp();
                boolean changeOccured = false;
                StringBuilder data = new StringBuilder();
                if (pulse > 120 || pulse < 40) {
                    changeOccured = true;
                    data.append("pulse" + " " + p.getPulse_rate() + " ");
                   // alerts.getAlertRoomData().add(new AlertRoom(p.getRoom(), String.valueOf(p.getPulse_rate())));
                }
                if(upperBound > 130 || upperBound < 110 || lowerBound > 100 || lowerBound<60){
                    changeOccured = true;
                    data.append("Blood pressure" + " " + p.getBlood_pressure() +" ");
                   // alerts.getAlertRoomData().add(new AlertRoom(p.getRoom(), p.getBlood_pressure()));
                }
                if(breathRate> 40 || breathRate < 10){
                    changeOccured = true;
                    data.append("Breathrate" + " " + p.getBreathing_rate() + " ");
                }
                if(bodyTemp > 39 || bodyTemp < 36){
                    changeOccured = true;
                    double d = Math.round(p.getBody_temp() * 10);
                    double f = d/10;

                    data.append("Temperature" + " " + f + " ");

                }
                if(changeOccured){
                    alerts.getAlertRoomData().add(new AlertRoom(p.getRoom(), data.toString()));
                }
                try {


                Thread.sleep(2000);
                }catch (InterruptedException e){e.printStackTrace();}
            }
        }
    }
}
