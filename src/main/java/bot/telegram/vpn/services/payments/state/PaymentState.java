package bot.telegram.vpn.services.payments.state;

public enum PaymentState {
    NONE,
    INITIALIZATION,
    PAYMENT,
    COMPLETED, REJECTED
}
