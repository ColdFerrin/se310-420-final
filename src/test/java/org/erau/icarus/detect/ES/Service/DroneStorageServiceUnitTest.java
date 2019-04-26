package org.erau.icarus.detect.ES.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@SpringBootConfiguration
@RunWith(SpringRunner.class)
public class DroneStorageServiceUnitTest {

    @Test
    public void parseHostsTest() {
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
}