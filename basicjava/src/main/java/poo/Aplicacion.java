import poo.DatosInvalidosException;
import poo.Pantalla;
import poo.PantallaRaytracing;


void main() {
    Pantalla pantallaBasicaHP = new Pantalla("hp");
    Pantalla pantallaBasicaMagic = new Pantalla("magic");

    pantallaBasicaHP.renderizar();
    pantallaBasicaMagic.renderizar();

    try {
        var scanner = new Scanner(System.in);
        IO.println("Ingrese marca: ");
        String m = scanner.nextLine();
        Pantalla pantallaMsi = new Pantalla(m);
        pantallaMsi.renderizar();
    } catch (DatosInvalidosException e) {
        IO.println(e.getMessage());
    }
}