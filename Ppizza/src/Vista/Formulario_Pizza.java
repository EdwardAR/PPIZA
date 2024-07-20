package Vista;

import Clases.Cliente;
import java.awt.Desktop;
import Clases.ClienteDao;
import Clases.DetallePV;
import Clases.Eventos;
import Clases.Login;
import Clases.Metodos_Busquedas;
import Clases.Pedido;
import Clases.PedidoDao;
import Clases.Producto;
import Clases.ProductosDao;
import Clases.LoginDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.JTableHeader;
import org.jfree.chart.ChartPanel;
import java.util.Date;
import java.text.SimpleDateFormat;
import Reportes.Grafico;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;

public class Formulario_Pizza extends javax.swing.JFrame {

    /*    GraficoComprasUsuarios gv= new GraficoComprasUsuarios();*/
    Cliente cl = new Cliente();
    ClienteDao client = new ClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    Producto pro = new Producto();
    ProductosDao proDao = new ProductosDao();
    Pedido pe = new Pedido();
    PedidoDao peDao = new PedidoDao();
    DetallePV Dpv = new DetallePV();
    Eventos event = new Eventos();
    Login lg = new Login();
    LoginDAO login = new LoginDAO();
    private String nombreUsuario;
    private String rolUsuario;
    Metodos_Busquedas metodosBusquedas = new Metodos_Busquedas();
    int item;
    double totalPagar = 0.00;

    public Formulario_Pizza() {
        initComponents();
        //CODIGO ICONO PARA JFRAME
        setIconImage(new ImageIcon(getClass().getResource("/Imagen/ppizza300px.png")).getImage());
        setLocationRelativeTo(null);
        txtIDClien.setVisible(false);
        txtIDprodc.setVisible(false);
        txtIdPediV.setVisible(false);
        txtIdPediV.setVisible(false);
        txtApellidoMaPedido.setVisible(false);
        txtApellidoPaPedido.setVisible(false);
        txtIdVenta.setVisible(false);
        cbxTipoPedidoi.setEnabled(false);
        cbxTamañoPedido.setEnabled(false);
        cargarDescripcionesProductos();

        cbxNombreProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descripcionSeleccionada = (String) cbxNombreProducto.getSelectedItem();
                if (descripcionSeleccionada != null) {
                    // Buscar el producto en base a la descripción seleccionada
                    Producto productoSeleccionado = proDao.BuscarPro(descripcionSeleccionada);
                    if (productoSeleccionado != null) {
                        // Si se encuentra el producto, actualizar los campos de texto y otros componentes con la información correspondiente
                        txtCodpedidoP.setText(productoSeleccionado.getCodigoP());
                        cbxTipoPedidoi.setSelectedItem(productoSeleccionado.getTipoPizza());
                        cbxTamañoPedido.setSelectedItem(productoSeleccionado.getTamañoPizza());
                        txtPrecioP.setText(String.valueOf(productoSeleccionado.getPrecio()));
                        txtStockdispo.setText(String.valueOf(productoSeleccionado.getCantidad()));
                        txtCantPedido.requestFocus();
                    }
                }
            }
        });
    }

//BUSQUEDA SECUENCIAL:
    public void setUsuario(String nombreUsuario, String rolUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.rolUsuario = rolUsuario;

        // Mostrar nombre y rol del usuario en el label lblVendedor
        lblVendedor.setText("   " + nombreUsuario + " " + "\n\n" + " - " + rolUsuario);

        // Desactivar botones según el rol
        if (rolUsuario.equals("Asistente")) {
            btnProductos.setEnabled(false);
            btnUsuarios.setEnabled(false);
            btnInfo.setEnabled(false);
        }
    }
