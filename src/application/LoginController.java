
	    
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
			public static ArrayList<Patient> initialValues = new ArrayList<>();
			@FXML private TextField username;
	    	@FXML private PasswordField password;
	    	@FXML private Button submitButton;
		    @FXML private Label statuslbl;

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
	    				System.out.println("User Name & Password correct!");
	    				String myClass;
	    				
	    				if(Main.isUserClass()) myClass = "Doctor";
	    				else myClass = "Staff";
	    				System.out.println("Login with userId -"+ Main.getUserID() +"- as " + myClass);
	    				Main.setCurrentUserName(username.getText());
	    					    					
	    				main.initMainLayout();
	    				initialValues.addAll(PatientsList.getStaticVitalList());
						for (Patient p:initialValues) {
							System.out.println(p.getFirstName()+"works!" + " " + p.getBody_temp() + p.getBlood_pressure() + p.getPulse_rate() + p.getBreathing_rate());
						}
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
