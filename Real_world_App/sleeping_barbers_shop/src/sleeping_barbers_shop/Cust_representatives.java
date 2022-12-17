
package sleeping_barbers_shop;


public class Cust_representatives implements Runnable{
     Call_center callCenter;
    int representativesId;
 
    public Cust_representatives(Call_center callCenter, int representativesId) {
    
        this.callCenter = callCenter;
        this.representativesId = representativesId;
    }
    
    public void run() {
    
        while(true) {
        
            callCenter.AnswerTheCall(representativesId);
        }
    }
}
