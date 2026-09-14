import poo.banca.Cajero;
import poo.banca.ConstruccionCuentaException;
import poo.banca.Cuenta;
import poo.banca.ValorInvalidoException;

void main() throws ConstruccionCuentaException, ValorInvalidoException {

    

    Cuenta origen = new Cuenta(
            "Sandra",
            "Fernandez",
            "010111",
            1000
    );

    Cuenta destino = new Cuenta(
            "Gerardo",
            "Richieri",
            "18909",
            20
    );

    Cajero tobias = new Cajero();
    tobias.transferir(origen, destino, 100);
}
