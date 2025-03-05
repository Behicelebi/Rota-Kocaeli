import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Panel extends JPanel {
    int WIDTH;
    int HEIGHT;
    public BufferedImage map_texture;
    private static final Logger logger = Logger.getLogger(Panel.class.getName());

    Panel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        try {
            map_texture = ImageIO.read(new File("textures/map.png"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading texture: textures/map.png", e);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(map_texture,0,0,this);
    }
}
