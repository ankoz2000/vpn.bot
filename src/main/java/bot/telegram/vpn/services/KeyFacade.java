package bot.telegram.vpn.services;

import bot.telegram.vpn.services.keys.strategy.KeyStrategy;
import bot.telegram.vpn.services.keys.strategy.KeyType;
import bot.telegram.vpn.services.payments.PaymentRequest;
import bot.telegram.vpn.services.payments.state.PaymentState;
import bot.telegram.vpn.services.payments.strategy.PaymentStrategy;
import bot.telegram.vpn.services.payments.PaymentType;
import com.pengrad.telegrambot.model.SuccessfulPayment;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static bot.telegram.vpn.services.payments.PaymentType.TEST_PAID;
import static bot.telegram.vpn.services.payments.PaymentType.TRIAL;

@Component
public class KeyFacade {
    @Inject
    @Qualifier(value = "PaymentStrategies")
    private Map<PaymentType, PaymentStrategy> paymentStrategies;
    @Inject
    @Qualifier(value = "KeyStrategies")
    private Map<KeyType, KeyStrategy> keyStrategies;

    public Object getKeyWithTestPayment(Long chatId, String username, KeyType keyType, int accessKeyLastId) throws Exception {
        var payment = PaymentRequest.builder()
                .chatId(chatId)
                .userName(username)
                .type(keyType)
                .state(PaymentState.INITIALIZATION)
                .build();

        var response = paymentStrategies.get(keyType.equals(KeyType.PAID) ? TEST_PAID : TRIAL).makePayment(payment);

        return null;
    }

    public String completePayment(SuccessfulPayment payment, KeyType keyType, int accessKeyLastId, Long chatId, String username) throws Exception {
        var request = PaymentRequest.builder()
                .chatId(chatId)
                .userName(username)
                .type(keyType)
                .state(PaymentState.COMPLETED)
                .build();

        var response = paymentStrategies.get(keyType.equals(KeyType.PAID) ? TEST_PAID : TRIAL).makePayment(request);

        return keyStrategies.get(keyType).createNewAccessKey(username, accessKeyLastId);
    }
}
