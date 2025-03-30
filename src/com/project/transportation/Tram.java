package com.project.transportation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Tram extends TopluTasima {
    public Tram(){
        type = "tram";
        try{
            durakTexture = ImageIO.read(new File("textures/tram_durak_texture.png"));
        } catch (IOException e) {
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
        return Color.yellow;
    }

    @Override
    public Color getLineColor() {
        return Color.magenta;
    }
}
