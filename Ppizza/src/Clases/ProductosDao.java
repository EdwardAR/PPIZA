package Clases;

import Interfaz.ProductoInterfaces;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDao implements ProductoInterfaces {

    Connection con;
    Conexion cn = new Conexion();
    ResultSet rs;
    PreparedStatement ps;

    // Método para generar el nuevo ID personalizado
private String generateNewProductoID() {
    String sql = "SELECT MAX(CAST(SUBSTRING(id, 4) AS UNSIGNED)) FROM productos";
    int maxId = 0;
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            maxId = rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return "PRO" + String.format("%03d", maxId + 1);
}


@Override
    public boolean Registrar(Producto pro) {
        String id = generateNewProductoID();
        String sql = "INSERT INTO productos(id, desc_Prod, codigo, tipo_Pizza, tama_Pizz, stock, precio, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pro.getDescPro());
            ps.setString(3, pro.getCodigoP());
            ps.setString(4, pro.getTipoPizza());
            ps.setString(5, pro.getTamañoPizza());
            ps.setInt(6, pro.getCantidad());
            ps.setDouble(7, pro.getPrecio());
            ps.setBoolean(8, pro.isActivo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


   public List<Producto> ListadoPro() {
        List<Producto> Listpro = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = true";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getString("id"));
                pro.setDescPro(rs.getString("desc_Prod"));
                pro.setCodigoP(rs.getString("codigo"));
                pro.setTipoPizza(rs.getString("tipo_Pizza"));
                pro.setTamañoPizza(rs.getString("tama_Pizz"));
                pro.setCantidad(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
                pro.setActivo(rs.getBoolean("activo"));
                Listpro.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Listpro;
    }

   
    public List<Producto> ListadoProINACTIVO() {
        List<Producto> Listpro = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = false";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getString("id"));
                pro.setDescPro(rs.getString("desc_Prod"));
                pro.setCodigoP(rs.getString("codigo"));
                pro.setTipoPizza(rs.getString("tipo_Pizza"));
                pro.setTamañoPizza(rs.getString("tama_Pizz"));
                pro.setCantidad(rs.getInt("stock"));
                pro.setPrecio(rs.getDouble("precio"));
                pro.setActivo(rs.getBoolean("activo"));
                Listpro.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Listpro;
    }
   
  public boolean EliminarPro(String id) {
        String sql = "UPDATE productos SET activo = false WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean modificar(Producto pro) {
        String sql = "UPDATE productos SET desc_Prod = ?, codigo = ?, tipo_Pizza = ?, tama_Pizz = ?, stock = ?, precio = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getDescPro());
            ps.setString(2, pro.getCodigoP());
            ps.setString(3, pro.getTipoPizza());
            ps.setString(4, pro.getTamañoPizza());
            ps.setInt(5, pro.getCantidad());
            ps.setDouble(6, pro.getPrecio());
            ps.setString(7, pro.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Producto BuscarPro(String NombPizza) {
        Producto product = new Producto();
        String sql = "SELECT * FROM productos WHERE desc_Prod = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, NombPizza);
            rs = ps.executeQuery();
            if (rs.next()) {
                product.setId(rs.getString("id"));
                product.setCodigoP(rs.getString("codigo"));
                product.setTipoPizza(rs.getString("tipo_Pizza"));
                product.setTamañoPizza(rs.getString("tama_Pizz"));
                product.setPrecio(rs.getDouble("precio"));
                product.setCantidad(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return product;
    }

public List<String> obtenerDescripcionesProductos() {
    List<String> descripciones = new ArrayList<>();
    String sql = "SELECT desc_Prod FROM productos WHERE activo = true";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String descripcion = rs.getString("desc_Prod");
            descripciones.add(descripcion);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return descripciones;
}

}