// Método para listar clientes

    public void ListarCliente() {
        List<Cliente> ListCl = client.ListadoCliente(); // Obtener la lista de clientes desde el objeto client
        modelo = (DefaultTableModel) tblCliente.getModel(); // Obtener el modelo de la tabla tblCliente
        modelo.setRowCount(0); // Limpiar el modelo de la tabla para evitar duplicados
        Object[] ob = new Object[6]; // Crear un arreglo para almacenar los datos de cada fila de la tabla
        for (int i = 0; i < ListCl.size(); i++) {
            // Llenar el arreglo con los datos del cliente actual
            ob[0] = ListCl.get(i).getId();
            ob[1] = ListCl.get(i).getDni();
            ob[2] = ListCl.get(i).getNombre();
            ob[3] = ListCl.get(i).getPaterno();
            ob[4] = ListCl.get(i).getMaterno();
            ob[5] = ListCl.get(i).getTelefono();

            modelo.addRow(ob); // Agregar una nueva fila a la tabla con los datos del cliente
        }
        tblCliente.setModel(modelo); // Establecer el modelo actualizado en la tabla tblCliente
    }

    public void ListarProductos() {
        List<Producto> Listpro = proDao.ListadoPro(); // Obtener la lista de productos desde el objeto proDao
        modelo = (DefaultTableModel) tblProducto.getModel(); // Obtener el modelo de la tabla tblProducto
        Object[] ob = new Object[7]; // Crear un arreglo para almacenar los datos de cada fila de la tabla
        for (int i = 0; i < Listpro.size(); i++) {
            // Llenar el arreglo con los datos del producto actual
            ob[0] = Listpro.get(i).getId();
            ob[1] = Listpro.get(i).getDescPro();
            ob[2] = Listpro.get(i).getCodigoP();
            ob[3] = Listpro.get(i).getTipoPizza();
            ob[4] = Listpro.get(i).getTamañoPizza();
            ob[5] = Listpro.get(i).getCantidad();
            ob[6] = Listpro.get(i).getPrecio();

            modelo.addRow(ob); // Agregar una nueva fila a la tabla con los datos del producto
        }
        tblProducto.setModel(modelo); // Establecer el modelo actualizado en la tabla tblProducto
    }

    private void ListarUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) TableUsuarios.getModel();
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

        List<Login> usuarios = login.ListarUsuarios(); // Obtener la lista de usuarios activos
        for (Login usuario : usuarios) {
            Object[] fila = {usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol()};
            modelo.addRow(fila); // Agregar cada usuario como una fila al modelo de la tabla
        }

        TableUsuarios.setModel(modelo); // Asignar el modelo actualizado a la tabla
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i); // Eliminar la fila actual del modelo
            i = i - 1; // Restar 1 al contador para evitar problemas con los índices
        }
    }

    private void LimpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) TableUsuarios.getModel();
        modelo.setRowCount(0); // Eliminar todas las filas de la tabla
        TableUsuarios.setModel(modelo); // Asignar el modelo actualizado a la tabla
    }

    public void ListarPedidosVenta() {
        List<Pedido> ListPedidoVenta = peDao.ListadoPedidos(); // Obtener la lista de productos desde el objeto proDao
        modelo = (DefaultTableModel) tblVentasPedidos.getModel(); // Obtener el modelo de la tabla tblProducto
        Object[] ob = new Object[4]; // Crear un arreglo para almacenar los datos de cada fila de la tabla
        for (int i = 0; i < ListPedidoVenta.size(); i++) {
            // Llenar el arreglo con los datos del producto actual
            ob[0] = ListPedidoVenta.get(i).getId();
            ob[1] = ListPedidoVenta.get(i).getCliente();
            ob[2] = ListPedidoVenta.get(i).getVendedor();
            ob[3] = ListPedidoVenta.get(i).getTotal();

            modelo.addRow(ob); // Agregar una nueva fila a la tabla con los datos del producto
        }
        tblVentasPedidos.setModel(modelo); // Establecer el modelo actualizado en la tabla tblProducto
    }

    //Metodo para confirmar el cierre del JFrame
    public void cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Confirmar Salida
    public void confirmarSalida() {
        int valor = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cerra sesión?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Gracias por su visita,Hasta pronto", "Gracias", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnClientes = new javax.swing.JButton();
        btnPedido = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnInfo = new javax.swing.JButton();
        lblVendedor = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombreCli = new javax.swing.JTextField();
        txtApelliPCli = new javax.swing.JTextField();
        txtApelliCliM = new javax.swing.JTextField();
        txtDniCli = new javax.swing.JTextField();
        txtTelefonoCli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        txtIDClien = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnGuardarCli = new javax.swing.JButton();
        btnEditarCli = new javax.swing.JButton();
        btnEliminarCli = new javax.swing.JButton();
        btnLimpiarCam = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnBurbujaDNi = new javax.swing.JButton();
        btnApellidoP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbxTipoPedidoi = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbxTamañoPedido = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtCantPedido = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPrecioP = new javax.swing.JTextField();
        btnElimPed = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPedido = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtDniP = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNombreCLiPedid = new javax.swing.JTextField();
        btnGenerarPed = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        lblTotalPagar = new javax.swing.JLabel();
        txtTelefonoPed = new javax.swing.JTextField();
        txtIdPediV = new javax.swing.JTextField();
        txtStockdispo = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtApellidoPaPedido = new javax.swing.JTextField();
        txtApellidoMaPedido = new javax.swing.JTextField();
        cbxNombreProducto = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        txtCodpedidoP = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtCodPizza = new javax.swing.JTextField();
        txtCantPro = new javax.swing.JTextField();
        cbxTipoPizzaPro = new javax.swing.JComboBox<>();
        cbxTamPizzaPro = new javax.swing.JComboBox<>();
        txtPrecioPro = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        btnGuardarPro = new javax.swing.JButton();
        btnEditarProd = new javax.swing.JButton();
        btnLimpiarPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        txtIDprodc = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcionProd = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnBusSecuencial = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnMenorStock = new javax.swing.JButton();
        REPORTE_HISTVENTA = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblVentasPedidos = new javax.swing.JTable();
        btnPdfVent = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        btnMenoraMayor = new javax.swing.JButton();
        ReportePDF = new javax.swing.JPanel();
        btnReporte = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        btnClienteReporte = new javax.swing.JButton();
        cmBoxClientes = new javax.swing.JComboBox<>();
        comboBoxSTOCK = new javax.swing.JComboBox<>();
        ReportePDF1 = new javax.swing.JPanel();
        btnreport = new javax.swing.JButton();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel34 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtPass = new javax.swing.JPasswordField();
        jLabel38 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtCorreo = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        btnIniciar = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        lblRucPPiz = new javax.swing.JLabel();
        lblDireccPpiza = new javax.swing.JLabel();
        lblTelefonoEmpre = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblRazonPpiza = new javax.swing.JLabel();
        lblNombPizza = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(45, 36, 31));

        btnClientes.setBackground(new java.awt.Color(44, 62, 80));
        btnClientes.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/cliente_png.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnPedido.setBackground(new java.awt.Color(44, 62, 80));
        btnPedido.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnPedido.setForeground(new java.awt.Color(255, 255, 255));
        btnPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/pedido.png"))); // NOI18N
        btnPedido.setText("Pedido");
        btnPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidoActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(44, 62, 80));
        btnVentas.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/ventas.png"))); // NOI18N
        btnVentas.setText("Reporte");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(44, 62, 80));
        btnProductos.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/producto_1.png"))); // NOI18N
        btnProductos.setText("Producto");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(44, 62, 80));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/apagado.png"))); // NOI18N
        btnSalir.setText("Salir del Sistema");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/pizzalogo2.png"))); // NOI18N

        btnInfo.setBackground(new java.awt.Color(44, 62, 80));
        btnInfo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/ppizalogoBton.png"))); // NOI18N
        btnInfo.setText("Inf. Ppizza");
        btnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });

        lblVendedor.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(255, 255, 255));
        lblVendedor.setToolTipText("");

        btnUsuarios.setBackground(new java.awt.Color(44, 62, 80));
        btnUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/userBTN.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 650));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/ppizza.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 700, 120));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/xdxdxdxxd.png"))); // NOI18N
        getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 0, 100, 140));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/xdxdxdxxd.png"))); // NOI18N
        getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 0, 100, 140));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/xdxdxdxxd.png"))); // NOI18N
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 0, 100, 140));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPane1.setFont(new java.awt.Font("Calibri", 1, 15)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nombre: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Apellido paterno:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Apellido materno:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("DNI:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Télefono:");

        txtNombreCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCliKeyTyped(evt);
            }
        });

        txtApelliPCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApelliPCliKeyTyped(evt);
            }
        });

        txtApelliCliM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApelliCliMActionPerformed(evt);
            }
        });
        txtApelliCliM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApelliCliMKeyTyped(evt);
            }
        });

        txtDniCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniCliKeyTyped(evt);
            }
        });

        txtTelefonoCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoCliKeyTyped(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "Nombre", "Apellido Paterno", "Apellido Materno", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        txtIDClien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDClienActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Botones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        btnGuardarCli.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarCli.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnGuardarCli.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/guardar50px.png"))); // NOI18N
        btnGuardarCli.setText("Registrar");
        btnGuardarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarCliMouseExited(evt);
            }
        });
        btnGuardarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCliActionPerformed(evt);
            }
        });

        btnEditarCli.setBackground(new java.awt.Color(0, 0, 0));
        btnEditarCli.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnEditarCli.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/actualizar50px.png"))); // NOI18N
        btnEditarCli.setText("Actualizar");
        btnEditarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarCliMouseExited(evt);
            }
        });
        btnEditarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCliActionPerformed(evt);
            }
        });

        btnEliminarCli.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarCli.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnEliminarCli.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar50px.png"))); // NOI18N
        btnEliminarCli.setText("Eliminar");
        btnEliminarCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarCliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarCliMouseExited(evt);
            }
        });
        btnEliminarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCliActionPerformed(evt);
            }
        });

        btnLimpiarCam.setBackground(new java.awt.Color(0, 0, 0));
        btnLimpiarCam.setFont(new java.awt.Font("Segoe UI", 3, 13)); // NOI18N
        btnLimpiarCam.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/limpiaar.png"))); // NOI18N
        btnLimpiarCam.setText("Limpiar");
        btnLimpiarCam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarCamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiarCamMouseExited(evt);
            }
        });
        btnLimpiarCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarCamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(btnGuardarCli)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditarCli)
                .addGap(11, 11, 11)
                .addComponent(btnLimpiarCam, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarCli)
                    .addComponent(btnEliminarCli)
                    .addComponent(btnLimpiarCam, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarCli))
                .addGap(81, 81, 81))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordenación", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        btnBurbujaDNi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBurbujaDNi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Clienteord.png"))); // NOI18N
        btnBurbujaDNi.setText(" Orden por DNI");
        btnBurbujaDNi.setMaximumSize(new java.awt.Dimension(192, 51));
        btnBurbujaDNi.setMinimumSize(new java.awt.Dimension(192, 51));
        btnBurbujaDNi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBurbujaDNiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBurbujaDNiMouseExited(evt);
            }
        });
        btnBurbujaDNi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBurbujaDNiActionPerformed(evt);
            }
        });

        btnApellidoP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnApellidoP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Clienteord.png"))); // NOI18N
        btnApellidoP.setText("Orden por Apellido P.");
        btnApellidoP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnApellidoPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnApellidoPMouseExited(evt);
            }
        });
        btnApellidoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApellidoPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnApellidoP)
                    .addComponent(btnBurbujaDNi, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnBurbujaDNi, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(txtApelliPCli, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtApelliCliM, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(txtDniCli, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7))
                                .addGap(46, 46, 46)
                                .addComponent(jLabel4)
                                .addGap(68, 68, 68)
                                .addComponent(txtNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDClien, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDniCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtIDClien, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtApelliPCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtApelliCliM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Registro Clientes", jPanel2);

        jPanel3.setBackground(new java.awt.Color(44, 62, 80));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nombre de Pizza");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tipo de Pizza");

        cbxTipoPedidoi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Americana", "Peperoni", "Hawaiana", "Vegetariana", "Chilli Pepper" }));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tamaño");

        cbxTamañoPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Personal", "Mediana", "Grande", "Familiar" }));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Cantidad");

        txtCantPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantPedidoActionPerformed(evt);
            }
        });
        txtCantPedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantPedidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantPedidoKeyTyped(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Precio");

        txtPrecioP.setEditable(false);

        btnElimPed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnElimPed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnElimPedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnElimPedMouseExited(evt);
            }
        });
        btnElimPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimPedActionPerformed(evt);
            }
        });

        tblPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N-Pizza", "Código", "Tipo de Pizza", "Tamaño", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane2.setViewportView(tblPedido);

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("DNI");

        txtDniP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniPActionPerformed(evt);
            }
        });
        txtDniP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniPKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniPKeyTyped(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Nombre");

        txtNombreCLiPedid.setEditable(false);

        btnGenerarPed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/generarpedidoo.png"))); // NOI18N
        btnGenerarPed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGenerarPedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGenerarPedMouseExited(evt);
            }
        });
        btnGenerarPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPedActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/bolsadinero.png"))); // NOI18N
        jLabel18.setText("Monto Total");

        lblTotalPagar.setText("---");

        txtTelefonoPed.setEditable(false);
        txtTelefonoPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoPedActionPerformed(evt);
            }
        });
        txtTelefonoPed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoPedKeyTyped(evt);
            }
        });

        txtStockdispo.setEditable(false);

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Stock Disponible");

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Telefono");

        txtApellidoMaPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoMaPedidoActionPerformed(evt);
            }
        });

        cbxNombreProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNombreProductoItemStateChanged(evt);
            }
        });
        cbxNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNombreProductoActionPerformed(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Codigo");

        txtCodpedidoP.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDniP, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtApellidoPaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(txtApellidoMaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtNombreCLiPedid, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(txtTelefonoPed, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addComponent(btnGenerarPed, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtIdPediV, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(110, 110, 110)
                                        .addComponent(jLabel33)
                                        .addGap(75, 75, 75)
                                        .addComponent(jLabel12)
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabel13)
                                        .addGap(68, 68, 68)
                                        .addComponent(jLabel14))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cbxNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCodpedidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(cbxTipoPedidoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(cbxTamañoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCantPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecioP, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStockdispo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel28))
                                .addGap(19, 19, 19)
                                .addComponent(btnElimPed))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtIdPediV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbxTipoPedidoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCodpedidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbxTamañoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtCantPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPrecioP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtStockdispo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbxNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnElimPed))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(6, 6, 6)
                        .addComponent(txtDniP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel29))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCLiPedid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoPed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGenerarPed)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellidoPaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellidoMaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedido Pizza", jPanel3);

        jPanel4.setBackground(new java.awt.Color(44, 62, 80));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Codigo:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tipo de Pizza:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tamaño de Pizza:");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Cantidad:");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Precio:");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 51, -1));

        txtCodPizza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPizzaKeyTyped(evt);
            }
        });
        jPanel4.add(txtCodPizza, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 120, -1));

        txtCantPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantProKeyTyped(evt);
            }
        });
        jPanel4.add(txtCantPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 105, -1));

        cbxTipoPizzaPro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Americana", "Peperoni", "Hawaiana", "Vegetariana", "Chilli Pepper" }));
        cbxTipoPizzaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoPizzaProActionPerformed(evt);
            }
        });
        jPanel4.add(cbxTipoPizzaPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 99, -1));

        cbxTamPizzaPro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Personal", "Mediana", "Grande", "Familiar" }));
        jPanel4.add(cbxTamPizzaPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 99, -1));

        txtPrecioPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProKeyTyped(evt);
            }
        });
        jPanel4.add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 105, -1));

        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre de Pizza", "Codigo", "Tipo de Pizza", "Tamaño de Pizza", "Stock", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProducto);
        if (tblProducto.getColumnModel().getColumnCount() > 0) {
            tblProducto.getColumnModel().getColumn(0).setPreferredWidth(55);
            tblProducto.getColumnModel().getColumn(1).setPreferredWidth(135);
            tblProducto.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 630, 350));

        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/guardar50px.png"))); // NOI18N
        btnGuardarPro.setMaximumSize(new java.awt.Dimension(45, 46));
        btnGuardarPro.setMinimumSize(new java.awt.Dimension(45, 46));
        btnGuardarPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarProMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarProMouseExited(evt);
            }
        });
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 60, 60));

        btnEditarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/actualizar50px.png"))); // NOI18N
        btnEditarProd.setMaximumSize(new java.awt.Dimension(45, 46));
        btnEditarProd.setMinimumSize(new java.awt.Dimension(45, 46));
        btnEditarProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarProdMouseExited(evt);
            }
        });
        btnEditarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProdActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 60, 60));

        btnLimpiarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/limpiaar.png"))); // NOI18N
        btnLimpiarPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLimpiarProMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLimpiarProMouseExited(evt);
            }
        });
        btnLimpiarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarProActionPerformed(evt);
            }
        });
        jPanel4.add(btnLimpiarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 410, 60, 60));

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminarcirculo (1).png"))); // NOI18N
        btnEliminarPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarProMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarProMouseExited(evt);
            }
        });
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 60, 60));
        jPanel4.add(txtIDprodc, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 8, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Descripción: ");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtDescripcionProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProdActionPerformed(evt);
            }
        });
        txtDescripcionProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProdKeyTyped(evt);
            }
        });
        jPanel4.add(txtDescripcionProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 120, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Busqueda Secuencial", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(255, 153, 51));

        btnBusSecuencial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBusSecuencial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/buscarMeto_46.png"))); // NOI18N
        btnBusSecuencial.setText("Busqueda por Codigo");
        btnBusSecuencial.setContentAreaFilled(false);
        btnBusSecuencial.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBusSecuencial.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBusSecuencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusSecuencialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBusSecuencial, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnBusSecuencial, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 260, 110));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordenar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14))); // NOI18N

        btnMenorStock.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMenorStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/bajoStock.png"))); // NOI18N
        btnMenorStock.setText("Producto con menor Stock");
        btnMenorStock.setContentAreaFilled(false);
        btnMenorStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMenorStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMenorStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenorStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenorStock, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMenorStock, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 260, 110));

        jTabbedPane1.addTab("Producto", jPanel4);

        REPORTE_HISTVENTA.setBackground(new java.awt.Color(44, 62, 80));
        REPORTE_HISTVENTA.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblVentasPedidos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblVentasPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Vendedor", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVentasPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentasPedidosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblVentasPedidos);

        REPORTE_HISTVENTA.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 34, 670, 250));

        btnPdfVent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPdfVent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Mostrar-Ojito.png"))); // NOI18N
        btnPdfVent.setText("Mostrar Venta");
        btnPdfVent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPdfVent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPdfVent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPdfVentMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPdfVentMouseExited(evt);
            }
        });
        btnPdfVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentActionPerformed(evt);
            }
        });
        REPORTE_HISTVENTA.add(btnPdfVent, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, 91));
        REPORTE_HISTVENTA.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1113, 6, 3, -1));

        btnMenoraMayor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMenoraMayor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/totalll.png"))); // NOI18N
        btnMenoraMayor.setText("Mayor consumo");
        btnMenoraMayor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMenoraMayor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMenoraMayor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMenoraMayorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMenoraMayorMouseExited(evt);
            }
        });
        btnMenoraMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenoraMayorActionPerformed(evt);
            }
        });
        REPORTE_HISTVENTA.add(btnMenoraMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, 91));

        ReportePDF.setBackground(new java.awt.Color(255, 255, 255));
        ReportePDF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnReporte.setBackground(new java.awt.Color(255, 102, 102));
        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setText("STOCK");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel42.setText("Reporte de Datos");

        btnClienteReporte.setBackground(new java.awt.Color(255, 102, 102));
        btnClienteReporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClienteReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnClienteReporte.setText("ClIENTES");
        btnClienteReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteReporteActionPerformed(evt);
            }
        });

        cmBoxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Clts. Activo", "Clts. Desactivo", " " }));
        cmBoxClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmBoxClientesActionPerformed(evt);
            }
        });

        comboBoxSTOCK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stk. Activo", "Stk. No Activo", "\t" }));
        comboBoxSTOCK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSTOCKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ReportePDFLayout = new javax.swing.GroupLayout(ReportePDF);
        ReportePDF.setLayout(ReportePDFLayout);
        ReportePDFLayout.setHorizontalGroup(
            ReportePDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportePDFLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(46, 46, 46))
            .addGroup(ReportePDFLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(ReportePDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReportePDFLayout.createSequentialGroup()
                        .addComponent(btnReporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(comboBoxSTOCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ReportePDFLayout.createSequentialGroup()
                        .addComponent(btnClienteReporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmBoxClientes, 0, 1, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ReportePDFLayout.setVerticalGroup(
            ReportePDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportePDFLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ReportePDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReporte)
                    .addComponent(comboBoxSTOCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(ReportePDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClienteReporte)
                    .addComponent(cmBoxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        REPORTE_HISTVENTA.add(ReportePDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 230, 170));
        ReportePDF.getAccessibleContext().setAccessibleName("");

        ReportePDF1.setBackground(new java.awt.Color(255, 255, 255));

        btnreport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnreport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/report64px.png"))); // NOI18N
        btnreport.setText("Ventas");
        btnreport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnreport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnreport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnreportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnreportMouseExited(evt);
            }
        });
        btnreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportActionPerformed(evt);
            }
        });

        btnGraficar.setBackground(new java.awt.Color(0, 0, 0));
        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/gCircular32px.png"))); // NOI18N
        btnGraficar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGraficarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGraficarMouseExited(evt);
            }
        });
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        Midate.setBackground(new java.awt.Color(204, 204, 204));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("Seleccionar:");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel44.setText("Visualización de Datos ");

        javax.swing.GroupLayout ReportePDF1Layout = new javax.swing.GroupLayout(ReportePDF1);
        ReportePDF1.setLayout(ReportePDF1Layout);
        ReportePDF1Layout.setHorizontalGroup(
            ReportePDF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportePDF1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel44)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(ReportePDF1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnreport, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ReportePDF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReportePDF1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(ReportePDF1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel34)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        ReportePDF1Layout.setVerticalGroup(
            ReportePDF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportePDF1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ReportePDF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReportePDF1Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ReportePDF1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnreport, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        REPORTE_HISTVENTA.add(ReportePDF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, 470, 150));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Reportes:");
        REPORTE_HISTVENTA.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 8, -1, -1));

        jTabbedPane1.addTab("Historial de Ventas", REPORTE_HISTVENTA);

        jPanel11.setBackground(new java.awt.Color(44, 62, 80));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(45, 36, 31));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("NUEVO USUARIO");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel40)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel40)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(45, 36, 31));
        jLabel37.setText("Rol:");

        jPanel17.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        txtNombre.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre.setBorder(null);

        jLabel36.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(45, 36, 31));
        jLabel36.setText("Nombres y Apellidos:");

        jPanel16.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        txtPass.setBackground(new java.awt.Color(204, 204, 204));
        txtPass.setBorder(null);

        jLabel38.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(45, 36, 31));
        jLabel38.setText("Contraseña:");

        jPanel15.setBackground(new java.awt.Color(0, 110, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        txtCorreo.setBackground(new java.awt.Color(204, 204, 204));
        txtCorreo.setBorder(null);
        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(45, 36, 31));
        jLabel39.setText("Correo Electrónico:");

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));

        btnIniciar.setBackground(new java.awt.Color(45, 36, 31));
        btnIniciar.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Registrar");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIniciarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIniciarMouseExited(evt);
            }
        });
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setBackground(new java.awt.Color(45, 36, 31));
        btnEliminarUsuario.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        btnEliminarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarUsuario.setText("Eliminar");
        btnEliminarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarUsuarioMouseExited(evt);
            }
        });
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(1, 1, 1)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Correo", "rol"
            }
        ));
        TableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableUsuarios);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jPanel11);

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/ppizza120px.png"))); // NOI18N
        jPanel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 370, -1, -1));

        lblRucPPiz.setBackground(new java.awt.Color(255, 255, 255));
        lblRucPPiz.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblRucPPiz.setForeground(new java.awt.Color(255, 255, 255));
        lblRucPPiz.setText("100082237233");
        jPanel5.add(lblRucPPiz, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 140, -1));

        lblDireccPpiza.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccPpiza.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblDireccPpiza.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccPpiza.setText("Av. Las Palmeras, Los Olivos 15304, Lima-Peru");
        jPanel5.add(lblDireccPpiza, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, -1, -1));

        lblTelefonoEmpre.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoEmpre.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblTelefonoEmpre.setForeground(new java.awt.Color(255, 255, 255));
        lblTelefonoEmpre.setText("988654212");
        jPanel5.add(lblTelefonoEmpre, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 200, -1, -1));

        jLabel9.setBackground(new java.awt.Color(153, 255, 153));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("RUC");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Dirección");
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 270, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Nombre de La Empresa");
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Razón Social");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Teléfono");
        jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        lblRazonPpiza.setBackground(new java.awt.Color(255, 255, 255));
        lblRazonPpiza.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblRazonPpiza.setForeground(new java.awt.Color(255, 255, 255));
        lblRazonPpiza.setText("Ppiza SAC");
        jPanel5.add(lblRazonPpiza, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        lblNombPizza.setBackground(new java.awt.Color(255, 255, 255));
        lblNombPizza.setFont(new java.awt.Font("Segoe UI", 0, 19)); // NOI18N
        lblNombPizza.setForeground(new java.awt.Color(255, 255, 255));
        lblNombPizza.setText("Ppizza");
        jPanel5.add(lblNombPizza, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/negro2.png"))); // NOI18N
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, 90, 510));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/pizzainfo.png"))); // NOI18N
        jPanel5.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 510));

        jTabbedPane1.addTab("Información Ppizza", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 960, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidoActionPerformed
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnPedidoActionPerformed

    private void txtDniPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniPActionPerformed

    private void btnEliminarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCliActionPerformed
        if (!"".equals(txtIDClien.getText())) {
            // Mostrar una ventana de confirmación con JOptionPane al usuario antes de eliminar el cliente
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este cliente?");
            if (pregunta == 0) {
                String id = txtIDClien.getText(); // Obtener el ID como String
                // Llamar al método ElimCliente en el objeto client para eliminar el cliente
                client.ElimCliente(id);
                LimpiarTable(); // Limpiar la tabla de clientes
                LimpiarCliente(); // Limpiar los campos de texto relacionados con el cliente
                ListarCliente(); // Volver a cargar la lista de clientes en la tabla
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID del cliente a eliminar");
        }
    }//GEN-LAST:event_btnEliminarCliActionPerformed

    private void btnGuardarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCliActionPerformed
        // Verifica que los campos no estén vacíos
        if (!"".equals(txtDniCli.getText()) && !"".equals(txtNombreCli.getText()) && !"".equals(txtApelliPCli.getText()) && !"".equals(txtTelefonoCli.getText())) {
            // Verifica que el DNI y el teléfono sean numéricos
            if (isNumeric(txtDniCli.getText()) && isNumeric(txtTelefonoCli.getText())) {
                String dni = txtDniCli.getText();
                String telefono = txtTelefonoCli.getText();
                // Verifica que el DNI tenga 8 dígitos y el teléfono tenga 9 dígitos
                if (dni.length() == 8 && telefono.length() == 9) {
                    // Obtener los datos del cliente de los campos de texto
                    cl.setDni(Integer.parseInt(dni));
                    cl.setNombre(txtNombreCli.getText());
                    cl.setPaterno(txtApelliPCli.getText());
                    cl.setMaterno(txtApelliCliM.getText());
                    cl.setTelefono(Integer.parseInt(telefono));
                    cl.setActivo(true); // Establecer el campo activo a true al registrar un nuevo cliente
                    // Llamar al método Registrar en el objeto client para registrar el cliente
                    client.Registrar(cl);
                    LimpiarCliente(); // Limpiar los campos de texto relacionados con el cliente
                    LimpiarTable(); // Limpiar la tabla de clientes
                    ListarCliente(); // Volver a cargar la lista de clientes en la tabla
                    JOptionPane.showMessageDialog(null, "Registrado con éxito");
                } else {
                    if (dni.length() != 8) {
                        JOptionPane.showMessageDialog(null, "El DNI debe tener 8 dígitos");
                    }
                    if (telefono.length() != 9) {
                        JOptionPane.showMessageDialog(null, "El teléfono debe tener 9 dígitos");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El DNI y el teléfono solo pueden contener números");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        }
    }

// Método para verificar si una cadena es numérica
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }//GEN-LAST:event_btnGuardarCliActionPerformed

    private void btnEditarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdActionPerformed
        if (txtIDprodc.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una Fila");
        } else {
            try {
                String idProducto = txtIDprodc.getText();
                String descProdText = txtDescripcionProd.getText();
                String codigoText = txtCodPizza.getText();

                if (codigoText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un código válido");
                    return;
                }

                // Obtener el producto actual para editar
                Producto pro = new Producto();
                pro.setId(idProducto);
                pro.setDescPro(descProdText);
                pro.setCodigoP(codigoText);
                pro.setTipoPizza(cbxTipoPizzaPro.getSelectedItem().toString());
                pro.setTamañoPizza(cbxTamPizzaPro.getSelectedItem().toString());
                pro.setCantidad(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));

                // Llamar al método para modificar el producto
                if (proDao.modificar(pro)) {
                    LimpiarTable();
                    LimpiarProductos();
                    ListarProductos();
                    cargarDescripcionesProductos(); // Actualizar el JComboBox de productos
                    JOptionPane.showMessageDialog(null, "Producto modificado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar el producto");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese números válidos en Cantidad y Precio");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al modificar el producto: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnEditarProdActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        confirmarSalida();

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnElimPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimPedActionPerformed
        modelo = (DefaultTableModel) tblPedido.getModel();
        modelo.removeRow(tblPedido.getSelectedRow());
        TotalPagar();
        cbxNombreProducto.requestFocus();
    }//GEN-LAST:event_btnElimPedActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        LimpiarTable();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        int fila = tblCliente.rowAtPoint(evt.getPoint());
        txtIDClien.setText(tblCliente.getValueAt(fila, 0).toString());
        txtDniCli.setText(tblCliente.getValueAt(fila, 1).toString());
        txtNombreCli.setText(tblCliente.getValueAt(fila, 2).toString());
        txtApelliPCli.setText(tblCliente.getValueAt(fila, 3).toString());
        txtApelliCliM.setText(tblCliente.getValueAt(fila, 4).toString());
        txtTelefonoCli.setText(tblCliente.getValueAt(fila, 5).toString());


    }//GEN-LAST:event_tblClienteMouseClicked

    private void btnEditarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCliActionPerformed
        if (txtIDClien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una Fila");
        } else {
            String dniText = txtDniCli.getText();
            String telefonoText = txtTelefonoCli.getText();

            if (!dniText.isEmpty() && !telefonoText.isEmpty()) {
                try {
                    int dni = Integer.parseInt(dniText);
                    int telefono = Integer.parseInt(telefonoText);

                    cl.setDni(dni);
                    cl.setNombre(txtNombreCli.getText());
                    cl.setPaterno(txtApelliPCli.getText());
                    cl.setMaterno(txtApelliCliM.getText());
                    cl.setTelefono(telefono);
                    cl.setId(txtIDClien.getText()); // Asigna directamente el texto al ID

                    client.modificar(cl);
                    LimpiarTable();
                    LimpiarCliente();
                    ListarCliente();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error Solo se permiten números");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacíos");
            }
        }
    }//GEN-LAST:event_btnEditarCliActionPerformed

    private void btnLimpiarCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarCamActionPerformed
        LimpiarCliente();
    }//GEN-LAST:event_btnLimpiarCamActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        // Verificar si alguno de los campos está vacío
        if (!"".equals(txtDescripcionProd.getText()) && !"".equals(txtCodPizza.getText())
                && cbxTipoPizzaPro.getSelectedItem() != null && cbxTamPizzaPro.getSelectedItem() != null
                && !"".equals(txtCantPro.getText()) && !"".equals(txtPrecioPro.getText())) {

            // Configurar el objeto Producto con los datos del formulario
            Producto pro = new Producto();
            pro.setDescPro(txtDescripcionProd.getText());
            pro.setCodigoP(txtCodPizza.getText());
            pro.setTipoPizza(cbxTipoPizzaPro.getSelectedItem().toString());
            pro.setTamañoPizza(cbxTamPizzaPro.getSelectedItem().toString());
            pro.setCantidad(Integer.parseInt(txtCantPro.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            pro.setActivo(true); // Asumiendo que por defecto el producto está activo

            // Registrar el producto en la base de datos
            boolean registrado = proDao.Registrar(pro);

            if (registrado) {
                // Limpiar campos y actualizar la tabla de productos
                LimpiarProductos();
                LimpiarTable();
                ListarProductos();

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Producto Registrado con éxito");

                // Actualizar el JComboBox de descripciones de productos
                cargarDescripcionesProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el producto");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");
        }
    }//GEN-LAST:event_btnGuardarProActionPerformed


    private void cbxTipoPizzaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoPizzaProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTipoPizzaProActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked
        // este codigo lo que hace es extraer el valor de de cada columna de esa fila.
        int fila = tblProducto.rowAtPoint(evt.getPoint());
        txtIDprodc.setText(tblProducto.getValueAt(fila, 0).toString());
        txtDescripcionProd.setText(tblProducto.getValueAt(fila, 1).toString());
        txtCodPizza.setText(tblProducto.getValueAt(fila, 2).toString());
        cbxTipoPizzaPro.setSelectedItem(tblProducto.getValueAt(fila, 3).toString());
        cbxTamPizzaPro.setSelectedItem(tblProducto.getValueAt(fila, 4).toString());
        txtCantPro.setText(tblProducto.getValueAt(fila, 5).toString());
        txtPrecioPro.setText(tblProducto.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_tblProductoMouseClicked

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        if (!"".equals(txtIDprodc.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este producto?", "Eliminar Producto", JOptionPane.WARNING_MESSAGE);
            if (pregunta == 0) {
                String id = txtIDprodc.getText(); // Obtener el ID como String
                boolean eliminado = proDao.EliminarPro(id);
                if (eliminado) {
                    LimpiarTable();
                    LimpiarProductos();
                    ListarProductos(); // Actualiza la tabla de productos
                    JOptionPane.showMessageDialog(null, "Producto marcado como inactivo");

                    // Actualizar el JComboBox de descripciones de productos
                    cargarDescripcionesProductos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al marcar el producto como inactivo");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese el ID del producto a eliminar");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnLimpiarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarProActionPerformed
        LimpiarProductos();
    }//GEN-LAST:event_btnLimpiarProActionPerformed

    private void txtCantPedidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantPedidoKeyPressed
        // Verificar que el campo "txtCantPedido" no esté vacío.
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantPedido.getText())) {
                String nomPedi = cbxNombreProducto.getSelectedItem().toString();
                String cod = txtCodpedidoP.getText();
                String tipoPizz = cbxTipoPedidoi.getSelectedItem().toString();
                String tamPizz = cbxTamañoPedido.getSelectedItem().toString();
                int cant = Integer.parseInt(txtCantPedido.getText());
                double precio = Double.parseDouble(txtPrecioP.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockdispo.getText());

                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) tblPedido.getModel();
                    for (int i = 0; i < tblPedido.getRowCount(); i++) {
                        if (tblPedido.getValueAt(i, 3).equals(cbxTipoPedidoi.getSelectedItem()) && tblPedido.getValueAt(i, 4).equals(cbxTamañoPedido.getSelectedItem())) {
                            return;
                        }

                    }
                    // Crear una lista para almacenar los datos del pedido
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(nomPedi);
                    lista.add(cod);
                    lista.add(tipoPizz);
                    lista.add(tamPizz);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[7];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    O[5] = lista.get(6);
                    O[6] = lista.get(7);
                    tmp.addRow(O);
                    tblPedido.setModel(tmp);
                    TotalPagar();
                    LimpiarPedido();
                    cbxNombreProducto.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no Disponible");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantPedidoKeyPressed

    private void txtTelefonoPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoPedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoPedActionPerformed

    private void txtDniPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniPKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtDniP.getText())) {
                int dni = Integer.parseInt(txtDniP.getText());
                cl = client.BuscarCliente(dni);
                if (cl.getNombre() != null) {
                    if (cl.isActivo()) { // Verificar si el cliente está activo
                        txtNombreCLiPedid.setText("" + cl.getNombre());
                        txtTelefonoPed.setText("" + cl.getTelefono());
                        txtApellidoPaPedido.setText("" + cl.getPaterno());
                        txtApellidoMaPedido.setText("" + cl.getMaterno());
                    } else {
                        txtDniP.setText("");
                        JOptionPane.showMessageDialog(null, "El Cliente está inactivo y no puede realizar pedidos");
                    }
                } else {
                    txtDniP.setText("");
                    JOptionPane.showMessageDialog(null, "El Cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtDniPKeyPressed

    private void btnGenerarPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPedActionPerformed
        if (tblPedido.getRowCount() > 0) {
            if (!"".equals(txtNombreCLiPedid.getText())) {
                // Verificar si el cliente está activo
                if (cl.isActivo()) {
                    ActualizarStock();
                    // Verificar si el pedido se realizó con éxito
                    boolean pedidoExitoso = verificarPedidoExitoso();

                    if (pedidoExitoso) {
                        RegistrarPedidoV();
                        RegistrarDetalle();
                        pdf();
                        LimpiarTablePedidoV();
                        LimpiarClientePedidoV();
                        JOptionPane.showMessageDialog(null, "Factura creada con éxito");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El Cliente está inactivo y no puede realizar pedidos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        }
    }//GEN-LAST:event_btnGenerarPedActionPerformed
    private boolean verificarPedidoExitoso() {
        // Verificar si todos los productos tuvieron stock suficiente
        for (int i = 0; i < tblPedido.getRowCount(); i++) {
            int cant = Integer.parseInt(tblPedido.getValueAt(i, 4).toString());
            if (cant == 0) {
                return false; // Al menos un producto no tuvo stock suficiente
            }
        }
        return true; // Todos los productos tuvieron stock suficiente
    }

    private void ActualizarStock() {
        PedidoDao pedidao = new PedidoDao();
        boolean pedidoExitoso = true; // Variable para verificar si el pedido se realizó con éxito

        for (int i = 0; i < tblPedido.getRowCount(); i++) {
            String cod = tblPedido.getValueAt(i, 1).toString();
            int cant = Integer.parseInt(tblPedido.getValueAt(i, 4).toString());

            if (cant == 0) {
                JOptionPane.showMessageDialog(null, "No se puede hacer el pedido para el producto, ya que no hay stock disponible ", "Error", JOptionPane.ERROR_MESSAGE);
                pedidoExitoso = false; // Cambiar el valor de la variable a false
                continue; // Salta a la siguiente iteración del bucle
            }

            // Verificar el stock actual del producto
            int stockActual = pedidao.obtenerStockProducto(cod); // 

            // Verificar si la cantidad a restar supera el stock actual
            if (cant <= stockActual) {
                // Actualizar stock del producto
                boolean actualizado = pedidao.ActualizarStock(cant, cod);

                if (actualizado) {
                    System.out.println("Stock actualizado para el producto con código " + cod);
                } else {
                    System.out.println("Error al actualizar el stock para el producto con código " + cod);
                }
            } else {
                System.out.println("No se puede ejecutar el pedido  " + cod);
            }
        }

        if (pedidoExitoso) {
            JOptionPane.showMessageDialog(null, "Pedido registrado con exito");
        }
    }
    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        LimpiarTable();
        ListarPedidosVenta();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoActionPerformed

        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnInfoActionPerformed

    private void cbxNombreProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNombreProductoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxNombreProductoItemStateChanged

    private void txtCantPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantPedidoActionPerformed

        String nomProSeleccionado = (String) cbxNombreProducto.getSelectedItem();
        if (nomProSeleccionado != null) {
            Producto productoSeleccionado = proDao.BuscarPro(nomProSeleccionado);
            if (productoSeleccionado != null) {
                cbxTipoPedidoi.setSelectedItem(productoSeleccionado.getTipoPizza());
                cbxTamañoPedido.setSelectedItem(productoSeleccionado.getTamañoPizza());
                txtPrecioP.setText(String.valueOf(productoSeleccionado.getPrecio()));
                txtStockdispo.setText(String.valueOf(productoSeleccionado.getCantidad()));
                txtCantPedido.requestFocus();

            }
        }
    }//GEN-LAST:event_txtCantPedidoActionPerformed

    private void cbxNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxNombreProductoActionPerformed

    private void txtCantPedidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantPedidoKeyTyped
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtCantPedidoKeyTyped

    private void txtDniPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniPKeyTyped
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtDniPKeyTyped

    private void txtTelefonoPedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoPedKeyTyped
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtTelefonoPedKeyTyped

    private void txtTelefonoCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoCliKeyTyped
        //LIMITE DE CARACTERES
        if (txtTelefonoCli.getText().length() >= 9) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoCliKeyTyped

    private void txtDniCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniCliKeyTyped
        // Verifica la longitud actual del campo
        if (txtDniCli.getText().length() >= 8) {
            evt.consume();
        }
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtDniCliKeyTyped

    private void txtPrecioProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProKeyTyped
        event.numberSoloDecimales(evt, txtPrecioPro);
    }//GEN-LAST:event_txtPrecioProKeyTyped

    private void txtCodPizzaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPizzaKeyTyped
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtCodPizzaKeyTyped

    private void txtCantProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantProKeyTyped
        event.numberSoloNumeros(evt);
    }//GEN-LAST:event_txtCantProKeyTyped

    private void txtDescripcionProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProdKeyTyped
        event.textSoloCaracteres(evt);
    }//GEN-LAST:event_txtDescripcionProdKeyTyped

    private void txtNombreCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCliKeyTyped
        event.textSoloCaracteres(evt);
    }//GEN-LAST:event_txtNombreCliKeyTyped

    private void txtApelliPCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApelliPCliKeyTyped
        event.textSoloCaracteres(evt);
    }//GEN-LAST:event_txtApelliPCliKeyTyped

    private void txtApelliCliMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApelliCliMKeyTyped
        event.textSoloCaracteres(evt);
    }//GEN-LAST:event_txtApelliCliMKeyTyped

    private void tblVentasPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentasPedidosMouseClicked
        // TODO add your handling code here:
        int fila = tblVentasPedidos.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(tblVentasPedidos.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tblVentasPedidosMouseClicked

    private void btnPdfVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentActionPerformed
        int selectedRow = tblVentasPedidos.getSelectedRow();
        if (selectedRow == -1) {
            // Si no hay fila seleccionada, mostrar una advertencia que no se ha seleccionado nada
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila primero.", "Fila no seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Si hay una fila seleccionada, Se muestra el pdf de la fila que se selecciono
        try {
            int id = Integer.parseInt(txtIdVenta.getText());
            File file = new File("src/pdf/pedidoVen" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Formulario_Pizza.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfVentActionPerformed

    private void btnBurbujaDNiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBurbujaDNiActionPerformed
        // Verifica si la tabla está vacía
        if (tblCliente.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para ordenar.", "Tabla vacía", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén la lista de clientes desde el objeto client
            List<Cliente> ListCl = client.ListadoCliente();

            // Llamada al método bubbleSort de la instancia metodosBusquedas para ordenar la lista
            metodosBusquedas.bubbleSort(ListCl);

            // Limpia el modelo de la tabla
            modelo.setRowCount(0);

            // Rellena la tabla con los DNIs ordenados
            for (Cliente cliente : ListCl) {
                Object[] ob = new Object[6];
                ob[0] = cliente.getId();
                ob[1] = cliente.getDni();
                ob[2] = cliente.getNombre();
                ob[3] = cliente.getPaterno();
                ob[4] = cliente.getMaterno();
                ob[5] = cliente.getTelefono();
                modelo.addRow(ob);
            }

            // Establece el modelo actualizado en la tabla
            tblCliente.setModel(modelo);
        }
    }//GEN-LAST:event_btnBurbujaDNiActionPerformed

    private void btnApellidoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApellidoPActionPerformed
        if (tblCliente.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para ordenar.", "Tabla vacía", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén la lista de clientes desde el objeto client
            List<Cliente> ListCl = client.ListadoCliente();

            // Limpia el modelo de la tabla
            modelo.setRowCount(0);

            // Llama al método shellSortApelliPater para ordenar la lista de clientes
            metodosBusquedas.shellSortApelliPater(ListCl);

            // Rellenando la tabla
            for (Cliente cliente : ListCl) {
                Object[] ob = new Object[6];
                ob[0] = cliente.getId();
                ob[1] = cliente.getDni();
                ob[2] = cliente.getNombre();
                ob[3] = cliente.getPaterno();
                ob[4] = cliente.getMaterno();
                ob[5] = cliente.getTelefono();
                modelo.addRow(ob);
            }

            // Establece el modelo actualizado en la tabla
            tblCliente.setModel(modelo);
        }
    }//GEN-LAST:event_btnApellidoPActionPerformed

    private void btnBusSecuencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusSecuencialActionPerformed

        String codigoBuscado = JOptionPane.showInputDialog(null, "Ingrese el código del producto:", "Búsqueda de Producto", JOptionPane.QUESTION_MESSAGE);

        if (codigoBuscado != null && !codigoBuscado.isEmpty()) {
            // Se realiza la busqueda
            Producto productoEncontrado = metodosBusquedas.buscarProductoPorCodigo(codigoBuscado);

            if (productoEncontrado != null) {
                String msj = "Descripción: " + productoEncontrado.getDescPro() + "\n"
                        + "Tamaño de Pizza: " + productoEncontrado.getTamañoPizza() + "\n"
                        + "Cantidad: " + productoEncontrado.getCantidad() + "\n"
                        + "Precio: " + productoEncontrado.getPrecio();

                JOptionPane.showMessageDialog(null, msj, "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
            } else {

                JOptionPane.showMessageDialog(null, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBusSecuencialActionPerformed

    private void btnMenoraMayorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenoraMayorActionPerformed

        if (tblVentasPedidos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para ordenar.", "Tabla vacía", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén la lista de pedidos desde donde sea que la tengas (ListPedidoVenta)

            List<Pedido> ListPedidoVenta = peDao.ListadoPedidos(); // Obtener la lista de pedidos desde el objeto peDao

            // Limpia el modelo de la tabla
            modelo.setRowCount(0);

            // Llama al método de selección para ordenar la lista de pedidos por total
            metodosBusquedas.ordenarPedidosPorTotalDescendente(ListPedidoVenta);

            // Rellenando la tabla
            for (Pedido pedido : ListPedidoVenta) {
                Object[] ob = new Object[4];
                ob[0] = pedido.getId();
                ob[1] = pedido.getCliente();
                ob[2] = pedido.getVendedor();
                ob[3] = pedido.getTotal();

                modelo.addRow(ob); // Agregar una nueva fila a la tabla con los datos del pedido
            }

            tblVentasPedidos.setModel(modelo); // Establecer el modelo actualizado en la tabla tblVentasPedidos
        }
    }//GEN-LAST:event_btnMenoraMayorActionPerformed

    private void btnMenorStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenorStockActionPerformed
        if (tblProducto.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para ordenar.", "Tabla vacía", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén la lista de productos desde donde sea que la tengas (Listpro)
            List<Producto> Listpro = proDao.ListadoPro();
            // Llama al método para ordenar la lista de productos por cantidad ascendente
            metodosBusquedas.ordenarPorCantidadAscendente(Listpro); // Reemplaza Listpro con tu lista de productos

            // Limpia el modelo de la tabla
            modelo.setRowCount(0);

            // Rellenando la tabla con los productos ordenados por cantidad
            for (Producto producto : Listpro) {
                Object[] ob = new Object[7];
                ob[0] = producto.getId();
                ob[1] = producto.getDescPro();
                ob[2] = producto.getCodigoP();
                ob[3] = producto.getTipoPizza();
                ob[4] = producto.getTamañoPizza();
                ob[5] = producto.getCantidad();
                ob[6] = producto.getPrecio();

                modelo.addRow(ob); // Agregar una nueva fila a la tabla con los datos del producto
            }

            tblProducto.setModel(modelo); // Establecer el modelo actualizado en la tabla tblProducto
        }
    }//GEN-LAST:event_btnMenorStockActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() || txtPass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
        } else {
            String correo = txtCorreo.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtNombre.getText();
            String rol = cbxRol.getSelectedItem().toString();

            // Validación del correo electrónico
            if (!(correo.endsWith("@ppizza.pe"))) {
                JOptionPane.showMessageDialog(null, "¡No es un correo valido!");
            } else if (pass.length() < 8) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres");
            } else if (login.correoExistente(correo)) {
                JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado");
            } else {
                // Si el correo y la contraseña son válidos, proceder con el registro
                Login lg = new Login();
                lg.setNombre(nom);
                lg.setCorreo(correo);
                lg.setPass(pass);
                lg.setRol(rol);
                lg.setActivo(true); // Por defecto, el usuario registrado está activo

                if (login.Registrar(lg)) {
                    LimpiarTable();  // Limpiar la tabla antes de cargar nuevos datos
                    ListarUsuarios();  // Volver a listar los usuarios actualizados
                    JOptionPane.showMessageDialog(null, "Usuario Registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar usuario");
                }
            }
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListarUsuarios();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnGuardarCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCliMouseEntered
        btnGuardarCli.setBackground(new Color(84, 153, 199));
    }//GEN-LAST:event_btnGuardarCliMouseEntered

    private void btnGuardarCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarCliMouseExited
        btnGuardarCli.setBackground(Color.black);
    }//GEN-LAST:event_btnGuardarCliMouseExited

    private void btnEditarCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarCliMouseEntered
        btnEditarCli.setBackground(new Color(28, 150, 26));
    }//GEN-LAST:event_btnEditarCliMouseEntered

    private void btnEditarCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarCliMouseExited
        btnEditarCli.setBackground(Color.black);
    }//GEN-LAST:event_btnEditarCliMouseExited

    private void btnEliminarCliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCliMouseEntered
        btnEliminarCli.setBackground(new Color(30, 118, 138));
    }//GEN-LAST:event_btnEliminarCliMouseEntered

    private void btnEliminarCliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCliMouseExited
        btnEliminarCli.setBackground(Color.black);
    }//GEN-LAST:event_btnEliminarCliMouseExited

    private void btnLimpiarCamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarCamMouseEntered
        btnLimpiarCam.setBackground(new Color(248, 196, 113));
    }//GEN-LAST:event_btnLimpiarCamMouseEntered

    private void btnLimpiarCamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarCamMouseExited
        btnLimpiarCam.setBackground(Color.black);
    }//GEN-LAST:event_btnLimpiarCamMouseExited

    private void btnBurbujaDNiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBurbujaDNiMouseEntered
        btnBurbujaDNi.setBackground(new Color(40, 116, 166));
    }//GEN-LAST:event_btnBurbujaDNiMouseEntered

    private void btnBurbujaDNiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBurbujaDNiMouseExited
        btnBurbujaDNi.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnBurbujaDNiMouseExited

    private void btnApellidoPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApellidoPMouseEntered
        btnApellidoP.setBackground(new Color(40, 116, 166));
    }//GEN-LAST:event_btnApellidoPMouseEntered

    private void btnApellidoPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApellidoPMouseExited
        btnApellidoP.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnApellidoPMouseExited

    private void btnGenerarPedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarPedMouseEntered
        btnGenerarPed.setBackground(new Color(244, 208, 63));
    }//GEN-LAST:event_btnGenerarPedMouseEntered

    private void btnGenerarPedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarPedMouseExited
        btnGenerarPed.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnGenerarPedMouseExited

    private void btnGuardarProMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProMouseEntered
        btnGuardarPro.setBackground(new Color(215, 189, 226));
    }//GEN-LAST:event_btnGuardarProMouseEntered

    private void btnGuardarProMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarProMouseExited
        btnGuardarPro.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnGuardarProMouseExited

    private void btnEditarProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarProdMouseEntered
        btnEditarProd.setBackground(new Color(115, 198, 182));
    }//GEN-LAST:event_btnEditarProdMouseEntered

    private void btnEditarProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarProdMouseExited
        btnEditarProd.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnEditarProdMouseExited

    private void btnEliminarProMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProMouseEntered
        btnEliminarPro.setBackground(new Color(241, 148, 138));
    }//GEN-LAST:event_btnEliminarProMouseEntered

    private void btnEliminarProMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProMouseExited
        btnEliminarPro.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnEliminarProMouseExited

    private void btnLimpiarProMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarProMouseEntered
        btnLimpiarPro.setBackground(new Color(211, 84, 0));
    }//GEN-LAST:event_btnLimpiarProMouseEntered

    private void btnLimpiarProMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarProMouseExited
        btnLimpiarPro.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnLimpiarProMouseExited

    private void txtDescripcionProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProdActionPerformed

    private void btnreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreportActionPerformed
