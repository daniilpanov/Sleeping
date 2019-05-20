package com.my.newgame.sprite;

import java.awt.*;

public class SpriteFactory
{
    public static Sprite createEnemy(double x, double y,
                                     Texture texture, Component parent,
                                     int delay)
    {
        int width = texture.getWidth(),
                height = texture.getHeight();
    
        return new Sprite.ControlledByTimer(
                x, y, width, height, new Texture[] {texture},
                parent, delay
        )
        {
            @Override
            public void moving()
            {
            
            }
        };
    }
    
    /*public static Sprite createPlayer(Dimension screen_size,
                                      Component parent, Component for_key_listener
    )
    {
        return new Sprite.ControlledByKeyEvents(
        
        )
        {
        
        };
    }*/
}
