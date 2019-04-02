package org.erau.icarus.detect.ES.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DroneStorageService implements DroneStorageInterface {

    //Create a default json mapper
    private static final ObjectMapper DEFAULT_JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(JsonParser.Feature.ALLOW_COMMENTS)
            .findAndRegisterModules();

    private RestHighLevelClient restHighLevelClient;

    @Autowired
    public DroneStorageService(@Value("${detect.es.hosts}") String[] esHosts) {
        //Parse the hosts in the file application.properties and create the http hosts for the database
        HttpHost[] httpHosts = new HttpHost[esHosts.length];
        String[][] hosts = parseHosts(esHosts);
        for (int i = 0; i < esHosts.length; i++) {
            httpHosts[i] = new HttpHost(hosts[i][0], Integer.parseInt(hosts[i][2]), hosts[i][1]);
        }
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(httpHosts)
        );
    }

    static String[][] parseHosts(String[] hosts){
        //Parse the hosts in the file application.properties and create the http hosts for the database
        String[][] output = new String[hosts.length][3];
        //loop through hosts
        for (int i = 0; i < hosts.length; i++) {
            String host;
            int port;
            String protocall;

            //parse this host on the semicolons
            String[] parts = hosts[i].split(":");
            //if there is only one part then there is only a web address set port and protocol to default
            if (parts.length == 1) {
                port = 9200;
                protocall = "http";
                host = parts[0];
            }
            // if there are two parts
            else if (parts.length == 2) {
                //check if the first part is http or https and use default port
                if (parts[0].equalsIgnoreCase("http") || parts[0].equalsIgnoreCase("https")) {
                    protocall = parts[0];
                    host = parts[1].substring(2);
                    port = 9200;
                }
                //otherwise use the default protocol
                else {
                    protocall = "http";
                    host = parts[0];
                    port = Integer.parseInt(parts[1]);
                }
            }
            //parse parts if there are 3
            else if (parts.length == 3) {
                protocall = parts[0];
                host = parts[1].substring(2);
                port = Integer.parseInt(parts[2]);
            }
            //else use all defaults
            else {
                protocall = "http";
                host = "localhost";
                port = 9200;
            }
            output[i][0] = protocall;
            output[i][1] = host;
            output[i][2] = String.valueOf(port);
        }
        return output;
    }

    @Override
    public DroneInfo save(DroneInfo entity) throws IOException {
        IndexRequest indexRequest = new IndexRequest(
                "icarus-drone-id",
                "_doc"
        );
        String jsonString = DEFAULT_JSON_MAPPER.writeValueAsString(entity);
        indexRequest.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        if (indexResponse.status().getStatus() == 200) {
            entity.setId(indexResponse.getId());
        } else {
            entity.setError(indexResponse.getResult().getLowercase());
        }
        return entity;
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
