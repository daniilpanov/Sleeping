package com.my.newgame.settings;

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
    static final String
            SETTINGS_FOLDER = "res/config/",
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
    int initConfigFile(String path) throws IllegalArgumentException
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
    int createConfigFile(File config)
    {
        // Изначально, как всегда, всё хорошо
        int res = ALL_RIGHT;
        // Пробуем
        try
        {
            // создать файл. И если это удаётся, то
            if (config.createNewFile())
            {
                // пробуем сделать его нередактируемым для всех, кроме себя
                if (!config.setReadOnly())
                {
                    // при неудаче говорим, что файл получился неправильным
                    res = FILE_IS_INVALID;
                }

                FileWriter tmp_config_editor = new FileWriter(config);
                // Если всё прошло удачно, то
                if (res == ALL_RIGHT)
                {
                    // записываем настройки по умолчанию,
                    tmp_config_editor.write(
                            "lookAndFeel: Nimbus\n" +
                            "lng: ru"
                    );
                    // очищаем буфер
                    tmp_config_editor.flush();
                    // и закрываем файл
                    tmp_config_editor.close();
                }
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
    int initConfigEditor(String path) throws IllegalArgumentException
    {
        // Проверяем путь
        checkPath(path);

        int res = ALL_RIGHT;
        
        try
        {
            switch (path)
            {
                case GLOBAL_SETTINGS:
                    global_config_editor = new FileWriter(global_config);
                    break;

                case GAME_SETTINGS:
                    game_config_editor = new FileWriter(game_config);
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
    int initConfigReader(String path) throws IllegalArgumentException
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
    void initConfigScanner(String path) throws IllegalArgumentException
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

    void initGlobalCash()
    {
        while (global_config_scanner.hasNext(": "))
        {
            global_settings_cash.add(new String[]
            {
                   global_config_scanner.next(),
                   global_config_scanner.next()
            });
        }
    }

    ///////////////////////
    /// CASH MANAGERS
    ///////////////////////

}
