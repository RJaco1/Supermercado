package modelo;

public class Producto {

    //var cantComprar sera usado para que el cliente seleccione la cantidad a comprar por producto
    private Integer idProducto, cantidad, idCategoria, cantComprar = 1;
    private String producto, imagenProducto;
    private double precio;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getCantComprar() {
        return cantComprar;
    }

    public void setCantComprar(Integer cantComprar) {
        this.cantComprar = cantComprar;
    }
}
