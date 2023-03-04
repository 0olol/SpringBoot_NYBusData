package com.Oolol.SpringBoot_NYBusData.Message;

public class responsemessage {
    private String message;

    public responsemessage(String message) {
      this.message = message;
    }
  
    public String getMessage() {
      return message;
    }
  
    public void setMessage(String message) {
      this.message = message;
    }
}
