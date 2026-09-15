void main() {
    var p = new Persona(
            "Facundo",
            "Cabral"
    );
    AccesoDatos accesoDatos = new AccesoDatosSQL();
    var agregarPersonaServicio = new AgregarPersonaServicio(accesoDatos);
    agregarPersonaServicio.agregar(p);

}

class AgregarPersonaServicio {
    private AccesoDatos accesoDatos;
    public AgregarPersonaServicio(AccesoDatos accesoDatos) {
        this.accesoDatos = accesoDatos;
    }
    public void agregar(Persona p) {
        accesoDatos.agregar(p);
        accesoDatos.buscar("Facundo");
    }
}

class Persona {
    private String nombre;
    private String apellido;
    public Persona(
            String nombre,
            String apellido
    ) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
}

interface AccesoDatos {
    void agregar(Persona persona);
    void buscar(String nombre);
}

class AccesoDatosSQL implements AccesoDatos {

    public void agregar(Persona persona) {
        IO.println("""
            INSERT INTO personas (nombre, apellido)
            VALUES ('%s'. '%s')""".formatted(persona.nombre, persona.apellido)
        );
    }

    public void buscar(String nombre) {
        IO.println("""
                SELECT * FROM personas WHERE nombre = %s
                """.formatted(nombre));
    }

    public void otroMetodoDeAcceso() {

    }
}

class AccesoDatosMongo implements AccesoDatos {

    public void agregar(Persona persona) {
        IO.println("""
            db.personas.insert(
                {
                    'nombre': '%s',
                    'apellido': '%s'
                })""".formatted(persona.nombre, persona.apellido)
        );
    }

    public void buscar(String nombre) {
        IO.println("""
                db.personas.findOne({'nombre': '%s'})
                """.formatted(nombre));
    }

    public void otroMetodoDeAcceso() {

    }
}

class AccesoDatosExcel implements AccesoDatos {

    @Override
    public void agregar(Persona persona) {
        IO.println("""
                nombre | apellido
                %s     | %s
                """.formatted(persona.nombre, persona.apellido));
    }

    @Override
    public void buscar(String nombre) {
        IO.println("Ctrl + f %s".formatted(nombre));
    }
}