import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraTest {

    private Calculadora calculadora = new Calculadora();
    private static int contador = 0;

    @BeforeEach
    public void setUp() {
        System.out.println("^^^");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("vvv");
    }

    @BeforeAll
    public static void setUpAll() {
        System.out.println("--- Before All ---");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("--- After All ---");
    }

    @Test
    public void testSoma() {
        System.out.println(++contador);
        Assertions.assertTrue(calculadora.soma(2, 2) == 4);
    }

    @Test
    public void assertivas() {
        System.out.println(++contador);
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

    @Test
    public void deveRetornarNumeroInteiroNaDivisao() {
        System.out.println(++contador);
        float resultado = calculadora.dividir(6, 2);
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void deveRetornarNumeroNegativoNaDivisao() {
        System.out.println(++contador);
        float resultado = calculadora.dividir(6, -2);
        Assertions.assertEquals(-3, resultado);
    }

    @Test
    public void deveRetornarNumeroDecimalNaDivisao() {
        float resultado = calculadora.dividir(10, 3);
        Assertions.assertEquals(3.3333332538604736, resultado);
        Assertions.assertEquals(3.33, resultado, 0.01);
    }

    @Test
    public void deveRetornarZeroComNumeradorZeroNaDivisao() {
        float resultado = calculadora.dividir(0, 2);
        Assertions.assertEquals(0, resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoDividirPorZero_Junit4() {
        try {
            float resultado = 10 / 0;
            Assertions.fail("Deveria ter sido lançado uma exceção na divisão");
        } catch (ArithmeticException e) {
            Assertions.assertEquals("/ by zero", e.getMessage());
        }
    }

    @Test
    public void deveLancarExcecaoQuandoDividirPorZero_Junit5() {
        ArithmeticException exception = Assertions.assertThrows(ArithmeticException.class, () -> {
            float resultado = 10 / 0;
        });
        Assertions.assertEquals("/ by zero", exception.getMessage());
    }

}
