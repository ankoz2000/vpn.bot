package bot.telegram.services.payments.strategy;

import bot.telegram.services.payments.PaymentRequest;
import bot.telegram.services.payments.PaymentType;

public interface PaymentStrategy {
    Boolean makePayment(PaymentRequest request) throws Exception;
    Object completePayment(String preCheckoutQueryId) throws Exception;
    PaymentType getType();
}
