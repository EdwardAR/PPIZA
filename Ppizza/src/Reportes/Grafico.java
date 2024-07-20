/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Grafico {
     public static void Graficar(Date fecha) {
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;
        try {
            // Formatear la fecha al formato adecuado para la base de datos (con hora)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaSql = dateFormat.format(fecha);

            // Calcular la fecha inicial y final para el rango de fechas
            String fechaInicial = fechaSql.substring(0, 10) + " 00:00:00";
            String fechaFinal = fechaSql.substring(0, 10) + " 23:59:59";

            String sql = "SELECT total FROM pedidos WHERE fecha >= ? AND fecha <= ?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicial);
            ps.setString(2, fechaFinal);
            rs = ps.executeQuery();

            // Crear el dataset para el gráfico de pastel
            DefaultPieDataset dataset = new DefaultPieDataset();
            double totalVentas = 0.0;

            while (rs.next()) {
                double total = rs.getDouble("total");
                dataset.setValue(Double.toString(total), total); // Añadir el total al dataset
                totalVentas += total;
            }

            // Crear el gráfico de pastel
            JFreeChart chart = ChartFactory.createPieChart("Reporte de Venta", dataset);

            // Agregar el total como título en el gráfico
            chart.setTitle(chart.getTitle().getText() + " - Total: S/" + totalVentas);

            ChartFrame frame = new ChartFrame("Total de Ventas por día", chart);
            frame.setSize(1000, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}