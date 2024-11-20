import br.ifpe.edu.br.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BancoTest {

    @Test
    public void testCriarContaPoupanca() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("Joana", 123456780, conta);

        Cliente cliente = banco.procurarClientePorCPF(123456780);
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Joana", cliente.getNome());
        Assertions.assertEquals(ContaTipo.POUPANCA, cliente.getConta().getTipoConta());
    }

    @Test
    public void testDepositarValorCorrente() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Marcelo", 987654320, conta);

        banco.creditarValor(987654320, 700.0, ContaTipo.CORRENTE);
        double saldo = banco.consultarSaldo(987654320, ContaTipo.CORRENTE);
        Assertions.assertEquals(700.0, saldo, 0);
    }

    @Test
    public void testDebitarValorPoupanca() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("Patrícia", 556677889, conta);

        banco.creditarValor(556677889, 400.0, ContaTipo.POUPANCA);
        banco.debitarValor(556677889, 150.0, ContaTipo.POUPANCA);
        double saldo = banco.consultarSaldo(556677889, ContaTipo.POUPANCA);
        Assertions.assertEquals(250.0, saldo, 0);
    }

    @Test
    public void testTransferirValorEntreTipos() {
        Banco banco = new Banco("Banco Teste");
        Conta contaOrigem = new Conta(1, ContaTipo.CORRENTE);
        Conta contaDestino = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("Ricardo", 123456781, contaOrigem);
        banco.criarConta("Sandra", 987654322, contaDestino);

        banco.creditarValor(123456781, 800.0, ContaTipo.CORRENTE);
        banco.transferirValor(123456781, ContaTipo.CORRENTE, 300, 987654322, ContaTipo.POUPANCA);

        double saldoOrigem = banco.consultarSaldo(123456781, ContaTipo.CORRENTE);
        double saldoDestino = banco.consultarSaldo(987654322, ContaTipo.POUPANCA);

        Assertions.assertEquals(500.0, saldoOrigem, 0);
        Assertions.assertEquals(300.0, saldoDestino, 0);
    }

    @Test
    public void testConsultarSaldoCorrente() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Beatriz", 223344556, conta);

        banco.creditarValor(223344556, 1200.0, ContaTipo.CORRENTE);
        double saldo = banco.consultarSaldo(223344556, ContaTipo.CORRENTE);

        Assertions.assertEquals(1200.0, saldo, 0);
    }

    @Test
    public void testConsultarSaldoPoupanca() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("José", 112233445, conta);

        banco.creditarValor(112233445, 1500.0, ContaTipo.POUPANCA);
        double saldo = banco.consultarSaldo(112233445, ContaTipo.POUPANCA);

        Assertions.assertEquals(1500.0, saldo, 0);
    }

    @Test
    public void testProcurarClientePorCPF() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Lucas", 998877665, conta);

        Cliente cliente = banco.procurarClientePorCPF(998877665);

        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Lucas", cliente.getNome());
    }

    @Test
    public void testProcurarContasPorCPFETipo() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("Mariana", 123123123, conta);

        Cliente cliente = banco.procurarContasPorCPF(123123123, ContaTipo.POUPANCA);
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Mariana", cliente.getNome());
        Assertions.assertEquals(ContaTipo.POUPANCA, cliente.getConta().getTipoConta());
    }

    @Test
    public void testCriarContaDiferentesComCpfExistente() {
        Banco banco = new Banco("Banco Teste");
        Conta conta1 = new Conta(1, ContaTipo.CORRENTE);
        Conta conta2 = new Conta(1, ContaTipo.POUPANCA);
        banco.criarConta("Lucas", 121212121, conta1);

        banco.criarConta("Lucas", 121212121, conta2);
        Assertions.assertNotNull(banco.procurarContasPorCPF(121212121, ContaTipo.CORRENTE));
        Assertions.assertNotNull(banco.procurarContasPorCPF(121212121, ContaTipo.POUPANCA));
    }

    @Test
    public void testCriarContaIguaisComCpfExistente() {
        Banco banco = new Banco("Banco Teste");
        Conta conta1 = new Conta(1, ContaTipo.CORRENTE);
        Conta conta2 = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Lucas", 121212121, conta1);

        banco.criarConta("Lucas", 121212121, conta2);
        Assertions.assertNotNull(banco.procurarContasPorCPF(121212121, ContaTipo.CORRENTE));
        Assertions.assertNotEquals(conta2, banco.procurarContasPorCPF(121212121, ContaTipo.CORRENTE).getConta());
    }

    @Test
    public void testDebitarValorContaInexistente() {
        Banco banco = new Banco("Banco Teste");

        banco.debitarValor(999999999, 100.0, ContaTipo.CORRENTE);
        Assertions.assertNull(banco.procurarClientePorCPF(999999999));
    }

    @Test
    public void testTransferirValorContaDestinoInexistente() {
        Banco banco = new Banco("Banco Teste");
        Conta contaOrigem = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Maria", 232323232, contaOrigem);

        banco.creditarValor(232323232, 500.0, ContaTipo.CORRENTE);
        banco.transferirValor(232323232, ContaTipo.CORRENTE, 200, 343434343, ContaTipo.CORRENTE);

        double saldoOrigem = banco.consultarSaldo(232323232, ContaTipo.CORRENTE);
        Assertions.assertEquals(500.0, saldoOrigem, 0);
    }

    @Test
    public void testTransferirValorContaOrigemInexistente() {
        Banco banco = new Banco("Banco Teste");
        Conta contaDestino = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("José", 454545454, contaDestino);

        banco.transferirValor(343434343, ContaTipo.CORRENTE, 200, 454545454, ContaTipo.CORRENTE);
        double saldoDestino = banco.consultarSaldo(454545454, ContaTipo.CORRENTE);
        Assertions.assertEquals(0.0, saldoDestino, 0);
    }

    @Test
    public void testConsultarSaldoContaInexistente() {
        Banco banco = new Banco("Banco Teste");

        Assertions.assertNull(banco.procurarContasPorCPF(565656565, ContaTipo.CORRENTE));
    }

    @Test
    public void testCreditarValorContaInexistente() {
        Banco banco = new Banco("Banco Teste");

        banco.creditarValor(777777777, 300.0, ContaTipo.POUPANCA);
        Assertions.assertNull(banco.procurarClientePorCPF(777777777));
    }

    @Test
    public void testCreditarDebitarValorContaCorrente() {
        Banco banco = new Banco("Banco Teste");
        Conta conta = new Conta(1, ContaTipo.CORRENTE);
        banco.criarConta("Ana", 343434343, conta);

        banco.creditarValor(343434343, 400.0, ContaTipo.CORRENTE);
        banco.debitarValor(343434343, 100.0, ContaTipo.CORRENTE);
        double saldo = banco.consultarSaldo(343434343, ContaTipo.CORRENTE);

        Assertions.assertEquals(300.0, saldo, 0);
    }

    @Test
    public void testNomeBanco() {
        Banco banco = new Banco("Banco Teste");
        banco.setNomeBanco("Banco Novo Nome");

        Assertions.assertEquals("Banco Novo Nome", banco.getNomeBanco());
    }

    @Test
    public void testNumeroAgencia() {
        Agencia agencia = new Agencia("Agencia Teste", 123);
        Assertions.assertEquals(123, agencia.getIdAgencia());
    }

    @Test
    public void testAlterarNomeAgencia() {
        Agencia agencia = new Agencia("Agencia Antiga", 456);
        agencia.setNomeAgencia("Agencia Nova");

        Assertions.assertEquals("Agencia Nova", agencia.getNomeAgencia());
    }
}
