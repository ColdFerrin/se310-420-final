package org.erau.icarus.detect.ES;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface DroneRepository extends ElasticsearchCrudRepository<DroneInfo, Long> {
}
