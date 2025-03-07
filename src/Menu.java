import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel{
    int WIDTH;
    int HEIGHT;

    Menu(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,40));
        g.drawString("EKOMOBİL 2",490,50);
        g.setFont(new Font("Arial",Font.PLAIN,15));
        g.drawString(Main.anaVeri.getCity(),30,350);
        g.drawString("Taksi Açılış Ücreti: " + Main.anaVeri.getTaxi().getOpeningFee(),30,380);
        g.drawString("Scotter Açılış Ücreti: " + Main.anaVeri.getScotter().getOpeningFee(),30,410);
        g.drawString("Martı Tag Açılış Ücreti: " + Main.anaVeri.getMarti_tag().getOpeningFee(),30,440);
        g.drawString(Main.anaVeri.getDuraklar().get(0).getId() + " Durağının bir sonraki durağı " + Main.anaVeri.getDuraklar().get(0).getNextStops().get(0).getStopId(), 30, 470);
    }
}
