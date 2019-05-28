package com.my.newgame.app.game;

import com.my.newgame.app.settings.CSettings;
import com.my.newgame.app.sprite.Sprite;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CGame extends MGame
{
    public static CGame createNewGame(boolean is_first_start)
    {
        CGame instance = new CGame();
        instance.is_first_start = is_first_start;
        
        return instance;
    }
    
    private JFrame game_frame;
    private VGame view = null;
    private boolean is_first_start;
    private List<Sprite> sprites = new ArrayList<>();
    
    private CGame()
    {
        super();
        
        setPriority(MAX_PRIORITY);
        setDaemon(true);
        
        game_frame = new JFrame(
                CSettings.getInstance()
                        .getSetting(CSettings.GLOBAL_SETTINGS, "gameName")
        );
        
        game_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        game_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //GameMenu.instance = new GameMenu();
    }
    
    public void addSprite(Sprite sprite)
    {
    
    }
    
    @Override
    public void run()
    {
    
    }
    
    @Override
    public synchronized void start()
    {
        if (view == null)
        {
            view = new VGame();
            game_frame.getContentPane().add(view);
        }
        game_frame.setVisible(true);
        
        super.start();
    }
    
    @Override
    public void interrupt()
    {
        
        
        super.interrupt();
    }
}
