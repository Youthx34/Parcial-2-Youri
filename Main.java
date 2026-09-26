enum Combustible {
    GASOLINA,
    DIESEL
}

interface Conducible {
    void arrancar();
    void detenerse();
}

interface Electrico {
    void recargar();
}

final class Precios {
    static final double GASOLINA = 42;
    static final double DIESEL = 48;
}

abstract class Vehiculo implements Conducible {
    private String matricula;
    private String marca;
    private int numeroPlazas;

    public Vehiculo(String matricula, String marca, int numeroPlazas) {
        this.matricula = matricula;
        this.marca = marca;
        this.numeroPlazas = numeroPlazas;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public abstract double calcularAutonomia();

    @Override
    public void arrancar() {
        System.out.println(marca + " arrancó");
    }

    @Override
    public void detenerse() {
        System.out.println(marca + " se detuvo");
    }
}

abstract class Terrestre extends Vehiculo {
    private int numeroRuedas;

    public Terrestre(String matricula, String marca, int numeroPlazas, int numeroRuedas) {
        super(matricula, marca, numeroPlazas);
        this.numeroRuedas = numeroRuedas;
    }

    public int getNumeroRuedas() {
        return numeroRuedas;
    }
}

class Automovil extends Terrestre {
    private Combustible combustible;
    private double galones;
    private double kilometrosPorGalon;

    public Automovil(String matricula, String marca, int numeroPlazas, Combustible combustible,
                     double galones, double kilometrosPorGalon) {
        super(matricula, marca, numeroPlazas, 4);
        this.combustible = combustible;
        this.galones = galones;
        this.kilometrosPorGalon = kilometrosPorGalon;
    }

    @Override
    public double calcularAutonomia() {
        return galones * kilometrosPorGalon;
    }

    public double calcularCostoTanque() {
        if (combustible == Combustible.GASOLINA) {
            return galones * Precios.GASOLINA;
        }
        return galones * Precios.DIESEL;
    }
}

class AutomovilElectrico extends Automovil implements Electrico {
    private double cargaActual;
    private double kilometrosPorKwh;

    public AutomovilElectrico(String matricula, String marca, int numeroPlazas,
                              double capacidadBateria, double kilometrosPorKwh) {
        super(matricula, marca, numeroPlazas, Combustible.GASOLINA, 0, 0);
        this.cargaActual = capacidadBateria;
        this.kilometrosPorKwh = kilometrosPorKwh;
    }

    @Override
    public double calcularAutonomia() {
        return cargaActual * kilometrosPorKwh;
    }

    @Override
    public void recargar() {
        cargaActual = 100;
        System.out.println(getMarca() + " está recargado");
    }
}

class Autobus extends Terrestre {
    private String ruta;
    private double galones;
    private double kilometrosPorGalon;

    public Autobus(String matricula, String marca, int numeroPlazas, String ruta,
                   double galones, double kilometrosPorGalon) {
        super(matricula, marca, numeroPlazas, 6);
        this.ruta = ruta;
        this.galones = galones;
        this.kilometrosPorGalon = kilometrosPorGalon;
    }

    public String getRuta() {
        return ruta;
    }

    @Override
    public double calcularAutonomia() {
        return galones * kilometrosPorGalon;
    }

    public double calcularCostoTanque() {
        return galones * Precios.DIESEL;
    }
}

class Motocicleta extends Terrestre {
    private double galones;
    private double kilometrosPorGalon;

    public Motocicleta(String matricula, String marca, int numeroPlazas,
                       double galones, double kilometrosPorGalon) {
        super(matricula, marca, numeroPlazas, 2);
        this.galones = galones;
        this.kilometrosPorGalon = kilometrosPorGalon;
    }

    @Override
    public double calcularAutonomia() {
        return galones * kilometrosPorGalon;
    }

    public double calcularCostoTanque() {
        return galones * Precios.GASOLINA;
    }
}

class Acuatico extends Vehiculo {
    private double desplazamientoToneladas;
    private double autonomia;

    public Acuatico(String matricula, String marca, int numeroPlazas,
                    double desplazamientoToneladas, double autonomia) {
        super(matricula, marca, numeroPlazas);
        this.desplazamientoToneladas = desplazamientoToneladas;
        this.autonomia = autonomia;
    }

    public double getDesplazamientoToneladas() {
        return desplazamientoToneladas;
    }

    @Override
    public double calcularAutonomia() {
        return autonomia;
    }
}

public class Main {
    public static void main(String[] args) {
        Automovil automovil = new Automovil(
                "P123ABC", "Toyota", 5, Combustible.GASOLINA, 12, 15);
        Autobus autobus = new Autobus(
                "P456DEF", "Mercedes", 40, "Centro", 50, 8);
        Motocicleta motocicleta = new Motocicleta(
                "P789GHI", "Honda", 2, 4, 35);
        AutomovilElectrico electrico = new AutomovilElectrico(
                "P321JKL", "Nissan", 5, 80, 6);
        Acuatico acuatico = new Acuatico(
                "A123XYZ", "Lancha", 8, 3.5, 250);

        Conducible[] conducibles = {
            automovil, autobus, motocicleta, electrico, acuatico
        };

        for (Conducible conducible : conducibles) {
            conducible.arrancar();
        }

        Vehiculo[] vehiculos = {
            automovil, autobus, motocicleta, electrico, acuatico
        };

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(
                    vehiculo.getMarca() + " (" + vehiculo.getMatricula()
                    + "): autonomía " + vehiculo.calcularAutonomia() + " km");

            if (vehiculo instanceof Automovil) {
                Automovil auto = (Automovil) vehiculo;
                if (!(vehiculo instanceof AutomovilElectrico)) {
                    System.out.println("Costo de tanque: Q" + auto.calcularCostoTanque());
                }
            } else if (vehiculo instanceof Autobus) {
                Autobus bus = (Autobus) vehiculo;
                System.out.println("Costo de tanque: Q" + bus.calcularCostoTanque());
            } else if (vehiculo instanceof Motocicleta) {
                Motocicleta moto = (Motocicleta) vehiculo;
                System.out.println("Costo de tanque: Q" + moto.calcularCostoTanque());
            }
        }

        electrico.recargar();

        for (Conducible conducible : conducibles) {
            conducible.detenerse();
        }
    }
}