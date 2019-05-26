package com.my.newgame.app.menu;

import javax.swing.*;

public class CMenu extends JFrame
{
    private static CMenu instance = null;
    
    public static CMenu getInstance()
    {
        if (instance == null)
        {
            instance = new CMenu();
        }
        
        return instance;
    }
    
    private CMenu()
    {
    
    }
    
    public void showMenu()
    {
    
    }
    
    public void hideMenu()
    {
    
    }
}
