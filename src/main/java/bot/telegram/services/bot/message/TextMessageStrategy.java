package bot.telegram.services.bot.message;

import bot.telegram.services.KeyFacade;
import bot.telegram.services.keys.strategy.KeyStrategy;
import bot.telegram.services.keys.strategy.KeyType;
import bot.telegram.services.vpn.outline.OutlineApi;
import bot.telegram.services.vpn.outline.dto.AccessKey;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.SuccessfulPayment;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bot.telegram.services.keys.strategy.KeyType.PAID;
import static bot.telegram.services.keys.strategy.KeyType.TRIAL;

@Component
public class TextMessageStrategy implements MessageStrategy<Update, Message> {
    public static final String[] CUSTOM_BUTTONS = {"Назад"};
    @Inject
    private OutlineApi outline;
    @Inject
    @Qualifier(value = "KeyStrategies")
    private Map<KeyType, KeyStrategy> keyStrategies;

    @Autowired
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;
    @Autowired
    private KeyFacade keyFacade;

    @Override
    public void interact(Update update) {

    }

    @Override
    public SendMessage interactWithReply(Update update) {
        var message = update.message();
        var chatId = message.chat().id();
        var text = message.text();
        var username = message.chat().username();
        var firstName = message.chat().firstName();

        if (text != null) {
            return switch (text) {
                case "/start" -> getWelcomeMessage(chatId, firstName);

                case "Мои ключи", "/my_keys" -> new SendMessage(chatId, getAccessKeysText(username))
                        .replyMarkup(new ReplyKeyboardMarkup(CUSTOM_BUTTONS)
                                .resizeKeyboard(true));
                case "Попробовать бесплатно", "/trial" -> newKey(chatId, username, TRIAL)
                        .replyMarkup(new ReplyKeyboardMarkup(CUSTOM_BUTTONS)
                                .resizeKeyboard(true));
                case "Получить ключ", "/get_key" -> newKey(chatId, username, PAID)
                        .replyMarkup(new ReplyKeyboardMarkup(CUSTOM_BUTTONS)
                                .resizeKeyboard(true));

                case "Мой профиль", "/profile" -> getBaseMessageWithText(chatId, "Функционал профиля в разработке...");
                case "Подтвердить" -> getBaseMessageWithText(chatId, "Функционал подтверждения в разработке...");

                default -> getBaseMessage(chatId);
            };
        }

        if (message.successfulPayment() != null) {
            var payment = message.successfulPayment();
            return completeOrder(payment, PAID, chatId, username)
                    .replyMarkup(new ReplyKeyboardMarkup(CUSTOM_BUTTONS)
                            .resizeKeyboard(true));
        }

        return getBaseMessage(chatId);
    }

    private SendMessage getWelcomeMessage(Long chatId, String firstName) {
        return new SendMessage(chatId, getWelcome(firstName))
                .replyMarkup(new ReplyKeyboardMarkup("Назад")
                        .addRow(new KeyboardButton("Получить ключ"),
                                new KeyboardButton("Попробовать бесплатно"))
                        .addRow(new KeyboardButton("Мои ключи"))
                        .addRow(new KeyboardButton("Мой профиль"))
                        .resizeKeyboard(true));
    }

    private SendMessage getBaseMessage(Long chatId) {
        return new SendMessage(chatId, "Выбери действие")
                .replyMarkup(new ReplyKeyboardMarkup("Попробовать бесплатно")
                        .addRow(new KeyboardButton("Получить ключ"))
                        .addRow(new KeyboardButton("Мои ключи"), new KeyboardButton("Мой профиль"))
                        .resizeKeyboard(true));
    }

    private SendMessage getBaseMessageWithText(Long chatId, String text) {
        return new SendMessage(chatId, text)
                .replyMarkup(new ReplyKeyboardMarkup("Назад")
                        .addRow(new KeyboardButton("Получить ключ"),
                                new KeyboardButton("Попробовать бесплатно"))
                        .addRow(new KeyboardButton("Мои ключи"))
                        .addRow(new KeyboardButton("Мой профиль"))
                        .resizeKeyboard(true));
    }

    private List<AccessKey> getAccessKeys() {
        return outline.getAccessKeys();
    }

    private String getAccessKeysText(String username) {
        return getAccessKeys()
                .stream()
                .filter(accessKey -> accessKey.getName().contains(username))
                .map(accessKey -> accessKey.getName() + " -> " + accessKey.getAccessUrl().substring(0, 9)
                        + "..." + accessKey.getAccessUrl().substring(accessKey.getAccessUrl().length() - 5))
                .collect(Collectors.joining("\n\n"));
    }

    private Integer getAccessKeysLastId() {
        return getAccessKeys()
                .stream()
                .mapToInt(AccessKey::getId)
                .max().orElse(0);
    }

    private SendMessage newKey(Long chatId, String username, KeyType type) {
        String url = null;
        try {
            keyFacade.getKeyWithTestPayment(chatId, username, type, getAccessKeysLastId());
//            url = keyStrategies.get(type)
//                    .createNewAccessKey(username, getAccessKeysLastId());
        } catch (Exception e) {
            return getBaseMessageWithText(chatId, "Не могу создать ключ из-за того, что " + e.getMessage());
        }
       return getBaseMessageWithText(chatId, "Произведите оплату, нажав кнопку \"Оплатить\"");
    }

    private SendMessage completeOrder(SuccessfulPayment payment, KeyType keyType, Long chatId, String username) {
        String url = null;
        try {
            url = keyFacade.completePayment(payment, keyType, getAccessKeysLastId(), chatId, username);
        } catch (Exception e) {
            return getBaseMessageWithText(chatId, "Не могу создать ключ из-за того, что " + e.getMessage());
        }
        bot.execute(new SendMessage(chatId, "Пробный период - 3 дня\nВот ссылка для доступа к vpn:"));
        return new SendMessage(chatId, url);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.TEXT;
    }

    private String getWelcome(String userName) {
        var lineSeparator = "\n";
        return "Привет, " + userName + "!" + lineSeparator
                + "Я помогу тебе настроить VPN для доступа к твоим любимым сервисам" + lineSeparator.repeat(2)
                + "/get_key - Купить ключ доступа" + lineSeparator
                + "/trial - Попробовать бесплатно";
    }
}
