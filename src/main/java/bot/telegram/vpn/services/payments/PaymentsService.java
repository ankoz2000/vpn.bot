package bot.telegram.vpn.services.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {
    @Value("bot.shopId")
    private String shopId;
    @Value("bot.shopArticleId")
    private String shopArticleId;
    @Value("bot.testCard")
    private String testCard;
    @Value("bot.paymentToken")
    private String paymentToken;
}
