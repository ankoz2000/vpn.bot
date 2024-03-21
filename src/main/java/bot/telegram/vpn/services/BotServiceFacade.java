package bot.telegram.vpn.services;

import bot.telegram.vpn.services.bot.BaseHandler;
import bot.telegram.vpn.services.bot.StartupService;
import bot.telegram.vpn.services.bot.message.MessageStrategy;
import bot.telegram.vpn.services.bot.message.MessageType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BotServiceFacade {
    private final StartupService startupService;
    private final BaseHandler handler;

    public BotServiceFacade(@Autowired StartupService startupService, BaseHandler handler) {
        this.startupService = startupService;
        this.handler = handler;
    }

    public void run() {
        startupService.startup(handler);
    }
}
