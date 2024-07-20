package Clases;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    // Método para autenticar un usuario por correo y contraseña
  public Login log(String correo, String pass) {
    Login l = new Login();
    String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ? AND activo = 1"; // Only select active users
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, correo);
        ps.setString(2, pass);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            // User found and is active
            l.setId(rs.getInt("id"));
            l.setNombre(rs.getString("nombre"));
            l.setCorreo(rs.getString("correo"));
            l.setPass(rs.getString("pass"));
            l.setRol(rs.getString("rol"));
            l.setActivo(rs.getBoolean("activo"));
        } else {
            // No active user found
            JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos. Si su cuenta está desactivada, póngase en contacto con soporte técnico: soporteppizza@ppizza.pe");
            return null; // Return null to indicate login failure
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        cerrarRecursos(); // Close resources in the finally block
    }
    return l;
}
    
    // Método para registrar un nuevo usuario
    public boolean Registrar(Login reg) {
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol, activo) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.setBoolean(5, reg.isActivo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            cerrarRecursos(); // Cerrar recursos en el bloque finally
        }
    }
    
    // Método para listar todos los usuarios registrados
    public List<Login> ListarUsuarios() {
    List<Login> Lista = new ArrayList<>();
    String sql = "SELECT * FROM usuarios WHERE activo = true"; // Filtrar solo usuarios activos
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Login lg = new Login();
            lg.setId(rs.getInt("id"));
            lg.setNombre(rs.getString("nombre"));
            lg.setCorreo(rs.getString("correo"));
            lg.setRol(rs.getString("rol"));
            lg.setActivo(rs.getBoolean("activo"));
            Lista.add(lg);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        cerrarRecursos(); // Cerrar recursos en el bloque finally
    }
    return Lista;
}
    
    // Método para verificar si un correo electrónico ya está registrado
    public boolean correoExistente(String correo) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    existe = true; // El correo ya existe en la base de datos
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            cerrarRecursos(); // Cerrar recursos en el bloque finally
        }
        
        return existe;
    }
 public boolean eliminarUsuario(int id) {
        String sql = "UPDATE usuarios SET activo = false WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            cerrarRecursos(); // Cerrar recursos en el bloque finally
        }
    }   
    // Método para cerrar recursos
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar recursos: " + e.toString());
        }
    }
}
