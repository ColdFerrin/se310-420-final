package org.erau.icarus.detect.Services.BNPId;

import org.erau.icarus.detect.ES.DroneRepository;
import org.erau.icarus.detect.Services.Classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNPIdService {

    private DroneRepository droneRepository;

    private ClassificationService classificationService;

    @Autowired
    public BNPIdService(DroneRepository droneRepository, ClassificationService classificationService) {
        this.droneRepository = droneRepository;
        this.classificationService = classificationService;
    }
}
