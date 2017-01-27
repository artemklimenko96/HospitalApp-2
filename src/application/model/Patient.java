package application.model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient {
	
	private final IntegerProperty id;
	private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gender;
    private final StringProperty birthday;
    private final StringProperty problem;
    private final BooleanProperty status;
    private final IntegerProperty room;
    private final StringProperty assignedDoctor;
    private final IntegerProperty doctorID;
       
    private final DoubleProperty body_temp;
    private final IntegerProperty breathing_rate;
    private final IntegerProperty pulse_rate;
    private final StringProperty blood_pressure;

    private final StringProperty inDate;

    
    public Patient() {
        this(null, null);
    }
    
    public Patient(String firstName, String lastName) {
        this.id = new SimpleIntegerProperty(0);
    	this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty("");
        this.birthday = new SimpleStringProperty("");
        this.problem = new SimpleStringProperty("");
        this.status = new SimpleBooleanProperty(true);
        this.room = new SimpleIntegerProperty(0);
       // this.vitalSignId = new SimpleIntegerProperty(0);
        this.assignedDoctor = new SimpleStringProperty("");
        this.inDate = new SimpleStringProperty("");
        this.breathing_rate = new SimpleIntegerProperty(0);
        this.pulse_rate = new SimpleIntegerProperty(0);
        this.body_temp = new SimpleDoubleProperty(0);
        this.blood_pressure = new SimpleStringProperty("");
        this.doctorID = new SimpleIntegerProperty(0);
    }

    public Patient(int roomNbr, String firstName, String lastName, String assignedDoctor) {
        this.id = new SimpleIntegerProperty(0);
        this.room = new SimpleIntegerProperty(roomNbr);
    	this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.assignedDoctor = new SimpleStringProperty(assignedDoctor);
        this.gender = new SimpleStringProperty("man");
        this.birthday = new SimpleStringProperty("");
        this.problem = new SimpleStringProperty("bla bla bla");
        this.status = new SimpleBooleanProperty(true);
        this.inDate = new SimpleStringProperty("01.01.2000");
        this.breathing_rate = new SimpleIntegerProperty(0);
        this.pulse_rate = new SimpleIntegerProperty(0);
        this.body_temp = new SimpleDoubleProperty(0);
        this.blood_pressure = new SimpleStringProperty("");   
        this.doctorID = new SimpleIntegerProperty(0);
    }
    
   
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

	public String getGender() {
		return gender.get();
	}
	
	public void setGender(String gender) {
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }
     
	public String getBirthday() {
		return birthday.get();
	}
	
	public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public StringProperty birthdayProperty() {
        return birthday;
    }
    
    
    
    public String getProblem() {
        return problem.get();
    }

    public void setProblem(String problem) {
        this.problem.set(problem);
    }

    public StringProperty problemProperty() {
        return problem;
    }
    
    public String getAssignedDoctor() {
        return assignedDoctor.get();
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor.set(assignedDoctor);
    }

    public StringProperty assignedDoctorProperty() {
        return assignedDoctor;
    }
    
    
    public String getInDate() {
        return inDate.get();
    }

    public void setInDate(String inDate) {
        this.inDate.set(inDate);
    }

    public StringProperty inDateProperty() {
        return inDate;
    }
    
    
    public Boolean getStatus() {
        return status.get();
    }

    public void setStatus(Boolean status) {
        this.status.set(status);
    }

    public BooleanProperty statusProperty() {
        return status;
    }
    
    public Integer getRoom() {
		return room.get();
	}
	
	public void setRoom(Integer room) {
        this.room.set(room);
    }

    public IntegerProperty roomProperty() {
        return room;
    }
    
    
    public Integer getId() {
		return id.get();
	}
	
	public void setId(Integer id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    
    /* Vitals */
    
    public Double getBody_temp() {
		return body_temp.get();
	}
	
	public void setBody_temp(Double body_temp) {
        this.body_temp.set(body_temp);
    }

    public DoubleProperty body_tempProperty() {
        return body_temp;
    }
    
    
    public Integer getBreathing_rate() {
		return breathing_rate.get();
	}
	
	public void setBreathing_rate(Integer breathing_rate) {
        this.breathing_rate.set(breathing_rate);
    }

    public IntegerProperty breathing_rateProperty() {
        return breathing_rate;
    }
    
    
    public Integer getPulse_rate() {
		return pulse_rate.get();
	}
	
	public void setPulse_rate(Integer pulse_rate) {
        this.pulse_rate.set(pulse_rate);
    }

    public IntegerProperty pulse_rateProperty() {
        return pulse_rate;
    }
    
    
    public String getBlood_pressure() {
        return blood_pressure.get();
    }

    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure.set(blood_pressure);
    }

    public StringProperty blood_pressureProperty() {
        return inDate;
    }

     
    public Integer getDoctorID() {
		return doctorID.get();
	}
	
	public void setDoctorID(Integer doctorID) {
        this.doctorID.set(doctorID);
    }

    public IntegerProperty doctorIDProperty() {
        return doctorID;
    }
}
