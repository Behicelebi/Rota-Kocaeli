package com.project.transportation;

import java.awt.*;
import java.util.logging.Logger;

public abstract class TopluTasima implements ColoringMethods{
    protected String type;
    protected Image durakTexture;
    protected static final Logger logger = Logger.getLogger(TopluTasima.class.getName());
    public abstract String getType();
    public abstract Image getDurakTexture();
}
