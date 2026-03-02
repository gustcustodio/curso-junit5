import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraTest {

    Calculadora calculadora = new Calculadora();

    @Test
    public void testSoma() {
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

    @Test
    public void deveRetornarNumeroInteiroNaDivisao() {
        float resultado = calculadora.dividir(6, 2);
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void deveRetornarNumeroNegativoNaDivisao() {
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
