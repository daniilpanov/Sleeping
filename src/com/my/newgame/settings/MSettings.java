package com.my.newgame.settings;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MSettings extends JFrame
{
    // Настройки (cash = {key = {name, value}})
    List<String[]> cash = new ArrayList<>();
    // Объекты для манипуляций файлом с настройками
    File config_file;
    FileWriter config_editor;
    FileReader config_reader;
    Scanner config_scanner;
    
    
}
