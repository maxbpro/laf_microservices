package com.maxb.like.repository;

import com.maxb.like.LikeServiceApplication;
import com.maxb.like.domain.Task;
import com.mongodb.DBCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//@RunWith(SpringRunner.class)
//@DataMongoTest
//@SpringBootTest(classes = LikeServiceApplication.class)
public class TaskRepositoryTest {

//    String collectionName;
//    Task task = null;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//
//    @Before
//    public void before() {
//        collectionName = "logs";
//        task = new Task();
//        task.setCurrentCount(2);
//    }
//
//    @Test
//    public void checkMongoTemplate() {
//        assertNotNull(mongoTemplate);
//        DBCollection createdCollection = mongoTemplate.createCollection(collectionName);
//        assertTrue(mongoTemplate.collectionExists(collectionName));
//    }
//
//    @After
//    public void after() {
//        mongoTemplate.dropCollection(collectionName);
//    }
}
