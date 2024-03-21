package bot.telegram.vpn.services.bot;

import bot.telegram.vpn.services.bot.message.MessageStrategy;
import bot.telegram.vpn.services.bot.message.MessageType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BaseHandler implements AbstractSendRequestFunctionalInterface {
    private final StartupService startupService;
    @Autowired
    @Qualifier(value = "VpnBot")
    private TelegramBot bot;
    private final Map<MessageType, MessageStrategy> messageStrategies;

    public BaseHandler(@Autowired StartupService startupService,
                            @Qualifier(value = "MessageStrategies") Map<MessageType, MessageStrategy> messageStrategies) {
        this.startupService = startupService;
        this.messageStrategies = messageStrategies;
    }

    @Override
    public AbstractSendRequest handle(Update update) {
        Message inputMessage = update.message();
        if (update.shippingQuery() != null) {
//            AnswerShippingQuery answerShippingQuery = new AnswerShippingQuery(update.shippingQuery().id(),
//                    new ShippingOption("1", "FREE", new LabeledPrice("free delivery", 0))
//            );
//            BaseResponse response = bot.execute(answerShippingQuery);
//
            AnswerShippingQuery answerShippingError = new AnswerShippingQuery(update.shippingQuery().id(), "Не могу доставить сюда");
            BaseResponse response = bot.execute(answerShippingError);
        }
        if (update.preCheckoutQuery() != null) {
            try {
                messageStrategies.get(MessageType.PRE_CHECKOUT).interact(update);
            } catch (Exception e) {
                return new SendMessage(update.preCheckoutQuery().from().id(), "Оплата прошла неуспешно");
            }
        }
        if (inputMessage != null) {
            if (inputMessage.photo() != null) {
                log.error("No handler for photo");
            } else if (inputMessage.video() != null) {
                log.error("No handler for video");
            }
            log.info("INPUT_MESSAGE: " + inputMessage.text());

            var upd = new UpdateAdapterPeng(update);
            return messageStrategies.get(MessageType.TEXT).interactWithReply(upd);
        }
        return new SendMessage(update.chatMember().from().id(), "shipping...");
    }
}
