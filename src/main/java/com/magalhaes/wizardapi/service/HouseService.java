package com.magalhaes.wizardapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magalhaes.wizardapi.domain.House;
import com.magalhaes.wizardapi.exception.HouseException;
import com.magalhaes.wizardapi.repository.HouseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HouseService {

    private HouseRepository houseRepository;

    @Value("${harry-potter-api.url}")
    private String url;

    @Value("${harry-potter-api.key}")
    private String key;

    private ObjectMapper mapper;

    public HouseService(HouseRepository houseRepository, ObjectMapper objectMapper) {
        this.houseRepository = houseRepository;
        this.mapper = objectMapper;
    }

    public House save(House house) {
        return houseRepository.save(house);
    }

    @HystrixCommand(fallbackMethod = "houseError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public House getHouseByApiId(String houseApiId) {

        House house = houseRepository.findByApiId(houseApiId);
        if (house != null) {
            return house;
        }

        JsonNode jsonResponse = getJsonResponse(houseApiId);

        if (jsonResponse.get("_id") == null) {
            this.houseError(houseApiId);
        }
        return newHouse(jsonResponse);
    }

    public House houseError(String houseApiId) {
        throw new HouseException(houseApiId);
    }

    private JsonNode getJsonResponse(String houseApiId) {
        try {
            ResponseEntity<String> responseEntity = new RestTemplate()
                    .getForEntity(url + "/houses/{houseId}?key={apiKey}", String.class, houseApiId, key);
            return mapper.readTree(responseEntity.getBody()).get(0);
        } catch (JsonProcessingException ex) {
            throw new HouseException(houseApiId, ex);
        }
    }

    private House newHouse(JsonNode jsonNode) {
        return House.builder()
                .apiId(jsonNode.get("_id").asText())
                .name(jsonNode.get("name").asText())
                .build();
    }
}
