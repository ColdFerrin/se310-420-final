package org.erau.icarus.detect.Services.DroneId;

import org.erau.icarus.detect.ES.Model.*;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DroneIdServiceTest {

    @Mock
    DroneStorageService droneStorageService;

    @InjectMocks
    DroneIdService droneIdService;

    private DroneInfo droneInfo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        droneInfo = new DroneInfo();
        droneInfo.setTest(true);
        droneInfo.setTimestamp(Date.from(Instant.now()));
        droneInfo.setImage("/==");

        CameraLocation cameraLocation = new CameraLocation();
        cameraLocation.setLat(48.852991);
        cameraLocation.setLon(2.349898);

        CameraModel cameraModel = new CameraModel();
        cameraModel.setLens("a test lens");
        cameraModel.setMake("a test make");
        cameraModel.setModel("a test model");

        Camera camera = new Camera();
        camera.setCameraID(999999999L);
        camera.setCameraLocation(cameraLocation);
        camera.setCameraModel(cameraModel);

        droneInfo.setCamera(camera);

        ArrayList<Identification> arrayList = new ArrayList<>();

        Identification identification1 = new Identification();
        identification1.setHasBeenReviewed(0);
        identification1.setHumanReviewNeeded(false);
        ArrayList<Float> floatArrayList1 = new ArrayList<>();
        floatArrayList1.add(0.0f);
        floatArrayList1.add(0.1f);
        floatArrayList1.add(0.2f);
        floatArrayList1.add(0.3f);
        identification1.setScore(floatArrayList1);
        identification1.setIdType("BNPDId TEST");
        arrayList.add(identification1);

        droneInfo.setIdentifications(arrayList);
    }

    @Test
    public void input() throws Exception{
        droneIdService.setRandSeed(1234L);

        when(droneStorageService.save(any(DroneInfo.class))).then(returnsFirstArg());

        DroneInfo saved = droneIdService.input(droneInfo);

        assert (saved.getIdentifications().get(1).getScore().size() == 128);
    }
}