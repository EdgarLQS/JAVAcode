
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class readKMLTest {

    readKML readKml = new readKML();

    // 传入的对象为 file 地址
    private File file = new File("E:\\SuperMapData\\testS3m\\1.kml");

    /**
     * 解析 XML 数据
     * @return JSONObject
     */
    @Test
    public void testResdKML(){
        File file = new File("E:\\SuperMapData\\test\\ExportModel.kml");
        List<String> list = new ArrayList();
        list.add("Placemark");
        list.add("Location");
        list.add("Scale");
        list.add("Link");
        JSONObject jsonObject =  readKml.creatNodeList(file, list);
        System.out.println(jsonObject);
    }

    /**
     * 获取某路径下的 XML 文件名
     * @return ArrayList<String>
     */
    @Test
    public void testGetXmlFileName(){
        File file = new File("E:\\SuperMapData\\testS3m");
        // D:\Tomcat\tomcat\apache-tomcat-9.0.27\webapps\s3m\examples\SampleData\models\test_stair-bim      E:\SuperMapData\testS3m
        List<String> listKml = readKml.getXmlFileName(file);
        List<String> listS3m = readKml.getS3mFileName(file);
        System.out.println("num: " + listKml.size() + "listKml: " + listKml);
        System.out.println("num: " + listS3m.size() + "listS3m: " + listS3m);
    }

    // TODO 将获取文件名和解析结合 得到 坐标 ，然后保存为文件
    @Test
    public void testGetInformationToFile(){
        // 第一步 获取 kml 文件名作为一个列表
        File file = new File("D:\\Tomcat\\tomcat\\apache-tomcat-9.0.27\\webapps\\s3m\\examples\\SampleData\\models\\testBim");
        List<String> listKml = readKml.getXmlFileName(file);
        StringBuilder s3mFileName = new StringBuilder();
        StringBuilder latLonInformation = new StringBuilder();
        // 解析 kml 文件得到 经纬度信息 和 对应的 s3m 文件信息
        for(int i = 0; i < listKml.size(); i ++){
            List<String> tempList = new ArrayList();
            tempList.add("Link");
            tempList.add("Location");
            File tempFile = new File(listKml.get(i));
            JSONObject jsonObject =  readKml.creatNodeList(tempFile, tempList);

            String name = jsonObject.getString("href");
            String newName = name.replace("#", "%23");
            System.out.println(newName);

            String strPath = "'" + "./SampleData/models/testBim" +  newName.substring(1, newName.length()) + "'"  + "," + "\n";
            s3mFileName.append(strPath);
            System.out.println("====" + strPath);

            String lon = jsonObject.getString("longitude");
            String lat = jsonObject.getString("latitude");
            latLonInformation.append( "[" + lon + ", " + lat + ", " +   4101973.0066891187 + "]" + "," + "\n");
        }
        // 保存路径
        Utils.writeStringToFile(s3mFileName.toString(), "D:\\Tomcat\\tomcat\\apache-tomcat-9.0.27\\webapps\\s3m\\examples\\SampleData\\models\\s3mFileName.txt");
        Utils.writeStringToFile(latLonInformation.toString(), "D:\\Tomcat\\tomcat\\apache-tomcat-9.0.27\\webapps\\s3m\\examples\\SampleData\\models\\latLon.txt");
    }

    /**
     * 获取某路径下的 s3m 文件名
     */
    @Test
    public void testGetS3mFileName(){
        File file = new File("D:\\Tomcat\\tomcat\\apache-tomcat-9.0.27\\webapps\\s3m\\examples\\SampleData\\models\\testBim");
        String [] fileName = file.list();
        StringBuilder s3mFileName = new StringBuilder();
        for(String path : fileName ){
            path = path.replace("#", "%23");
            String strPath = "'" + "./SampleData/models/testBim/" +  path + "'"  + "," + "\n";
            s3mFileName.append(strPath);
        }
//        System.out.println(s3mFileName);
        Utils.writeStringToFile(s3mFileName.toString(), "D:\\Tomcat\\tomcat\\apache-tomcat-9.0.27\\webapps\\s3m\\examples\\SampleData\\models\\s3mFileName.txt");
    }

}