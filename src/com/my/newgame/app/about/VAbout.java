package com.my.newgame.app.about;

import javax.swing.*;
import java.awt.*;

class VAbout extends JPanel
{
    private String[] slides;
    private Image[] images;
    private String current_slide;
    private Image current_image;
    private int x, y;
    
    VAbout(String[] slides, Image[] images)
    {
        this.slides = slides;
        this.images = images;
        
        setCurrentSlide(0);
        setCurrentImage(0);
    }
    
    boolean setCurrentSlide(int index)
    {
        if (index >= slides.length)
        {
            return false;
        }
        
        current_slide = slides[index];
        
        return true;
    }
    
    boolean setCurrentImage(int index)
    {
        if (index >= images.length)
        {
            return false;
        }
    
        current_image = images[index];
    
        return true;
    }
    
    void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
    }
}
