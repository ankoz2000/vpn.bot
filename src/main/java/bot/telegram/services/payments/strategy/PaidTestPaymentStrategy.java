package bot.telegram.services.payments.strategy;

import bot.telegram.services.payments.PaymentRequest;
import bot.telegram.services.payments.PaymentType;
import bot.telegram.services.payments.state.PaymentState;
import bot.telegram.services.payments.state.PaymentsState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.AnswerPreCheckoutQuery;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static bot.telegram.services.payments.PaymentType.TEST_PAID;

@Component
public class PaidTestPaymentStrategy implements PaymentStrategy {
    @Inject
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;

    @Value("${bot.shopId}")
    private String shopId;
    @Value("${bot.shopArticleId}")
    private String shopArticleId;
    @Value("${bot.testCard}")
    private String testCard;
    @Value("${bot.paymentToken}")
    private String providerToken;

    @Inject
    @Qualifier(value = "PaymentStates")
    private Map<PaymentState, PaymentsState> states;

    @Override
    public Boolean makePayment(PaymentRequest request) throws Exception {
        states.get(request.getState()).run(request);
        return true;
    }

    @Override
    public Object completePayment(String preCheckoutQueryId) throws Exception {
        AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery(preCheckoutQueryId);
        BaseResponse response = bot.execute(answerPreCheckoutQuery);
        return null;
    }

    @Override
    public PaymentType getType() {
        return TEST_PAID;
    }
}
