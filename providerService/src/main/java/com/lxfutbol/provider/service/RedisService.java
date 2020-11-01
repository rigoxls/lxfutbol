package com.lxfutbol.provider.service;
import java.util.Map;
 
import javax.annotation.PostConstruct;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lxfutbol.provider.repository.ProviderTemplateEntity;
import com.mysql.cj.xdevapi.JsonArray;
 
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
 
    public JSONArray findById(final String id) throws JSONException {
        var template = hashOperations.get(PROVIDER_TEMPLATE_CACHE, id);
        JSONObject jsonObjectMessage = new JSONObject(template.getTemplate());
        var search = jsonObjectMessage.getJSONObject("search");
        var book = jsonObjectMessage.getJSONObject("book");
        
        JSONArray jsonTemplate = new JSONArray();
        jsonTemplate.put(search);
        jsonTemplate.put(book);
        
        return jsonTemplate;
    }
    
    public Map<String, ProviderTemplateEntity> findAll() {
        return hashOperations.entries(PROVIDER_TEMPLATE_CACHE);
    }

    public void delete(String id) {
        hashOperations.delete(PROVIDER_TEMPLATE_CACHE, id);
    }
}
