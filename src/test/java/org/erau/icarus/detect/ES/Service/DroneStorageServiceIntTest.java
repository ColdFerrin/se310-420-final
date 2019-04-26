package org.erau.icarus.detect.ES.Service;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@SpringBootConfiguration
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "detect.es.hosts=localhost:9200",
})
public class DroneStorageServiceIntTest {

    @Autowired
    private DroneStorageService droneStorageService;

    private DroneInfo droneInfo;

    @Before
    public void init(){

    }

    @Test
    public void saveTest(){
        //droneStorageService.save()
    }
}
