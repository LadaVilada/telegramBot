package ladavilada;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import org.apache.log4j.Logger;

/**
 * Created by vbobina on 14.07.2017.
 */
public class TelergramBot {
    private final static Logger LOGGER = Logger.getLogger(TelergramBot.class);


    private void createBot() {
        TelegramBot bot = TelegramBotAdapter.build("BOT_TOKEN");
    }
}
