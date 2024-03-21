package bot.telegram.vpn.services.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;

@FunctionalInterface
public interface AbstractSendRequestFunctionalInterface {
    AbstractSendRequest handle(Update update);
}
