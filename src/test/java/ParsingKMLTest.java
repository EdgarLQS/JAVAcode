import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParsingKMLTest {

    ParsingKML parsingKML = new ParsingKML();

    @Test
    public void testKML(){
        String record = parsingKML.readFile("model002", "E:\\SuperMapData\\test\\testBim", "E:\\SuperMapData\\test\\saveTest");
        System.out.println(record);
    }

}