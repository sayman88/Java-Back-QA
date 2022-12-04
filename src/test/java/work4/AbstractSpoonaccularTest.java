package work4;

import net.javacrumbs.jsonunit.JsonAssert;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AbstractSpoonaccularTest {

    public void assertJson(Object expected, Object actually) {
        JsonAssert.assertJsonEquals(
                expected,
                actually
        );
    }

    public String getResource(String name) throws IOException {
        String resource = getClass().getSimpleName() + "/" + name;
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public File getFile(String name) {
        String resource = getClass().getSimpleName() + "/" + name;
        String file = getClass().getResource(resource).getFile();
        return new File(file);
    }
}
