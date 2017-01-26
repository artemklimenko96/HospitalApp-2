package application.model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final IntegerProperty vital1;
    private final IntegerProperty vital2;
    private final IntegerProperty vital3;
    private final IntegerProperty vital4;
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
        this.vital1 = new SimpleIntegerProperty(0);
        this.vital2 = new SimpleIntegerProperty(0);
        this.vital3 = new SimpleIntegerProperty(0);
        this.vital4 = new SimpleIntegerProperty(0);
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
        this.vital1 = new SimpleIntegerProperty(0);
        this.vital2 = new SimpleIntegerProperty(0);
        this.vital3 = new SimpleIntegerProperty(0);
        this.vital4 = new SimpleIntegerProperty(0);    
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
    

    
    public Integer getVital1() {
		return vital1.get();
	}
	
	public void setVital1(Integer vital1) {
        this.vital1.set(vital1);
    }

    public IntegerProperty vital1Property() {
        return vital1;
    }
    
    public Integer getVital2() {
		return vital2.get();
	}
	
	public void setVital2(Integer vital2) {
        this.vital2.set(vital2);
    }

    public IntegerProperty vital2Property() {
        return vital2;
    }
    
    public Integer getVital3() {
		return vital3.get();
	}
	
	public void setVital3(Integer vital3) {
        this.vital3.set(vital3);
    }

    public IntegerProperty vital3Property() {
        return vital3;
    }
    
    public Integer getVital4() {
		return vital4.get();
	}
	
	public void setVital4(Integer vital4) {
        this.vital4.set(vital4);
    }

    public IntegerProperty vital4Property() {
        return vital4;
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
