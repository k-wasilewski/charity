package pl.coderslab.charity.repos;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.*;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.Category;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    private Category category;
    String name = "test category";

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer();

    @BeforeClass
    public static void setKafkaContainerName() {
        kafkaContainer.setNetworkAliases(Arrays.asList("kafka"));
        KafkaConsumerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaProducerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaTopicConfig.setUrl(kafkaContainer.getBootstrapServers());
    }

    @Before
    public void init() {
        //given
        category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Test
    @Transactional
    public void findOne() {
        //given
        int numberOfRows = categoryRepository.findAll().size();

        //when
        Category savedCategory =
                categoryRepository.findById(numberOfRows).get();

        //then
        assertEquals(category, savedCategory);
    }
}

