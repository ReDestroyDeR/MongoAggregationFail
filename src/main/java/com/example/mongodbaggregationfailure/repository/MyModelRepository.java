package com.example.mongodbaggregationfailure.repository;

import com.example.mongodbaggregationfailure.domain.MyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyModelRepository extends MongoRepository<MyModel, String> {
}
