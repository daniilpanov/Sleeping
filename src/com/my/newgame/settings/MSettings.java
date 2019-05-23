package com.my.newgame.settings;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MSettings extends JFrame
{
    // Константы для логов
    static final int ALL_RIGHT = 0,
            FILE_NOT_EXISTS = 1,
            FILE_CAN_NOT_BE_CREATED = 2,
            UNKNOWN_ERROR = -1,
            FILE_IS_INVALID = 3,
            ACCESS_DENIED = 4;
    
    // Настройки (cash = {key = {name, value}})
    List<String[]> global_settings_cash = new ArrayList<>(), // глобальные
            game_settings_cash = new ArrayList<>(); // игровые
    // Объекты для манипуляций файлом с глобальными настройками
    File global_config = null;
    FileWriter global_config_editor = null;
    FileReader global_config_reader = null;
    Scanner global_config_scanner = null;
    // Для манипуляций файлом с настройками игры
    File game_config = null;
    FileWriter game_config_editor = null;
    FileReader game_config_reader = null;
    Scanner game_config_scanner = null;
    
    // Инициализация объекта типа File для работы с файлом с глобальными настройками
    int initGlobalConfigFile()
    {
        // Изначально всё хорошо
        int res = ALL_RIGHT;
        // Инициализация
        global_config = new File("res/config/global_settings.ini");
        // Если такого пути не существует,
        if (!global_config.exists())
        {
            // то всё плохо,
            // и уведомляем о том, что файла не существует
            res = FILE_NOT_EXISTS;
        }
        
        return res;
    }
    
    // Создание файла глобальных настроек
    int createGlobalConfigFile()
    {
        // Изначально, как всегда, всё хорошо
        int res = ALL_RIGHT;
        // Пробуем
        try
        {
            // создать файл. И если это удаётся, то
            if (global_config.createNewFile())
            {
                // пробуем сделать его нередактируемым для всех, кроме себя
                if (!global_config.setWritable(false, false)
                    || !global_config.setWritable(true, true)
                )
                {
                    // при неудаче говорим, что файл получился неправильным
                    res = FILE_IS_INVALID;
                }
                
                // Если не инициализируется объект-редактор глобальных настроек,
                if (initGlobalConfigEditor() != ALL_RIGHT)
                {
                    // то выходим из метода, возвратив "UNKNOWN_ERROR"
                    return UNKNOWN_ERROR;
                }
                // Если всё прошло удачно, то
                if (res == ALL_RIGHT)
                {
                    // записываем настройки по умолчанию,
                    global_config_editor.write(
                            "lookAndFeel: Nimbus\n" +
                            "lng: ru"
                    );
                    // очищаем буфер,
                    global_config_editor.flush();
                    // закрываем файл
                    global_config_editor.close();
                    // и убираем объект
                    global_config_editor = null;
                }
            }
            // Если создать файл (наверное, читая мой код,
            // вы уже забыли, что мы делаем) "global_settings.ini",
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
    int initGlobalConfigEditor()
    {
        int res = ALL_RIGHT;
        
        try
        {
            global_config_editor = new FileWriter(global_config);
        }
        catch (IOException e)
        {
            res = UNKNOWN_ERROR;
        }
    
    
        return res;
    }
    
    //
    int initGlobalConfigReader()
    {
        int res = ALL_RIGHT;
        
        try
        {
            global_config_reader = new FileReader(global_config);
        }
        catch (FileNotFoundException e)
        {
            res = FILE_NOT_EXISTS;
        }
    
        return res;
    }
    
    // Инициализация сканнера
    void initGlobalConfigScanner()
    {
        // Если все предыдущие методы прошли успешно, тогда здесь точно всё будет хорошо
        global_config_scanner = new Scanner(global_config_reader);
        // *упс, что-то пошло не так* (шутка)
    }
}
