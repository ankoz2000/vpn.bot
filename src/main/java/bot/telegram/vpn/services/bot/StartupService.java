package bot.telegram.vpn.services.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@PropertySource("classpath:application.properties")
public class StartupService {
    private final TelegramBot bot;

    @Autowired
    public StartupService(@Qualifier(value = "VpnBot") TelegramBot bot) {
        this.bot = bot;
    }

    public void startup(AbstractSendRequestFunctionalInterface handler) {
        bot.setUpdatesListener(list -> {
            list.forEach(update -> bot.execute(handler.handle(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public TelegramBot getBot() {
        return bot;
    }
}
