package com.ericsson;

import com.ericsson.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * springboot集成mongo, 查询结果不封装实体, 操作mongo常用CRUD API
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoTest {

    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 多条件查询
     */
    @Test
    public void find() {
        //设置查询条件 age小于30,且person_name="张三"
        Criteria criteria = Criteria.where("age").lt(30)
                .and("person_name").is("张三");

        //设置查询条件
        Query query = new Query(criteria);
        //查询
        List<Person> list = mongoTemplate.find(query, Person.class);

        for (Person person : list) {
            System.out.println(person);
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void findPage() {
        //设置查询条件 age小于30,且person_name="张三"
        Criteria criteria = Criteria.where("age").lt(30)
                .and("person_name").is("张三");

        //根据条件 查询总数
        Query queryCount = new Query(criteria);
        long count = mongoTemplate.count(queryCount, Person.class);


        //查询当前页的数据列表, 查询第二页，每页查询2条
        Query queryLimit = new Query(criteria)
                .with(Sort.by(Sort.Order.desc("age")))
                .limit(2)//每页查询条数
                .skip(2); //从第几页开始 （page-1）*size

        List<Person> list = mongoTemplate.find(queryLimit, Person.class);
        for (Person person : list) {
            System.out.println(person);
        }
    }

    /**
     * 更新数据
     */
    @Test
    public void update() {
        //设置查询条件 age小于30,且person_name="张三"
        Criteria criteria = Criteria.where("person_name").is("王五");
        //设置更新条件
        Query query = new Query(criteria);
        //设置更新数据
        Update update = new Update();
        update.set("age", 16);
        mongoTemplate.upsert(query, update, Person.class);
    }

    /**
     * 保存
     */
    @Test
    public void save() {
        Person person = new Person();
        person.setName("张三");
        person.setAge(18);
        mongoTemplate.save(person);
    }

    /**
     * 删除数据
     */
    @Test
    public void dlete() {
        mongoTemplate.remove(Query.query(Criteria.where("person_name").is("张三")), Person.class);
    }

}
