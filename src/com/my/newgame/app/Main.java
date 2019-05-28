package com.my.newgame.app;

import com.my.newgame.app.about.CAbout;
import com.my.newgame.app.game.CGame;
import com.my.newgame.app.menu.CMenu;
import com.my.newgame.app.settings.CSettings;

import javax.swing.*;

import java.awt.*;

import static javax.swing.UIManager.*;

public class Main
{
    public static final String
            // LookAndFeel по умолчанию
            DEFAULT_LOOK_AND_FEEL = UIManager.getLookAndFeel().getName(),
            // MODES ("режимы" игры - страницы, Activities):
            GAME_MODE      = "Game", // Сама игра
            MENU_MODE      = "Menu", // Главное меню
            SETTINGS_MODE  = "Settings", // Настройки
            ABOUT_MODE     = "About", // Об игре
            START_MODE     =  MENU_MODE; // Начальный "режим"
    //
    private static String mode = null,
            //
            look_and_feel = DEFAULT_LOOK_AND_FEEL;
    //
    private static boolean is_first_start;
    //
    private static Thread game_process = null, old_game_process = null;
    
    //
    private Main()
    {
        switchMode(START_MODE);
    }
    
    //
    public static void main(String[] args)
    {
        try
        {
            //
            if (!CSettings.settingsInit())
            {
                //
                showSettingsInitErrorDialog();
            }
            else
            {
                is_first_start
                        = CSettings.getInstance()
                        .getSetting(CSettings.GLOBAL_SETTINGS, "isFirstStart")
                        .equals("true");
            }
        }
        //
        catch(Exception e)
        {
            //
            System.err.println(e.getMessage());
        }
        
        //
        look_and_feel
                = CSettings.getInstance()
                .getSetting(CSettings.GLOBAL_SETTINGS, "lookAndFeel");
        //
        if (!setNewLookAndFeel(look_and_feel))
        {
            //
            look_and_feel = DEFAULT_LOOK_AND_FEEL;
            setNewLookAndFeel(DEFAULT_LOOK_AND_FEEL);
        }
    
        //
        EventQueue.invokeLater(Main::new);
    }
    
    //
    public static synchronized void switchMode(String new_mode)
    {
        //
        boolean switched = false;
        //
        if (mode != null)
        {
            if (mode.equals(GAME_MODE) && new_mode.equals(GAME_MODE))
            {
                startGame();
                stopGame();
                switched = true;
            }
            //
            else if (!mode.equals(new_mode))
            {
                //
                switch (new_mode)
                {
                    //
                    case GAME_MODE:
                        switched = true;
                        startGame();
                        break;
                    //
                    case SETTINGS_MODE:
                        switched = true;
                        CSettings.getInstance().showSettings();
                        break;
                    //
                    case ABOUT_MODE:
                        switched = true;
                        CAbout.getInstance().showAbout();
                        break;
                    //
                    case MENU_MODE:
                        switched = true;
                        CMenu.getInstance().showMenu();
                        break;
                }
                
                if (switched)
                {
                    switched = checkMode();
                }
            }
            //
            else
            {
                switched = true;
            }
            //
            if (switched)
            {
                mode = new_mode;
            }
            //
            else
            {
                //
                showSwitchErrorDialog(new_mode);
            }
        }
        else
        {
            if (!checkMode())
            {
            
            }
        }
    }
    
    private static boolean checkMode()
    {
        boolean switched = false;
        //
        switch (mode)
        {
            //
            case GAME_MODE:
                switched = true;
                stopGame();
                break;
            //
            case SETTINGS_MODE:
                switched = true;
                CSettings.getInstance().hideSettings();
                break;
            //
            case ABOUT_MODE:
                switched = true;
                CAbout.getInstance().hideAbout();
                break;
            //
            case MENU_MODE:
                switched = true;
                CMenu.getInstance().hideMenu();
                break;
        }
        
        return switched;
    }
    
    //
    private static void startGame()
    {
        //
        old_game_process = game_process;
        //
        game_process = CGame.createNewGame(is_first_start);
        game_process.start();
    }
    
    //
    private static void stopGame()
    {
        //
        if (old_game_process != null)
        {
            //
            if (!old_game_process.isInterrupted())
            {
                //
                old_game_process.interrupt();
            }
        }
    }
    
    //
    private static boolean setNewLookAndFeel(String look_and_feel_name)
    {
        //
        boolean is_set = false;
        //
        try
        {
            //
            LookAndFeelInfo[] looks_and_feels
                    = getInstalledLookAndFeels();
            //
            for (LookAndFeelInfo look_and_feel : looks_and_feels)
            {
                //
                if (look_and_feel.getName().equals(look_and_feel_name))
                {
                    is_set = true;
                    setLookAndFeel(look_and_feel.getClassName());
                }
            }
        }
        //
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
    
    //
    private static void showSwitchErrorDialog(String new_mode)
    {
    
    }
    
    //
    public static void showSettingsInitErrorDialog()
    {
        
    }
    
    //
    public static String getLookAndFeel()
    {
        return look_and_feel;
    }
}
