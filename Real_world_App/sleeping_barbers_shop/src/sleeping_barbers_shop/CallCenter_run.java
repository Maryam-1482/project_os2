
package sleeping_barbers_shop;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class CallCenter_run {

    public static void main(String[] args) throws InterruptedException {
      int num_cust_representatives, cust_Id=1, num_customers, num_Chairs;	
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of customer service representatives (K):");			
    	num_cust_representatives=sc.nextInt();
    	
    	System.out.println("Enter the number of waiting room"			
    			+ " chairs(c):");
    	num_Chairs=sc.nextInt();
    	
    	System.out.println("Enter the number of customers:");			
   	num_customers=sc.nextInt();
    	
		ExecutorService exec = Executors.newFixedThreadPool(12);		
    	Call_center callCenter = new Call_center(num_cust_representatives, num_Chairs);				
    	Random r = new Random();  										
        System.out.println("\ncallCenter opened with "
        		+num_cust_representatives+" customer service representatives(s)\n");
        
        long startTime  = System.currentTimeMillis();					
        
        for(int i=1; i<=num_cust_representatives;i++) {								
        	
        	Cust_representatives barber = new Cust_representatives(callCenter, i);	
        	Thread thbarber = new Thread(barber);
            exec.execute(thbarber);
        }
        
        for(int i=0;i<num_customers;i++) {								
            Customer customer = new Customer(callCenter);
            customer.setInTime(new Date());
            Thread thcustomer = new Thread(customer);
            customer.setcustomerId(cust_Id++);
            exec.execute(thcustomer);
            System.out.println("\n******************************************");
            
            try {
            	
            	double val = r.nextGaussian() * 2000 + 2000;			
            	int millisDelay = Math.abs((int) Math.round(val));		
            	Thread.sleep(millisDelay);							
            }
            catch(InterruptedException iex) {
            }
            
        }
        
        exec.shutdown();												
        exec.awaitTermination(12, SECONDS);							
 
        long elapsedTime = System.currentTimeMillis() - startTime;		
        
        System.out.println("\ncallCenter shop closed");
        System.out.println("\nTotal time elapsed in seconds for serving "+num_customers+" customers by "
        		+num_cust_representatives+" customer service representatives with "+num_Chairs+
        		" chairs in the waiting room is: "
        		+TimeUnit.MILLISECONDS
        	    .toSeconds(elapsedTime));
        System.out.println("\nTotal customers: "+num_customers+
        		"\nTotal customers served: "+callCenter.getTotalAnswerTheCall()
        		+"\nTotal customers lost: "+callCenter.getCustomerLost());
               
        sc.close();
    
    }
    
}
