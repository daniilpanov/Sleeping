package com.my.newgame.app.settings;

import com.sun.istack.internal.NotNull;

import javax.management.openmbean.InvalidKeyException;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// !!!УБЕДИТЕЛЬНАЯ ПРОСЬБА!!!
// СЛАБОНЕРВНЫМ НЕ ЧИТАТЬ!

class MSettings extends JFrame
{
    // Константы для логов
    static final int ALL_RIGHT = 0,
            FILE_NOT_EXISTS = 1,
            FILE_CAN_NOT_BE_CREATED = 2,
            UNKNOWN_ERROR = -1,
            FILE_IS_INVALID = 3,
            ACCESS_DENIED = 4;
    // Константы пути
    private static final String SETTINGS_FOLDER = "res/config/";
    public static final String
            GLOBAL_SETTINGS = SETTINGS_FOLDER + "global_settings.ini",
            GAME_SETTINGS = SETTINGS_FOLDER + "game_settings.ini";
    
    // Настройки (cash = {key = {name, value}})
    private List<String[]> global_settings_cash = new ArrayList<>(), // глобальные
            game_settings_cash = new ArrayList<>(); // игровые

    // Объекты для манипуляций файлом с глобальными настройками
    File global_config = null;
    private FileWriter global_config_editor = null;
    private FileReader global_config_reader = null;
    private Scanner global_config_scanner = null;
    // Для манипуляций файлом с настройками игры
    File game_config = null;
    private FileWriter game_config_editor = null;
    private FileReader game_config_reader = null;
    private Scanner game_config_scanner = null;

    // Проверка пути до файла с настройками
    private void checkPath(String path) throws IllegalArgumentException
    {
        if (!path.equals(GLOBAL_SETTINGS) && !path.equals(GAME_SETTINGS))
        {
            throw new IllegalArgumentException();
        }
    }
    
    ///////////////////////
    /// INITIALIZATIONS
    ///////////////////////

    // Инициализация объекта типа File для работы с файлом с глобальными настройками
    synchronized int initConfigFile(String path) throws IllegalArgumentException
    {
        // Проверяем путь
        checkPath(path);
        // Изначально всё хорошо
        int res = ALL_RIGHT;
        // Инициализация
        global_config = new File("res/config/global_settings.ini");
        // Если такого пути не существует,
        if (!global_config.exists())
        {
            // то всё плохо,
            // и мы уведомляем о том, что файла не существует
            res = FILE_NOT_EXISTS;
        }
        
        return res;
    }
    
    // Создание файла глобальных настроек
    synchronized int createConfigFile(File config)
    {
        // Изначально, как всегда, всё хорошо
        int res = ALL_RIGHT;
        // Пробуем
        try
        {
            // создать файл. И если это удаётся, то
            if (config.createNewFile())
            {
                // создаём временный редактор,
                FileWriter tmp_config_editor = new FileWriter(config);
                // записываем настройки по умолчанию,
                tmp_config_editor.write(
                        "gameName: Sleeping\n" +
                                "lookAndFeel: Nimbus\n" +
                                "lng: ru\n" +
                                "slides: 5\n" +
                                "isFirstStart: true"
                );
                // очищаем буфер
                tmp_config_editor.flush();
                // и закрываем файл
                tmp_config_editor.close();
                //
                initConfigFile(config.getPath().replace("\\", "/"));
            }
            // Если создать файл (наверное, читая мой код,
            // вы уже забыли, что мы делаем) не получилось,
            else
            {
                // то сообщаем, что мы не можем создать файл
                res = FILE_CAN_NOT_BE_CREATED;
            }
        }
        // При непонятной ошибке говорим, что возникла эта непонятная ошибка
        catch (IOException e)
        {
            res = UNKNOWN_ERROR;
        }
        
        return res;
    }
    
