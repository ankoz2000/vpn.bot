package bot.telegram.vpn.services.bot.message;

import bot.telegram.vpn.services.bot.UpdateInterface;
import com.pengrad.telegrambot.request.SendMessage;

public interface MessageStrategy<U, M> {
    void interact(U update) throws Exception;
    SendMessage interactWithReply(UpdateInterface<U, M> update);
    MessageType getMessageType();
}
