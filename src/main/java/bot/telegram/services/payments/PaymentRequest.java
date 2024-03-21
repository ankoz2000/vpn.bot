package bot.telegram.services.payments;

import bot.telegram.services.keys.strategy.KeyType;
import bot.telegram.services.payments.state.PaymentState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
    private Long chatId;
    private KeyType type;
    private String userName;
    private PaymentState state;
    private String preCheckoutQueryId;
}
