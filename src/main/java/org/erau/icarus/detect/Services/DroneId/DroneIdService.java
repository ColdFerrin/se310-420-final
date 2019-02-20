package org.erau.icarus.detect.Services.DroneId;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.Classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneIdService {

    private DroneStorageService droneStorageService;

    private ClassificationService classificationService;

    @Autowired
    public DroneIdService(DroneStorageService droneStorageService, ClassificationService classificationService) {
        this.droneStorageService = droneStorageService;
        this.classificationService = classificationService;
    }
}
