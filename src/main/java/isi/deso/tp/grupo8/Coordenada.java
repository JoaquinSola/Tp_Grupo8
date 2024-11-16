package isi.deso.tp.grupo8;

public class Coordenada {
    private long id;
    private double latitud;
    private double longitud;

    public Coordenada(double latitud, double longitud, long id) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.id =id;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
