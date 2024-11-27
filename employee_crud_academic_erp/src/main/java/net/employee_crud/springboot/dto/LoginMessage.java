package net.employee_crud.springboot.dto;

public class LoginMessage {
    String message;
    Boolean status;
    
    public LoginMessage() {
    	
    }
    	
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public LoginMessage(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    
}
