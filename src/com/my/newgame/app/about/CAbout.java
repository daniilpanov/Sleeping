package com.my.newgame.app.about;

import com.my.newgame.app.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CAbout extends MAbout
{
    private static CAbout instance = null;
    
    public static CAbout getInstance()
    {
        if (instance == null)
        {
            instance = new CAbout();
        }
        return instance;
    }
    
    private VAbout view;
    private Timer change_slides, moving_slides;
    private List<String> slides = new ArrayList<>();
    
    private CAbout()
    {
        if (!initAbout())
        {
            Main.switchMode(Main.START_MODE);
        }
    }
    
    private boolean initAbout()
    {
        if (!initAboutFile())
        {
            return false;
        }
    
        return initScanner();
    }
    
    public void showAbout()
    {
    
    }
    
    public void hideAbout()
    {
    
    }
}
