package com.project.view;

import com.project.main.*;
import com.project.model.RotaBilgisi;
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
import java.util.List;

public class Panel extends JPanel implements ActionListener , MouseListener {
    int WIDTH;
    int HEIGHT;
    public Image map_texture,bus_durak_texture,tram_durak_texture,startingLocation,finalLocation;
    int BaslangicX =-10, BaslangicY =-10, BitisX=-10, BitisY=-10;
    JComboBox<String> selectType, selectBuy, selectArac, selectPath, baslangicDurak, bitisDurak;
    JButton baslangicButton, bitisButton, calculateButton;
    boolean baslangicSeciliyor=false,bitisSeciliyor=false,calculated=false;
    double baslangic_lat=0, baslangic_lon=0, bitis_lat=0, bitis_lon=0;
    private static final Logger logger = Logger.getLogger(Panel.class.getName());
    double minlat=40.75,maxlat=40.83,minlon=29.90,maxlon=29.97;
    List<RotaBilgisi> rotaInfo;

    //SOLID PRENSIPLERINDEN AÇIK/KAPALI PRENSİBİNİN DOĞRU UYGULANMASI İÇİN BURASI ÖNEMLİ

    RotaHesaplayici rotaHesaplayici = new RotaHesaplayici(new HaversineDistance());

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

