package org.erau.icarus.detect.ES.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        //loop through hosts
        for (int i = 0; i < esHosts.length; i++){
            String host;
            int port;
            String protocall;

            //parse this host on the semicolons
            String[] parts = esHosts[i].split(":");
            //if there is only one part then there is only a web address set port and protocol to default
            if(parts.length == 1){
                port = 9200;
                protocall = "http";
                host = parts[0];
            }
            // if there are two parts
            else if (parts.length == 2) {
                //check if the first part is http or https and use default port
                if(parts[0].equalsIgnoreCase("http") || parts[0].equalsIgnoreCase("https")){
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
            else if (parts.length == 3){
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
            httpHosts[i] = new HttpHost(host, port, protocall);
        }
        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(httpHosts)
        );
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
