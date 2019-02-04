package org.erau.icarus.detect.Services.Classification;

import org.erau.icarus.detect.ES.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {

    private DroneRepository droneRepository;

    @Autowired
    public ClassificationService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }
}
