import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.awt.Desktop;
import java.io.File;

public class CifradoDES extends JFrame {
    private SecretKey secretKey;
    private JTextField txtArchivo;
    private JButton btnCifrar, btnDescifrar, btnSeleccionar, btnAbrirCifrado, btnAbrirDescifrado;
    private JFileChooser fileChooser;
    private JLabel lblEstado;

    public CifradoDES() {
        setTitle("Cifrado y Descifrado con DES");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        JLabel titulo = new JLabel("Cifrado y Descifrado con DES");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con controles
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campo de archivo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        txtArchivo = new JTextField();
        txtArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtArchivo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(txtArchivo, gbc);
        
        // Botón seleccionar
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        btnSeleccionar = crearBoton("Seleccionar Archivo", new Color(70, 130, 180));
        panel.add(btnSeleccionar, gbc);
        
        // Botones de operación
        gbc.gridx = 0;
        gbc.gridy = 1;
        btnCifrar = crearBoton("Cifrar", new Color(34, 139, 34));
        panel.add(btnCifrar, gbc);
        
        gbc.gridx = 1;
        btnDescifrar = crearBoton("Descifrar", new Color(220, 20, 60));
        panel.add(btnDescifrar, gbc);
        
        // Botones de apertura
        gbc.gridx = 0;
        gbc.gridy = 2;
        btnAbrirCifrado = crearBoton("Abrir Archivo Cifrado", new Color(100, 149, 237));
        panel.add(btnAbrirCifrado, gbc);
        
        gbc.gridx = 1;
        btnAbrirDescifrado = crearBoton("Abrir Archivo Descifrado", new Color(147, 112, 219));
        panel.add(btnAbrirDescifrado, gbc);
        
        add(panel, BorderLayout.CENTER);
        
        // Panel de estado
        lblEstado = new JLabel(" Listo. Seleccione un archivo para comenzar.");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        lblEstado.setForeground(new Color(70, 70, 70));
        add(lblEstado, BorderLayout.SOUTH);
        
        fileChooser = new JFileChooser();
        generarClave();

        // Eventos
        btnSeleccionar.addActionListener(e -> seleccionarArchivo());
        btnCifrar.addActionListener(e -> cifrarArchivo());
        btnDescifrar.addActionListener(e -> descifrarArchivo());
        btnAbrirCifrado.addActionListener(e -> abrirArchivo(txtArchivo.getText() + ".cifrado"));
        btnAbrirDescifrado.addActionListener(e -> abrirArchivo(txtArchivo.getText().replace(".cifrado", "_descifrado.txt")));
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.BLACK);  // Cambiado a color negro
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void generarClave() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56, new SecureRandom());
            secretKey = keyGen.generateKey();
        } catch (Exception e) {
            mostrarError("Error al generar la clave: " + e.getMessage());
        }
    }

    private void seleccionarArchivo() {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            txtArchivo.setText(fileChooser.getSelectedFile().getAbsolutePath());
            lblEstado.setText(" Archivo seleccionado: " + fileChooser.getSelectedFile().getName());
        }
    }

    private void cifrarArchivo() {
        try {
            String rutaArchivo = txtArchivo.getText();
            if (rutaArchivo.isEmpty()) {
                mostrarError("Seleccione un archivo primero.");
                return;
            }

            byte[] contenido = Files.readAllBytes(Paths.get(rutaArchivo));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cifrado = cipher.doFinal(contenido);

            String archivoCifrado = rutaArchivo + ".cifrado";
            Files.write(Paths.get(archivoCifrado), cifrado);
            lblEstado.setText(" Archivo cifrado con éxito: " + new File(archivoCifrado).getName());
            JOptionPane.showMessageDialog(this, 
                "Archivo cifrado con éxito:\n" + archivoCifrado, 
                "Operación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            mostrarError("Error al cifrar: " + e.getMessage());
        }
    }

    private void descifrarArchivo() {
        try {
            String rutaArchivo = txtArchivo.getText();
            if (rutaArchivo.isEmpty()) {
                mostrarError("Seleccione un archivo primero.");
                return;
            }

            if (!rutaArchivo.endsWith(".cifrado")) {
                mostrarError("El archivo debe tener extensión .cifrado");
                return;
            }

            byte[] contenido = Files.readAllBytes(Paths.get(rutaArchivo));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] descifrado = cipher.doFinal(contenido);

            String archivoDescifrado = rutaArchivo.replace(".cifrado", "_descifrado.txt");
            Files.write(Paths.get(archivoDescifrado), descifrado);
            lblEstado.setText(" Archivo descifrado con éxito: " + new File(archivoDescifrado).getName());
            JOptionPane.showMessageDialog(this, 
                "Archivo descifrado con éxito:\n" + archivoDescifrado, 
                "Operación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            mostrarError("Error al descifrar: " + e.getMessage());
        }
    }

    private void abrirArchivo(String ruta) {
        try {
            File archivo = new File(ruta);
            if (archivo.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
                lblEstado.setText(" Archivo abierto: " + archivo.getName());
            } else {
                mostrarError("No se pudo abrir el archivo: " + ruta);
            }
        } catch (IOException e) {
            mostrarError("Error al abrir el archivo: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        lblEstado.setText(" Error: " + mensaje);
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CifradoDES();
        });
    }
}