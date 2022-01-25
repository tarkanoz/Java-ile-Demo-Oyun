
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

class Ateş {
    private int x;
    private int y;

    public Ateş(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    

}

public class Oyun extends JPanel implements KeyListener,ActionListener {
    Timer timer = new Timer(5,this);
    
    private int geçen_süre = 0;
    private int harcanan_ateş = 0;
    
    private BufferedImage image;
    private ArrayList<Ateş> ateşler = new ArrayList<Ateş>();
    
    private int ateşdirY = 1;
    private int topX = 0;
    private int topdirX = 2;
    private int uzayGemisiX = 0;
    private int dirUzayX = 20;
    private int uzayGemisiY =490;
    private int dirUzayY = 20;
    
    public boolean KontrolEt(){
     
        for(Ateş ateş : ateşler){
        
            if(new Rectangle(ateş.getX(),ateş.getY(),10,20).intersects(new Rectangle(topX,0,20,20))){
            
                return true;
            } 
        }
        return false;
     }

    public Oyun() {
        
        try {  
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
       geçen_süre +=5;
        g.setColor(Color.red);
        g.fillOval(topX, 0, 20,20);
        g.drawImage(image,uzayGemisiX,uzayGemisiY,image.getWidth() /10,image.getHeight() /10,this);
        
       // g.setColor(Color.red);
     //   g.fillOval(topX+100, 0, 20,20);
      //  g.drawImage(image,uzayGemisiX,uzayGemisiY,image.getWidth() /10,image.getHeight() /10,this);
        
       // g.setColor(Color.red);
        //g.fillOval(topX, 0, 20,20);
       // g.drawImage(image,uzayGemisiX,uzayGemisiY,image.getWidth() /10,image.getHeight() /10,this);
    
       for(Ateş ateş : ateşler){
         if(ateş.getY() <=0){
           ateşler.remove(ateş);
         }
       }
       g.setColor(Color.BLUE);
       
       for(Ateş ateş : ateşler){
          g.fillRect(ateş.getX(),ateş.getY(),10,20);

       }
       if(KontrolEt()){
         timer.stop();
         
         String message = "Kazandınız..\n" +
                         "Harcanan Ateş : " + harcanan_ateş +
                         "\nGeçen Süre : " + geçen_süre /1000.0+" saniye";
         
         JOptionPane.showMessageDialog(this,message);
         System.exit(0);
        
       }
    
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
         int c = e.getKeyCode();
        if(c == KeyEvent.VK_LEFT){
           if(uzayGemisiX <= 0){
             uzayGemisiX = 0;
           }
           else{
             uzayGemisiX -= dirUzayX;
           }
        }
        else if(c == KeyEvent.VK_RIGHT){
              if(uzayGemisiX >=740){
          uzayGemisiX= 740;
        }
        else{
         uzayGemisiX +=dirUzayX;
                 
        }
        }
        
       else if(c == KeyEvent.VK_SPACE){
       ateşler.add(new Ateş(uzayGemisiX+15,uzayGemisiY));
       
       harcanan_ateş++;
       }
      else if(c == KeyEvent.VK_UP){
             if(uzayGemisiY <=0){
                 uzayGemisiY =0;
             }
             else{
             uzayGemisiY -=dirUzayY;
             }
       }
        else if(c == KeyEvent.VK_DOWN){
             if(uzayGemisiY >=490){
                 uzayGemisiY =490;
             }
             else{
             uzayGemisiY +=dirUzayY;
             }
       }
        
       
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Ateş ateş : ateşler){
        
            ateş.setY(ateş.getY()- ateşdirY);
        }
        topX += topdirX;
        
        if(topX >= 750){
            
            topdirX = -topdirX;
        }
        if(topX <=0 ){
            
            topdirX = -topdirX;
        }
        repaint();
    }
    
}
