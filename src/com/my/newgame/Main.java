package com.my.newgame;

import com.my.newgame.settings.CSettings;

import javax.swing.*;

import static javax.swing.UIManager.*;

public class Main
{
    private static final String DEFAULT_LOOK_AND_FEEL = getLookAndFeel().getName();
    private static JFrame current_frame = null;
    private static String mode = null,
            look_and_feel = DEFAULT_LOOK_AND_FEEL;
    
    private static Thread game_process = null;
    
    private Main()
    {
    
    }
    
    public static void main(String[] args)
    {
        try
        {
            if (!CSettings.settingsInit())
            {
                System.out.println("Что-то пошло не так...");
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        /*if (!setNewLookAndFeel("Nimbus"))
        {
            setNewLookAndFeel(DEFAULT_LOOK_AND_FEEL);
        }*/
    }
    
    public static boolean switchMode(String new_mode)
    {
        boolean res = false;
        
        if (mode.equals(new_mode))
        {
            res = true;
        }
        else
        {
            switch (new_mode)
            {
                case "GAME":
                    
                    break;
                
                case "SETTINGS":
                    
                    break;
                
                case "HELP":
                    
                    break;
                
                case "MENU":
                    
                    break;
            }
        }
        
        return res;
    }
    
    private static boolean setNewLookAndFeel(String look_and_feel_name)
    {
        boolean is_set = false;
        try
        {
            LookAndFeelInfo[] looks_and_feels
                    = getInstalledLookAndFeels();
        
            for (LookAndFeelInfo look_and_feel : looks_and_feels)
            {
                if (look_and_feel.getName().equals(look_and_feel_name))
                {
                    is_set = true;
                    setLookAndFeel(look_and_feel.getClassName());
                }
            }
        }
        catch (IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException
                | ClassNotFoundException e)
        {
            is_set = false;
            e.printStackTrace();
        }
        
        return is_set;
    }
}
