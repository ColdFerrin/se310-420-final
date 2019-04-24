package org.erau.icarus.detect.ES.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.erau.icarus.detect.ES.Model.DroneInfo;

import java.io.IOException;
import java.util.Optional;

public interface DroneStorageInterface {
    DroneInfo save(DroneInfo entity) throws IOException;

    Optional<DroneInfo> findOne(String id) throws IOException;

    Iterable<DroneInfo> findAllPaging() throws IOException;

    java.lang.Iterable findAllPaging(int pageSize, int pageNum) throws IOException;

    long count() throws IOException;

    void delete(DroneInfo entity) throws IOException;

    boolean existsById(String primaryKey) throws IOException;
}
