package rsa;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.math.BigInteger;

public class Cifrar extends JFrame {

    private JButton btnRegresar;
    private JButton btnCifrar;
    private JLabel txtresp, txtresq, txtresn, txtresfi, txtrese, txtresd;
    private JTextField numacifrar;
    private JTextArea numcifrado;
    BigInteger n, p, q;
    BigInteger fi; 
    BigInteger e, d;
    String mensaje;

    public Cifrar() {
        setTitle("Cifrar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con fondo azul claro
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Parte superior con el botón regresar y título
        mainPanel.add(crearPanelSuperior(), BorderLayout.NORTH);
        // Parte central con parámetros RSA y campos de entrada/salida
        mainPanel.add(crearPanelContenido(), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel crearPanelSuperior() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255)); // Azul claro

        // Botón regresar
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topButtonPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(220, 20, 60)); // Rojo
        btnRegresar.setForeground(Color.WHITE);
        topButtonPanel.add(btnRegresar);
        topPanel.add(topButtonPanel, BorderLayout.NORTH);
        btnRegresar.addActionListener(e -> regresarVentana());

        // Título
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(240, 248, 255)); // Azul claro
        JLabel title = new JLabel("Cifrar", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(new Color(25, 25, 112)); // Azul oscuro
        titlePanel.add(title);
        topPanel.add(titlePanel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel crearPanelContenido() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        contentPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Parámetros RSA como lista vertical - CON SCROLL
        JPanel rsaPanel = new JPanel();
        rsaPanel.setLayout(new BoxLayout(rsaPanel, BoxLayout.Y_AXIS));
        rsaPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        rsaPanel.setBorder(new EmptyBorder(5, 50, 5, 50));

        contentPanel.add(crearCampoRSALista("p", txtresp = new JLabel("", JLabel.LEFT)));
        contentPanel.add(crearCampoRSALista("q", txtresq = new JLabel("", JLabel.LEFT)));
        contentPanel.add(crearCampoRSALista("n", txtresn = new JLabel("", JLabel.LEFT)));
        contentPanel.add(crearCampoRSALista("fi", txtresfi = new JLabel("", JLabel.LEFT)));
        contentPanel.add(crearCampoRSALista("e", txtrese = new JLabel("", JLabel.LEFT)));
        contentPanel.add(crearCampoRSALista("d", txtresd = new JLabel("", JLabel.LEFT)));

        

        // Campo "Número a Cifrar"
        JPanel inputSection = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        contentPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
        contentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JLabel lblNumeroCifrar = new JLabel("Número a Cifrar");
        lblNumeroCifrar.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblNumeroCifrar.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblNumeroCifrar, BorderLayout.EAST);


        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textFieldPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        numacifrar = new JTextField(20);
        numacifrar.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldPanel.add(numacifrar);
        inputSection.add(textFieldPanel, BorderLayout.CENTER);

        contentPanel.add(inputSection);

        // Botón Cifrar
        JPanel cifrarButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cifrarButtonPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        cifrarButtonPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        cifrarButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnCifrar = new JButton("Cifrar");
        btnCifrar.setBackground(new Color(60, 179, 113)); // Verde
        btnCifrar.setForeground(Color.WHITE);
        cifrarButtonPanel.add(btnCifrar);
        btnCifrar.addActionListener(e -> cifrarNumero());

        contentPanel.add(cifrarButtonPanel);

        // Resultado
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        resultPanel.setBorder(new EmptyBorder(5, 50, 10, 50));
        resultPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblNumeroCifrado = new JLabel("Número cifrado");
        lblNumeroCifrado.setForeground(new Color(25, 25, 112)); // Azul oscuro
        lblNumeroCifrado.setFont(new Font("Arial", Font.BOLD, 14));
        resultPanel.add(lblNumeroCifrado, BorderLayout.NORTH);

        JPanel resultFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultFieldPanel.setBackground(new Color(240, 248, 255)); // Azul claro
        numcifrado = new JTextArea(2, 20);
        numcifrado.setLineWrap(true);
        numcifrado.setEditable(false);
        numcifrado.setBackground(Color.WHITE);
        JScrollPane scrollPane2 = new JScrollPane(numcifrado);
        scrollPane2.setPreferredSize(new Dimension(300, 45));
        resultFieldPanel.add(scrollPane2);
        resultPanel.add(resultFieldPanel, BorderLayout.CENTER);

        contentPanel.add(resultPanel);

        return contentPanel;
    }

    private JPanel crearCampoRSALista(String label, JLabel valueLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(240, 248, 255)); // Azul claro
        panel.setBorder(new EmptyBorder(5, 0, 5, 0)); // Espaciado vertical entre elementos
        
        JLabel labelComponent = new JLabel(label + ": ");
        labelComponent.setForeground(new Color(25, 25, 112)); // Azul oscuro
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setPreferredSize(new Dimension(30, 20)); // Ancho fijo para alineación
        panel.add(labelComponent);
        
        valueLabel.setForeground(new Color(25, 25, 112)); // Azul oscuro
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueLabel);
        
        return panel;
    }

    public void cifrarNumero(){
        mensaje = numacifrar.getText().trim();
        try {
            int numero = Integer.parseInt(mensaje);
            if (numero < 0 || numero > 999) {
            JOptionPane.showMessageDialog(this, "El número debe tener máximo 3 dígitos (0 - 999)");
            return;
            }
        }catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Debes ingresar un número válido");
        return;
        }
        FuncionRSA a = new FuncionRSA();
        a.generarPrimos();
        a.generarClaves();

        p = a.getP();
        q = a.getQ();
        n = a.getN();
        fi = a.getFi();
        e = a.getE();
        d = a.getD();

        txtresp.setText(p.toString());
        txtresq.setText(q.toString());
        txtresn.setText(n.toString());
        txtresfi.setText(fi.toString());
        txtrese.setText(e.toString());
        txtresd.setText(d.toString());

        BigInteger[] mensajeCifrado = a.cifrar(mensaje);

        StringBuilder cifradoStr = new StringBuilder();
        for (BigInteger num : mensajeCifrado) {
        cifradoStr.append(num.toString()).append(" ");
        }
        numcifrado.setText(cifradoStr.toString());

    }

    private void regresarVentana() {
        new App();
        this.setVisible(false);
    }
}