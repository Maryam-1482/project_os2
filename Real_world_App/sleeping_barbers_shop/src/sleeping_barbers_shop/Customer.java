
package sleeping_barbers_shop;

import java.util.Date;

public class Customer implements Runnable{
    
    int cust_Id;
    Date inTime;
 
    Call_center callCenter;
 
    public Customer(Call_center callCenter) {
    
        this.callCenter = callCenter;
    }
 
    public int getCustomerId() {										
        return cust_Id;
    }
 
    public Date getInTime() {
        return inTime;
    }
 
    public void setcustomerId(int cust_Id) {
        this.cust_Id = cust_Id;
    }
 
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
 
    public void run() {													
        goForAnswerTheCall();
    }
    private synchronized void goForAnswerTheCall() {							
    
        callCenter.add(this);
    }
}
