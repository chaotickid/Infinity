package com.infinity.couchbaseRefereceListMaintainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserReferenceList {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id = "UserReferenceList";

    @Field
    private String latestCount;

    @Field
    private List<String> userIdList;
}