package com.my.newgame.app.about;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MAbout extends JFrame
{
    private File about_file = null;
    private Scanner about_scanner = null;
    List<String> content = new ArrayList<>();
    
    boolean initAboutFile()
    {
        boolean init = false;
        
        about_file = new File("res/config/about.txt");

        if (about_file.exists())
        {
            init = true;
        }
        
        return init;
    }
    
    boolean initScanner()
    {
        boolean init = true;
    
        try
        {
            about_scanner = new Scanner(about_file);
        }
        catch (FileNotFoundException e)
        {
            init = false;
        }
        
        return init;
    }
    
    void initContent()
    {
        while (about_scanner.hasNext())
        {
            content.add(about_scanner.next());
        }
        
        about_scanner.reset();
    }
}
