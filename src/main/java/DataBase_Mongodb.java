import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataBase_Mongodb {

    public static void main(String args[]) {
        /**
         * 本实例中 Mongo 数据库无需用户名密码验证。如果你的 Mongo 需要验证用户名及密码，可以使用以下代码：testMongo01方法
         */
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("127.0.0.1", 27017); // 127.0.0.1   localhost
            // 连接到数据库,指定数据库名称，如果指定的数据库不存在，mongo会自动创建数据库。创建完成后，在Compass里面刷新看不到，但是创建Collection后刷新才会出现
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test1");
            System.out.println(mongoDatabase.getName());
            System.out.println("Connect to database successfully DBName is : " + mongoDatabase.getName());
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // 安装的Mongo默认没有用户名和密码，需要自己配置，配置用户名和密码参考：https://blog.csdn.net/qq_32502511/article/details/80619277
    public void testConnectMongo01() {
        try {
            // 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            // ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("localhost", 27017);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);
            // MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName",
                    "password".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            // 通过连接认证获取MongoDB连接
            // MongoClient mongoClient = new MongoClient(addrs, credentials);
            // //过时，第二个参数不要用List
            MongoClient mongoClient = new MongoClient(addrs, credential, null);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");
            System.out.println("Connect to database successfully DBName is " + mongoDatabase.getName());
            mongoClient.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
