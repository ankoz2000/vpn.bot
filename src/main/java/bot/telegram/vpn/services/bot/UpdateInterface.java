package bot.telegram.vpn.services.bot;

public interface UpdateInterface<U, M> {
    M getMessage();
    void setUpdate(U update);
}
