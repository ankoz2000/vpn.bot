package bot.telegram.vpn.services.payments.strategy;

import bot.telegram.vpn.services.payments.PaymentRequest;
import bot.telegram.vpn.services.payments.PaymentType;
import org.springframework.stereotype.Component;

import static bot.telegram.vpn.services.payments.PaymentType.TRIAL;

@Component
public class TrialPaymentStrategy implements PaymentStrategy {
    @Override
    public Object makePayment(PaymentRequest request) throws Exception {
        throw new Exception("не реализован");
    }

    @Override
    public Object completePayment(String preCheckoutQueryId) throws Exception {
        throw new Exception("не реализован");
    }

    @Override
    public PaymentType getType() {
        return TRIAL;
    }
}
