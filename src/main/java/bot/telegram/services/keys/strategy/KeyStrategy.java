package bot.telegram.services.keys.strategy;

import bot.telegram.services.BaseStrategy;

public interface KeyStrategy<RT> extends BaseStrategy<KeyType> {
    String createNewAccessKey(String userName, int id) throws Exception;

    RT getDefaultCreateRequestBody();
}
