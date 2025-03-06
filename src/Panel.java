import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Panel extends JPanel implements ActionListener {
    int WIDTH;
    int HEIGHT;
    public BufferedImage map_texture;
    JComboBox selectType, selectBuy, baslangicDurak, bitisDurak;
    JButton baslangicButton, bitisButton, calculateButton;
    double baslangic_lat=0, baslangic_lon=0, bitis_lat=0, bitis_lon=0;
    private static final Logger logger = Logger.getLogger(Panel.class.getName());
    double minlat=40.75,maxlat=40.83,minlon=29.90,maxlon=29.97;

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

        calculateButton = new JButton("Hesapla");
        calculateButton.setBounds(20,200,100,20);
        calculateButton.setFocusable(false);
        calculateButton.addActionListener(this);
        this.add(calculateButton);
    }

    public int mapToX(double longitude) {
        return (int) (1200.0 * (longitude - minlon) / (maxlon - minlon));
    }

    public int mapToY(double latitude) {
        // Latitude is inverted because y increases downwards on the screen
        return (int) (800.0 * (maxlat - latitude) / (maxlat - minlat));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //g.drawImage(map_texture,0,0,this);
        g.setColor(Color.blue);
        g.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,8));
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getType(), "bus")){g.setColor(Color.red);}
            else{g.setColor(Color.blue);}
            g.fillRect(mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),10,10);
            g.drawString(Main.anaVeri.getDuraklar().get(i).getName(),mapToX(Main.anaVeri.getDuraklar().get(i).getLon()) - 20,mapToY(Main.anaVeri.getDuraklar().get(i).getLat()) + 20);
            g.setColor(Color.gray);
            for (int j = 0; j < Main.anaVeri.getDuraklar().get(i).getNextStops().size(); j++) {
                for (int k = 0; k < Main.anaVeri.getDuraklar().size(); k++) {
                    if(Objects.equals(Main.anaVeri.getDuraklar().get(k).getId(), Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId())){
                        g.drawLine(mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDuraklar().get(k).getLon()),mapToY(Main.anaVeri.getDuraklar().get(k).getLat()));
                    }
                }
            }
            g.setColor(Color.white);
            if(Main.anaVeri.getDuraklar().get(i).getTransfer()!=null){
                for (int j = 0; j < Main.anaVeri.getDuraklar().size(); j++) {
                    if(Objects.equals(Main.anaVeri.getDuraklar().get(j).getId(), Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId())){
                        g.drawLine(mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDuraklar().get(j).getLon()),mapToY(Main.anaVeri.getDuraklar().get(j).getLat()));
                    }
                }
            }
        }
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
        } else if (e.getSource() == calculateButton) {
            RotaHesaplayici.calculateShortestPath(baslangic_lat,baslangic_lon,bitis_lat,bitis_lon);
        }
    }
}
