package application;

import java.text.DecimalFormat;
import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class MainController {
	
	refreshThread refreshThread = new refreshThread();;
	
	@FXML private Label userLabel;
	@FXML private Label timeLabel;	
	
	@FXML Label clockLabel;
		
	public class refreshThread extends Thread {
		
		DecimalFormat mFormat = new DecimalFormat("00");
	    
		public refreshThread() {
	        super("MyThread");
	    }
		
		public void run() {
			
			try {
				while(!Thread.currentThread().isInterrupted()) {
					for(;;) {
					DecimalFormat mFormat = new DecimalFormat("00");
						Calendar cal = Calendar.getInstance();
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						int minute = cal.get(Calendar.MINUTE);
						int second = cal.get(Calendar.SECOND);
						System.out.println(mFormat.format(Double.valueOf(hour))+":"+mFormat.format(Double.valueOf(minute))+":"+mFormat.format(Double.valueOf(second)));						
						//clockLabel.setText(mFormat.format(Double.valueOf(hour))+":"+mFormat.format(Double.valueOf(minute))+":"+mFormat.format(Double.valueOf(second)));					
						sleep(1000);
					}
				}
			} catch(InterruptedException e) {
				System.out.println("'Closed' Thread");
			}
			
		}	
		
		public void cancel() { interrupt(); }
	}
	
	
	@FXML private void initialize() {
			
		userLabel.setText(Main.getCurrentUserName());	
		DecimalFormat mFormat = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		timeLabel.setText("Login at: " +mFormat.format(Double.valueOf(hour))+":"+mFormat.format(Double.valueOf(minute)));
		//refreshThread.start();
	}
	
	@FXML public void logout(ActionEvent e) {
		System.out.println("logout");
		//refreshThread.cancel();
		Main.getMainStage().close();
		//main.initLogin();
	}
	

}