package com.project.view;

import com.project.main.*;
import com.project.util.*;
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

    //SOLID PRENSIPLERINDEN AÇIK/KAPALI PRENSİBİNİN DOĞRU UYGULANMASI İÇİN BURASI ÖNEMLİ

    DistanceCalculator haversineCalculator = new HaversineDistance();
    RotaHesaplayici rotaHesaplayici = new RotaHesaplayici(haversineCalculator);

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
        selectType.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.Yolcular.size(); i++){selectType.addItem(Main.Yolcular.get(i).getClassName());}
        selectType.addActionListener(this);
        selectType.setFocusable(false);
        selectType.setBounds(20,194,160,23);
        this.add(selectType);

        selectBuy = new JComboBox();
        selectBuy.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.OdemeYontemleri.size(); i++){selectBuy.addItem(Main.OdemeYontemleri.get(i).getClassName());}
        selectBuy.addActionListener(this);
        selectBuy.setFocusable(false);
        selectBuy.setBounds(20,257,160,23);
        this.add(selectBuy);

        baslangicDurak = new JComboBox();
        baslangicDurak.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){baslangicDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        baslangicDurak.addActionListener(this);
        baslangicDurak.setFocusable(false);
        baslangicDurak.setBounds(20,377,160,23);
        this.add(baslangicDurak);

        bitisDurak = new JComboBox();
        bitisDurak.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){bitisDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        bitisDurak.addActionListener(this);
        bitisDurak.setFocusable(false);
        bitisDurak.setBounds(20,567,160,23);
        this.add(bitisDurak);

        baslangicButton = new JButton("Manuel Seç");
        baslangicButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        baslangicButton.setBounds(20,320,160,23);
        baslangicButton.setFocusable(false);
        baslangicButton.addActionListener(this);
        this.add(baslangicButton);

        bitisButton = new JButton("Manuel Seç");
        bitisButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        bitisButton.setBounds(20,510,160,23);
        bitisButton.setFocusable(false);
        bitisButton.addActionListener(this);
        this.add(bitisButton);

        calculateButton = new JButton("Hesapla");
        calculateButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        calculateButton.setBounds(20,700,160,23);
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
        g.drawImage(map_texture,0,0,this);
        g.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,11));
        g.setColor(Color.blue);
        //Set  anti-alias
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set anti-alias for text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            g.setColor(Color.green);
            for (int j = 0; j < Main.anaVeri.getDuraklar().get(i).getNextStops().size(); j++) {
                drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLat()));
            }
            if(Main.anaVeri.getDuraklar().get(i).getTransfer()!=null){
                g.setColor(Color.orange);
                drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLat()));
            }
        }
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getType(), "bus")){
                g2d.setColor(Color.red);
                g2d.drawImage(bus_durak_texture,mapToX(Main.anaVeri.getDuraklar().get(i).getLon())-5,mapToY(Main.anaVeri.getDuraklar().get(i).getLat())-5,this);
            }
            else if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getType(), "tram")){
                g2d.setColor(Color.yellow);
                g2d.drawImage(tram_durak_texture,mapToX(Main.anaVeri.getDuraklar().get(i).getLon())-5,mapToY(Main.anaVeri.getDuraklar().get(i).getLat())-5,this);
            }

            g2d.drawString(Main.anaVeri.getDuraklar().get(i).getName(),mapToX(Main.anaVeri.getDuraklar().get(i).getLon()) - 40,mapToY(Main.anaVeri.getDuraklar().get(i).getLat()) + 20);
        }
        g2d.drawImage(startingLocation,BaslangicX-6,BaslangicY-6,this);
        g2d.drawImage(finalLocation,BitisX-6,BitisY-15,this);
        g2d.setColor(new Color(128,128,128,150));
        g2d.fillRect(0,0,200,HEIGHT);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,25));
        g2d.drawString("E-KOMOBIL 2", 10,110);
        g2d.setFont(new Font("Consolas",Font.PLAIN,13));
        g2d.drawString("Yolcu tipi seçiniz",10,180);
        g2d.drawString("Ödeme tipi seçiniz",10,243);
        g2d.drawString("Başlangıç noktası seçiniz",10,306);
        g2d.drawString("Bitiş noktası seçiniz",10,496);
        g2d.drawString("Durak seç",20,363);
        g2d.drawString("Seçilen:",20,420);
        g2d.drawString("Lat: "+baslangic_lat,20,440);
        g2d.drawString("Lon: "+baslangic_lon,20,460);
        g2d.drawString("Durak seç",20,553);
        g2d.drawString("Seçilen:",20,610);
        g2d.drawString("Lat: "+bitis_lat,20,630);
        g2d.drawString("Lon: "+bitis_lon,20,650);
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
            else{System.out.println(rotaHesaplayici.findPaths(baslangic_lat,baslangic_lon,bitis_lat,bitis_lon));} //Buraya Döneceğiz
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
