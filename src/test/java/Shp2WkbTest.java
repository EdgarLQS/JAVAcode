import org.junit.Test;
import org.locationtech.jts.geom.Geometry;

import java.io.File;
import java.util.ArrayList;

public class Shp2WkbTest {

    private String shpURL = this.getClass().getResource("/D/D.shp").getFile();
    private File shpFile = new File(shpURL);
    private Shp2Wkb shp2WKB = new Shp2Wkb(shpFile);

    @Test
    public void testShp2Wkb() {
        ArrayList<Geometry> geometryArrayList = shp2WKB.getGeometry();
        System.out.println(geometryArrayList);
    }

    @Test
    public void testSaveWKB(){
        String path = "E:\\SuperMapData\\test\\test.wkb";
        shp2WKB.save(new File(path));
    }
}

