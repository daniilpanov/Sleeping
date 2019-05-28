package com.my.newgame.app.menu;

import com.my.newgame.app.Main;

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
    
    private VMenu view = new VMenu();
    
    private CMenu()
    {
        setUndecorated(true);
        getContentPane().add(view);
        
        view.play.addActionListener(e -> play());
        view.settings.addActionListener(e -> settings());
        view.about.addActionListener(e -> about());
        view.exit.addActionListener(e -> exit());
        
        setExtendedState(EXIT_ON_CLOSE);
    }
    
    private void play()
    {
        Main.switchMode(Main.GAME_MODE);
    }
    
    private void settings()
    {
        Main.switchMode(Main.SETTINGS_MODE);
    }
    
    private void about()
    {
        Main.switchMode(Main.ABOUT_MODE);
    }
    
    private void exit()
    {
        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (answer == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
        
    }
    
    public void showMenu()
    {
        setVisible(true);
    }
    
    public void hideMenu()
    {
        setVisible(false);
    }
}
