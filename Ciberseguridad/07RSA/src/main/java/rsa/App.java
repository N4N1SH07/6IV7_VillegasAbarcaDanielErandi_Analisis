/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsa;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;

public class App extends JFrame {
    
    private JButton btnCifrar;
    private JButton btnDescifrar;
    
    public App() {
        setTitle("RSA - Menú Principal");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Azul claro
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("Sistema RSA");
        titulo.setFont(new Font("Arial", Font.PLAIN, 40));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(new Color(25, 25, 112)); // Azul oscuro
        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));
        botonesPanel.setBackground(new Color(240, 248, 255)); // Mismo fondo
        
        btnCifrar = new JButton("Cifrar");
        btnCifrar.setBackground(new Color(60, 179, 113));
        btnCifrar.setPreferredSize(new Dimension(150, 60));
        btnCifrar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnCifrar.setVerticalAlignment(SwingConstants.CENTER);// Verde
        btnCifrar.setForeground(Color.WHITE);
        botonesPanel.add(btnCifrar);
        
        btnDescifrar = new JButton("Descifrar");
        btnDescifrar.setBackground(new Color(220, 20, 60));
        btnDescifrar.setPreferredSize(new Dimension(150, 60));
        btnDescifrar.setFont(new Font("Arial", Font.PLAIN, 20));
        btnDescifrar.setVerticalAlignment(SwingConstants.CENTER);
        btnDescifrar.setForeground(Color.WHITE);
        botonesPanel.add(btnDescifrar);
        
        panel.add(botonesPanel, BorderLayout.CENTER);
        
        add(panel);
        configurarEventos();
        setVisible(true);
    }
    
    private void configurarEventos() {
        btnCifrar.addActionListener(e -> abrirVentanaCifrar());
        btnDescifrar.addActionListener(e -> abrirVentanaDescifrar());
    }
    
    private void abrirVentanaCifrar() {
        this.setVisible(false);
        new Cifrar();
    }
    
    private void abrirVentanaDescifrar() {
        this.setVisible(false);
        new Descifrar();
    }
    
    public static void main(String[] args) {
        // Ejecutar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new App();
        });
    }
}