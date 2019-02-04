package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.Services.Classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassificationController {

    private ClassificationService classificationService;

    @Autowired
    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }
}
