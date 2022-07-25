package com.example.mongodbaggregationfailure.repository;

import com.example.mongodbaggregationfailure.domain.MyModel;
import com.example.mongodbaggregationfailure.domain.MyModelContainer;
import com.example.mongodbaggregationfailure.enums.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@DataMongoTest
class MyModelContainerRepositoryTest {
    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @Autowired
    MyModelContainerRepository containerRepository;

    @Autowired
    MyModelRepository modelRepository;

    @BeforeEach
    void setUp() {
        var container1 = containerRepository.save(new MyModelContainer("group 1"));
        var container2 = containerRepository.save(new MyModelContainer("group 1"));
        var container3 = containerRepository.save(new MyModelContainer("group 1"));

        // Container 1 = 3 Active models & 2 Inactive models
        var activeModel11 = modelRepository.save(new MyModel(container1.getId(), Status.ACTIVE));
        var activeModel12 = modelRepository.save(new MyModel(container1.getId(), Status.ACTIVE));
        var activeModel13 = modelRepository.save(new MyModel(container1.getId(), Status.ACTIVE));
        var inactiveModel11 = modelRepository.save(new MyModel(container1.getId(), Status.NOT_ACTIVE));
        var inactiveModel12 = modelRepository.save(new MyModel(container1.getId(), Status.NOT_ACTIVE));

        // Container 2 = 2 Active models & 3 Inactive models
        var activeModel21 = modelRepository.save(new MyModel(container2.getId(), Status.ACTIVE));
        var activeModel22 = modelRepository.save(new MyModel(container2.getId(), Status.ACTIVE));
        var inactiveModel21 = modelRepository.save(new MyModel(container2.getId(), Status.NOT_ACTIVE));
        var inactiveModel22 = modelRepository.save(new MyModel(container2.getId(), Status.NOT_ACTIVE));
        var inactiveModel23 = modelRepository.save(new MyModel(container2.getId(), Status.NOT_ACTIVE));

        // Container 3 = 0 Active models & 4 Inactive models
        var inactiveModel31 = modelRepository.save(new MyModel(container3.getId(), Status.NOT_ACTIVE));
        var inactiveModel32 = modelRepository.save(new MyModel(container3.getId(), Status.NOT_ACTIVE));
        var inactiveModel33 = modelRepository.save(new MyModel(container3.getId(), Status.NOT_ACTIVE));
        var inactiveModel34 = modelRepository.save(new MyModel(container3.getId(), Status.NOT_ACTIVE));
    }

    @AfterEach
    void tearDown() {
        modelRepository.deleteAll();
        containerRepository.deleteAll();
    }

    @Test
    void workaroundTest() {
        List<MyModelContainer> containers =
                containerRepository.findAllByGroupIdCountingStatus("group 1", Status.ACTIVE.name());

        containers.forEach(System.out::println);
    }

    @Test
    void desiredBehaviourTest() {
        List<MyModelContainer> containers =
                containerRepository.desiredFindAllByGroupIdCountingStatus("group 1", Status.ACTIVE.name());

        containers.forEach(System.out::println);
    }
}