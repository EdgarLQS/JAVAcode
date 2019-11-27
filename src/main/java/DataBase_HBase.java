import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;


public class DataBase_HBase{

    private static Admin admin = null;
    private static Connection connection = null;
    private static Configuration configuration = null;
    static {
        // HBase配置文件
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "192.168.40.156");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        //获取连接对象
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 连接后关闭资源
    private static void close(Connection conn,Admin admin){
        if(conn != null){
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(admin != null){
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 判断表存在
    public static boolean tableExist(String tablename) throws IOException {
        // HBase配置文件
//        configuration.set("hbase.zookeeper.quorum", "192.168.40.156");
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        //获取连接对象
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        boolean tableExists = admin.tableExists(TableName.valueOf(tablename));
        //获取HBASE管理员对象
//        HBaseAdmin admin =  new HBaseAdmin(configuration);
        //执行
//        boolean tableExists = admin.tableExists(tablename);
        //关闭
        admin.close();
        return tableExists;
    }

    //    创建表(表名和列族) ---后面是可以设置多个字符串，即多个列族
    public static void creatTable(String tableName,String...cfs) throws IOException {
        //判断表存在
        if(tableExist(tableName)){
            System.out.println("表存在");
            return;
        }
        //创建表描述器
        HTableDescriptor hTableDescriptor =  new HTableDescriptor(TableName.valueOf(tableName));
        //添加列族
        for(String cf:cfs){
            //创建。列描述器
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        //创建表操作
        admin.createTable(hTableDescriptor);
        System.out.println("创建表成功");
    }

    //    删除表(先disable后delete)
    public static void deleteTable(String tableName)  {
        try{
            //判断表存在
            if(!tableExist(tableName)){
                return;
            }
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        }catch (IOException e){
        }
        System.out.println("表已删除");
    }

    // 增、改(表名、行名、列族、列名、数据)
    public static void putData(String tableName,String rowKey,String cf,String cn,byte[] value){
        try {
            //获取白表对象
//            HTable table = new HTable(configuration, TableName.valueOf(tableName));
            Table table =  connection.getTable(TableName.valueOf(tableName));
            //创建put对象
            Put put = new Put(Bytes.toBytes(rowKey));
            //添加数据
            put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn),Bytes.toBytes(ByteBuffer.wrap(value)));
            //执行操作
            table.put(put);
            table.close();
        }catch (IOException e){
        }
    }

    //删除表里面的内容
    public static void delete(String tableName,String rowKey,String cf,String cn){
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            //创建delete对象
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            //删除到指定的列和值
            delete.addColumns(Bytes.toBytes(cf),Bytes.toBytes(cn));
            table.delete(delete);
            table.close();
        }catch (IOException e){
        }
    }

    //查==全表扫描
    public static void scanTable(String tableName){
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            //设置扫描范围
//            scan.setStartRow()
            ResultScanner results = table.getScanner(scan);
            //遍历数据打印(一个rowKey下多个列，多个版本)
            for(Result result:results ){
                Cell[] cells = result.rawCells();
                for(Cell cell:cells){
                    System.out.println("RK:" + Bytes.toString(CellUtil.cloneRow(cell))
                            +",CF:" + Bytes.toString(CellUtil.cloneFamily(cell) )
                            +",CN:" + Bytes.toString(CellUtil.cloneQualifier(cell))
                            +",VALUE:" + Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            table.close();
        }catch (IOException e){
        }
    }

    //获取指定列族，列的数据
    public static byte[] getData(String tableName,String rowKey,String cf,String cn){
        byte[] Value = null;
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            // 创建get对象
            Get get = new Get(Bytes.toBytes(rowKey));
            //指定到列族或列
            get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn));
            //获取最新的版本
//            get.setMaxVersions();
            Result results = table.get(get);
            Cell[] cells = results.rawCells();
            for(Cell cell:cells){
                Value = CellUtil.cloneValue(cell);
//                System.out.println("RK:" + Bytes.toString(CellUtil.cloneRow(cell))
//                        +",CF:" + Bytes.toString(CellUtil.cloneFamily(cell) )
//                        +",CN:" + Bytes.toString(CellUtil.cloneQualifier(cell))
//                        +",VALUE:" + Bytes.toString(CellUtil.cloneValue(cell)));
            }
            table.close();
        }catch (IOException e){
        }
        return Value;
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(tableExist("bcgis_test"));
//        creatTable("bcgis_test","info");
//        deleteTable("test");
//        scanTable("bcgis_test");

        //上传数据
        String upload_path = "E:\\DemoRecording\\File_storage\\JerseyTest\\test1.jpg";
        byte[] data =  new Utils().fileToByte(upload_path);
        putData("bcgis_test","1002","info","name",data);
        //获取数据并保存到本地
        byte[] data_load = getData("bcgis_test","1002","info","name");
        String download_path = "E:\\DemoRecording\\File_storage\\JerseyTest\\HBase_download.jpg";
        OutputStream out = new FileOutputStream(download_path);
        out.write(data_load);
        out.close();
        close(connection,admin);
    }
}







