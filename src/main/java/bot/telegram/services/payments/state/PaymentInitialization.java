package bot.telegram.services.payments.state;

import bot.telegram.services.payments.PaymentRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.LabeledPrice;
import com.pengrad.telegrambot.request.SendInvoice;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static bot.telegram.services.payments.state.PaymentState.*;

@Component
public class PaymentInitialization implements PaymentsState {
    @Inject
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;

    @Value("${bot.paymentToken}")
    private String providerToken;

    @Override
    public void previous(PaymentRequest payment) {
        payment.setState(NONE);
    }

    @Override
    public void next(PaymentRequest payment) {
        payment.setState(PAYMENT);
    }

    @Override
    public PaymentState current() {
        return INITIALIZATION;
    }

    @Override
    public void run(PaymentRequest payment) {
        SendInvoice sendInvoice = new SendInvoice(payment.getChatId(), "Оплата услуг (Test)", "Оплата посреднических услуг", "vpn",
                providerToken, "RUB", new LabeledPrice("Посреднические услуги", 10000))
                .needPhoneNumber(false)
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Оплатить").pay(),
                        new InlineKeyboardButton("Отмена").callbackData("Назад")
                }));
        SendResponse response = bot.execute(sendInvoice);

        this.next(payment);
    }
}
