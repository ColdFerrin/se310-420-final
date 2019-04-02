package org.erau.icarus.detect.ES.Service;

import org.apache.http.HttpHost;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@SpringBootConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "detect.es.hosts=localhost, localhost:12345, http://localhost, https://test.url.com",
})
public class DroneStorageServiceTest {

    @Test
    public void parseHostsTest(){
        String[] testHosts = {"localhost", "localhost:12345", "http://localhost", "https://test.url.com"};
        String[][] hosts = DroneStorageService.parseHosts(testHosts);

        assertEquals(hosts[0][0], "http");
        assertEquals(hosts[0][1], "localhost");
        assertEquals(hosts[0][2], "9200");
        assertEquals(hosts[1][0], "http");
        assertEquals(hosts[1][1], "localhost");
        assertEquals(hosts[1][2], "12345");
        assertEquals(hosts[2][0], "http");
        assertEquals(hosts[2][1], "localhost");
        assertEquals(hosts[2][2], "9200");
        assertEquals(hosts[3][0], "https");
        assertEquals(hosts[3][1], "test.url.com");
        assertEquals(hosts[3][2], "9200");
    }

    @Test
    public void save() {
    }

    @Test
    public void findByID() {
    }

    @Test
    public void findAllPaging() {
    }

    @Test
    public void count() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void existsById() {
    }
}