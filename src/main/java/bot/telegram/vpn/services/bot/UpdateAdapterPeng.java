package bot.telegram.vpn.services.bot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

public class UpdateAdapterPeng implements UpdateInterface<Update, Message> {
    Update update;
    @Override
    public Message getMessage() {
        return update.message();
    }

    @Override
    public void setUpdate(Update update) {
        this.update = update;
    }

    public UpdateAdapterPeng(Update update) {
        this.update = update;
    }
}
