package bot.telegram.services.payments.state;

import bot.telegram.services.payments.PaymentRequest;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.AnswerPreCheckoutQuery;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static bot.telegram.services.payments.state.PaymentState.*;

@Component
public class PaymentCompletion implements PaymentsState {
    @Inject
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;

    @Override
    public void previous(PaymentRequest payment) {
        payment.setState(INITIALIZATION);
    }

    @Override
    public void next(PaymentRequest payment) {
        payment.setState(COMPLETED);
    }

    @Override
    public PaymentState current() {
        return PAYMENT;
    }

    @Override
    public void run(PaymentRequest payment) throws Exception {
        AnswerPreCheckoutQuery answerCheckout = new AnswerPreCheckoutQuery(payment.getPreCheckoutQueryId());
        BaseResponse response = bot.execute(answerCheckout);
        if (response.errorCode() >= 0) {
            throw new Exception("Оплата не прошла");
        }
    }
}
