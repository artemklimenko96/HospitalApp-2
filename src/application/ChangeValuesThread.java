package application;

import application.LoginController;
import application.model.Patient;

import java.util.ArrayList;

/**
 * Created by Artem Klimenko on 27 Jan 2017.
 */
public class ChangeValuesThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            if(Main.userClass){
            for (Patient p : LoginController.changableValues) {
                p.setBlood_pressure((int)(Math.random() * 140) + 40 + "/" + (int)((Math.random() * 120) + 40));
                p.setPulse_rate((int) (Math.random() * 180) + 20);
                p.setBreathing_rate((int) (Math.random() * 75) + 5);
                p.setBody_temp((Math.random() * 8) + 34);

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){e.printStackTrace();}
            }

        }
         else{
                for (Patient p : LoginController.roomList) {
                    p.setBlood_pressure((int)(Math.random() * 140) + 40 + "/" + (int)((Math.random() * 120) + 40));
                    p.setPulse_rate((int) (Math.random() * 180) + 20);
                    p.setBreathing_rate((int) (Math.random() * 75) + 5);
                    p.setBody_temp((Math.random() * 8) + 34);

                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e){e.printStackTrace();}
                }
            }
        }
    }
}
