package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.BNPDId.BNPDIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
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
    private BNPDIdService BNPDIdService;

    @Mock
    private DroneStorageService droneStorageService;

    @InjectMocks
    private InputController inputController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(inputController).build();
    }

    @Test
    public void inputImageTest() throws Exception{
        mockMvc.perform(put("/input/image")
                .content("{\"image\":\"/==\",\"camera\":{\"cameraID\":1,\"cameraLocation\":\"34.616026, -112.450895\",\"cameraModel\":{\"make\":\"flir\",\"model\":\"some flir\",\"lens\":\"some lens\"}}}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("image uploaded")))
                .andReturn();

        mockMvc.perform(put("/input/image")
                .content("")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void updateMetadataTest() throws Exception {
        mockMvc.perform(put("/input/update")
                .content("{\"image\":\"/==\",\"camera\":{\"cameraID\":1,\"cameraLocation\":\"34.616026, -112.450895\",\"cameraModel\":{\"make\":\"flir\",\"model\":\"some flir\",\"lens\":\"some lens\"}}}")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andReturn();

        mockMvc.perform(put("/input/update")
                .content("")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}