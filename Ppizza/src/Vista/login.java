package Vista;

import Clases.Login;
import Clases.LoginDAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class login extends javax.swing.JFrame {
 Login lg= new Login();
 LoginDAO login =new LoginDAO();
    private Timer tiempo;
    int contador;
    int segundos = 30; 
    private int loginAttempts = 0;

    Formulario_Pizza formularioPizza; 
    
    public login() {
        initComponents(); setBackground(new Color(0,0,0,0));
        //CODIGO ICONO PARA JFRAME
        setIconImage(new ImageIcon(getClass().getResource("/Imagen/ppizza300px.png")).getImage());
        this.setLocationRelativeTo(null);
        txtCorreo.setText("");
        txtPass.setText("");
        barra.setVisible(false);
    }
    
 public class BarraProgreso implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            contador++;
            barra.setValue(contador);
            if (contador == 100) {
                tiempo.stop();
                if (barra.getValue() == 100) {
                    // Mostrar el formulario principal y configurar el usuario
                    formularioPizza.setVisible(true);
                    dispose();
                }
            }
        }
    }
   /* public void validar(){
        String correo = txtCorreo.getText();
        String pass = String.valueOf(txtPass.getPassword());
        if (!"".equals(correo) || !"".equals(pass)) {
            
            lg = login.log(correo, pass);
            if (lg.getCorreo()!= null && lg.getPass() != null) {
                barra.setVisible(true);
                contador = -1;
                barra.setValue(0);
                barra.setStringPainted(true);
                tiempo = new Timer(segundos, new BarraProgreso());
                tiempo.start();
            }else{
                JOptionPane.showMessageDialog(null, "Correo o la Contraseña incorrecta");
            }
        }
    } */
 
    //Metodo para confirmar el cierre del JFrame
    public void cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        BotonX = new javax.swing.JButton();
        JLPpizza = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        txtCorreo = new javax.swing.JTextField();
        btIniciar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Login1 = new javax.swing.JLabel();
        barra = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        Login3 = new javax.swing.JLabel();
        Login5 = new javax.swing.JLabel();
        Login6 = new javax.swing.JLabel();
        Login7 = new javax.swing.JLabel();
        Login8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        jPanel1.setForeground(new java.awt.Color(51, 0, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BotonX.setBackground(new java.awt.Color(0, 0, 0));
        BotonX.setForeground(new java.awt.Color(255, 255, 255));
        BotonX.setText("X");
        BotonX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonXActionPerformed(evt);
            }
        });
        jPanel1.add(BotonX, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 40, -1));

        JLPpizza.setBackground(new java.awt.Color(234, 231, 231));
        JLPpizza.setFont(new java.awt.Font("Nirmala UI", 1, 26)); // NOI18N
        JLPpizza.setForeground(new java.awt.Color(204, 204, 204));
        JLPpizza.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLPpizza.setText("PPizza");
        jPanel1.add(JLPpizza, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 90, 30));

        txtPass.setBackground(new java.awt.Color(6, 6, 6));
        txtPass.setForeground(new java.awt.Color(204, 204, 204));
        txtPass.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        jPanel1.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 170, 30));

        txtCorreo.setBackground(new java.awt.Color(9, 9, 9));
        txtCorreo.setForeground(new java.awt.Color(204, 204, 204));
        txtCorreo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel1.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 145, 170, 30));

        btIniciar.setBackground(new java.awt.Color(13, 13, 41));
        btIniciar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btIniciar.setForeground(new java.awt.Color(204, 204, 204));
        btIniciar.setText("Iniciar");
        btIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIniciarActionPerformed(evt);
            }
        });
        jPanel1.add(btIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 90, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/pasword24px.png"))); // NOI18N
        jLabel1.setText("Password");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 183, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/user24px.png"))); // NOI18N
        jLabel2.setText("Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 114, 100, 30));

        Login1.setBackground(new java.awt.Color(234, 231, 231));
        Login1.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login1.setForeground(new java.awt.Color(204, 204, 204));
        Login1.setText("Login");
        jPanel1.add(Login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, 70, 30));
        jPanel1.add(barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 230, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Ppizza50px.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 28, -1, -1));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 320));

        Login3.setBackground(new java.awt.Color(234, 231, 231));
        Login3.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login3.setForeground(new java.awt.Color(204, 204, 204));
        Login3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/food-pict-pizza-removebg-preview.png"))); // NOI18N
        jPanel2.add(Login3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 200, 190));

        Login5.setBackground(new java.awt.Color(234, 231, 231));
        Login5.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login5.setForeground(new java.awt.Color(204, 204, 204));
        Login5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/facebook_black_logo_icon_147136.png"))); // NOI18N
        jPanel2.add(Login5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 40, 40));

        Login6.setBackground(new java.awt.Color(234, 231, 231));
        Login6.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login6.setForeground(new java.awt.Color(204, 204, 204));
        Login6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login6.setText("Bienvenido-PPizza");
        jPanel2.add(Login6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 260, 70));

        Login7.setBackground(new java.awt.Color(234, 231, 231));
        Login7.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login7.setForeground(new java.awt.Color(204, 204, 204));
        Login7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/4494468.png"))); // NOI18N
        jPanel2.add(Login7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 40, 40));

        Login8.setBackground(new java.awt.Color(234, 231, 231));
        Login8.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        Login8.setForeground(new java.awt.Color(204, 204, 204));
        Login8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Login8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/icone-du-logo-whatsapp-noir (3).png"))); // NOI18N
        jPanel2.add(Login8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 40, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 550, 320));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIniciarActionPerformed
     String correo = txtCorreo.getText();
    String pass = String.valueOf(txtPass.getPassword());
    
    if (!correo.isEmpty() && !pass.isEmpty()) {
        lg = login.log(correo, pass);
        
        if (lg != null) { // Check if login was successful
            barra.setVisible(true);
            contador = -1;
            barra.setValue(0);
            barra.setStringPainted(true);
            tiempo = new Timer(segundos, new BarraProgreso());
            tiempo.start();
            
            // Show main form and set user
            formularioPizza = new Formulario_Pizza();
            formularioPizza.setUsuario(lg.getNombre(), lg.getRol());
            
            // Close the login window
            dispose();
            loginAttempts = 0; // Reset attempts on successful login
        } else {
            loginAttempts++;
            if (loginAttempts >= 3) {
                JOptionPane.showMessageDialog(null, "Demasiados intentos fallidos. La aplicación se cerrará.");
                System.exit(0); // Close the application
            } else {
                // Inform the user of the remaining attempts
                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos. Intentos restantes: " + (3 - loginAttempts));
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese correo y contraseña");
    }
    }//GEN-LAST:event_btIniciarActionPerformed

    private void BotonXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonXActionPerformed
      confirmarSalida();
    }//GEN-LAST:event_BotonXActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonX;
    private javax.swing.JLabel JLPpizza;
    private javax.swing.JLabel Login1;
    private javax.swing.JLabel Login3;
    private javax.swing.JLabel Login5;
    private javax.swing.JLabel Login6;
    private javax.swing.JLabel Login7;
    private javax.swing.JLabel Login8;
    public javax.swing.JProgressBar barra;
    private javax.swing.JButton btIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
