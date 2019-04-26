package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.BNPDId.BNPDIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class InputControllerTest {


    private MockMvc mockMvc;

    @Mock
    private BNPDIdService bnpdIdService;

    @Mock
    private DroneStorageService droneStorageService;

    @InjectMocks
    private InputController inputController;

    private static final String inputString = "{\"image\":\"/==\",\"camera\":{\"cameraID\":1,\"cameraLocation\":{\"lat\":34.616026,\"lon\":-112.450895},\"cameraModel\":{\"make\":\"flir\",\"model\":\"some flir\",\"lens\":\"some lens\"}}}";

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(inputController).build();
    }

    @Test
    public void inputImageTest() throws Exception{
        DroneInfo toReturn = DroneStorageService.DEFAULT_JSON_MAPPER.readValue(inputString, DroneInfo.class);

        Mockito.when(bnpdIdService.input(any(DroneInfo.class))).thenReturn(toReturn);

        // Test for good image uploaded successfully.
        mockMvc.perform(put("/input/image")
                .content(inputString)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Image uploaded")))
                .andReturn();


        // Test of empty body upload
        mockMvc.perform(put("/input/image")
                .content("")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void updateMetadataTest() throws Exception {

        // test for drone not found.
        mockMvc.perform(put("/input/update")
                .content(inputString)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn();

        // test for drone found no change
        DroneInfo testDroneInfo = new DroneInfo();
        Optional<DroneInfo> testOptional = Optional.of(testDroneInfo);

        Mockito.when(droneStorageService.findOne(null)).thenReturn(testOptional);

        mockMvc.perform(put("/input/update")
                .content(inputString)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        // Test for no input
        mockMvc.perform(put("/input/update")
                .content("")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}