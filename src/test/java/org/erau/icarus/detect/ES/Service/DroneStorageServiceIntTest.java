package org.erau.icarus.detect.ES.Service;

import org.erau.icarus.detect.ES.Model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DroneStorageServiceIntTest {

    private static DroneStorageService droneStorageService;

    private static DroneInfo droneInfo;

    private static DroneInfo savedInfo;

    private static String id;

    @BeforeClass
    public static void setUp() throws Exception {
        String[] testHosts = {"localhost:9200"};
        droneStorageService = new DroneStorageService(testHosts);

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
    public void saveTest() throws Exception {
        DroneInfo save = droneStorageService.save(droneInfo);

        id = save.getId();
        savedInfo = save;
        assert save.getCamera().getCameraID().equals(droneInfo.getCamera().getCameraID());
    }

    @Test
    public void findOneTest() throws Exception {
        Optional<DroneInfo> infoOptional = droneStorageService.findOne(id);

        assert infoOptional.isPresent();
    }

    @Test
    public void findAllPagingTest() throws Exception {
        ArrayList<DroneInfo> allPaging = (ArrayList<DroneInfo>) droneStorageService.findAllPaging();

        assert allPaging.size() <= 10;
    }

    @Test
    public void countTest() throws Exception {
        long count = droneStorageService.count();

        assert count > 0;
    }

    @Test
    public void existsByIdTest() throws Exception {
        boolean entryExists = droneStorageService.existsById(id);

        assert entryExists;
    }

    @Test
    public void deleteTest() throws Exception {
        String deleted = droneStorageService.delete(id);

        assert deleted.equals(id);
    }
}
