import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

// https://www.jianshu.com/p/046ed8cac2f1
public class Test_Hbase {

    private Connection connection;
    private HTable table;
    HBaseAdmin admin;

    @Before
    public void init() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        //设置zookeeper的地址，可以有多个，以逗号分隔
        configuration.set("hbase.zookeeper.quorum","master");
        //设置zookeeper的端口
        configuration.set("hbase.zookeeper.property.clientPort","2181");
        //创建hbase的连接，这是一个分布式连接
        connection = ConnectionFactory.createConnection(configuration);
        //获取hbase中的表
        table = (HTable) connection.getTable(TableName.valueOf("user"));
        //这个admin是管理table时使用的，比如说创建表
        admin = (HBaseAdmin) connection.getAdmin();
    }

    /**
     * 创建表，创建表只需要指定列族，不需要指定列
     * 其实用命令真的会更快，create 'user','info1','info2'
     */
    @Test
    public void createTable() throws IOException {
        //声明一个表名
        TableName tableName = TableName.valueOf("user_test");
        //构造一个表的描述
        HTableDescriptor desc = new HTableDescriptor(tableName);
        //创建列族
        HColumnDescriptor family1 = new HColumnDescriptor("info1");
        HColumnDescriptor family2 = new HColumnDescriptor("info2");
        //添加列族
        desc.addFamily(family1);
        desc.addFamily(family2);
        //创建表
        admin.createTable(desc);
    }

    /**
     * 添加数据
     * 对同一个row key进行重新put同一个cell就是修改数据
     */
    /**
     * 添加数据
     * 对同一个row key进行重新put同一个cell就是修改数据
     */
    @Test
    public void insertUpdate() throws IOException {
        //构造参数是row key，必传
        for(int i = 0 ; i < 100 ; i ++){
            Put put = new Put(Bytes.toBytes("zhangsan_123" + i));
            //put.add()已经被弃用了
            //这里的参数依次为，列族名，列名，值
            put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("name"),Bytes.toBytes("lisi" + i));
            put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("age"),Bytes.toBytes(22 + i));
            put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("sex"),Bytes.toBytes("男"));
            put.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("address"),Bytes.toBytes("天堂" + i));
            table.put(put);
            //table.put(List<Put>); //通过一个List集合，可以添加一个集合
        }
    }

    /**
     * 删除数据
     */
    @Test
    public void delete() throws IOException {
        Delete deleteRow = new Delete(Bytes.toBytes("zhangsan_1235")); //删除一个行
        Delete delete = new Delete(Bytes.toBytes("zhangsan_1235"));
        delete.addFamily(Bytes.toBytes("info1"));//删除该行的指定列族
        delete.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("name"));//删除指定的一个单元
        table.delete(deleteRow);
        //table.delete(List<Delete>); //通过添加一个list集合，可以删除多个
    }
}

