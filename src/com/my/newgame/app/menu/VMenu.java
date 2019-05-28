package com.my.newgame.app.menu;

import com.my.newgame.app.Img;

import javax.swing.*;
import java.awt.*;

class VMenu extends JPanel
{
    JButton
            play = new JButton("Play"),
            settings = new JButton("Settings"),
            about = new JButton("About"),
            exit = new JButton("Exit");
    JPanel menu = new JPanel(new GridLayout(4, 1, 10, 20));
    
    VMenu()
    {
        super(new BorderLayout(15, 10));
        
        addIconToButton(play, Img.menu_play);
        addIconToButton(settings, Img.menu_settings);
        addIconToButton(about, Img.menu_about);
        addIconToButton(exit, Img.menu_exit);
        
        add(menu, play, settings, about, exit);
        
        add(menu, BorderLayout.EAST);
    }
    
    private void addIconToButton(JButton button, Icon icon)
    {
        button.setPreferredSize(
                new Dimension(icon.getIconWidth(),
                        icon.getIconHeight()
                )
        );
        
        button.setIcon(icon);
    }
    
    public static void add(JComponent parent, Component ... comp)
    {
        for (Component component : comp)
        {
            parent.add(component);
        }
    }
}
