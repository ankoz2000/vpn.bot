package bot.telegram.vpn.services.bot.message;

import bot.telegram.vpn.services.KeyFacade;
import bot.telegram.vpn.services.bot.UpdateInterface;
import bot.telegram.vpn.services.keys.strategy.KeyStrategy;
import bot.telegram.vpn.services.keys.strategy.KeyType;
import bot.telegram.vpn.services.payments.PaymentRequest;
import bot.telegram.vpn.services.payments.PaymentType;
import bot.telegram.vpn.services.payments.state.PaymentState;
import bot.telegram.vpn.services.payments.strategy.PaymentStrategy;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

import static bot.telegram.vpn.services.bot.message.MessageType.PRE_CHECKOUT;
import static bot.telegram.vpn.services.payments.PaymentType.TEST_PAID;

@Component
public class PreCheckoutMessageStrategy implements MessageStrategy<Update, Message> {
    @Inject
    @Qualifier(value = "KeyStrategies")
    private Map<KeyType, KeyStrategy> keyStrategies;
    @Autowired
    private KeyFacade keyFacade;
    @Inject
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
    public SendMessage interactWithReply(UpdateInterface<Update, Message> update) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return PRE_CHECKOUT;
    }
}
