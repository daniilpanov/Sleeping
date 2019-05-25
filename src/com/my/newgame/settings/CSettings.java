package com.my.newgame.settings;

import java.util.ArrayList;
import java.util.List;

public class CSettings extends MSettings
{
    private static CSettings instance = null;
    private List<String[]> logs = new ArrayList<>();
    
    public static CSettings getInstance()
    {
        return instance;
    }
    
    public static boolean settingsInit() throws Exception
    {
        if (instance != null)
        {
            if (instance.logs.isEmpty())
            {
                throw new Exception("Settings have already initialised");
            }
        }
        
        instance = new CSettings();
        
        return instance.logs.isEmpty();
    }
    
    // Вид
    private VSettings view = null;
    
    private CSettings()
    {
        if (!initGlobalSettings())
        {
            System.out.println("error!");
            //printLogs();
        }
    }
    
    private boolean initGlobalSettings()
    {
        // Сначала всё хорошо
        boolean res = true;

        try
        {
            // Если файла с настройками нет,
            int init = initConfigFile(GLOBAL_SETTINGS);
            if (init == FILE_NOT_EXISTS)
            {
                // то пробуем его создать
                int created = createConfigFile(global_config);
                // И если что-то пошло не так, смотрим, что
                if (created != ALL_RIGHT)
                {
                    // Объявляем переменную с текстом ошибки
                    String message = "";
                    // Смотрим, что пошло не так
                    switch (created)
                    {
                        // Если мы не можем создать файл
                        case FILE_CAN_NOT_BE_CREATED:
                            message = "<b>File can not be created!</b> You should to reload this game";
                            break;
                        // Если мы создали неправильный файл
                        case FILE_IS_INVALID:
                            message = "<b>File creating crashed!</b> You should to reload this game";
                            break;
                        // Если мы получили неизвестную ошибку
                        case UNKNOWN_ERROR:
                            message = "<i><b>Unknown error!</b></i>";
                            break;
                    }
                    // Говорим, что всё плохо - файл не создался
                    res = false;
                    // и добавляем сообщение
                    logs.add(new String[]{"createGlobalConfigFile", message});
                }
            }
            // А если всё хорошо
            else if (init == ALL_RIGHT)
            {
                // то идём дальше (#идёмдальше, www.go-then.com)!
                //
                init = initConfigReader(GLOBAL_SETTINGS);
                if (init == FILE_NOT_EXISTS)
                {
                    //
                    logs.add(
                            new String[]
                                    {
                                            "initGlobalConfigReader",
                                            "<i><b>Unknown error!</b></i> " +
                                                    "We was trying create a new config file but..."
                                    }
                    );
                    res = false;
                }
                //
                else if (init == ALL_RIGHT)
                {
                    //
                    initConfigScanner(GLOBAL_SETTINGS);
                    //
                    initGlobalCash();
                }
                
                //
                if (initConfigEditor(GLOBAL_SETTINGS) == UNKNOWN_ERROR)
                {
                    //
                    logs.add(
                            new String[]
                                    {
                                            "initGlobalConfigEditor",
                                            "<i><b>Unknown error!</b></i>"
                                    }
                    );
                    res = false;
                }
            }
        }
        catch (IllegalArgumentException ex)
        {
            logs.add(
                     new String[]
                     {
                             "Fatal Error",
                             "<b>Fatal error:</b> <i>Unknown error</i> was threw when we trying to get settings.<br>" +
                                     "You should to reload this game"
                     }
            );
        }
        
        return res;
    }
    
    //
    public String getSetting(String from, String name)
    {
        String value = null;
        try
        {
            value = super.getSetting(from, name);
        }
        catch (IllegalArgumentException ex)
        {
            logs.add(
                    new String[]
                            {
                                    "initGlobalConfigEditor",
                                    "<i><b>Unknown error!</b></i>"
                            }
            );
        }
        catch (Exception ex)
        {
            //
            initGlobalCash();
            //
            try
            {
                value = super.getSetting(from, name);
            }
            catch (IllegalArgumentException ex2)
            {
                logs.add(
                        new String[]
                                {
                                        "initGlobalConfigEditor",
                                        "<i><b>Unknown error!</b></i>"
                                }
                );
            }
            catch (Exception ignored)
            {
            
            }
        }
        
        return value;
    }
    
    // Показать настройки
    public void showSettings()
    {
        // Если вида ещё нет,
        if (view == null)
        {
            // то создаём его и добавляем на окно
            view = new VSettings();
            getContentPane().add(view);
        }
        
        setVisible(true);
    }
    
    // Скрыть настройки
    public void hideSettings()
    {
        setVisible(false);
    }
    
    private void printLogs()
    {
        for (String[] log : logs)
        {
            System.out.println(log[0] + ": " + log[1]);
        }
    }
}
