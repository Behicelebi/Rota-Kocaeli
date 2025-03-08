import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Panel extends JPanel implements ActionListener , MouseListener {
    int WIDTH;
    int HEIGHT;
    public Image map_texture,bus_durak_texture,tram_durak_texture,startingLocation,finalLocation;
    int BaslangicX =-10, BaslangicY =-10, BitisX=-10, BitisY=-10;
    JComboBox selectType, selectBuy, baslangicDurak, bitisDurak;
    JButton baslangicButton, bitisButton, calculateButton;
    boolean baslangicSeciliyor=false,bitisSeciliyor=false;
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
        addMouseListener(this);

        try {
            map_texture = ImageIO.read(new File("textures/map.png"));
            bus_durak_texture = ImageIO.read(new File("textures/bus_durak_texture.png"));
            tram_durak_texture = ImageIO.read(new File("textures/tram_durak_texture.png"));
            startingLocation = ImageIO.read(new File("textures/starting_location.png"));
            finalLocation = ImageIO.read(new File("textures/final_location.png"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading texture", e);
        }

        selectType = new JComboBox();
        for (int i = 0; i< Main.Yolcular.size(); i++){selectType.addItem(Main.Yolcular.get(i).getClassName());}
        selectType.addActionListener(this);
        selectType.setFocusable(false);
        selectType.setBounds(20,40,150,20);
        this.add(selectType);

        selectBuy = new JComboBox();
        for (int i = 0; i< Main.OdemeYontemleri.size(); i++){selectBuy.addItem(Main.OdemeYontemleri.get(i).getClassName());}
        selectBuy.addActionListener(this);
        selectBuy.setFocusable(false);
        selectBuy.setBounds(20,90,150,20);
        this.add(selectBuy);

        baslangicDurak = new JComboBox();
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){baslangicDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        baslangicDurak.addActionListener(this);
        baslangicDurak.setFocusable(false);
        baslangicDurak.setBounds(20,180,150,20);
        this.add(baslangicDurak);

        bitisDurak = new JComboBox();
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){bitisDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        bitisDurak.addActionListener(this);
        bitisDurak.setFocusable(false);
        bitisDurak.setBounds(20,300,150,20);
        this.add(bitisDurak);

        baslangicButton = new JButton("Manuel Sec");
        baslangicButton.setBounds(20,140,150,20);
        baslangicButton.setFocusable(false);
        baslangicButton.addActionListener(this);
        this.add(baslangicButton);

        bitisButton = new JButton("Manuel Sec");
        bitisButton.setBounds(20,260,150,20);
        bitisButton.setFocusable(false);
        bitisButton.addActionListener(this);
        this.add(bitisButton);

        calculateButton = new JButton("Hesapla");
        calculateButton.setBounds(20,360,150,20);
        calculateButton.setFocusable(false);
        calculateButton.addActionListener(this);
        this.add(calculateButton);
    }

    public int mapToX(double longitude) {
        return (int) (1200.0 * (longitude - minlon) / (maxlon - minlon));
    }

    public int mapToY(double latitude) {
        return (int) (800.0 * (maxlat - latitude) / (maxlat - minlat));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void drawLineWithArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));

        double midX = (x1 + x2) / 2.0;
        double midY = (y1 + y2) / 2.0;

        int arrowLength = 10;
        int arrowWidth = 5;

        double angle = Math.atan2(y2 - y1, x2 - x1);

        double x1_arrow = midX - arrowLength * Math.cos(angle - Math.atan2(arrowWidth, arrowLength));
        double y1_arrow = midY - arrowLength * Math.sin(angle - Math.atan2(arrowWidth, arrowLength));
        double x2_arrow = midX - arrowLength * Math.cos(angle + Math.atan2(arrowWidth, arrowLength));
        double y2_arrow = midY - arrowLength * Math.sin(angle + Math.atan2(arrowWidth, arrowLength));

        Path2D.Double arrowHead = new Path2D.Double();
        arrowHead.moveTo(midX, midY);
        arrowHead.lineTo(x1_arrow, y1_arrow);
        arrowHead.lineTo(x2_arrow, y2_arrow);
        arrowHead.closePath();

        g2d.fill(arrowHead);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //g.drawImage(map_texture,0,0,this);
        g.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,8));
        g.setColor(Color.blue);
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            g.setColor(Color.gray);
            for (int j = 0; j < Main.anaVeri.getDuraklar().get(i).getNextStops().size(); j++) {
                drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLat()));
            }
            if(Main.anaVeri.getDuraklar().get(i).getTransfer()!=null){
                g.setColor(Color.white);
                drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLat()));
            }
        }
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getType(), "bus")){
                g.setColor(Color.red);
                g.drawImage(bus_durak_texture,mapToX(Main.anaVeri.getDuraklar().get(i).getLon())-5,mapToY(Main.anaVeri.getDuraklar().get(i).getLat())-5,this);
            }
            else if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getType(), "tram")){
                g.setColor(Color.blue);
                g.drawImage(tram_durak_texture,mapToX(Main.anaVeri.getDuraklar().get(i).getLon())-5,mapToY(Main.anaVeri.getDuraklar().get(i).getLat())-5,this);
            }

            g.drawString(Main.anaVeri.getDuraklar().get(i).getName(),mapToX(Main.anaVeri.getDuraklar().get(i).getLon()) - 20,mapToY(Main.anaVeri.getDuraklar().get(i).getLat()) + 20);
        }
        g.drawImage(startingLocation,BaslangicX-6,BaslangicY-6,this);
        g.drawImage(finalLocation,BitisX-6,BitisY-15,this);
        g.setColor(new Color(128,128,128,150));
        g.fillRect(0,0,200,HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,13));
        g.drawString("Yolcu tipi seçiniz",10,30);
        g.drawString("Ödeme tipi seçiniz",10,80);
        g.drawString("Başlangıç noktası seçiniz",10,130);
        g.drawString("Bitiş noktası seçiniz",10,250);
        g.setFont(new Font("Arial",Font.PLAIN,10));
        g.drawString("Durak seç",20,175);
        g.drawString("Seçilen:",20,210);
        g.drawString("Lat: "+baslangic_lat,20,220);
        g.drawString("Lon: "+baslangic_lon,20,230);
        g.drawString("Durak seç",20,295);
        g.drawString("Seçilen:",20,330);
        g.drawString("Lat: "+bitis_lat,20,340);
        g.drawString("Lon: "+bitis_lon,20,350);
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
            baslangicButton.setText("Seciliyor...");
            baslangicSeciliyor = true;
            selectType.setEnabled(false);
            selectBuy.setEnabled(false);
            baslangicButton.setEnabled(false);
            bitisButton.setEnabled(false);
            baslangicDurak.setEnabled(false);
            bitisDurak.setEnabled(false);
            calculateButton.setEnabled(false);
        } else if (e.getSource() == bitisButton) {
            bitisButton.setText("Seciliyor...");
            bitisSeciliyor = true;
            selectType.setEnabled(false);
            selectBuy.setEnabled(false);
            baslangicButton.setEnabled(false);
            bitisButton.setEnabled(false);
            baslangicDurak.setEnabled(false);
            bitisDurak.setEnabled(false);
            calculateButton.setEnabled(false);
        } else if (e.getSource() == baslangicDurak) {
            for (int i = 0; i < Main.anaVeri.getDuraklar().size(); i++) {
                if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getName(), baslangicDurak.getSelectedItem())){
                    BaslangicX = mapToX(Main.anaVeri.getDuraklar().get(i).getLon());
                    BaslangicY = mapToY(Main.anaVeri.getDuraklar().get(i).getLat());
                    break;
                }
            }
            baslangic_lat = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLat();
            baslangic_lon = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLon();
            repaint();
        } else if (e.getSource() == bitisDurak) {
            for (int i = 0; i < Main.anaVeri.getDuraklar().size(); i++) {
                if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getName(), bitisDurak.getSelectedItem())){
                    BitisX = mapToX(Main.anaVeri.getDuraklar().get(i).getLon());
                    BitisY = mapToY(Main.anaVeri.getDuraklar().get(i).getLat());
                    break;
                }
            }
            bitis_lat = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLat();
            bitis_lon = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLon();
            repaint();
        } else if (e.getSource() == calculateButton) {
            if(baslangic_lat==bitis_lat && baslangic_lon==bitis_lon){JOptionPane.showMessageDialog(null,"Aynı yeri seçmeyiniz lütfen");}
            else{System.out.println(RotaHesaplayici.findPaths(baslangic_lat,baslangic_lon,bitis_lat,bitis_lon));}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int SecilenX=e.getX(), SecilenY=e.getY();
        if(baslangicSeciliyor){
            BaslangicX = SecilenX;
            BaslangicY = SecilenY;
            baslangic_lon = minlon + ((maxlon - minlon) * (double)SecilenX / 1200.0);
            baslangic_lat = maxlat - ((maxlat - minlat) * (double)SecilenY / 800.0);
            System.out.println(baslangic_lat);
            System.out.println(baslangic_lon);
            baslangicButton.setText("Manuel Sec");
            baslangicSeciliyor = false;
            selectType.setEnabled(true);
            selectBuy.setEnabled(true);
            baslangicButton.setEnabled(true);
            bitisButton.setEnabled(true);
            baslangicDurak.setEnabled(true);
            bitisDurak.setEnabled(true);
            calculateButton.setEnabled(true);
            repaint();
        }
        else if (bitisSeciliyor) {
            BitisX = SecilenX;
            BitisY = SecilenY;
            bitis_lon = minlon + ((maxlon - minlon) * (double)SecilenX / 1200.0);
            bitis_lat = maxlat - ((maxlat - minlat) * (double)SecilenY / 800.0);
            System.out.println(bitis_lat);
            System.out.println(bitis_lon);
            bitisButton.setText("Manuel Sec");
            bitisSeciliyor = false;
            selectType.setEnabled(true);
            selectBuy.setEnabled(true);
            baslangicButton.setEnabled(true);
            bitisButton.setEnabled(true);
            baslangicDurak.setEnabled(true);
            bitisDurak.setEnabled(true);
            calculateButton.setEnabled(true);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