    //
    synchronized int initConfigEditor(String path) throws IllegalArgumentException
    {
        // Проверяем путь
        checkPath(path);

        int res = ALL_RIGHT;
        
        try
        {
            switch (path)
            {
                case GLOBAL_SETTINGS:
                    global_config_editor = new FileWriter(global_config, true);
                    break;

                case GAME_SETTINGS:
                    game_config_editor = new FileWriter(game_config, true);
                    break;
            }
        }
        catch (IOException e)
        {
            res = UNKNOWN_ERROR;
        }
    
    
        return res;
    }
    
    //
    synchronized int initConfigReader(String path) throws IllegalArgumentException
    {
        // Проверяем путь
        checkPath(path);

        int res = ALL_RIGHT;
        
        try
        {
            switch (path)
            {
                case GLOBAL_SETTINGS:
                    global_config_reader = new FileReader(global_config);
                    break;

                case GAME_SETTINGS:
                    game_config_reader = new FileReader(game_config);
                    break;
            }
        }
        catch (FileNotFoundException e)
        {
            res = FILE_NOT_EXISTS;
        }
    
        return res;
    }
    
    // Инициализация сканнера
    synchronized void initConfigScanner(String path) throws IllegalArgumentException
    {
        // Проверяем путь
        checkPath(path);
        // Если все предыдущие методы прошли успешно, тогда здесь точно всё будет хорошо
        // Здесь просто выбираем нужный нам объект и инициализируем его
        switch (path)
        {
            case GLOBAL_SETTINGS:
                global_config_scanner = new Scanner(global_config_reader);
                break;

            case GAME_SETTINGS:
                game_config_scanner = new Scanner(game_config_reader);
                break;
        }
        // *упс, что-то пошло не так* (шутка)
    }

    synchronized void initGlobalCash()
    {
        String key, value;
        
        while (global_config_scanner.hasNext())
        {
            key = global_config_scanner.next();
            value =  global_config_scanner.next();
            global_settings_cash.add(new String[]{key, value});
        }
        
        global_config_scanner.reset();
    }

    ///////////////////////
    /// CASH MANAGERS
    ///////////////////////
    synchronized String getSetting(String from, String key) throws Exception
    {
        List<String[]> config;
        String value = null;
        boolean is_found = false;
        
        key += ":";
        
        switch (from)
        {
            //
            case GLOBAL_SETTINGS:
                config = global_settings_cash;
                break;
            //
            case GAME_SETTINGS:
                config = game_settings_cash;
                break;
            //
            default:
                throw new IllegalArgumentException();
        }
    
        if (config.isEmpty())
        {
            throw new Exception("Settings is not initialized!");
        }
        
        for (String[] row : config)
        {
            if (row[0].equals(key))
            {
                value = row[1];
                is_found = true;
                break;
            }
        }
        
        if (!is_found)
        {
            throw new InvalidKeyException("The setting with this key does not exist!");
        }
        
        return value;
    }
    
    synchronized boolean setSetting(String from, String key, String new_value)
            throws Exception
    {
        boolean is_set = false;
        
        FileWriter writer;
        List<String[]> config;
        
        switch (from)
        {
            case GLOBAL_SETTINGS:
                writer = global_config_editor;
                config = global_settings_cash;
                break;
            
            case GAME_SETTINGS:
                writer = game_config_editor;
                config = game_settings_cash;
                break;
            
            default:
                throw new IllegalArgumentException();
        }
    
        for (int i = 0; i < config.size(); i++)
        {
            String[] item = config.get(i);
            if (item[0].equals(key + ":"))
            {
                config.set(i, new String[] {key, new_value});
                is_set = true;
                break;
            }
        }
        
        if (!is_set)
        {
            throw new Exception("This setting does not exist!");
        }
        
        try
        {
            writeCash(writer, config);
        }
        catch (IOException ex)
        {
            is_set = false;
        }
        
        return is_set;
    }
    
    private synchronized void writeCash(@NotNull FileWriter writer, List<String[]> config)
            throws IOException
    {
        writer.write("");
    
        for (String[] item : config)
        {
            writer.append(item[0]).append(" ").append(item[1]);
        }
        
        writer.flush();
    }
}
