import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraTest {

    @Test
    public void testSoma() {
        Calculadora calculadora = new Calculadora();
        Assertions.assertTrue(calculadora.soma(2, 2) == 4);
    }

    @Test
    public void assertivas() {
        Assertions.assertEquals("Casa", "Casa");
        Assertions.assertNotEquals("Casa", "casa");
        Assertions.assertTrue("casa".equalsIgnoreCase("CASA"));
        Assertions.assertTrue("Casa".endsWith("sa"));
        Assertions.assertTrue("Casa".startsWith("Ca"));

        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = null;

        Assertions.assertEquals(list1, list2);
        Assertions.assertSame(list1, list1);
        Assertions.assertNotEquals(list1, list3);
        Assertions.assertNull(list3);
        Assertions.assertNotNull(list1);
    }

}
