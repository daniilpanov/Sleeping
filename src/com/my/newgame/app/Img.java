package com.my.newgame.app;

import com.my.newgame.app.settings.CSettings;

import javax.swing.*;
import java.awt.*;

public class Img
{
    private static final String DIRECTORY = "res/img/";
    
    private Img()
    {
    }
    
    public static final Image[] slides = getSlides();
    public static final ImageIcon player = getImageIcon("player.png");
    
    private static Image[] getSlides()
    {
        int slides = Integer.parseInt(
                CSettings.getInstance()
                        .getSetting(CSettings.GLOBAL_SETTINGS, "slides")
        );
        
        Image[] slides_list = new Image[slides];
    
        for (int i = 0; i < slides; i++)
        {
            slides_list[i] = getImage("slide" + i + ".jpg");
        }
        
        return slides_list;
    }
    
    private static Image getImage(String name)
    {
        return getImageIcon(name).getImage();
    }
    
    private static ImageIcon getImageIcon(String name)
    {
        return new ImageIcon(DIRECTORY + name);
    }
}
