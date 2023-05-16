package com.ericsson;

import com.ericsson.entity.Address;
import com.ericsson.entity.Person;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * springboot集成mongo, 并以面向对象的方式操作MongoDB
 */
public class TestPerson {

    MongoCollection<Person> mongoCollection;

    @Before
    public void init(){
        //定义对象的解码注册器
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        //建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.100.100:27017");
        //选择数据库
        MongoDatabase database = mongoClient.getDatabase("testdb").withCodecRegistry(codecRegistry);
        //选择表
        this.mongoCollection = database.getCollection("person", Person.class);
    }

    /**
     * 测试插入: 插入单条 & 插入多条
     */
    @Test
    public void testInsert(){
        //插入一条
        Person person = new Person();
        person.setId(ObjectId.get());
        person.setName("jerry");
        person.setAge(16);
        Address addr = new Address("人民路", "上海市", "666666");
        person.setAddress(addr);
        this.mongoCollection.insertOne(person);

        //插入多条
        List<Person> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Person p = new Person(ObjectId.get(), "tom_"+i, 20+i, new Address("汤姆路", "汤姆市", "666666_" + i));
            list.add(p);
        }
        this.mongoCollection.insertMany(list);
    }

    /**
     * 测试更新: 更新一条, 更新多条
     */
    @Test
    public void testUpdate(){
        //更新一条
//        UpdateResult updateResult = this.mongoCollection.updateOne(eq("name", "tom_2"), Updates.set("age", 888));
//        System.out.println("-----updateResult: " + updateResult);

        //更新多条
        UpdateResult updateResult = this.mongoCollection.updateMany(eq("age", 888), Updates.set("address", new Address("汤姆路-U", "汤姆市-U", "666666-U")));
        System.out.println("-----updateResult: " + updateResult);
    }

    /**
     * 测试删除: 删除一条 & 删除多条
     */
    @Test
    public void delete(){
        //删除1条
//        DeleteResult deleteResult = this.mongoCollection.deleteOne(eq("name", "tom_2"));
//        System.out.println("-----deleteResult: " + deleteResult);

        //删除多条
        DeleteResult deleteResult = this.mongoCollection.deleteMany(eq("age", 16));
        System.out.println("-----deleteResult: " + deleteResult);
    }

}
