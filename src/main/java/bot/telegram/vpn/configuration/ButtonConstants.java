package bot.telegram.vpn.configuration;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonRequestUser;

public class ButtonConstants {
    static KeyboardButton SEND_CONTACT;
    static {
        SEND_CONTACT = new KeyboardButton("Отправить контакт");
        SEND_CONTACT.setRequestUser(KeyboardButtonRequestUser.builder().build());
    }
}
