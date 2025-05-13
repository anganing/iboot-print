package features;

import com.iboot.io.print.App;

import org.junit.jupiter.api.Test;

import org.noear.solon.test.HttpTester;
import org.noear.solon.test.SolonTest;

import java.io.IOException;

@SolonTest(App.class)
class HelloTest extends HttpTester {
    @Test
    void hello() {
        assert path("/hello?name=world").get().contains("world");
        assert path("/hello?name=solon").get().contains("solon");
    }
}