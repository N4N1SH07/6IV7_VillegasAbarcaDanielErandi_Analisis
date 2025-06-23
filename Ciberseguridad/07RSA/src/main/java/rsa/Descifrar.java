package rsa;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.math.BigInteger;

public class Descifrar extends JFrame {
    
    private JButton btnRegresar;
    private JButton btnDescifrar;
    private JTextField contd, contn;
    private JTextField contcif;
    private JTextArea contdes;
    String cadenacifrada; 
    BigInteger d, n;
    
    public Descifrar() {
        this.setTitle("Descifrar");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Panel principal con fondo azul claro
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // 1. Panel superior con botón regresar y título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        
        // Panel para botón regresar
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topButtonPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(220, 20, 60)); // Rojo
        btnRegresar.setForeground(Color.WHITE);
        topButtonPanel.add(btnRegresar);
        topPanel.add(topButtonPanel, BorderLayout.NORTH);
        
        // Panel para título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 248, 255)); // Azul claro
        JLabel title = new JLabel("Descifrar", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(25, 25, 112)); // Azul oscuro
        titlePanel.add(title);
        topPanel.add(titlePanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // 2. Panel central con contenido - Usamos BoxLayout para apilar verticalmente
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        contentPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        // 2.1 Panel para "Ingrese d"
        JPanel dSection = new JPanel(new BorderLayout());
        dSection.setBackground(new Color(240, 248, 255)); // Azul claro
        dSection.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        JLabel lblIngreseD = new JLabel("Ingrese d:");
        lblIngreseD.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblIngreseD.setFont(new Font("Arial", Font.BOLD, 14));
        dSection.add(lblIngreseD, BorderLayout.NORTH);
        
        contd = new JTextField();
        contd.setFont(new Font("Arial", Font.PLAIN, 14));
        contd.setPreferredSize(new Dimension(0, 30));
        contd.setBackground(Color.WHITE);
        dSection.add(contd, BorderLayout.CENTER);
        
        contentPanel.add(dSection);
        
        // 2.2 Panel para "Ingrese n"
        JPanel nSection = new JPanel(new BorderLayout());
        nSection.setBackground(new Color(240, 248, 255)); // Azul claro
        nSection.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        JLabel lblIngreseN = new JLabel("Ingrese n:");
        lblIngreseN.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblIngreseN.setFont(new Font("Arial", Font.BOLD, 14));
        nSection.add(lblIngreseN, BorderLayout.NORTH);
        
        contn = new JTextField();
        contn.setFont(new Font("Arial", Font.PLAIN, 14));
        contn.setPreferredSize(new Dimension(0, 30));
        contn.setBackground(Color.WHITE);
        nSection.add(contn, BorderLayout.CENTER);
        
        contentPanel.add(nSection);
        
        // 2.3 Panel para "Número a descifrar"
        JPanel inputSection = new JPanel(new BorderLayout());
        inputSection.setBackground(new Color(240, 248, 255)); // Azul claro
        inputSection.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        JLabel lblNumeroDescifrar = new JLabel("Ingrese el numero a descifrar:");
        lblNumeroDescifrar.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblNumeroDescifrar.setFont(new Font("Arial", Font.BOLD, 14));
        inputSection.add(lblNumeroDescifrar, BorderLayout.NORTH);
        
        contcif = new JTextField();
        contcif.setFont(new Font("Arial", Font.PLAIN, 14));
        contcif.setPreferredSize(new Dimension(0, 30));
        contcif.setBackground(Color.WHITE);
        inputSection.add(contcif, BorderLayout.CENTER);
        
        contentPanel.add(inputSection);
        
        // 2.4 Panel con botón descifrar
        JPanel descifrarButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        descifrarButtonPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        descifrarButtonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        btnDescifrar = new JButton("Descifrar");
        btnDescifrar.setBackground(new Color(60, 179, 113)); // Verde
        btnDescifrar.setForeground(Color.WHITE);
        descifrarButtonPanel.add(btnDescifrar);
        btnDescifrar.addActionListener(e -> descifrarNumero());
        
        contentPanel.add(descifrarButtonPanel);
        
        // 2.5 Panel para el resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        resultPanel.setBorder(new EmptyBorder(10, 50, 20, 50));
        
        JLabel lblNumeroDescifrado = new JLabel("Numero descifrado");
        lblNumeroDescifrado.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblNumeroDescifrado.setFont(new Font("Arial", Font.BOLD, 14));
        resultPanel.add(lblNumeroDescifrado, BorderLayout.NORTH);
        
        contdes = new JTextArea();
        contdes.setEditable(false);
        contdes.setRows(4);
        contdes.setFont(new Font("Arial", Font.PLAIN, 20));
        contdes.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(contdes);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(resultPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        this.add(mainPanel);
        btnRegresar.addActionListener(e -> regresarVentana());
        this.setVisible(true);
    }
    
    public void descifrarNumero(){
        cadenacifrada = contcif.getText().trim();
        String[] dividir = cadenacifrada.split(" ");
        BigInteger[] cifrado = new BigInteger[dividir.length];
        
        for(int i = 0; i < dividir.length; i++){
            cifrado[i] = new BigInteger(dividir[i]);
        }
        
        d = new BigInteger(contd.getText());
        n = new BigInteger(contn.getText());
        
        FuncionRSA a = new FuncionRSA();
        String descifrado = a.descifrar(cifrado,d,n);
        
        contdes.setText(descifrado);
    }
    
    public void regresarVentana() {
        new App();
        this.setVisible(false);
    }
}