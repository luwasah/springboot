package com.ericsson.mongoDemo;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.function.Consumer;

/**
 * JAVA API操作MongoDB
 */
public class MongoDemo {
    public static void main(String[] args) {
        //建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://192.168.100.100:27017");
        //选择数据库
        MongoDatabase database = mongoClient.getDatabase("testdb");
        //选择数据表
        MongoCollection<Document> collection = database.getCollection("user");
        //查询数据
        FindIterable<Document> documents = collection.find().limit(10);
        documents.forEach((Consumer<? super Document>)  document -> {
            System.out.println("-----------document.toJson():" + document.toJson());;
        });
        //关闭连接
        mongoClient.close();
    }
}
