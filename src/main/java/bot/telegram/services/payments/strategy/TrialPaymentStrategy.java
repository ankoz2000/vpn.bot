package bot.telegram.services.payments.strategy;

import bot.telegram.services.payments.PaymentRequest;
import bot.telegram.services.payments.PaymentType;
import org.springframework.stereotype.Component;

import static bot.telegram.services.payments.PaymentType.TRIAL;

@Component
public class TrialPaymentStrategy implements PaymentStrategy {
    @Override
    public Boolean makePayment(PaymentRequest request) throws Exception {
        return true;
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
