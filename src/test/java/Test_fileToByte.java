import org.junit.Test;

import java.io.File;

public class Test_fileToByte {

    File file = new File("E:\\DemoRecording\\test.jpg");


    @Test
    public void testImageChangeToByte(){
        // 图片转byte数组
        String path = "E:\\DemoRecording\\test.jpg";
        fileToByte fileToByte = new fileToByte();
        byte[] data = fileToByte.imageTobyte(path);
        System.out.println(data);

        // byte数组转图片
        String newpath = "E:\\DemoRecording\\test1.jpg";
        fileToByte.byteToimage(data,newpath);
    }
}
