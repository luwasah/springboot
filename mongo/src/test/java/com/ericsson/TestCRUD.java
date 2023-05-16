package com.ericsson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;

/**
 * 手动连接mongo并操作CRUD API
 */
public class TestCRUD {
    private MongoCollection mongoCollection;

    @Before
    public void init() {
        // 建立连接
        MongoClient mongoClient =
                MongoClients.create("mongodb://192.168.100.100:27017");
        // 选择数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb");
        // 选择表
        this.mongoCollection = mongoDatabase.getCollection("user");
    }

    /**
     * 查询age<=100并且id>=81的用户信息，并且按照id倒序排序，只返回id，age字段，不返回_id字段
     */
    @Test
    public void testQuery() {
        this.mongoCollection.find(
                        //查询条件
                        Filters.and(
                                lte("age", 100), //age <= 100
                                gte("id", 81)  //id >= 81
                        )
                )
                //排序
                .sort(Sorts.descending("id"))   //按id倒序排序
                //过滤字段
                .projection(
                        Projections.fields(
                                Projections.include("id","age"),   //需要查询的字段
                                Projections.excludeId()                      //排除的字段
                        )
                )
                .forEach((Consumer<? super Document>) document -> {
                    System.out.println("----------------document.toJson(): " + document.toJson());
                });
    }

    /**
     * 测试插入数据: 单条插入 & 多条插入
     */
    @Test
    public void testInsert(){
//        Document document = new Document("id", 1001);
//        document.append("username", "name_" + 1001);
//        document.append("age", 1011);
//        this.mongoCollection.insertOne(document);   //插入一条数据

        List<Document> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Document doc = new Document("id", 1001+i);
            doc.append("username", "name_" + (1001+i));
            doc.append("age", 1001+i);
            list.add(doc);
        }
        this.mongoCollection.insertMany(list);

    }

    /**
     * 测试更新: 根据条件更新一条, 更新多条
     */
    @Test
    public void testUpdate(){
        //更新一条
//        UpdateResult updateResult = this.mongoCollection.updateOne(eq("id", 1006), Updates.set("age", 9999));
//        System.out.println("-----updateResult: " + updateResult);

        //更新多条
        UpdateResult updateResult = this.mongoCollection.updateMany(eq("age", 9999), Updates.set("username", "tom"));
        System.out.println("-----updateResult: " + updateResult);
    }

    
    /**
     * 测试删除: 根据条件删除1条 & 多条
     */
    @Test
    public void testDelete(){
        //删除1条
//        DeleteResult deleteResult = this.mongoCollection.deleteOne(eq("id", 1004));
//        System.out.println("-----deleteResult: " + deleteResult);

        //删除多条
        DeleteResult deleteResult = this.mongoCollection.deleteMany(eq("username", "tom"));
        System.out.println("-----deleteResult: " + deleteResult);
    }


}
