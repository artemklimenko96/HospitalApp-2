/*
package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
	
		@FXML private TextField username;

	    @FXML private PasswordField password;

	    @FXML private Button submitButton;
	  
	    @FXML private Label statuslbl;
	    
	    private Main main;
	    
	    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
	        System.out.println("submit");
	        System.out.println(username.getText() + "/" + password.getText());
	        //temporary validation
	        if(username.getText().equals("a") && password.getText().equals("a")) {
	        	System.out.println("Login as Doctor");
	        	Main.setUserClass(true);
	        	Main.setCurrentUserName(username.getText());
	        	main.initMainLayout();
	        	//main.initUserInfo();
	        	main.initDoctorControls();
	        }
	        
	        if(username.getText().equals("b") && password.getText().equals("b")) {
	        	System.out.println("Login as Staff");
	        	Main.setUserClass(false);
	        	Main.setCurrentUserName(username.getText());
	        	main.initMainLayout();
	        	//main.initUserInfo();
	        	main.initStaffControls();
	        }
	        
	        else {
	        	
	        	statuslbl.setText("Login failed");
	        }
	    }	
    
	*/    
	    
	    
	    package application;

	    import java.net.URL;
	    import java.sql.Connection;
	    import java.sql.PreparedStatement;
	    import java.sql.ResultSet;
		import java.util.ArrayList;
		import java.util.ResourceBundle;

		import application.model.Patient;
		import javafx.event.ActionEvent;
	    import javafx.fxml.FXML;
	    import javafx.fxml.Initializable;
	    import javafx.scene.control.Button;
	    import javafx.scene.control.Label;
	    import javafx.scene.control.PasswordField;
	    import javafx.scene.control.TextField;
	    import application.Main;


	    public class LoginController implements Initializable {
	    	
	        private Main main;
	        private Connection con;
	    	
	        int userId;
	        
	    	@FXML private TextField username;
	    	@FXML private PasswordField password;
	    	@FXML private Button submitButton;
		    @FXML private Label statuslbl;
	    	public static ArrayList<Patient> p;
	    	@FXML protected void handleSubmitButtonAction(ActionEvent event) throws Exception {

	    		ResultSet rs = null;
	    			
	    		String query = "SELECT * FROM userlogin WHERE username=? and password=?";
	    		PreparedStatement pst = con.prepareStatement(query);
	    						
	    		pst.setString(1, username.getText());
	    		pst.setString(2, password.getText());			
	    				
	    		rs = pst.executeQuery();
	    				
	    		int count = 0;
	    			
	    			while(rs.next()) {
	    				count++;
	    				Main.setUserClass(rs.getBoolean("userClass"));
	    				Main.setUserID(rs.getInt("Id"));
	    			}
	    			
	    			if(count == 1) {
	    				System.out.println("User Name & Password correct");	
	    				System.out.println("Login as class " + Main.isUserClass() +  " with ID " + Main.getUserID());
	    				Main.setCurrentUserName(username.getText());

						p = PatientsList.getAllPatients();
						AlertVitalSigns alerts = new AlertVitalSigns();
						Thread t = new Thread(alerts);
						t.start();
	    				main.initMainLayout();
	    				
	    			}
			
	    			else if(count > 1) {
	    				Main.setCurrentUserName(username.getText());
	    				System.out.println("Duplicated username");
	    			}
	  
	    			else {
	    				System.out.println("Wrong Password or username");
	    	        	statuslbl.setText("Login failed");
	    			}
	    			
	    			pst.close();
	    			rs.close();
	    						
	    	    	System.out.println("submit");        	    	
	    	    }
	    	    
	    	    
	    	    public void setMain(Main main) {
	    	        this.main = main;
	    	        con = Main.getCon();
	    	    }

	    	    
	    		@Override
	    		public void initialize(URL arg0, ResourceBundle arg1) {
	    		}

	    }
	    
	    
	  //  public void setMain(Main main) {
	   //     this.main = main;
	   // }

//}
