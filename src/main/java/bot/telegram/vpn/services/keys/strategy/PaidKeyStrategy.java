package bot.telegram.vpn.services.keys.strategy;

import bot.telegram.vpn.services.KeyChecker;
import bot.telegram.vpn.services.vpn.outline.OutlineApi;
import bot.telegram.vpn.services.vpn.outline.dto.AccessKeyCreateRequest;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import static bot.telegram.vpn.services.keys.strategy.KeyType.PAID;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class PaidKeyStrategy implements KeyStrategy<AccessKeyCreateRequest> {
    @Inject
    private OutlineApi outline;

    @Override
    public KeyType getType() {
        return PAID;
    }

    @Override
    public String createNewAccessKey(String userName, int id) throws Exception {
        if (isFalse(KeyChecker.isEmpty(userName))) {
            throw new Exception("ключ уже был создан");
        }
        var body = getDefaultCreateRequestBody();
        body.setName(userName + "/acc=" + id);

        var newKey = outline.createNewKey(body);
//        OutlineApi.KeyCache.addNewKeyToCache(userName, newKey);
        return newKey.getAccessUrl();
    }

    @Override
    public AccessKeyCreateRequest getDefaultCreateRequestBody() {
        return new AccessKeyCreateRequest();
    }
}
