/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logic.Barberia;
import Logic.Barbero;
import Logic.Cliente;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Graphics;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Jorge
 */
public final class Lienzo extends javax.swing.JPanel implements Runnable {

    /**
     * Creates new form Lienzo
     */
    
    public Barbero barber;
    public List<Cliente> clientes;
    private Barberia barbershop;
    public List<Imagen> sillas;

    Imagen[] sprites = new Imagen[Imagen.TOTAL_SPRITES];
    Imagen[] guyDown = new Imagen[3];
    Imagen[] guyUp = new Imagen[3];
    Imagen[] guyRight = new Imagen[3];
    Imagen[] guyLeft = new Imagen[3];

    //private Animation currentAnimation;
    private final Imagen fondo;
    static final long FPS = 30;
    volatile boolean running = false;
    public boolean paused;
    public boolean haSidoPausado;
    
    public Lienzo(int x, int y, Barberia barberia, ResourceBundle messages) {

        initComponents();
        //setLocation((x - 900) / 2, 0);
        setLocation(50, 0);
        setSize(900, 600);
        fondo = new Imagen("fondo");
        clientes = new CopyOnWriteArrayList<>();
        barbershop = barberia;
        barber = new Barbero(barberia, messages.getString("barber"), messages);
        barber.setCurrentAnimation(barber.getAnimaciones()[1]);
        sillas = barbershop.spriteSillas;
        paused = false;
        haSidoPausado = false;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo.getImagen(), 0, 0, null);

        for (Imagen silla : sillas) {
            silla.drawImagen(g);
        }
        for (Cliente cliente : clientes) {
            cliente.getCurrentAnimation().drawSprite(g);
        }
        barber.getCurrentAnimation().drawSprite(g);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void start() {
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        long tiempoActual = System.currentTimeMillis();

        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        long beginTime = System.currentTimeMillis();

        while (running) {
            if (!paused) {
                startTime = System.currentTimeMillis() - beginTime;

                for (Cliente cliente : clientes) {
                    cliente.getCurrentAnimation().update();
                    cliente.label.setLocation(cliente.getCurrentAnimation().x+16, cliente.getCurrentAnimation().y-10);
                    cliente.moveHandler(this);
                }
                barber.getCurrentAnimation().update();
                barber.moveHandler(this);
                repaint();
                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                try {

                    if (sleepTime > 0) {
                        sleep(sleepTime);
                    } else {
                        sleep(10);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
