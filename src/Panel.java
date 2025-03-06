import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Panel extends JPanel implements ActionListener {
    int WIDTH;
    int HEIGHT;
    public BufferedImage map_texture;
    JComboBox selectType, selectBuy, baslangicDurak, bitisDurak;
    JButton baslangicButton, bitisButton;
    double baslangic_lat=0, baslangic_lon=0, bitis_lat=0, bitis_lon=0;
    private static final Logger logger = Logger.getLogger(Panel.class.getName());

    Panel(int WIDTH, int HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);

        try {
            map_texture = ImageIO.read(new File("textures/map.png"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading texture: textures/map.png", e);
        }

        selectType = new JComboBox();
        for (int i = 0; i< Main.Yolcular.size(); i++){selectType.addItem(Main.Yolcular.get(i).getClassName());}
        selectType.addActionListener(this);
        selectType.setFocusable(false);
        selectType.setBounds(20,20,90,20);
        this.add(selectType);

        selectBuy = new JComboBox();
        for (int i = 0; i< Main.OdemeYontemleri.size(); i++){selectBuy.addItem(Main.OdemeYontemleri.get(i).getClassName());}
        selectBuy.addActionListener(this);
        selectBuy.setFocusable(false);
        selectBuy.setBounds(200,20,90,20);
        this.add(selectBuy);

        baslangicDurak = new JComboBox();
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){baslangicDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        baslangicDurak.addActionListener(this);
        baslangicDurak.setFocusable(false);
        baslangicDurak.setBounds(230,100,150,20);
        this.add(baslangicDurak);

        bitisDurak = new JComboBox();
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){bitisDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        bitisDurak.addActionListener(this);
        bitisDurak.setFocusable(false);
        bitisDurak.setBounds(230,150,150,20);
        this.add(bitisDurak);

        baslangicButton = new JButton("Baslangic Sec");
        baslangicButton.setBounds(20,100,150,20);
        baslangicButton.setFocusable(false);
        baslangicButton.addActionListener(this);
        this.add(baslangicButton);

        bitisButton = new JButton("Bitis Sec");
        bitisButton.setBounds(20,150,150,20);
        bitisButton.setFocusable(false);
        bitisButton.addActionListener(this);
        this.add(bitisButton);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //g.drawImage(map_texture,0,0,this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectType){
            System.out.println(selectType.getSelectedIndex());
            System.out.println(Main.Yolcular.get(selectType.getSelectedIndex()).getDiscountPrice());
        } else if (e.getSource() == selectBuy) {
            System.out.println(selectBuy.getSelectedIndex());
            System.out.println(Main.OdemeYontemleri.get(selectBuy.getSelectedIndex()).getDiscountPrice());
        } else if (e.getSource() == baslangicButton) {
            baslangic_lat = Double.parseDouble(JOptionPane.showInputDialog("Enter Latitude"));
            baslangic_lon = Double.parseDouble(JOptionPane.showInputDialog("Enter Longitude"));
        } else if (e.getSource() == bitisButton) {
            bitis_lat = Double.parseDouble(JOptionPane.showInputDialog("Enter Latitude"));
            bitis_lon = Double.parseDouble(JOptionPane.showInputDialog("Enter Longitude"));
        } else if (e.getSource() == baslangicDurak) {
            baslangic_lat = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLat();
            baslangic_lon = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLon();
        } else if (e.getSource() == bitisDurak) {
            bitis_lat = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLat();
            bitis_lon = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLon();
        }
    }
}
