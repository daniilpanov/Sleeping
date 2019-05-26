package com.my.newgame.app.sprite;

import com.my.newgame.app.Img;

import java.awt.*;

public class SpriteFactory
{
    public static Sprite createBackground(Dimension screen_size,
                                          Texture[][] textures,
                                          Component parent,
                                          Component for_key_listener
    )
    {
        int texture_width = textures[0][0].getWidth(),
                texture_height = textures[0][0].getHeight(),
                width = texture_width * 3,
                height = texture_height * 3;
        double x = (screen_size.getWidth() - width) / 2,
                y = (screen_size.getHeight() - height) / 2;
        
        return new Sprite.ControlledByKeyEvents(x, y, width, height,
                textures, parent, for_key_listener
        )
        {
            @Override
            public void moving()
            {
            
            }
    
            @Override
            public void stop()
            {
        
            }
        };
    }
    
    public static Sprite createEnemy(double x, double y,
                                     Texture texture, Component parent,
                                     int delay
    )
    {
        int width = texture.getWidth(),
                height = texture.getHeight();
    
        return new Sprite.ControlledByTimer(
                x, y, width, height, new Texture[][] {{texture}},
                parent, delay
        )
        {
            @Override
            public void moving()
            {
            
            }
        };
    }
    
    public static Sprite createPlayer(Dimension screen_size,
                                      Component parent,
                                      Component for_key_listener
    )
    {
        Texture texture = new Texture(Img.player, parent);
    
        int
                width  = texture.getWidth() - 10,
                height = texture.getHeight() - 10;
        
        double
                x = (screen_size.getWidth() - texture.getWidth()) / 2,
                y = (screen_size.getHeight() - texture.getHeight()) / 2;
        
        return new Sprite.ControlledByKeyEvents(
                x, y, width, height,
                new Texture[][] {{texture}},
                parent, for_key_listener
        )
        {
            @Override
            public void moving()
            {
        
            }
    
            @Override
            public void stop()
            {
        
            }
        };
    }
}
