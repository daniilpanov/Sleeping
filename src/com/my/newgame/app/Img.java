package com.my.newgame.app;

import com.my.newgame.app.settings.CSettings;

import javax.swing.*;
import java.awt.*;

public class Img
{
    //
    private static final String DIRECTORY = "res/img/";
    //
    private static String current_directory = DIRECTORY;
    //
    private static void setCurrentDirectory(String current_directory)
    {
        Img.current_directory = DIRECTORY + current_directory + "/";
    }
    //
    private Img()
    {
    }
    
    //
    public static final Image[] slides;
    //
    public static final ImageIcon
            //
            player,
            //
            menu_play, menu_settings, menu_about, menu_exit;
    //
    static
    {
        //
        setCurrentDirectory("sprites");
        player = getImageIcon("player.png");
        //
        setCurrentDirectory("menu_buttons");
        menu_play = getImageIcon("play.png");
        menu_settings = getImageIcon("settings.png");
        menu_about = getImageIcon("about.png");
        menu_exit = getImageIcon("exit.png");
        //
        setCurrentDirectory("slides");
        slides = getSlides();
        //
        setCurrentDirectory("");
    }
    //
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
    //
    private static Image getImage(String name)
    {
        return getImageIcon(name).getImage();
    }
    //
    private static ImageIcon getImageIcon(String name)
    {
        return new ImageIcon(current_directory + name);
    }
}
