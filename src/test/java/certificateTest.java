import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;

public class certificateTest {

    CertificateDemo certificateDemo = new CertificateDemo();
    @Test
    public void testCertificate() throws IOException, CertificateException {
        certificateDemo.deCoding();
    }

    @Test
    public void testOU() throws IOException {
        certificateDemo.testOu();
    }
}