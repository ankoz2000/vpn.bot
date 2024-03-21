package bot.telegram.services.vpn.outline;

import bot.telegram.services.vpn.outline.dto.AccessKey;
import bot.telegram.services.vpn.outline.dto.AccessKeyCreateRequest;
import bot.telegram.utils.Ssl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
public class OutlineApi implements BeanPostProcessor {
    public static final String ACCESS_KEYS = "accessKeys";
    public static final String SPLIT_BY = "/acc=";
    private final RestTemplate restTemplate;
    private final URI accessKeysEndpoint;
    private boolean once;

    public OutlineApi(RestTemplate restTemplate, @Value("${outline.apiUrl}") String connectionString) {
        this.restTemplate = restTemplate;
        accessKeysEndpoint = getUri(connectionString + "/access-keys");
    }

    private URI getUri(String url) {
        return URI.create(url);
    }

    public List<AccessKey> getAccessKeys() {
        var requestEntity = new RequestEntity<List<Object>>(GET, accessKeysEndpoint);
        var response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String, List<AccessKey>>>() {});
        var body = response.getBody();
        if (body != null && body.containsKey(ACCESS_KEYS)) return body.get(ACCESS_KEYS);
        else return new ArrayList<>();
    }

    public AccessKey createNewKey(AccessKeyCreateRequest request) {
        var requestEntity = new RequestEntity<>(request, POST, accessKeysEndpoint);
        var response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<AccessKey>() {});
        return response.getBody();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isFalse(once)) {
            Ssl.disableVerification();
            var accessKeys = getAccessKeys();
            KeyCache.accessKeysCache = accessKeys.stream()
                    .collect(Collectors.groupingBy(k -> k.getName().split(SPLIT_BY)[0]));
            once = !once;
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public static class KeyCache {
        private static Map<String, List<AccessKey>> accessKeysCache;

        public static Map<String, List<AccessKey>> getAccessKeysCache() {
            return accessKeysCache;
        }

        public static void addNewKeyToCache(String username, AccessKey accessKey) {
            var current = accessKeysCache.get(username);
            if (current == null) {
                current = new ArrayList<>();
            }
            current.add(accessKey);
            accessKeysCache.put(username, current);
        }
    }
}
