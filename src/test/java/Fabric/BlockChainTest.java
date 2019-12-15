package Fabric;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class BlockChainTest {

    private BlockChain client;
    private File networkFile = new File(this.getClass().getResource("/network-config-test.yaml").toURI());

    public BlockChainTest() throws URISyntaxException {
        client = new BlockChain(networkFile);
    }



    @Test
    public void testPut(){
        String key = "key";
        String value = "value";
        String result = client.putRecord(
                key,
                value,
                "bcgiscc"
        );
        System.out.println(result);
    }

    @Test
    public void testGet(){
        String key = "key";
        String result = client.getRecord(
                key,
                "bcgiscc"
        );
        System.out.println(result);
    }
}