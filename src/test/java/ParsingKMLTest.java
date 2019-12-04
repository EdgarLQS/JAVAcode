import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParsingKMLTest {

    ParsingKML parsingKML = new ParsingKML();
    String filePath = "E:\\SuperMapData\\test\\testBim";
    String saveFilePath = "E:\\SuperMapData\\test\\saveTest";

    @Test
    public void testParsingKmlToModifyS3mFileName(){
        String s = parsingKML.parsingKmlToModifyS3mFileName(filePath, saveFilePath);
        System.out.println(s);
    }

    @Test
    public void testReadS3m(){
        JSONArray jsonArray = parsingKML.readS3m("model002", saveFilePath);
        System.out.println(jsonArray);
    }

}