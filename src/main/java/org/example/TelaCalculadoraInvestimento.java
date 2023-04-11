package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TelaCalculadoraInvestimento extends JFrame{
    private JPanel pnlCalculadoraInvestimento;
    private JLabel txtPoupexxx;
    private JLabel lblCliente;
    private JLabel lblCPF;
    private JLabel lblJuros;
    private JLabel lblAnos;
    private JLabel lblDepositoMensal;
    private JTextField txtCliente;
    private JTextField txtCPF;
    private JTextField txtJuros;
    private JTextField txtAnos;
    private JTextField txtDeposito;
    private JButton btnOK;
    final String URL = "jdbc:mysql://localhost:3306/poupexx";
    final String USER = "root";
    final String PASSWORD = "root";
    final String INSERIR = "INSERT INTO cliente (cpf, cliente, juros, anos, deposito) VALUES(?,?,?,?,?)";
public TelaCalculadoraInvestimento() {
    iniciarComponentes();
    conectar();

}
    public void iniciarComponentes(){
        setTitle("Folha de Pagamento");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(pnlCalculadoraInvestimento);
        setVisible(true);
    }

    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado!");

            final PreparedStatement stmtInserir;

            stmtInserir = con.prepareStatement(INSERIR);
            btnOK.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cpf = txtCPF.getText();
                    String cliente = txtCliente.getText();
                    String juros1 = txtJuros.getText();
                    String anos1 = txtAnos.getText();
                    String deposito1 = txtDeposito.getText();
                        try {
                            Double juros = Double.parseDouble(juros1);
                            Integer anos = Integer.parseInt(anos1);
                            Double deposito = Double.parseDouble(deposito1);
                            stmtInserir.setString(1, cpf);
                            stmtInserir.setString(2, cliente);
                            stmtInserir.setDouble(3, juros);
                            stmtInserir.setInt(4, anos);
                            stmtInserir.setDouble(5, deposito);
                            stmtInserir.executeUpdate();
                            System.out.println("Dados Inseridos!");
                            Double rendimentoSemJuros = (deposito *(anos*12));
                            Double rendimentoComJuros = (rendimentoSemJuros + (rendimentoSemJuros * (juros/100)));
                            String informeRendimento = "Cliente: " + txtCliente.getText() + "\n"
                                    + "CPF: " + txtCPF.getText() + "\n"
                                    + "Juros ao Mês: " + txtJuros.getText() + "%" +"\n"
                                    + "Tempo da Poupança: " + txtAnos.getText() + " Anos"+"\n"
                                    + "Deposito Mensal: R$" + txtDeposito.getText() + "\n"
                                    + "Rendimento Total: " + rendimentoComJuros + "\n";
                            JOptionPane.showMessageDialog(btnOK, informeRendimento);
                            txtCPF.setText("");
                            txtCliente.setText("");
                            txtJuros.setText("");
                            txtAnos.setText("");
                            txtDeposito.setText("");
                        }catch (SQLException ex) {
                            System.out.println("Erro ao conectar o banco de dados 1!");
                        }
                }
            });
        }catch (Exception ex) {
            System.out.println("Erro ao conectar o banco de dados 2!");
        }
    }

    public static void main(String[] args) {
        TelaCalculadoraInvestimento calculadoraInvestimento = new TelaCalculadoraInvestimento();
    }
}
