package bot.telegram.vpn.services.payments.strategy;

import bot.telegram.vpn.services.payments.PaymentRequest;
import bot.telegram.vpn.services.payments.PaymentType;

public interface PaymentStrategy {
    Object makePayment(PaymentRequest request) throws Exception;
    Object completePayment(String preCheckoutQueryId) throws Exception;
    PaymentType getType();
}
