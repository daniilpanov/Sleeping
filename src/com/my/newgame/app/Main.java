package com.my.newgame.app;

import com.my.newgame.app.about.CAbout;
import com.my.newgame.app.game.CGame;
import com.my.newgame.app.menu.CMenu;
import com.my.newgame.app.settings.CSettings;

import javax.swing.*;

import java.awt.*;

import static javax.swing.UIManager.*;

public class Main
{
    public static final String
            // LookAndFeel по умолчанию
            DEFAULT_LOOK_AND_FEEL = UIManager.getLookAndFeel().getName(),
            // MODES ("режимы" игры - страницы, Activities):
            GAME_MODE      = "Game", // Сама игра
            MENU_MODE      = "Menu", // Главное меню
            SETTINGS_MODE  = "Settings", // Настройки
            ABOUT_MODE     = "About", // Об игре
            START_MODE     =  MENU_MODE; // Начальный "режим"
    // Текущий режим игры
    private static String mode = null,
            // Текущий Look And Feel
            look_and_feel = DEFAULT_LOOK_AND_FEEL;
    // Первый ли раз запускалась эта игра? (для обучения)
    private static boolean is_first_start;
    // Игровые потоки (старый и новый)
    private static Thread game_process = null, old_game_process = null;
    
    // Конструктор
    private Main()
    {
        switchMode(START_MODE);
    }
    
    // Главный статический метод "main"
    public static void main(String[] args)
    {
        try
        {
            // Пробуем инициализировать настройки
            if (!CSettings.settingsInit())
            {
                // Если не получилось, то выводим диалог, сообщающий об ошибке
                showSettingsInitErrorDialog();
            }
            else
            {
                // А если всё прошло гладко, то берём из настроек, первый ли этот запуск
                is_first_start
                        = CSettings.getInstance()
                        .getSetting(CSettings.GLOBAL_SETTINGS, "isFirstStart")
                        .equals("true");
            }
        }
        // При исключении
        catch(Exception e)
        {
            // TODO: 28.05.2019 do something
            System.err.println(e.getMessage());
        }
        
        // В настройках смотрим, какой нужно установить Look And Feel
        look_and_feel
                = CSettings.getInstance()
                .getSetting(CSettings.GLOBAL_SETTINGS, "lookAndFeel");
        // Если его не получилось установить,
        if (!setNewLookAndFeel(look_and_feel))
        {
            // то устанавливаем Look And Feel по умолчанию
            look_and_feel = DEFAULT_LOOK_AND_FEEL;
            setNewLookAndFeel(DEFAULT_LOOK_AND_FEEL);
        }
    
        // Вызываем конструктор ( чисто для приличия - можно было обойтись и без этого ;) )
        EventQueue.invokeLater(Main::new);
    }

    // ПРЕДУПРЕЖДЕНИЕ! Дальше читайте осторожно; можете предварительно схватиться за сердце

    // Метод для переключения режимов игры, т.е. РОУТЕР
    public static synchronized void switchMode(String new_mode)
    {
        // Флаг (переключено, или нет)
        boolean switched = false;

        /*
         * Смотрим на новую модификацию и пытаемся понять, к чему бы это...
         * Шутка ;D
         * Смотрим, и... смотрим... и... думаем, что нам делать
         * При новом режиме мы или просто запускаем новый поток,
         * или делаем какой-либо элемент видимым
         * и устанавливаем флаг
         */
        switch (new_mode)
        {
            // Игра
            case GAME_MODE:
                switched = true;
                startGame();
                break;
            // Настройки
            case SETTINGS_MODE:
                switched = true;
                CSettings.getInstance().showSettings();
                break;
            // Об игре
            case ABOUT_MODE:
                switched = true;
                CAbout.getInstance().showAbout();
                break;
            // Меню
            case MENU_MODE:
                switched = true;
                CMenu.getInstance().showMenu();
                break;
        }

        // Если до этого переключения были другие и новый режим проверен,
        if (switched && mode != null)
        {
            /*
             * то делаем то же самое со старым режимом
             * с той лишь разницей, что, вместо того, чтобы делать
             * какой-либо элемент видимым, мы скрываем его,
             * или убиваем поток
             */
            switch (mode)
            {
                // Игра
                case GAME_MODE:
                    switched = true;
                    stopGame();
                    break;
                // Настройки
                case SETTINGS_MODE:
                    switched = true;
                    CSettings.getInstance().hideSettings();
                    break;
                // Об игре
                case ABOUT_MODE:
                    switched = true;
                    CAbout.getInstance().hideAbout();
                    break;
                // Меню
                case MENU_MODE:
                    switched = true;
                    CMenu.getInstance().hideMenu();
                    break;
            }
        }
        // Если новый режим неправильный,
        else if (!switched)
        {
            // то показываем диалог, в котором излагаем ошибку
            showSwitchErrorDialog(new_mode);
        }

        // Если старый режим тоже был проверен,
        // или его проверки вообще не было, т.к. не было старого режима,
        if (switched)
        {
            // то устанавливаем текущий режим
            mode = new_mode;
        }
        // Иначе
        else
        {
            // выводим диалог с ошибкой
            showSwitchErrorDialog(mode);
        }
    }
    
    // Старт новой игры:
    private static void startGame()
    {
        // Устанавливаем старый поток игры,
        old_game_process = game_process;
        // создаём новый и запускаем его
        game_process = CGame.createNewGame(is_first_start);
        game_process.start();
    }
    
    // Остановка старой игры:
    private static void stopGame()
    {
        // Если старая игра есть,
        if (old_game_process != null)
        {
            // и если она не остановлена до нас, то
            if (!old_game_process.isInterrupted())
            {
                // останавливаем её
                old_game_process.interrupt();
            }
        }
    }
    
    //
    private static boolean setNewLookAndFeel(String look_and_feel_name)
    {
        //
        boolean is_set = false;
        //
        try
        {
            //
            LookAndFeelInfo[] looks_and_feels
                    = getInstalledLookAndFeels();
            //
            for (LookAndFeelInfo look_and_feel : looks_and_feels)
            {
                //
                if (look_and_feel.getName().equals(look_and_feel_name))
                {
                    is_set = true;
                    setLookAndFeel(look_and_feel.getClassName());
                }
            }
        }
        //
        catch (IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException
                | ClassNotFoundException e)
        {
            is_set = false;
            e.printStackTrace();
        }
        
        return is_set;
    }
    
    //
    private static void showSwitchErrorDialog(String mode)
    {

    }
    
    //
    private static void showSettingsInitErrorDialog()
    {
        
    }
    
    //
    public static String getLookAndFeel()
    {
        return look_and_feel;
    }

    public static void exit()
    {

        System.exit(0);
    }
}
