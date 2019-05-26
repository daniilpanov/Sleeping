package com.my.newgame.app.sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Sprite
{
    protected double x, y, width, height;
    protected Texture[][] textures;
    private Component parent;
    
    public Sprite(double x, double y, double width, double height,
                  Texture[][] textures, Component parent
    )
    {
        moveTo(x, y);
        
        this.width = width;
        this.height = height;
        
        this.textures = textures;
        
        this.parent = parent;
    }
    
    public boolean checkCollision(Sprite sprite)
    {
        boolean res = false;
        
        if (sprite.x + sprite.width >= x && sprite.x <= x + width)
        {
            if (sprite.y + sprite.height >= y && sprite.y <= y + height)
            {
                res = true;
            }
        }
        
        return res;
    }
    
    public void moveTo(double x, double y)
    {
        this.x = x;
        this.y = y;
        
        repaint();
    }
    
    public void goOn(double x, double y)
    {
        this.x += x;
        this.y += y;
        
        repaint();
    }
    
    protected void repaint()
    {
        parent.repaint();
    }
    
    static abstract class ControlledByTimer
            extends Sprite implements ControlledSprite
    {
        private Timer moving;
        
        ControlledByTimer(double x, double y,
                                  double width, double height,
                                  Texture[][] textures, Component parent,
                                  int delay
        )
        {
            super(x, y, width, height, textures, parent);
            
            moving = new Timer(delay, e -> moving());
        }
    
        @Override
        public void setMoving()
        {
            moving.start();
        }
    
        public void stopMoving()
        {
            moving.stop();
        }
    
        public abstract void moving();
    }
    
    static abstract class ControlledByKeyEvents
            extends Sprite implements ControlledSprite
    {
        private Component for_key_listener;
        
        ControlledByKeyEvents(double x, double y,
                              double width, double height,
                              Texture[][] textures, Component parent,
                              Component for_key_listener
        )
        {
            super(x, y, width, height, textures, parent);
            
            this.for_key_listener = for_key_listener;
        }
    
        @Override
        public void setMoving()
        {
            for_key_listener.addKeyListener(new KeyAdapter()
            {
                /**
                 * Invoked when a key has been pressed.
                 *
                 * @param e {@link KeyEvent}
                 */
                @Override
                public void keyPressed(KeyEvent e)
                {
                    super.keyPressed(e);
                }
    
                /**
                 * Invoked when a key has been released.
                 *
                 * @param e {@link KeyEvent}
                 */
                @Override
                public void keyReleased(KeyEvent e)
                {
                    super.keyReleased(e);
                }
            });
        }
    
        @Override
        public abstract void moving();
        
        public abstract void stop();
    }
}
