import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    int WIDTH;
    int HEIGHT;
    Panel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
    }
}
