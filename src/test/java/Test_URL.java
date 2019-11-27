import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

public class Test_URL {

    // 在Factory中关于绝对路径和相对路径的测试分析
    @Test
    public void testURI() throws MalformedURLException {
//        // File.separator 表示//的意思
        // 相对路径分析
        System.out.println("==============相对路径分析");
        String str = "data\\atlchain-sdk-0.0.3\\network-config-test.yaml";
        String string = "data_dir" + File.separator +str;
        File file1 = new File(string);
        if(!file1.exists()) {
            System.out.println(file1.toURI());
//            System.out.println(file1.toURL());
            System.out.println(file1.toPath().toUri());
        }

        System.out.println("==============绝对路径分析");
        // 绝对路径分析
        String str1 = "D:\\Program Files (x86)\\GeoServer 2.15.0\\data_dir\\data\\atlchain-sdk-0.0.3\\network-config-test.yaml";
        File file = new File(str1);
        if(file.exists()) {
            System.out.println(file.toURI());
//            System.out.println(file.toURL());
            System.out.println(file.toPath().toUri());
        }
    }







}
