package com.my.newgame.settings;

import java.util.ArrayList;
import java.util.List;

public class CSettings extends MSettings
{
    private static CSettings instance = null;
    private List<String[]> logs = new ArrayList<>();
    
    public static CSettings getInstance()
    {
        return instance;
    }
    
    public static boolean settingsInit() throws Exception
    {
        if (instance != null)
        {
            if (instance.logs.isEmpty())
            {
                throw new Exception("Settings have already initialised");
            }
        }
        
        instance = new CSettings();
        
        return instance.logs.isEmpty();
    }
    
    
    private VSettings view = null;
    
    private CSettings()
    {
    
    }
    
    public void showSettings()
    {
        if (view == null)
        {
            view = new VSettings();
            getContentPane().add(view);
        }
        
        setVisible(true);
    }
    
    public void hideSettings()
    {
        setVisible(false);
    }
}
