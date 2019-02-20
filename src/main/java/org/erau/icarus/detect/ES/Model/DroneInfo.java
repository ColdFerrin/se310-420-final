package org.erau.icarus.detect.ES.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "DroneInfo", refreshInterval = "5s")
public class DroneInfo {
    @Id
    private String id;

    @Field(type = FieldType.Date)
    private Date timestamp;

    
}
