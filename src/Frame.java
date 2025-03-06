import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    int WIDTH = 1200;
    int HEIGHT = 800;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int)screenSize.getWidth()/2-(WIDTH/2);
    int screenHeight = (int)screenSize.getHeight()/2-(HEIGHT/2);

    Panel gamePanel = new Panel(WIDTH,HEIGHT);
    Menu menu = new Menu(WIDTH,HEIGHT);

    JButton button;

    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("EKOMOBİL 2");
        this.setResizable(false);
        this.setLocation(screenWidth,screenHeight);
        this.setSize(WIDTH,HEIGHT);
        this.add(menu);
        button = new JButton("START");
        button.setBounds(220,200,80,30);
        button.setFocusable(false);
        button.addActionListener(this);
        menu.add(button);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Frame frame = this;
        if(e.getSource()==button){
            frame.getContentPane().remove(menu);
            frame.getContentPane().add(gamePanel);
            frame.getContentPane().revalidate();
            frame.getContentPane().repaint();
            gamePanel.requestFocus();
        }
    }
}