// Crear una instancia de GraficoComprasUsuarios
        GraficoComprasUsuarios grafico = new GraficoComprasUsuarios("Gráfico de Compras por Usuario");

        // Configurar las propiedades de la ventana
        grafico.setSize(800, 600);
        grafico.setLocationRelativeTo(this); // Centrar respecto a la ventana actual
        grafico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana

        // Mostrar la ventana del gráfico
        grafico.setVisible(true);

    }//GEN-LAST:event_btnreportActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        try {
            // Obtener la fecha del JDateChooser
            Date fechaReporte = Midate.getDate();

            // Llamar al método Graficar con la fecha en el formato correcto
            Grafico.Graficar(fechaReporte);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnPdfVentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPdfVentMouseEntered
        btnPdfVent.setBackground(new Color(32, 249, 55));
    }//GEN-LAST:event_btnPdfVentMouseEntered

    private void btnPdfVentMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPdfVentMouseExited
        btnPdfVent.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnPdfVentMouseExited

    private void btnMenoraMayorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenoraMayorMouseEntered
        btnMenoraMayor.setBackground(new Color(255, 179, 0));
    }//GEN-LAST:event_btnMenoraMayorMouseEntered

    private void btnMenoraMayorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenoraMayorMouseExited
        btnMenoraMayor.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnMenoraMayorMouseExited

    private void btnreportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreportMouseEntered
        btnreport.setBackground(new Color(178, 235, 242));
    }//GEN-LAST:event_btnreportMouseEntered

    private void btnreportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnreportMouseExited
        btnreport.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_btnreportMouseExited

    private void btnGraficarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGraficarMouseEntered
        btnGraficar.setBackground(new Color(215, 219, 221));
    }//GEN-LAST:event_btnGraficarMouseEntered

    private void btnGraficarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGraficarMouseExited
        btnGraficar.setBackground(Color.black);
    }//GEN-LAST:event_btnGraficarMouseExited

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        if (comboBoxSTOCK.getSelectedItem().equals("Stk. Activo")) {

            try {
                // Eliminar el archivo anterior si existe
                File archivoAnterior = new File("src/pdfReportPro/productos_reporte.pdf");
                if (archivoAnterior.exists()) {
                    archivoAnterior.delete();
                }

                // Crear el archivo PDF
                FileOutputStream archivo;
                File file = new File("src/pdfReportPro/productos_reporte.pdf");
                archivo = new FileOutputStream(file);

                // Crear el documento
                Document doc = new Document();
                PdfWriter.getInstance(doc, archivo);
                doc.open();

                // Agregar tabla para el encabezado
                PdfPTable tablaEncabezado = new PdfPTable(3);
                tablaEncabezado.setWidthPercentage(100);

                // Celda para la imagen del logo (derecha)
                PdfPCell celdaImagen = new PdfPCell();
                celdaImagen.setBorder(Rectangle.NO_BORDER);
                Image imagenLogo = Image.getInstance("src/imagen/Logo_pdf.png");
                imagenLogo.scaleAbsolute(95f, 95f); // Ajustar tamaño si es necesario
                celdaImagen.addElement(imagenLogo);
                celdaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT); // Alineación a la derecha
                tablaEncabezado.addCell(celdaImagen);

                // Celda para los datos de la empresa (izquierda)
                PdfPCell celdaDatosEmpresa = new PdfPCell();
                celdaDatosEmpresa.setBorder(Rectangle.NO_BORDER);
                Paragraph datosEmpresa = new Paragraph("\n\n"
                        + "RUC: " + lblRucPPiz.getText() + "\n"
                        + "Nombre: " + lblNombPizza.getText() + "\n"
                        + "Teléfono: " + lblTelefonoEmpre.getText() + "\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                datosEmpresa.setAlignment(Element.ALIGN_LEFT);
                celdaDatosEmpresa.addElement(datosEmpresa);
                tablaEncabezado.addCell(celdaDatosEmpresa);

                // Celda para la razón social y dirección (centro)
                PdfPCell celdaRazonDireccion = new PdfPCell();
                celdaRazonDireccion.setBorder(Rectangle.NO_BORDER);
                Paragraph razonDireccion = new Paragraph("\n\n"
                        + "Razón: " + lblRazonPpiza.getText() + "\n"
                        + "Dirección: " + lblDireccPpiza.getText() + "\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                razonDireccion.setAlignment(Element.ALIGN_CENTER);
                celdaRazonDireccion.addElement(razonDireccion);
                tablaEncabezado.addCell(celdaRazonDireccion);

                // Agregar la tabla de encabezado al documento
                doc.add(tablaEncabezado);

                // Agregar título
                Paragraph titulo = new Paragraph("\n\nReporte de Productos\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
                titulo.setAlignment(Element.ALIGN_LEFT);
                doc.add(titulo);

                // Obtener la lista de productos
                List<Producto> productos = proDao.ListadoPro();

                // Crear tabla para los productos
                PdfPTable tablaProductos = new PdfPTable(5); // 7 columnas para los datos del producto, incluyendo el stock
                tablaProductos.setWidthPercentage(100);
                tablaProductos.setSpacingBefore(10f);
                tablaProductos.setSpacingAfter(10f);

                // Encabezados de las columnas
                PdfPCell encabezado1 = new PdfPCell(new Phrase("ID", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado1.setBackgroundColor(BaseColor.GRAY);
                encabezado1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado2 = new PdfPCell(new Phrase("Descripción", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado2.setBackgroundColor(BaseColor.GRAY);
                encabezado2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado3 = new PdfPCell(new Phrase("Código", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado3.setBackgroundColor(BaseColor.GRAY);
                encabezado3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado4 = new PdfPCell(new Phrase("Stock", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado4.setBackgroundColor(BaseColor.GRAY);
                encabezado4.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado5 = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado5.setBackgroundColor(BaseColor.GRAY);
                encabezado5.setHorizontalAlignment(Element.ALIGN_CENTER);

                tablaProductos.addCell(encabezado1);
                tablaProductos.addCell(encabezado2);
                tablaProductos.addCell(encabezado3);
                tablaProductos.addCell(encabezado4);
                tablaProductos.addCell(encabezado5);

                // Llenar la tabla con los datos de los productos
                for (Producto producto : productos) {
                    tablaProductos.addCell(producto.getId());
                    tablaProductos.addCell(producto.getDescPro());
                    tablaProductos.addCell(producto.getCodigoP());
                    tablaProductos.addCell(String.valueOf(producto.getCantidad())); // Cambiado para que el stock aparezca antes
                    tablaProductos.addCell(String.valueOf(producto.getPrecio()));
                }

                doc.add(tablaProductos);

                // Cerrar el documento
                doc.close();
                archivo.close();

                // Abrir el archivo PDF generado
                if (Desktop.isDesktopSupported()) {
                    try {
                        File pdfFile = new File("src/pdfReportPro/productos_reporte.pdf");
                        Desktop.getDesktop().open(pdfFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (comboBoxSTOCK.getSelectedItem().equals("Stk. No Activo")) {

            
            
             try {
                // Eliminar el archivo anterior si existe
                File archivoAnterior = new File("src/pdfReportPro/productos_reporte.pdf");
                if (archivoAnterior.exists()) {
                    archivoAnterior.delete();
                }

                // Crear el archivo PDF
                FileOutputStream archivo;
                File file = new File("src/pdfReportPro/productos_reporte.pdf");
                archivo = new FileOutputStream(file);

                // Crear el documento
                Document doc = new Document();
                PdfWriter.getInstance(doc, archivo);
                doc.open();

                // Agregar tabla para el encabezado
                PdfPTable tablaEncabezado = new PdfPTable(3);
                tablaEncabezado.setWidthPercentage(100);

                // Celda para la imagen del logo (derecha)
                PdfPCell celdaImagen = new PdfPCell();
                celdaImagen.setBorder(Rectangle.NO_BORDER);
                Image imagenLogo = Image.getInstance("src/imagen/Logo_pdf.png");
                imagenLogo.scaleAbsolute(95f, 95f); // Ajustar tamaño si es necesario
                celdaImagen.addElement(imagenLogo);
                celdaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT); // Alineación a la derecha
                tablaEncabezado.addCell(celdaImagen);

                // Celda para los datos de la empresa (izquierda)
                PdfPCell celdaDatosEmpresa = new PdfPCell();
                celdaDatosEmpresa.setBorder(Rectangle.NO_BORDER);
                Paragraph datosEmpresa = new Paragraph("\n\n"
                        + "RUC: " + lblRucPPiz.getText() + "\n"
                        + "Nombre: " + lblNombPizza.getText() + "\n"
                        + "Teléfono: " + lblTelefonoEmpre.getText() + "\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                datosEmpresa.setAlignment(Element.ALIGN_LEFT);
                celdaDatosEmpresa.addElement(datosEmpresa);
                tablaEncabezado.addCell(celdaDatosEmpresa);

                // Celda para la razón social y dirección (centro)
                PdfPCell celdaRazonDireccion = new PdfPCell();
                celdaRazonDireccion.setBorder(Rectangle.NO_BORDER);
                Paragraph razonDireccion = new Paragraph("\n\n"
                        + "Razón: " + lblRazonPpiza.getText() + "\n"
                        + "Dirección: " + lblDireccPpiza.getText() + "\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                razonDireccion.setAlignment(Element.ALIGN_CENTER);
                celdaRazonDireccion.addElement(razonDireccion);
                tablaEncabezado.addCell(celdaRazonDireccion);

                // Agregar la tabla de encabezado al documento
                doc.add(tablaEncabezado);

                // Agregar título
                Paragraph titulo = new Paragraph("\n\nReporte de Productos\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
                titulo.setAlignment(Element.ALIGN_LEFT);
                doc.add(titulo);

                // Obtener la lista de productos
                List<Producto> productos = proDao.ListadoProINACTIVO();

                // Crear tabla para los productos
                PdfPTable tablaProductos = new PdfPTable(5); // 7 columnas para los datos del producto, incluyendo el stock
                tablaProductos.setWidthPercentage(100);
                tablaProductos.setSpacingBefore(10f);
                tablaProductos.setSpacingAfter(10f);

                // Encabezados de las columnas
                PdfPCell encabezado1 = new PdfPCell(new Phrase("ID", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado1.setBackgroundColor(BaseColor.GRAY);
                encabezado1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado2 = new PdfPCell(new Phrase("Descripción", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado2.setBackgroundColor(BaseColor.GRAY);
                encabezado2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado3 = new PdfPCell(new Phrase("Código", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado3.setBackgroundColor(BaseColor.GRAY);
                encabezado3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado4 = new PdfPCell(new Phrase("Stock", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado4.setBackgroundColor(BaseColor.GRAY);
                encabezado4.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell encabezado5 = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                encabezado5.setBackgroundColor(BaseColor.GRAY);
                encabezado5.setHorizontalAlignment(Element.ALIGN_CENTER);

                tablaProductos.addCell(encabezado1);
                tablaProductos.addCell(encabezado2);
                tablaProductos.addCell(encabezado3);
                tablaProductos.addCell(encabezado4);
                tablaProductos.addCell(encabezado5);

                // Llenar la tabla con los datos de los productos
                for (Producto producto : productos) {
                    tablaProductos.addCell(producto.getId());
                    tablaProductos.addCell(producto.getDescPro());
                    tablaProductos.addCell(producto.getCodigoP());
                    tablaProductos.addCell(String.valueOf(producto.getCantidad())); // Cambiado para que el stock aparezca antes
                    tablaProductos.addCell(String.valueOf(producto.getPrecio()));
                }

                doc.add(tablaProductos);

                // Cerrar el documento
                doc.close();
                archivo.close();

                // Abrir el archivo PDF generado
                if (Desktop.isDesktopSupported()) {
                    try {
                        File pdfFile = new File("src/pdfReportPro/productos_reporte.pdf");
                        Desktop.getDesktop().open(pdfFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            
            
            
            
        }
        else{
            
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    private void TableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUsuariosMouseClicked
        int filaSeleccionada = TableUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            // Obtener datos de la fila seleccionada
            int id = (int) TableUsuarios.getValueAt(filaSeleccionada, 0); // Suponiendo que la columna 0 contiene el id
            String nombre = (String) TableUsuarios.getValueAt(filaSeleccionada, 1); // Suponiendo que la columna 1 contiene el nombre
            String correo = (String) TableUsuarios.getValueAt(filaSeleccionada, 2); // Suponiendo que la columna 2 contiene el correo
            String rol = (String) TableUsuarios.getValueAt(filaSeleccionada, 3); // Suponiendo que la columna 3 contiene el rol

            // Mostrar los datos en los campos correspondientes
            txtNombre.setText(nombre);
            txtCorreo.setText(correo);
            cbxRol.setSelectedItem(rol);

            // Puedes establecer otros campos si es necesario, como la contraseña
            // txtPass.setText(pass);
        }
    }//GEN-LAST:event_TableUsuariosMouseClicked

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioActionPerformed
        int filaSeleccionada = TableUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idUsuario = (int) TableUsuarios.getValueAt(filaSeleccionada, 0); // Obtener el ID del usuario seleccionado
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminacionExitosa = login.eliminarUsuario(idUsuario);
                if (eliminacionExitosa) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
                    LimpiarUsuarios(); // Método para limpiar los campos de texto si es necesario
                    LimpiarTabla(); // Método para limpiar la tabla de usuarios
                    ListarUsuarios(); // Método para volver a cargar la lista de usuarios activos
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario para eliminar");
        }
    }//GEN-LAST:event_btnEliminarUsuarioActionPerformed

    private void txtIDClienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDClienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDClienActionPerformed

    private void txtApellidoMaPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoMaPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoMaPedidoActionPerformed

    private void txtApelliCliMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApelliCliMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApelliCliMActionPerformed

    private void btnClienteReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteReporteActionPerformed
        // Check if the selected item in comboBoxSTOCK is "Clts. Activo"
        if (cmBoxClientes.getSelectedItem().equals("Clts. Activo")) {
            try {
                // Eliminar el archivo anterior si existe
                File archivoAnterior = new File("src/pdfReportPro/clientes_reporte.pdf");
                if (archivoAnterior.exists()) {
                    archivoAnterior.delete();
                }

                // Crear el archivo PDF
                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream("src/pdfReportPro/clientes_reporte.pdf"));
                doc.open();

                // Agregar tabla para el encabezado
                PdfPTable tablaEncabezado = new PdfPTable(3);
                tablaEncabezado.setWidthPercentage(100);

                // Celda para la imagen del logo (derecha)
                PdfPCell celdaImagen = new PdfPCell();
                celdaImagen.setBorder(Rectangle.NO_BORDER);
                Image imagenLogo = Image.getInstance("src/imagen/Logo_pdf.png");
                imagenLogo.scaleAbsolute(95f, 95f);
                celdaImagen.addElement(imagenLogo);
                celdaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tablaEncabezado.addCell(celdaImagen);

                // Celda para los datos de la empresa (izquierda)
                PdfPCell celdaDatosEmpresa = new PdfPCell();
                celdaDatosEmpresa.setBorder(Rectangle.NO_BORDER);
                Paragraph datosEmpresa = new Paragraph("\n\n"
                        + "RUC: " + lblRucPPiz.getText() + "\n"
                        + "Nombre: " + lblNombPizza.getText() + "\n"
                        + "Teléfono: " + lblTelefonoEmpre.getText() + "\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                datosEmpresa.setAlignment(Element.ALIGN_LEFT);
                celdaDatosEmpresa.addElement(datosEmpresa);
                tablaEncabezado.addCell(celdaDatosEmpresa);

                // Celda para la razón social y dirección (centro)
                PdfPCell celdaRazonDireccion = new PdfPCell();
                celdaRazonDireccion.setBorder(Rectangle.NO_BORDER);
                Paragraph razonDireccion = new Paragraph("\n\n"
                        + "Razón: " + lblRazonPpiza.getText() + "\n"
                        + "Dirección: " + lblDireccPpiza.getText() + "\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                razonDireccion.setAlignment(Element.ALIGN_CENTER);
                celdaRazonDireccion.addElement(razonDireccion);
                tablaEncabezado.addCell(celdaRazonDireccion);

                // Agregar la tabla de encabezado al documento
                doc.add(tablaEncabezado);

                // Agregar título
                Paragraph titulo = new Paragraph("\n\nReporte de Clientes Activos\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
                titulo.setAlignment(Element.ALIGN_LEFT);
                doc.add(titulo);

                // Crear tabla para los clientes
                PdfPTable tablaClientes = new PdfPTable(6);
                tablaClientes.setWidthPercentage(100);
                tablaClientes.setSpacingBefore(10f);
                tablaClientes.setSpacingAfter(10f);

                // Encabezados de las columnas
                String[] encabezados = {"ID", "DNI", "Nombre", "Apellido Paterno", "Apellido Materno", "Teléfono"};
                for (String encabezado : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(encabezado, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                    cell.setBackgroundColor(BaseColor.GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaClientes.addCell(cell);
                }

                // Obtener lista de clientes activos
                ClienteDao clienteDao = new ClienteDao();
                List<Cliente> clientes = clienteDao.ListadoCliente();

                // Llenar la tabla con los datos de los clientes
                for (Cliente cliente : clientes) {
                    if (cliente.isActivo()) { // Check if the client is active
                        tablaClientes.addCell(cliente.getId());
                        tablaClientes.addCell(String.valueOf(cliente.getDni()));
                        tablaClientes.addCell(cliente.getNombre());
                        tablaClientes.addCell(cliente.getPaterno());
                        tablaClientes.addCell(cliente.getMaterno());
                        tablaClientes.addCell(String.valueOf(cliente.getTelefono()));
                    }
                }

                doc.add(tablaClientes);
                doc.close();

                // Abrir el archivo PDF generado
                if (Desktop.isDesktopSupported()) {
                    File pdfFile = new File("src/pdfReportPro/clientes_reporte.pdf");
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
            }
        } else if (cmBoxClientes.getSelectedItem().equals("Clts. Desactivo")) {
            try {
                // Eliminar el archivo anterior si existe
                File archivoAnterior = new File("src/pdfReportPro/clientes_reporte.pdf");
                if (archivoAnterior.exists()) {
                    archivoAnterior.delete();
                }

                // Crear el archivo PDF
                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream("src/pdfReportPro/clientes_reporte.pdf"));
                doc.open();

                // Agregar tabla para el encabezado
                PdfPTable tablaEncabezado = new PdfPTable(3);
                tablaEncabezado.setWidthPercentage(100);

                // Celda para la imagen del logo (derecha)
                PdfPCell celdaImagen = new PdfPCell();
                celdaImagen.setBorder(Rectangle.NO_BORDER);
                Image imagenLogo = Image.getInstance("src/imagen/Logo_pdf.png");
                imagenLogo.scaleAbsolute(95f, 95f);
                celdaImagen.addElement(imagenLogo);
                celdaImagen.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tablaEncabezado.addCell(celdaImagen);

                // Celda para los datos de la empresa (izquierda)
                PdfPCell celdaDatosEmpresa = new PdfPCell();
                celdaDatosEmpresa.setBorder(Rectangle.NO_BORDER);
                Paragraph datosEmpresa = new Paragraph("\n\n"
                        + "RUC: " + lblRucPPiz.getText() + "\n"
                        + "Nombre: " + lblNombPizza.getText() + "\n"
                        + "Teléfono: " + lblTelefonoEmpre.getText() + "\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                datosEmpresa.setAlignment(Element.ALIGN_LEFT);
                celdaDatosEmpresa.addElement(datosEmpresa);
                tablaEncabezado.addCell(celdaDatosEmpresa);

                // Celda para la razón social y dirección (centro)
                PdfPCell celdaRazonDireccion = new PdfPCell();
                celdaRazonDireccion.setBorder(Rectangle.NO_BORDER);
                Paragraph razonDireccion = new Paragraph("\n\n"
                        + "Razón: " + lblRazonPpiza.getText() + "\n"
                        + "Dirección: " + lblDireccPpiza.getText() + "\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 12));
                razonDireccion.setAlignment(Element.ALIGN_CENTER);
                celdaRazonDireccion.addElement(razonDireccion);
                tablaEncabezado.addCell(celdaRazonDireccion);

                // Agregar la tabla de encabezado al documento
                doc.add(tablaEncabezado);

                // Agregar título
                Paragraph titulo = new Paragraph("\n\nReporte de Clientes Inactivos\n\n",
                        new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
                titulo.setAlignment(Element.ALIGN_LEFT);
                doc.add(titulo);

                // Crear tabla para los clientes
                PdfPTable tablaClientes = new PdfPTable(6);
                tablaClientes.setWidthPercentage(100);
                tablaClientes.setSpacingBefore(10f);
                tablaClientes.setSpacingAfter(10f);

                // Encabezados de las columnas
                String[] encabezados = {"ID", "DNI", "Nombre", "Apellido Paterno", "Apellido Materno", "Teléfono"};
                for (String encabezado : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(encabezado, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                    cell.setBackgroundColor(BaseColor.GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaClientes.addCell(cell);
                }

                // Obtener lista de clientes inactivos
                ClienteDao clienteDao = new ClienteDao();
                List<Cliente> clientes = clienteDao.ListadoClientesInactivos();

                // Llenar la tabla con los datos de los clientes inactivos
                for (Cliente cliente : clientes) {
                    tablaClientes.addCell(cliente.getId());
                    tablaClientes.addCell(String.valueOf(cliente.getDni()));
                    tablaClientes.addCell(cliente.getNombre());
                    tablaClientes.addCell(cliente.getPaterno());
                    tablaClientes.addCell(cliente.getMaterno());
                    tablaClientes.addCell(String.valueOf(cliente.getTelefono()));
                }

                doc.add(tablaClientes);
                doc.close();

                // Abrir el archivo PDF generado
                if (Desktop.isDesktopSupported()) {
                    File pdfFile = new File("src/pdfReportPro/clientes_reporte.pdf");
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
            }
        }

    }//GEN-LAST:event_btnClienteReporteActionPerformed

    private void comboBoxSTOCKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSTOCKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSTOCKActionPerformed

    private void cmBoxClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmBoxClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmBoxClientesActionPerformed

    private void btnElimPedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElimPedMouseEntered
        btnElimPed.setBackground(new Color(95, 73, 53));
    }//GEN-LAST:event_btnElimPedMouseEntered

    private void btnElimPedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElimPedMouseExited
        btnElimPed.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnElimPedMouseExited

    private void btnIniciarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarMouseEntered
        btnIniciar.setBackground(new Color(44,62,80));
    }//GEN-LAST:event_btnIniciarMouseEntered

    private void btnIniciarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarMouseExited
        btnIniciar.setBackground(new Color(45,36,31));
    }//GEN-LAST:event_btnIniciarMouseExited

    private void btnEliminarUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioMouseExited
        btnEliminarUsuario.setBackground(new Color(45,36,31));
    }//GEN-LAST:event_btnEliminarUsuarioMouseExited

    private void btnEliminarUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioMouseEntered
        btnEliminarUsuario.setBackground(new Color(44,62,80));
    }//GEN-LAST:event_btnEliminarUsuarioMouseEntered

    private void LimpiarUsuarios() {
        txtCorreo.setText("");
        txtDescripcionProd.setText("");
        txtNombre.setText("");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Formulario_Pizza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario_Pizza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario_Pizza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario_Pizza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Formulario_Pizza().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JPanel REPORTE_HISTVENTA;
    private javax.swing.JPanel ReportePDF;
    private javax.swing.JPanel ReportePDF1;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JButton btnApellidoP;
    private javax.swing.JButton btnBurbujaDNi;
    private javax.swing.JButton btnBusSecuencial;
    private javax.swing.JButton btnClienteReporte;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEditarCli;
    private javax.swing.JButton btnEditarProd;
    private javax.swing.JButton btnElimPed;
    private javax.swing.JButton btnEliminarCli;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnGenerarPed;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCli;
    private javax.swing.JButton btnGuardarPro;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnLimpiarCam;
    private javax.swing.JButton btnLimpiarPro;
    private javax.swing.JButton btnMenorStock;
    private javax.swing.JButton btnMenoraMayor;
    private javax.swing.JButton btnPdfVent;
    private javax.swing.JButton btnPedido;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnreport;
    private javax.swing.JComboBox<String> cbxNombreProducto;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JComboBox<String> cbxTamPizzaPro;
    private javax.swing.JComboBox<String> cbxTamañoPedido;
    private javax.swing.JComboBox<String> cbxTipoPedidoi;
    private javax.swing.JComboBox<String> cbxTipoPizzaPro;
    private javax.swing.JComboBox<String> cmBoxClientes;
    private javax.swing.JComboBox<String> comboBoxSTOCK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDireccPpiza;
    private javax.swing.JLabel lblNombPizza;
    private javax.swing.JLabel lblRazonPpiza;
    private javax.swing.JLabel lblRucPPiz;
    private javax.swing.JLabel lblTelefonoEmpre;
    private javax.swing.JLabel lblTotalPagar;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTable tblPedido;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTable tblVentasPedidos;
    private javax.swing.JTextField txtApelliCliM;
    private javax.swing.JTextField txtApelliPCli;
    private javax.swing.JTextField txtApellidoMaPedido;
    private javax.swing.JTextField txtApellidoPaPedido;
    private javax.swing.JTextField txtCantPedido;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCodPizza;
    private javax.swing.JTextField txtCodpedidoP;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDescripcionProd;
    private javax.swing.JTextField txtDniCli;
    private javax.swing.JTextField txtDniP;
    private javax.swing.JTextField txtIDClien;
    private javax.swing.JTextField txtIDprodc;
    private javax.swing.JTextField txtIdPediV;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCLiPedid;
    private javax.swing.JTextField txtNombreCli;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPrecioP;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtStockdispo;
    private javax.swing.JTextField txtTelefonoCli;
    private javax.swing.JTextField txtTelefonoPed;
    // End of variables declaration//GEN-END:variables
        private void LimpiarCliente() {
        txtIDClien.setText("");
        txtDniCli.setText("");
        txtNombreCli.setText("");
        txtApelliPCli.setText("");
        txtApelliCliM.setText("");
        txtTelefonoCli.setText("");

    }

    private void LimpiarProductos() {
        txtIDprodc.setText("");
        txtDescripcionProd.setText("");
        txtCodPizza.setText("");
        cbxTipoPizzaPro.setSelectedItem(null);
        cbxTamPizzaPro.setSelectedItem(null);
        txtCantPro.setText("");
        txtPrecioPro.setText("");

    }

    private double calcularTotalPagar() {
        double totalPagar = 0.00;
        int numFilas = tblPedido.getRowCount();

        for (int i = 0; i < numFilas; i++) {
            double total = Double.parseDouble(String.valueOf(tblPedido.getModel().getValueAt(i, 6)));
            totalPagar += total;
        }

        return totalPagar;
    }

    private void TotalPagar() {
        double totalPagar = calcularTotalPagar();
        lblTotalPagar.setText(String.format("%.2f", totalPagar));
    }

    private void LimpiarPedido() {
        cbxNombreProducto.setSelectedItem(null);
        txtCodpedidoP.setText("");
        cbxTipoPedidoi.setSelectedItem(null);
        cbxTamañoPedido.setSelectedItem(null);
        txtCantPedido.setText("");
        txtPrecioP.setText("");
        txtStockdispo.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarPedidoV() {
        String cliente = txtNombreCLiPedid.getText();
        String vendedor = lblVendedor.getText();
        double monto = calcularTotalPagar();

        pe.setCliente(cliente);
        pe.setVendedor(vendedor);
        pe.setTotal(monto);
        peDao.RegistrarPedidoVen(pe);
    }

    private void RegistrarDetalle() {
        int id = peDao.IdVenta();
        for (int i = 0; i < tblPedido.getRowCount(); i++) {
            String cod = tblPedido.getValueAt(i, 1).toString();
            int cant = Integer.parseInt(tblPedido.getValueAt(i, 4).toString());
            double precio = Double.parseDouble(tblPedido.getValueAt(i, 5).toString());
            Dpv.setCod_pro(cod);
            Dpv.setCantidad(cant);
            Dpv.setPrecio(precio);
            Dpv.setId(id);
            peDao.RegistrarDetallePedV(Dpv);
        }
    }

    private void cargarDescripcionesProductos() {
        List<String> descripcionesProductos = proDao.obtenerDescripcionesProductos();

        // Limpiar el JComboBox antes de agregar las descripciones de productos
        cbxNombreProducto.removeAllItems();

        // Agregar las descripciones de productos al JComboBox
        for (String descripcion : descripcionesProductos) {
            cbxNombreProducto.addItem(descripcion);
        }
    }

    private void LimpiarTablePedidoV() {
        tmp = (DefaultTableModel) tblPedido.getModel();
        int fila = tblPedido.getRowCount();

        // Recorrer las filas en orden inverso
        // para evitar el desfase en los índices al eliminar
        for (int i = fila - 1; i >= 0; i--) {
            tmp.removeRow(i); // Eliminar la fila actual
        }
    }

    private void LimpiarClientePedidoV() {
        txtDniP.setText("");
        txtNombreCLiPedid.setText("");
        txtTelefonoPed.setText("");
        txtApellidoMaPedido.setText("");
        txtApellidoPaPedido.setText("");
    }

    private void pdf() {
        try {
            int id = peDao.IdVenta();
            // objeto FileoutputStream para escribir el archivo pdf en disco
            FileOutputStream archivo;
            File file = new File("src/pdf/pedidoVen" + id + ".pdf");
            archivo = new FileOutputStream(file);
            // Se crea un objeto document para trabajar con el contenido del pdf
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image imagen = Image.getInstance("src/imagen/Logo_pdf.png");
            // Se crea un parrafo para mostrar la fecha del pedido
            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.ORANGE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Pedido: " + id + "\n" + "Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            // Creamos una tabla para el encabezado del documento
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(imagen);

            String ruc = lblRucPPiz.getText();
            String nom = lblNombPizza.getText();
            String tel = lblTelefonoEmpre.getText();
            String dir = lblDireccPpiza.getText();
            String ra = lblRazonPpiza.getText();

            // Se añaden los datos de la empresa al encabezado
            Encabezado.addCell("");
            Encabezado.addCell("Ruc: " + ruc + "\nNombre: " + nom + "\nTelefono: " + tel + "\nDireccion: " + dir + "\nRazon: " + ra);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Datos del Cliente" + "\n\n");
            doc.add(cli);

            PdfPTable tablaCli = new PdfPTable(5);
            tablaCli.setWidthPercentage(100);
            tablaCli.getDefaultCell().setBorder(0);
            float[] ColumnaCli = new float[]{30f, 40f, 25f, 25f, 40f};
            tablaCli.setWidths(ColumnaCli);
            tablaCli.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cl1 = new PdfPCell(new Phrase("Dni", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cl3 = new PdfPCell(new Phrase("Apellido Paterno", negrita));
            PdfPCell cl4 = new PdfPCell(new Phrase("Apellido Materno", negrita));
            PdfPCell cl5 = new PdfPCell(new Phrase("Telefono", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            cl3.setBorder(0);
            cl4.setBorder(0);
            cl5.setBorder(0);

            // Se añaden las celdas de cabecera a la tabla
            tablaCli.addCell(cl1);
            tablaCli.addCell(cl2);
            tablaCli.addCell(cl3);
            tablaCli.addCell(cl4);
            tablaCli.addCell(cl5);

            tablaCli.addCell(txtDniP.getText());
            tablaCli.addCell(txtNombreCLiPedid.getText());
            tablaCli.addCell(txtApellidoPaPedido.getText());
            tablaCli.addCell(txtApellidoMaPedido.getText());
            tablaCli.addCell(txtTelefonoPed.getText());

            doc.add(tablaCli);
            //ESPACIO EN BLANCO :
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);

            //AGREGADO DE PRODUCTOS:
            PdfPTable tablaPro = new PdfPTable(6);
            tablaPro.setWidthPercentage(100);
            tablaPro.getDefaultCell().setBorder(0);
            float[] ColumnaPro = new float[]{10f, 40f, 20f, 20f, 20f, 20f};
            tablaPro.setWidths(ColumnaPro);
            tablaPro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Tipo de Pizza", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Tamaño", negrita));
            PdfPCell pro5 = new PdfPCell(new Phrase("Precio U.", negrita));
            PdfPCell pro6 = new PdfPCell(new Phrase("Precio Total", negrita));
            //esto sirve para quitar los bordes de las tablas que estamos creando
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro5.setBorder(0);
            pro6.setBorder(0);

            pro1.setBackgroundColor(BaseColor.DARK_GRAY);
            pro2.setBackgroundColor(BaseColor.DARK_GRAY);
            pro3.setBackgroundColor(BaseColor.DARK_GRAY);
            pro4.setBackgroundColor(BaseColor.DARK_GRAY);
            pro5.setBackgroundColor(BaseColor.DARK_GRAY);
            pro6.setBackgroundColor(BaseColor.DARK_GRAY);
            // Se añaden las celdas de cabecera a la tabla de productos
            tablaPro.addCell(pro1);
            tablaPro.addCell(pro2);
            tablaPro.addCell(pro3);
            tablaPro.addCell(pro4);
            tablaPro.addCell(pro5);
            tablaPro.addCell(pro6);
            // Se obtienen los datos de los productos del pedido y se añaden a la tabla
            for (int i = 0; i < tblPedido.getRowCount(); i++) {
                String producto = tblPedido.getValueAt(i, 0).toString();
                String cantidad = tblPedido.getValueAt(i, 4).toString();
                String tipoPizza = tblPedido.getValueAt(i, 2).toString();
                String tamañoP = tblPedido.getValueAt(i, 3).toString();
                String precio = tblPedido.getValueAt(i, 5).toString();
                String total = tblPedido.getValueAt(i, 6).toString();
                tablaPro.addCell(cantidad);
                tablaPro.addCell(producto);
                tablaPro.addCell(tipoPizza);
                tablaPro.addCell(tamañoP);
                tablaPro.addCell(precio);
                tablaPro.addCell(total);

            }
            doc.add(tablaPro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: S/" + lblTotalPagar.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Muchas gracias por comprar en PPizza\n\n");
            firma.add("_____________________________");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Que tenga buen día");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();

// Verificar si el soporte de Desktop está disponible en el sistema
            if (Desktop.isDesktopSupported()) {
                try {
                    // Crea una instancia de File con la ruta del archivo PDF
                    File pdfFile = new File("src/pdf/pedidoVen" + id + ".pdf");
                    // Abre el archivo PDF con la aplicación predeterminada del sistema
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    // Si ocurre una excepción al abrir el archivo, se imprime la traza del error
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
        }
    }

    private void generarReporteProductos() {
        try {
            // Crear el archivo PDF
            FileOutputStream archivo;
            File file = new File("src/pdfReportPro/productos_reporte.pdf");
            archivo = new FileOutputStream(file);

            // Crear el documento
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // Agregar imagen (opcional)
            Image imagen = Image.getInstance("src/imagen/Logo_pdf.png");
            imagen.scaleAbsolute(100f, 100f); // Ajustar tamaño si es necesario
            imagen.setAlignment(Element.ALIGN_CENTER);
            doc.add(imagen);

            // Agregar título
            Paragraph titulo = new Paragraph("Reporte de Productos\n\n",
                    new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            // Obtener la lista de productos
            List<Producto> productos = proDao.ListadoPro();

            // Crear tabla para los productos
            PdfPTable tablaProductos = new PdfPTable(7); // 7 columnas para los datos del producto, incluyendo el stock
            tablaProductos.setWidthPercentage(100);
            tablaProductos.setSpacingBefore(10f);
            tablaProductos.setSpacingAfter(10f);

            // Encabezados de las columnas
            PdfPCell encabezado1 = new PdfPCell(new Phrase("ID", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado1.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado2 = new PdfPCell(new Phrase("Descripción", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado2.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado3 = new PdfPCell(new Phrase("Código", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado3.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado3.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado4 = new PdfPCell(new Phrase("Tipo Pizza", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado4.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado4.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado5 = new PdfPCell(new Phrase("Tamaño", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado5.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado5.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado6 = new PdfPCell(new Phrase("Stock", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado6.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado6.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell encabezado7 = new PdfPCell(new Phrase("Precio", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            encabezado7.setBackgroundColor(BaseColor.DARK_GRAY);
            encabezado7.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablaProductos.addCell(encabezado1);
            tablaProductos.addCell(encabezado2);
            tablaProductos.addCell(encabezado3);
            tablaProductos.addCell(encabezado4);
            tablaProductos.addCell(encabezado5);
            tablaProductos.addCell(encabezado6);
            tablaProductos.addCell(encabezado7);

            // Llenar la tabla con los datos de los productos
            for (Producto producto : productos) {
                tablaProductos.addCell(producto.getId());
                tablaProductos.addCell(producto.getDescPro());
                tablaProductos.addCell(producto.getCodigoP());
                tablaProductos.addCell(producto.getTipoPizza());
                tablaProductos.addCell(producto.getTamañoPizza());
                tablaProductos.addCell(String.valueOf(producto.getCantidad())); // Cambiado para que el stock aparezca antes
                tablaProductos.addCell(String.valueOf(producto.getPrecio()));
            }

            doc.add(tablaProductos);

            // Agregar información de la empresa (usando datos fijos o variables)
            Paragraph infoEmpresa = new Paragraph("\n\nInformación de la Empresa:\n\n"
                    + "RUC: " + lblRucPPiz.getText() + "\n"
                    + "Nombre: " + lblNombPizza.getText() + "\n"
                    + "Teléfono: " + lblTelefonoEmpre.getText() + "\n"
                    + "Dirección: " + lblDireccPpiza.getText() + "\n"
                    + "Razón: " + lblRazonPpiza.getText() + "\n\n",
                    new Font(Font.FontFamily.TIMES_ROMAN, 12));
            infoEmpresa.setAlignment(Element.ALIGN_LEFT);
            doc.add(infoEmpresa);

            // Cerrar el documento
            doc.close();
            archivo.close();

            // Abrir el archivo PDF generado
            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File("src/pdfReportPro/productos_reporte.pdf");
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
