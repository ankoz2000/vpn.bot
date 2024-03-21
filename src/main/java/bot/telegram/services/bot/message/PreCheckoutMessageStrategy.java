package bot.telegram.services.bot.message;

import bot.telegram.services.KeyFacade;
import bot.telegram.services.keys.strategy.KeyStrategy;
import bot.telegram.services.keys.strategy.KeyType;
import bot.telegram.services.payments.PaymentRequest;
import bot.telegram.services.payments.PaymentType;
import bot.telegram.services.payments.state.PaymentState;
import bot.telegram.services.payments.strategy.PaymentStrategy;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static bot.telegram.services.bot.message.MessageType.PRE_CHECKOUT;
import static bot.telegram.services.payments.PaymentType.TEST_PAID;

@Component
public class PreCheckoutMessageStrategy implements MessageStrategy<Update, Message> {
    @Autowired
    @Qualifier(value = "KeyStrategies")
    private Map<KeyType, KeyStrategy> keyStrategies;
    @Autowired
    private KeyFacade keyFacade;
    @Autowired
    @Qualifier(value = "PaymentStrategies")
    private Map<PaymentType, PaymentStrategy> paymentStrategies;

    @Override
    public void interact(Update update) throws Exception {
        var payment = PaymentRequest.builder()
                .preCheckoutQueryId(update.preCheckoutQuery().id())
                .state(PaymentState.PAYMENT)
                .build();

        var response = paymentStrategies.get(TEST_PAID).makePayment(payment);
    }

    @Override
    public SendMessage interactWithReply(Update update) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return PRE_CHECKOUT;
    }
}
