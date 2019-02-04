package org.erau.icarus.detect.Services.DroneId;

import org.erau.icarus.detect.ES.DroneRepository;
import org.erau.icarus.detect.Services.Classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneIdService {

    private DroneRepository droneRepository;

    private ClassificationService classificationService;

    @Autowired
    public DroneIdService(DroneRepository droneRepository, ClassificationService classificationService) {
        this.droneRepository = droneRepository;
        this.classificationService = classificationService;
    }
}
