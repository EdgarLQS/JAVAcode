
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 参考地址：https://blog.csdn.net/m0_37499059/article/details/80505567   参考<创建DocumentBuilderFactory对象>解析 XML 数据
 *
 * 做法
 *      1、将 kml 数据当成 XML 数据来解析
 *      2、获取属性里面的 name 数据 和 经纬度信息
 *      3、后期有需求再把数据加上去
 */
public class readKML {

    public JSONObject creatNodeList(File file, List<String> list){
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < list.size(); i++) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            NodeList nodeList = null;
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document d = builder.parse(file);
                nodeList = d.getElementsByTagName(list.get(i));
                JSONObject jsonTemp = node(nodeList);
                jsonObject.putAll(jsonTemp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public JSONObject node(NodeList list) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        jsonObject.put(childNodes.item(j).getNodeName(), childNodes.item(j).getFirstChild().getNodeValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonObject;
    }

    /**
     * 对 xml 文件进行判断 获取文件名
     * @param file
     * @return
     */
    public List<String> getXmlFileName(File file){

        List<String> listKml = new ArrayList<>();
        List<String> list = Arrays.asList(file.list());
        String fileExtName;
        for(int i = 0; i < list.size() ; i++){
            String tempFileName = list.get(i);
            fileExtName = tempFileName.substring(tempFileName.lastIndexOf('.') + 1, tempFileName.length()).toString();
            if(fileExtName.equals("kml")){
                listKml.add(file.toString() + File.separator + list.get(i));
            }
        }
        return listKml;
    }

    /**
     * 对 s3m 文件进行判断 获取某路径下的文件名的绝对路径
     * @param file
     * @return
     */
    public List<String> getS3mFileName(File file){

        List<String> listS3m = new ArrayList<>();
        List<String> list = Arrays.asList(file.list());
        String fileExtName;
        for(int i = 0; i < list.size() ; i++){
            String tempFileName = list.get(i);
            fileExtName = tempFileName.substring(tempFileName.lastIndexOf('.') + 1, tempFileName.length()).toString();
            if(fileExtName.equals("s3m")){
                listS3m.add( file.toString() + File.separator + list.get(i));
            }
        }
        return listS3m;
    }

}
