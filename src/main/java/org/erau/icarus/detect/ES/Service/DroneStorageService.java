package org.erau.icarus.detect.ES.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service    //Creates the class as a library managed singleton.
public class DroneStorageService implements DroneStorageInterface {

    //Create a default json mapper
    public static final ObjectMapper DEFAULT_JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(JsonParser.Feature.ALLOW_COMMENTS)
            .findAndRegisterModules();

    private RestHighLevelClient client;

    @Autowired
    public DroneStorageService(@Value("${detect.es.hosts}") String[] esHosts) {
        //Parse the hosts in the file application.properties and create the http hosts for the database
        HttpHost[] httpHosts = new HttpHost[esHosts.length];
        String[][] hosts = parseHosts(esHosts);
        for (int i = 0; i < esHosts.length; i++) {
            httpHosts[i] = new HttpHost(hosts[i][1], Integer.parseInt(hosts[i][2]), hosts[i][0]);
        }
        client = new RestHighLevelClient(
                RestClient.builder(httpHosts)
        );
    }

    public DroneStorageService(RestHighLevelClient client) {
        this.client = client;
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


    @Override //Tells compiler that the inteface method is being overridden
    //Saves Drone info to database
    public DroneInfo save(DroneInfo entity) throws IOException {
        //generate an index request which is the equivalent to save
        IndexRequest indexRequest = new IndexRequest("icarus-drone-id", "_doc");
        //generate a random id string
        indexRequest.id();
        //write the object to a json formatted string with the json mapper
        String jsonString = DEFAULT_JSON_MAPPER.writeValueAsString(entity);
        //save the json formatted string to the index request
        indexRequest.source(jsonString, XContentType.JSON);
        //run the index request with the default option on the client and get the response synchronously
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        //get the id that was saved in the database from the response
        entity.setId(indexResponse.getId());
        //get the shard information from the response
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        //if the successful shards is less then the total shards set the error message
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            entity.setError(indexResponse.getShardInfo().toString());
        }
        //return the done info with all new information
        return entity;
    }

    @Override
    public Optional<DroneInfo> findOne(String id) throws IOException{
        //Generate new get request. database method for find one with input id
        GetRequest getRequest = new GetRequest("icarus-drone-id", "_doc", id);
        //Send the get request to the client and get the response.
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        //get the json string value from the response and generate the drone info object
        DroneInfo toReturn = DEFAULT_JSON_MAPPER.readValue(getResponse.getSourceAsString(), DroneInfo.class);
        //generate the return optional from the drone info object
        return Optional.of(toReturn);
    }

    @Override
    //calls find all paging with default values
    public Iterable<DroneInfo> findAllPaging() throws IOException {
        return findAllPaging(10, 0);
    }

    @Override
    public Iterable<DroneInfo> findAllPaging(int pageSize, int pageNum) throws IOException{
        //
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(pageSize * pageNum);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("icarus-drone-id");

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] searchHitArray = searchHits.getHits();

        ArrayList<DroneInfo> droneInfoArrayList = new ArrayList<>();

        for (SearchHit hit :
                searchHitArray) {
            DroneInfo droneInfo = DEFAULT_JSON_MAPPER.readValue(hit.getSourceAsString(), DroneInfo.class);
            droneInfoArrayList.add(droneInfo);
        }
        return droneInfoArrayList;
    }

    @Override
    public long count() throws IOException{
        CountRequest countRequest = new CountRequest("icarus-drone-id");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }

    @Override
    public String delete(String id) throws IOException{
        DeleteRequest deleteRequest = new DeleteRequest("icarus-drone-id",
                "_doc",
                id);
        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return deleteResponse.getId();
    }

    @Override
    public boolean existsById(String primaryKey) throws IOException{
        GetRequest getRequest = new GetRequest("icarus-drone-id", "_doc", primaryKey);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return client.exists(getRequest, RequestOptions.DEFAULT);
    }
}
