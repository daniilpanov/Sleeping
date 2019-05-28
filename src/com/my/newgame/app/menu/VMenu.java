package com.my.newgame.app.menu;

import javax.swing.*;
import java.awt.*;

class VMenu extends JPanel
{
    // Another variants: Stencil, Traditional Arabic
    private Font font = new Font("Algerian", Font.ITALIC, 50);
    //
    JButton
            play = new JButton("Play"),
            settings = new JButton("Settings"),
            about = new JButton("About"),
            exit = new JButton("Exit");
    //
    private JPanel menu = new JPanel(new GridLayout(4, 1, 10, 20));
    
    VMenu()
    {
        super(new BorderLayout(15, 10));

        JButton[] buttons = new JButton[]
        {
                play, settings, about, exit
        };

        addFontToEveryButton(buttons);
        addEveryButtonToMenu(buttons);
        
        add(menu, BorderLayout.EAST);
    }
    
    public static void addIconToButton(JButton button, Icon icon)
    {
        button.setPreferredSize(
                new Dimension(icon.getIconWidth(),
                        icon.getIconHeight()
                )
        );
        
        button.setIcon(icon);
    }
    
    private void addEveryButtonToMenu(Component[] comp)
    {
        for (Component component : comp)
        {
            menu.add(component);
        }
    }

    private void addFontToEveryButton(JButton[] buttons)
    {
        for (JButton button : buttons)
        {
            button.setFont(font);
        }
    }
}
