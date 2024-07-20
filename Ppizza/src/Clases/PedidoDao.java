/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {
    Connection con;
    Conexion cn=new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int res;
    
    public int IdVenta(){
        int id=0;
        String sql =" SELECT MAX(id) FROM pedidos";
        try {
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            if(rs.next()){
                id=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }
    public int RegistrarPedidoVen(Pedido pe){
        String sql = "INSERT INTO pedidos(cliente,vendedor,total) VALUES (?,?,?)";
        try {
            con =cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, pe.getCliente());
            ps.setString(2, pe.getVendedor());
            ps.setDouble(3, pe.getTotal());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
        return res;
    }
    
    
   public int RegistrarDetallePedV(DetallePV detp){
       String sql= "INSERT INTO detalle(cod_pro, cantidad, precio, id_ve) VALUES (?,?,?,?)";
       try {
           con=cn.getConnection();
           ps=con.prepareStatement(sql);
           ps.setString(1, detp.getCod_pro());
           ps.setInt(2, detp.getCantidad());
           ps.setDouble(3, detp.getPrecio());
           ps.setInt(4, detp.getId());
           ps.execute();
       } catch (SQLException e) {
           System.out.println(e.toString());
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
       return res;
   }
   

    public boolean ActualizarStock(int cant, String cod) {
    String sql = "UPDATE productos SET stock = stock - ? WHERE codigo = ?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, cant);
        ps.setString(2, cod);
        ps.executeUpdate();
        return true; // Actualización exitosa, se devuelve true
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    return false; // Si hay un error, se devuelve false
}

public int obtenerStockProducto(String codigo) {
    int stockActual = 0;
    String sql = "SELECT stock FROM productos WHERE codigo = ?";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, codigo);
        rs = ps.executeQuery();

        if (rs.next()) {
            stockActual = rs.getInt("stock");
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    } finally {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    return stockActual;
}

public List ListadoPedidos(){
    List<Pedido> ListaPedidos =new ArrayList();
    String sql ="SELECT* FROM pedidos ";
    try {
        con=cn.getConnection();
        ps=con.prepareStatement(sql);
        rs=ps.executeQuery();
        while(rs.next()){
            Pedido ped =new Pedido();
            ped.setId(rs.getInt("id"));
            ped.setCliente(rs.getString("Cliente"));
            ped.setVendedor(rs.getString("Vendedor"));
            ped.setTotal(rs.getDouble("Total"));
            ListaPedidos.add(ped);
        }
        
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return ListaPedidos;
}

}
