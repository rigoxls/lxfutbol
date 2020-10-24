package com.lxfutbol.transformSoap.service;

import javax.annotation.PostConstruct;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lxfutbol.transformSoap.repository.ProviderTemplateEntity;

@Service
public class RedisService {

    private final String PROVIDER_TEMPLATE_CACHE = "PROVIDER_TEMPLATE";

    @Autowired
    RedisTemplate<String, ProviderTemplateEntity> redisTemplate;
    private HashOperations<String, String, ProviderTemplateEntity> hashOperations;

    @PostConstruct
    private void intializeHashOperations() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(final ProviderTemplateEntity providerTemplateEntity) {
        hashOperations.put(PROVIDER_TEMPLATE_CACHE, providerTemplateEntity.getId(), providerTemplateEntity);
    }

    public JSONObject findById(final String id, String operation) throws JSONException {
        var template = hashOperations.get(PROVIDER_TEMPLATE_CACHE, id);
        JSONObject jsonObjectMessage = new JSONObject(template.getTemplate());
        Iterator<?> keys = jsonObjectMessage.keys();
        var jsonTemplate = new JSONObject();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            if (operation.equals(key)) {
            	jsonTemplate = jsonObjectMessage.getJSONObject(key);
			}
        }
        return jsonTemplate;
    }

    public Map<String, ProviderTemplateEntity> findAll() {
        return hashOperations.entries(PROVIDER_TEMPLATE_CACHE);
    }

    public void delete(String id) {
        hashOperations.delete(PROVIDER_TEMPLATE_CACHE, id);
    }
}