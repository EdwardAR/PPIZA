import config.Conexion;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraficoComprasUsuarios extends JFrame {

    private Connection conexion;
    private DefaultCategoryDataset dataset;

    public GraficoComprasUsuarios(String titulo) {
        super(titulo);

        // Establecer la conexión a la base de datos
        Conexion db = new Conexion();
        conexion = db.getConnection();

        // Inicializar el dataset
        dataset = new DefaultCategoryDataset();

        // Crear el gráfico de barras con datos vacíos
        JFreeChart chart = ChartFactory.createBarChart(
                "Cantidad de Compras por Usuario",
                "Usuario",
                "Cantidad de Compras",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Obtener el plot (cuerpo del gráfico) para personalizar el renderer
        CategoryPlot plot = chart.getCategoryPlot();

        // Crear un renderer personalizado para asignar colores automáticamente
        BarRenderer renderer = new CustomBarRenderer();
        plot.setRenderer(renderer);

        // Crear un panel para el gráfico y ajustar propiedades
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 600));
        setContentPane(panel);

        // Obtener y actualizar los datos desde la base de datos
        actualizarDatosDesdeBaseDeDatos();
    }

    // Método para obtener datos desde la base de datos y actualizar el gráfico
    public void actualizarDatosDesdeBaseDeDatos() {
        String sql = "SELECT cliente AS usuario, SUM(total) AS total_compras " +
                     "FROM pedidosven " +
                     "GROUP BY usuario " +
                     "ORDER BY usuario";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Limpiar dataset actual antes de agregar nuevos datos
            dataset.clear();

            // Procesar resultados de la consulta y agregar al dataset
            while (rs.next()) {
                String usuario = rs.getString("usuario");
                int totalCompras = rs.getInt("total_compras");
                dataset.addValue(totalCompras, "Compras", usuario);
            }

        } catch (SQLException e) {
            Logger.getLogger(GraficoComprasUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Renderer personalizado para asignar colores automáticamente a las barras
    class CustomBarRenderer extends BarRenderer {
        private static final long serialVersionUID = 1L;
        private final Color[] colors = {
                new Color(79, 129, 189),
                new Color(155, 187, 89),
                new Color(128, 100, 162),
                new Color(247, 150, 70),
                new Color(146, 208, 80),
                new Color(89, 89, 89)
        };

        @Override
        public Paint getItemPaint(int row, int column) {
            int colorIndex = column % colors.length;
            return colors[colorIndex];
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraficoComprasUsuarios example = new GraficoComprasUsuarios("Gráfico de Compras por Usuario");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
