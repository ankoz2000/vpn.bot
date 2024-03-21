package bot.telegram.vpn.services.keys.strategy;

import bot.telegram.vpn.services.KeyChecker;
import bot.telegram.vpn.services.vpn.outline.OutlineApi;
import bot.telegram.vpn.services.vpn.outline.dto.AccessKeyCreateRequest;
import bot.telegram.vpn.services.vpn.outline.dto.DataLimit;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import static bot.telegram.vpn.services.keys.strategy.KeyType.TRIAL;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
public class TrialKeyStrategy implements KeyStrategy<AccessKeyCreateRequest> {
    @Inject
    private OutlineApi outline;

    @Override
    public KeyType getType() {
        return TRIAL;
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
        var body = new AccessKeyCreateRequest();
        body.setLimit(new DataLimit(1000));
        body.setPort(7501); //todo random port from range without repeating
        return body;
    }
}
