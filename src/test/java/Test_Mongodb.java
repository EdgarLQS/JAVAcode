
import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.bson.Document;
import org.bson.types.Binary;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test_Mongodb {

    // 使用com.mongodb.client.MongoDatabase类的 getCollection() 方法来获取一个集合
    @Test
    public void testGetCollection() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017); // localhost
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("testLQS");
            System.out.println("Connect to database successfully =====" + mongoDatabase.getName());
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");
            System.out.println("======Collection ：" + collection.getNamespace());
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // 使用com.mongodb.client.MongoCollection类的 insertMany() 方法来插入一个文档

    /**
     * 往mongodb数据库中增加新的文档
     */
    @Test
    public void testInsertDoc() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("Test_bcgis");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> collection = mongoDatabase.getCollection("Test1");
            System.out.println("=========Collection ：" + collection.getNamespace());
            // 插入文档
            /**
             * 1. 创建文档 org.bson.Document 参数为key-value的格式 2. 创建文档集合List<Document> 3.
             * 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
             * mongoCollection.insertOne(Document)
             */
            // 新加  加入一个byte数组
            String path = "E:\\DemoRecording\\File_storage\\JerseyTest\\test1.jpg";
            fileToByte fileToByte = new fileToByte();
            byte[] data = fileToByte.imageTobyte(path);

            Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100)
                    .append("by", "CUIT").append("byte",data);
            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // 使用 com.mongodb.client.MongoCollection 类中的 find()
    // 方法来获取集合中的所有文档。此方法返回一个游标，所以你需要遍历这个游标
    /**
     * 连接到mongodb数据库 根据数据库和集合找到需要下载的文件 然后保存到自定义的地址
     */
    @Test
    public void testFind() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
            // 连接到数据库（数据库 + 集合）
            MongoDatabase mongoDatabase = mongoClient.getDatabase("Test_bcgis");
            MongoCollection<Document> collection = mongoDatabase.getCollection("Test1");

            //1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3. 通过游标遍历检索出的文档集合
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                Document doc1 = mongoCursor.next();
                Binary doc2 = (Binary) doc1.get("byte");
                byte[] data1 = doc2.getData();
                System.out.println(doc2);
                Files.write(Paths.get("E:\\DemoRecording\\File_storage\\JerseyTest\\mongodbNew.jpg"), data1);
            }
            mongoClient.close();
        } catch (Exception e) {
        }
    }

    /**
     *  updateMany() 方法来更新集合中的文档
     */
    @Test
    public void testUpdate() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("Test_bcgis");
            System.out.println("Connect to database successfully");
            MongoCollection<Document> collection = mongoDatabase.getCollection("test1");
            System.out.println("Connect to collection successfully");

            // 更新文档 将文档中likes=100的文档修改为likes=200
            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
            // 检索查看结果
            FindIterable<Document> findIterable = collection.find();
            // Apache Collections
            IteratorUtils.toList(findIterable.iterator()).forEach(System.out::println);
            IterableUtils.toList(findIterable).forEach(System.out::println);
            // Google guava 或者 Google Collections【前者包含后者，提供的工具类更多】
            Lists.newArrayList(findIterable).forEach(System.out::println);
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // 要删除集合中的第一个文档，首先你需要使用com.mongodb.DBCollection类中的
    // findOne()方法来获取第一个文档，然后使用remove 方法删除。
    @Test
    public void testDelete() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("Test_bcgis");
            MongoCollection<Document> collection = mongoDatabase.getCollection("Test1");

            // 删除符合条件的第一个文档
//            collection.deleteOne(Filters.eq("likes", 200));
            // 删除所有符合条件的文档
            collection.deleteMany(Filters.eq("ID", "66f1519a16fab0795cfa5ef9b5027dea420899a5b8cefd8506bfd455939f925f"));
            // 检索查看结果
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            mongoCursor.forEachRemaining(System.out::println);
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