        selectType = new JComboBox<>();
        selectType.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.Yolcular.size(); i++){selectType.addItem(Main.Yolcular.get(i).getClassName());}
        selectType.addActionListener(this);
        selectType.setFocusable(false);
        selectType.setBounds(20,164,160,23);
        this.add(selectType);

        selectBuy = new JComboBox<>();
        selectBuy.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.OdemeYontemleri.size(); i++){selectBuy.addItem(Main.OdemeYontemleri.get(i).getClassName());}
        selectBuy.addActionListener(this);
        selectBuy.setFocusable(false);
        selectBuy.setBounds(20,227,160,23);
        this.add(selectBuy);

        selectArac = new JComboBox<>();
        selectArac.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.Araclar.size(); i++){selectArac.addItem(Main.Araclar.get(i).getName());}
        selectArac.addActionListener(this);
        selectArac.setFocusable(false);
        selectArac.setBounds(20,104,160,23);
        this.add(selectArac);

        baslangicDurak = new JComboBox<>();
        baslangicDurak.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){baslangicDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        baslangicDurak.setSelectedItem(null);
        baslangicDurak.addActionListener(this);
        baslangicDurak.setFocusable(false);
        baslangicDurak.setBounds(20,347,160,23);
        this.add(baslangicDurak);

        bitisDurak = new JComboBox<>();
        bitisDurak.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){bitisDurak.addItem(Main.anaVeri.getDuraklar().get(i).getName());}
        bitisDurak.setSelectedItem(null);
        bitisDurak.addActionListener(this);
        bitisDurak.setFocusable(false);
        bitisDurak.setBounds(20,526,160,23);
        this.add(bitisDurak);

        baslangicButton = new JButton("Haritadan Seç");
        baslangicButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        baslangicButton.setBounds(20,290,160,23);
        baslangicButton.setFocusable(false);
        baslangicButton.addActionListener(this);
        baslangicButton.setBackground(Color.cyan);
        this.add(baslangicButton);

        bitisButton = new JButton("Haritadan Seç");
        bitisButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        bitisButton.setBounds(20,469,160,23);
        bitisButton.setFocusable(false);
        bitisButton.addActionListener(this);
        bitisButton.setBackground(new Color(255,99,71));
        this.add(bitisButton);

        calculateButton = new JButton("Yolları Hesapla");
        calculateButton.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        calculateButton.setBounds(20,624,160,23);
        calculateButton.setFocusable(false);
        calculateButton.addActionListener(this);
        calculateButton.setBackground(Color.YELLOW);
        this.add(calculateButton);

        selectPath = new JComboBox<>();
        selectPath.setFont(new Font("Consolas Bold",Font.PLAIN,15));
        selectPath.addActionListener(this);
        selectPath.setFocusable(false);
        selectPath.setBounds(20,680,160,23);
        this.add(selectPath);
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

    public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){
        Graphics2D g2d = (Graphics2D) g.create();
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        drawLineWithArrow(g2d, x1, y1, x2, y2);
        g2d.dispose();
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //Set  anti-alias
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Set anti-alias for text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawImage(map_texture,0,0,this);
        g2d.setFont(new Font("Copperplate Gothic Bold",Font.PLAIN,11));
        g.setColor(Color.blue);
        if(!calculated){
            for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
                g.setColor(Color.green);
                for (int j = 0; j < Main.anaVeri.getDuraklar().get(i).getNextStops().size(); j++) {
                    drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getNextStops().get(j).getStopId()).getLat()));
                }
                if(Main.anaVeri.getDuraklar().get(i).getTransfer()!=null){
                    g.setColor(Color.orange);
                    drawDashedLine(g,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLat()));
                    //drawLineWithArrow(g2d,mapToX(Main.anaVeri.getDuraklar().get(i).getLon()),mapToY(Main.anaVeri.getDuraklar().get(i).getLat()),mapToX(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLon()),mapToY(Main.anaVeri.getDurakMap().get(Main.anaVeri.getDuraklar().get(i).getTransfer().getTransferStopId()).getLat()));
                }
            }
        }else {
            if (!rotaInfo.isEmpty()) {
                if(!rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().isEmpty()){
                    g.setColor(Color.cyan);
                    if(rotaHesaplayici.getDistanceCalculator().calculateDistance(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLatitude(),rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLongitude(),Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(0)).getLat(),Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(0)).getLon()) > 3.0){g.setColor(Main.Araclar.get(selectArac.getSelectedIndex()).getColor());}
                    drawLineWithArrow(g2d, mapToX(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLongitude()), mapToY(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLatitude()), mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(0)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(0)).getLat()));
                    g.setColor(Color.green);
                    for (int i = 0; i < rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().size() - 1; i++) {
                        if(!Objects.equals(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i)).getType(), Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i + 1)).getType())){
                            drawDashedLine(g, mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i)).getLat()), mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i + 1)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i + 1)).getLat()));
                        }else{
                            drawLineWithArrow(g2d, mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i)).getLat()), mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i + 1)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(i + 1)).getLat()));
                        }
                    }
                    g.setColor(Color.cyan);
                    if(rotaHesaplayici.getDistanceCalculator().calculateDistance(rotaInfo.get(selectPath.getSelectedIndex()).getBitisLatitude(),rotaInfo.get(selectPath.getSelectedIndex()).getBitisLongitude(),Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().size()-1)).getLat(),Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().size()-1)).getLon()) > 3.0){g.setColor(Main.Araclar.get(selectArac.getSelectedIndex()).getColor());}
                    drawLineWithArrow(g2d, mapToX(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().size() - 1)).getLon()), mapToY(Main.anaVeri.getDurakMap().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().get(rotaInfo.get(selectPath.getSelectedIndex()).getYolDuraklari().size() - 1)).getLat()), mapToX(rotaInfo.get(selectPath.getSelectedIndex()).getBitisLongitude()), mapToY(rotaInfo.get(selectPath.getSelectedIndex()).getBitisLatitude()));
                } else {
                    g.setColor(Color.cyan);
                    if(rotaHesaplayici.getDistanceCalculator().calculateDistance(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLatitude(),rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLongitude(),rotaInfo.get(selectPath.getSelectedIndex()).getBitisLatitude(),rotaInfo.get(selectPath.getSelectedIndex()).getBitisLongitude()) > 3.0){g.setColor(Main.Araclar.get(selectArac.getSelectedIndex()).getColor());}
                    drawLineWithArrow(g2d, mapToX(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLongitude()), mapToY(rotaInfo.get(selectPath.getSelectedIndex()).getBaslangicLatitude()), mapToX(rotaInfo.get(selectPath.getSelectedIndex()).getBitisLongitude()), mapToY(rotaInfo.get(selectPath.getSelectedIndex()).getBitisLatitude()));
                }
            }
        }
        for (int i = 0; i< Main.anaVeri.getDuraklar().size(); i++){
            if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getVehicle().getType(), "bus")){
                g2d.setColor(Color.red);
                g2d.drawImage(bus_durak_texture,mapToX(Main.anaVeri.getDuraklar().get(i).getLon())-5,mapToY(Main.anaVeri.getDuraklar().get(i).getLat())-5,this);
            }
            else if(Objects.equals(Main.anaVeri.getDuraklar().get(i).getVehicle().getType(), "tram")){
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
        g2d.drawString("E-KOMOBIL 2", 10,50);
        g2d.setFont(new Font("Consolas",Font.PLAIN,13));
        g2d.drawString("Araç tipi seçiniz",10,90);
        g2d.drawString("Yolcu tipi seçiniz",10,150);
        g2d.drawString("Ödeme tipi seçiniz",10,213);
        g2d.drawString("Başlangıç noktası seçiniz",10,276);
        g2d.drawString("Bitiş noktası seçiniz",10,455);
        g2d.drawString("Ya da durak seç",20,333);
        g2d.drawString("Seçilen:",20,390);
        g2d.drawString(String.format("Enlem: %.6f°",baslangic_lat),20,410);
        g2d.drawString(String.format("Boylam: %.6f°",baslangic_lon),20,430);
        g2d.drawString("Ya da durak seç",20,512);
        g2d.drawString("Seçilen:",20,569);
        g2d.drawString(String.format("Enlem: %.6f°",bitis_lat),20,589);
        g2d.drawString(String.format("Boylam: %.6f°",bitis_lon),20,609);
        g2d.drawString("Hesaplanan yollar",10,667);
        if(rotaInfo == null || rotaInfo.isEmpty() || !calculated){
            g2d.drawString("Uzunluk: 0.0 km",20,725);
            g2d.drawString("Sure: 0.0 dk",20,745);
            g2d.drawString("Ucret: 0.0 TL",20,765);
        }else{
            g2d.drawString(String.format("Uzunluk: %.2f km",rotaInfo.get(selectPath.getSelectedIndex()).getYolUzunlugu()),20,725);
            g2d.drawString(String.format("Sure: %.2f dk",rotaInfo.get(selectPath.getSelectedIndex()).getYolSuresi()),20,745);
            g2d.drawString(String.format("Ucret: %.2f TL",rotaInfo.get(selectPath.getSelectedIndex()).getYolUcreti()),20,765);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == baslangicButton) {
            baslangicButton.setText("Seciliyor...");
            baslangicSeciliyor = true;
            selectType.setEnabled(false);
            selectBuy.setEnabled(false);
            baslangicButton.setEnabled(false);
            bitisButton.setEnabled(false);
            baslangicDurak.setEnabled(false);
            bitisDurak.setEnabled(false);
            calculateButton.setEnabled(false);
            selectPath.removeAllItems();
            calculated=false;
            repaint();
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
            selectPath.removeAllItems();
            calculated=false;
            repaint();
        } else if (e.getSource() == baslangicDurak) {
            BaslangicX = mapToX(Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLon());
            BaslangicY = mapToY(Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLat());
            baslangic_lat = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLat();
            baslangic_lon = Main.anaVeri.getDuraklar().get(baslangicDurak.getSelectedIndex()).getLon();
            selectPath.removeAllItems();
            calculated=false;
            repaint();
        } else if (e.getSource() == bitisDurak) {
            BitisX = mapToX(Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLon());
            BitisY = mapToY(Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLat());
            bitis_lat = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLat();
            bitis_lon = Main.anaVeri.getDuraklar().get(bitisDurak.getSelectedIndex()).getLon();
            selectPath.removeAllItems();
            calculated=false;
            repaint();
        } else if (e.getSource() == calculateButton) {
            if(baslangic_lat==0.0 && baslangic_lon==0.0 && bitis_lat==0.0 && bitis_lon==0.0){JOptionPane.showMessageDialog(null,"Bir yer seçiniz lütfen");}
            else if(baslangic_lat==bitis_lat && baslangic_lon==bitis_lon){JOptionPane.showMessageDialog(null,"Aynı yeri seçmeyiniz lütfen");}
            else if(baslangic_lat==0.0 || baslangic_lon==0.0){JOptionPane.showMessageDialog(null,"Başlangıç yerini seçiniz lütfen");}
            else if(bitis_lat==0.0 || bitis_lon==0.0){JOptionPane.showMessageDialog(null,"Bitiş yerini seçiniz lütfen");}
            else{
                rotaInfo = rotaHesaplayici.calculatePathDetails(baslangic_lat, baslangic_lon, bitis_lat, bitis_lon,
                        selectArac.getSelectedIndex(), selectType.getSelectedIndex(), selectBuy.getSelectedIndex());
                selectPath.removeAllItems();
                for (int i = 1; i <= rotaInfo.size(); i++) {selectPath.addItem("Path "+i);}
                calculated=true;
                repaint();
            } //Buraya Döneceğiz
        } else if (e.getSource() == selectPath) {
            repaint();
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
