package bot.telegram.services;

import bot.telegram.services.vpn.outline.dto.AccessKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyChecker {
    private static Map<String, List<AccessKey>> users;

    static {
        users = new HashMap<>();
        users.put("ankoz2000", new ArrayList<>());
        users.put("theadamyan", new ArrayList<>());
    }

    public static boolean isEmpty(String username) {
//        var keys = OutlineApi.KeyCache.getAccessKeysCache();
//        return !(keys.containsKey(username) && !keys.get(username).isEmpty());
        return false;
    }
}
