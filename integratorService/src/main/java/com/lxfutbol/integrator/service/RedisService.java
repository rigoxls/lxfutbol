package com.lxfutbol.integrator.service;

import javax.annotation.PostConstruct;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lxfutbol.integrator.repository.ProviderTemplateEntity;

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
 
    public JSONObject findSearchTemplateById(final String id) throws JSONException {
        var template = hashOperations.get(PROVIDER_TEMPLATE_CACHE, id);
        JSONObject jsonObjectMessage = new JSONObject(template.getTemplate());
        return jsonObjectMessage.getJSONObject("search");
    }
    
    public JSONObject findBookTemplateById(final String id) throws JSONException {
        var template = hashOperations.get(PROVIDER_TEMPLATE_CACHE, id);
        JSONObject jsonObjectMessage = new JSONObject(template.getTemplate());        
        return jsonObjectMessage.getJSONObject("book");
    }    
    
    public Map<String, ProviderTemplateEntity> findAll() {
        return hashOperations.entries(PROVIDER_TEMPLATE_CACHE);
    }

    public void delete(String id) {
        hashOperations.delete(PROVIDER_TEMPLATE_CACHE, id);
    }
}