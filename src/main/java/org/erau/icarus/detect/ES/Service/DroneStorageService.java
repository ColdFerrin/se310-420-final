package org.erau.icarus.detect.ES.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class DroneStorageService implements DroneStorageInterface {

    private static final ObjectMapper DEFAULT_JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(JsonParser.Feature.ALLOW_COMMENTS)
            .findAndRegisterModules();


    @Value("${detect.es.hosts}")
    private String[] esHosts;

    public DroneStorageService() {
    }

    @PostConstruct
    public void init(){
        HttpHost[] httpHost = new HttpHost[esHosts.length];
        for (int i = 0; i < esHosts.length; i++){
            String host;
            int port;
            String protocall;

            String[] parts = esHosts[i].split(":");
        }
    }

    @Override
    public DroneInfo save(DroneInfo entity) {
        System.out.println("you have saved");
        return null;
    }

    @Override
    public Optional<DroneInfo> findByID(String id) {
        System.out.println("you tried to find by id");
        return Optional.empty();
    }

    @Override
    public Iterable<DroneInfo> findAllPaging(int pageSize, int pageNum){
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(DroneInfo entity) {

    }

    @Override
    public boolean existsById(String primaryKey) {
        return false;
    }
}
