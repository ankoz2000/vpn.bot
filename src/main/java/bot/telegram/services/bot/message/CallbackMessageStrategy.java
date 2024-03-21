package bot.telegram.services.bot.message;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import static bot.telegram.services.bot.message.MessageType.CALLBACK;

@Component
public class CallbackMessageStrategy implements MessageStrategy<Update, Message> {

    @Override
    public void interact(Update update) throws Exception {
    }

    @Override
    public SendMessage interactWithReply(Update update) {
        return new SendMessage(update.callbackQuery().from().id(), "Оплата отменена")
                .replyMarkup(new ReplyKeyboardMarkup("Назад")
                        .addRow(new KeyboardButton("Получить ключ"),
                                new KeyboardButton("Попробовать бесплатно"))
                        .addRow(new KeyboardButton("Мои ключи"))
                        .addRow(new KeyboardButton("Мой профиль"))
                        .resizeKeyboard(true));
    }

    @Override
    public MessageType getMessageType() {
        return CALLBACK;
    }
}
