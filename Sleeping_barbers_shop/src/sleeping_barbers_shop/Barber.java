
package sleeping_barbers_shop;
public class Barber implements Runnable {										// initializing the barber

   Barber_shop shop;
    int barberId;
 
    public Barber(Barber_shop shop, int barberId) {
    
        this.shop = shop;
        this.barberId = barberId;
    }
    
    public void run() {
    
        while(true) {
        
            shop.cutHair(barberId);
        }
    }
}
