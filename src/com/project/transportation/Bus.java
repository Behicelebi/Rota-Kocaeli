package com.project.transportation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Bus extends TopluTasima {
    public Bus(){
        type = "bus";
        try{
            durakTexture = ImageIO.read(new File("textures/bus_durak_texture.png"));
        } catch (IOException e){
            logger.log(Level.SEVERE, "Error loading texture", e);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Image getDurakTexture() {
        return durakTexture;
    }

    @Override
    public Color getIconColor() {
        return Color.red;
    }

    @Override
    public Color getLineColor() {
        return Color.green;
    }
}