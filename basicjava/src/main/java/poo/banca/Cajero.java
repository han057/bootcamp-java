package poo.banca;

public class Cajero {

    public void transferir(Cuenta origen, Cuenta destino, long valor) {
        //try {
            origen.retirar(valor);
            destino.depositar(valor);
        //} catch (ValorInvalidoException e) {
        //   IO.println(e.getMessage());
        //} finally {
            IO.println("""
                    Cliente: %s Cuenta: %s  Nuevo Saldo: %s
                    Cliente: %s Cuenta: %s Nuevo Saldo: %s
                    """.formatted(
                            origen.getNombreCliente(),
                    origen.getNumero(),
                    origen.getSaldo(),
                    destino.getNombreCliente(),
                    destino.getNumero(),
                    destino.getSaldo())
            );
        //}
    }
}
