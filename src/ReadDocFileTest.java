import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReadDocFileTest {

    @Test
    public void compareDocuments() throws Exception {

        ReadDocFile r = new ReadDocFile();

        r.readInputFile("/tmp/1.txt", "/tmp/2.txt" );
        r.readResultFile("/tmp/3.txt");
        assertTrue(r.compareDocuments());

    }


}