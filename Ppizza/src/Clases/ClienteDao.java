package Clases;

import Interfaz.ClienteInterfaces;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class ClienteDao implements ClienteInterfaces {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private String generateNewClienteID() {
        String sql = "SELECT id FROM clientes ORDER BY id DESC LIMIT 1";
        String lastId = "";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                lastId = rs.getString("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        if (!lastId.isEmpty()) {
            int idNum = Integer.parseInt(lastId.substring(3)); // Obtén el número después de "CLI"
            return "CLI" + String.format("%03d", idNum + 1); // Incrementa y formatea el nuevo ID
        } else {
            return "CLI001"; // Si no hay registros, comienza con "CLI001"
        }
    }

    @Override
    public boolean Registrar(Cliente cl) {
        String id = generateNewClienteID();
        String sql = "INSERT INTO clientes(id, dni, nombre, apellidoPa, apellidoMa, telefono, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setInt(2, cl.getDni());
            ps.setString(3, cl.getNombre());
            ps.setString(4, cl.getPaterno());
            ps.setString(5, cl.getMaterno());
            ps.setInt(6, cl.getTelefono());
            ps.setBoolean(7, cl.isActivo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }


    public List<Cliente> ListadoCliente() {
        List<Cliente> ListCl = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE activo = true";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();
                cl.setId(rs.getString("id"));
                cl.setDni(rs.getInt("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setPaterno(rs.getString("ApellidoPa"));
                cl.setMaterno(rs.getString("ApellidoMa"));
                cl.setTelefono(rs.getInt("Telefono"));
                cl.setActivo(rs.getBoolean("activo")); // Asigna el valor del campo activo
                ListCl.add(cl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListCl;
    }


    public boolean ElimCliente(String id) {
        String sql = "UPDATE clientes SET activo = false WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);  
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    @Override
    public boolean modificar(Cliente cl) {
        String sql = "UPDATE clientes SET dni=?, nombre=?, ApellidoPa=?, ApellidoMa=?, Telefono=? WHERE id=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getPaterno());
            ps.setString(4, cl.getMaterno());
            ps.setInt(5, cl.getTelefono());
            ps.setString(6, cl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
public List<Cliente> ListadoClientesInactivos() {
    List<Cliente> ListClInactivos = new ArrayList<>();
    String sql = "SELECT * FROM clientes WHERE activo = false"; // Query to get inactive clients
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Cliente cl = new Cliente();
            cl.setId(rs.getString("id"));
            cl.setDni(rs.getInt("dni"));
            cl.setNombre(rs.getString("nombre"));
            cl.setPaterno(rs.getString("ApellidoPa"));
            cl.setMaterno(rs.getString("ApellidoMa"));
            cl.setTelefono(rs.getInt("Telefono"));
            cl.setActivo(rs.getBoolean("activo")); // This should be false for inactive clients
            ListClInactivos.add(cl);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    return ListClInactivos;
}

 public Cliente BuscarCliente(int dni) {
    Cliente cl = new Cliente();
    String sql = "SELECT * FROM clientes WHERE dni = ?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, dni);
        rs = ps.executeQuery();
        if (rs.next()) {
            cl.setNombre(rs.getString("nombre"));
            cl.setPaterno(rs.getString("ApellidoPa"));
            cl.setMaterno(rs.getString("ApellidoMa"));
            cl.setTelefono(rs.getInt("Telefono"));
            cl.setActivo(rs.getBoolean("activo")); // Obtener el estado activo
        }
    } catch (Exception e) {
        System.out.println(e.toString());
    }
    return cl;
}

}
