package bot.telegram.services.payments.state;

public enum PaymentState {
    NONE,
    INITIALIZATION,
    PAYMENT,
    COMPLETED, REJECTED
}
