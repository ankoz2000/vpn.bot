package bot.telegram;

import bot.telegram.vpn.services.BotServiceFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class VpnApplication {
    private final BotServiceFacade facade;

    public VpnApplication(BotServiceFacade facade) {
        this.facade = facade;
    }

    public static void main(String[] args) {
        SpringApplication.run(VpnApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        facade.run();
    }
}