
package sleeping_barbers_shop;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
public class Call_center {
     private final AtomicInteger TotalAnswerTheCall = new AtomicInteger(0);
	private final AtomicInteger customersLost = new AtomicInteger(0);
	int nchair, noOfRepresentatives, availableRepresentatives;
    List<Customer> listCustomer;
    
    Random r = new Random();	 
    
    public Call_center(int noOfRepresentatives, int noOfChairs){
    
        this.nchair = noOfChairs;														
        listCustomer = new LinkedList<>();						
        this.noOfRepresentatives = noOfRepresentatives;									
        availableRepresentatives = noOfRepresentatives;
    }
 
    public AtomicInteger getTotalAnswerTheCall() {
    	
    	TotalAnswerTheCall.get();
    	return TotalAnswerTheCall;
    }
    
    public AtomicInteger getCustomerLost() {
    	
    	customersLost.get();
    	return customersLost;
    }
    
    public void AnswerTheCall(int representativesId)
    {
        Customer customer;
        synchronized (listCustomer) {									
        															 	
            while(listCustomer.size()==0) {
            
                System.out.println("\nRepresentatives "+representativesId+" is waiting "
                		+ "for the customer and sleeps in his chair");
                
                try {
                
                    listCustomer.wait();								
                }
                catch(InterruptedException iex) {
                
                    iex.printStackTrace();
                }
            }
            
            customer = (Customer)((LinkedList<?>)listCustomer).poll();	
            
            System.out.println("Customer "+customer.getCustomerId()+
            		" finds the representatives asleep and wakes up "
            		+ "the representatives "+representativesId);
        }
        
        int millisDelay=0;
                
        try {
        	
        	availableRepresentatives--; 										
        																
            System.out.println("Representatives "+representativesId+" AnswerTheCall of "+
            		customer.getCustomerId()+ " so customer sleeps");
        	
            double val = r.nextGaussian() * 2000 + 4000;				
        	millisDelay = Math.abs((int) Math.round(val));				
        	Thread.sleep(millisDelay);
        	
        	System.out.println("\nCompleted Answering call of "+
        			customer.getCustomerId()+" by Representatives " + 
        			representativesId +" in "+millisDelay+ " milliseconds.");
        
        	TotalAnswerTheCall.incrementAndGet();
            															
            if(listCustomer.size()>0) {									
            	System.out.println("Representatives "+representativesId+					
            			" wakes up a customer in the "					
            			+ "waiting room");		
            }
            
            availableRepresentatives++;											
        }
        catch(InterruptedException iex) {
        
            iex.printStackTrace();
        }
        
    }
 
    public void add(Customer customer) {
    
        System.out.println("\nCustomer "+customer.getCustomerId()+
        		" enters through the entrance door in the the shop at "
        		+customer.getInTime());
 
        synchronized (listCustomer) {
        
            if(listCustomer.size() == nchair) {			
            
                System.out.println("\nNo chair available "
                		+ "for customer "+customer.getCustomerId()+
                		" so customer leaves the shop");
                
              customersLost.incrementAndGet();
                
                return;
            }
            else if (availableRepresentatives > 0) {							
            															
            	((LinkedList<Customer>)listCustomer).offer(customer);
				listCustomer.notify();
			}
            else {														
            	((LinkedList<Customer>)listCustomer).offer(customer);
                
            	System.out.println("All Representatives(s) are busy so "+
            			customer.getCustomerId()+
                		" takes a chair in the waiting room");
                 
                if(listCustomer.size()==1)
                    listCustomer.notify();
            }
        }
    }
    
}
