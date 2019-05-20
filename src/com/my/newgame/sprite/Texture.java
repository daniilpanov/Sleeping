package com.my.newgame.sprite;

import javax.swing.*;
import java.awt.*;

public class Texture
{
    protected double x, y, z, angle;
    protected ImageIcon texture_image;
    protected Component parent;
    
    /////////////////////
    /// CONSTRUCTORS
    /////////////////////
    
    public Texture(double x, double y, ImageIcon texture_image, Component parent
    )
    {
        this.x = x;
        this.y = y;
        this.texture_image = texture_image;
        this.angle = 0;
        this.parent = parent;
    }
    
    public Texture(ImageIcon texture_image, Component parent)
    {
        this(0, 0, texture_image, parent);
    }
    
    /////////////////////
    /// ANOTHER METHODS
    /////////////////////
    
    public void render(Graphics2D g)
    {
        g.drawImage(getTextureImage(), (int) x, (int) y, null);
    }
    
    public void rotate(int angle)
    {
        setAngle(Math.toRadians(angle));
    }
    
    public void repaint()
    {
        parent.repaint();
    }
    
    /////////////////////
    /// GETTERS
    ////////////////////
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getZ()
    {
        return z;
    }
    
    public int getWidth()
    {
        return texture_image.getIconWidth();
    }
    
    public int getHeight()
    {
        return texture_image.getIconHeight();
    }
    
    public Image getTextureImage()
    {
        return texture_image.getImage();
    }
    
    public Icon getTextureIcon()
    {
        return texture_image;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    /////////////////////
    /// SETTERS
    /////////////////////
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public void setZ(double z)
    {
        this.z = z;
    }
    
    public void setTextureImage(ImageIcon texture_image)
    {
        this.texture_image = texture_image;
    }
    
    public void setAngle(double angle)
    {
        this.angle = angle;
    }
}
