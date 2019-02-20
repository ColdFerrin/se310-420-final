package org.erau.icarus.detect.Services.Classification;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {

    private DroneStorageService droneStorageService;

    @Autowired
    public ClassificationService(DroneStorageService droneStorageService) {
        this.droneStorageService = droneStorageService;
    }
}
