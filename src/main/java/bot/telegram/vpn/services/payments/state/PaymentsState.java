package bot.telegram.vpn.services.payments.state;

import bot.telegram.vpn.services.payments.PaymentRequest;

public interface PaymentsState {
    void previous(PaymentRequest payment);
    void next(PaymentRequest payment);
    PaymentState current();

    void run(PaymentRequest payment) throws Exception;
}
