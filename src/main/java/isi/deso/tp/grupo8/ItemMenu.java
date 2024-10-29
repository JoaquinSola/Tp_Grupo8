package isi.deso.tp.grupo8;

public abstract class ItemMenu {
    private int id;
    public String nombre;
    private String descripcion;
    public double precio;
    private Categoria categoria;
    public abstract double peso(double p);
    public abstract boolean esComida();
    public abstract boolean esBebida();
    public abstract boolean aptoVegano();
    public abstract boolean esAlcoholica();
    public abstract String toString();
    public abstract double getPrecio();
     public String getDesc() {
        return descripcion;
    }

    public void setDesc(String descripcion) {
        this.descripcion = descripcion;
    }
      public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
