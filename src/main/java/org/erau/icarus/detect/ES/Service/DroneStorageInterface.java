package org.erau.icarus.detect.ES.Service;

import org.erau.icarus.detect.ES.Model.DroneInfo;

import java.util.Optional;

public interface DroneStorageInterface {
    DroneInfo save(DroneInfo entity);

    Optional<DroneInfo> findByID(String id);

    Iterable<DroneInfo> findAllPaging(int pageSize, int pageNum);

    long count();

    void delete(DroneInfo entity);

    boolean existsById(String primaryKey);
}
