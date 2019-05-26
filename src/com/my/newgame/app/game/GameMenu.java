package com.my.newgame.app.game;

import com.my.newgame.app.sprite.Texture;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel
{
    static GameMenu instance = null;
    
    public static GameMenu getInstance()
    {
        return instance;
    }
    
    private JFrame menu_frame;
    
    GameMenu(Texture background, Texture pause, Texture resume, Texture exit)
    {
        menu_frame = new JFrame();
        menu_frame.setUndecorated(true);
        menu_frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
    
    public void showMenu()
    {
    
    }
    
    public void hideMenu()
    {
    
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        
    }
}
