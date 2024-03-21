package bot.telegram.services.payments.state;

import bot.telegram.services.payments.PaymentRequest;
import org.springframework.stereotype.Component;

import static bot.telegram.services.payments.state.PaymentState.*;

@Component
public class PaymentCompleted implements PaymentsState {
    @Override
    public void previous(PaymentRequest payment) {
        payment.setState(PAYMENT);
    }

    @Override
    public void next(PaymentRequest payment) {

    }

    @Override
    public PaymentState current() {
        return COMPLETED;
    }

    @Override
    public void run(PaymentRequest payment) {

    }
}
