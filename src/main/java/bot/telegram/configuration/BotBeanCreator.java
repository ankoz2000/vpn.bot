package bot.telegram.configuration;

import bot.telegram.services.bot.message.MessageStrategy;
import bot.telegram.services.bot.message.MessageType;
import bot.telegram.services.keys.strategy.KeyStrategy;
import bot.telegram.services.keys.strategy.KeyType;
import bot.telegram.services.payments.PaymentType;
import bot.telegram.services.payments.state.PaymentState;
import bot.telegram.services.payments.state.PaymentsState;
import bot.telegram.services.payments.strategy.PaymentStrategy;
import com.pengrad.telegrambot.TelegramBot;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Configuration
public class BotBeanCreator {
    @Bean(name = "VpnBot")
    public TelegramBot initBot(@Value("${bot.token:}") String token) {
        return new TelegramBot(token);
    }

    @Bean(name = "MessageStrategies")
    public Map<MessageType, MessageStrategy> getMessageStrategiesMap(@NonNull Collection<MessageStrategy> strategies) {
        return strategies.stream()
                .collect(toMap(MessageStrategy::getMessageType, Function.identity()));
    }

    @Bean(name = "KeyStrategies")
    public Map<KeyType, KeyStrategy> getKeyStrategiesMap(@NonNull Collection<KeyStrategy> strategies) {
        return strategies.stream()
                .collect(toMap(s -> (KeyType) s.getType(), Function.identity()));
    }

    @Bean(name = "PaymentStrategies")
    public Map<PaymentType, PaymentStrategy> getPaymentStrategiesMap(@NonNull Collection<PaymentStrategy> strategies) {
        return strategies.stream()
                .collect(toMap(PaymentStrategy::getType, Function.identity()));
    }


    @Bean(name = "PaymentStates")
    public Map<PaymentState, PaymentsState> getPaymentStatesMap(@NonNull Collection<PaymentsState> states) {
        return states.stream()
                .collect(toMap(PaymentsState::current, Function.identity()));
    }
}
