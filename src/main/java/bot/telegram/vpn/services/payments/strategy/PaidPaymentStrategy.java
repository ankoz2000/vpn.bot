package bot.telegram.vpn.services.payments.strategy;

import bot.telegram.vpn.services.payments.PaymentRequest;
import bot.telegram.vpn.services.payments.PaymentType;
import bot.telegram.vpn.services.payments.state.PaymentState;
import bot.telegram.vpn.services.payments.state.PaymentsState;
import com.pengrad.telegrambot.TelegramBot;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.util.Map;

import static bot.telegram.vpn.services.payments.PaymentType.PAID;

@Component
public class PaidPaymentStrategy implements PaymentStrategy {
    @Inject
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;

    @Inject
    @Qualifier(value = "PaymentStates")
    private Map<PaymentState, PaymentsState> states;

    @Override
    public Object makePayment(PaymentRequest request) throws Exception {
        states.get(request.getState()).run(request);

        return null;
    }

    @Override
    public Object completePayment(String preCheckoutQueryId) throws Exception {
        throw new Exception("не реализован");
    }

    @Override
    public PaymentType getType() {
        return PAID;
    }
}
