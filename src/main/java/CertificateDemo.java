import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;

/**
 * 2019.10.29
 *
 * 具体是实现从CA证书里面提取出 ou 的属性值  为 peer
 * 然后运用到区块链存证系统里面的省市县里面的实现逻辑
 */
public class CertificateDemo {



    // 区块链证书进行解码操作
    public  void deCoding() throws IOException, CertificateException {

        String s = testOu();

        InputStream inputStream = new ByteArrayInputStream(s.getBytes());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);

        // 现在是需要获取 OID 作为新的属性值
        byte[] bytes = certificate.getExtensionValue("1.2.3.4.5.6.7.8.1");
        String s1 = String.valueOf(bytes);
        System.out.println(s1);


        Principal principal = certificate.getSubjectDN();

        String string = principal.toString();

        List<String> result = Arrays.asList(string.split(","));
        System.out.println(string);

        String textContent = result.get(1);
        textContent = textContent.trim();
        while (textContent.startsWith("　")) {
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        String OU = textContent.substring(3, textContent.length());
        System.out.println("OU = " + OU);
    }

    public String testOu() throws IOException {
//        String shpURL = this.getClass().getResource("/getInit.pem").getFile(); //最开始得到的字符串
        String shpURL = this.getClass().getResource("/test.pem").getFile(); //最开始得到的字符串

        File file = new File(shpURL);
        // 第一、获取到 CA 证书的 String
        String initString = FileUtils.readFileToString(file);
        String[] strings = initString.split("\n");
        // 写到临时文件
        FileWriter fw = new FileWriter("out.pem");
        StringBuilder stringBuilder = new StringBuilder();
        int line = 1;
        for(String s : strings){
            if( line == 1){
                fw.write("-----BEGIN CERTIFICATE-----" + "\n");             // 一行行获取数据的一种方式
                stringBuilder.append("-----BEGIN CERTIFICATE-----" + "\n");     // 因为是换行，所以采用StringBuilder追加形成
            }else{
                fw.write(s + "\n");
                stringBuilder.append(s +"\n");
            }
            line++;
        }
        fw.close();
        return stringBuilder.toString();
    }
}
