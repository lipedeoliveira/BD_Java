/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;

import conexao.Conexao;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

import com.mysql.cj.jdbc.exceptions.SQLError;

import javax.swing.table.DefaultTableModel;


public class TelaCad extends JFrame {
    
    JLabel rCodigo,rNome,rEmail,rTel,rData;
    JTextField tCodigo,tNome,tEmail,tTel,tData,tPesquisa;
    JButton buttonFirst,buttonPrevious,buttonLast,buttonNext,buttonClean,buttonRegister,buttonUpdate,buttonDelete;
    JFormattedTextField tel,data;
    MaskFormatter mTel,mData;    
    Conexao con_cliente;
    JTable tbclientes;
    JScrollPane scp_tabela;
    
    public TelaCad() {
        con_cliente = new Conexao();
        con_cliente.conecta();
        setTitle("Conexão Java com MySql");
        setResizable(false);
        Container tela = getContentPane();
        tela.setLayout(null);        
        
        
        //JLabel's 
        rCodigo = new JLabel("Código:: ");
        rCodigo.setBounds(60,60,80,20);
        
        rNome = new JLabel("Nome:: ");
        rNome.setBounds(60,90,120,20);

        rData = new JLabel("Data:: ");
        rData.setBounds(60,120,100,20);

        rTel = new JLabel("Telefone:: ");
        rTel.setBounds(60,150,130,20);
        
        rEmail = new JLabel("Email:: ");
        rEmail.setBounds(60,180,100,20);        
 
        //
        
        //Mascara
        try {
        
            mTel = new MaskFormatter("(##) ##z##-####");
            mData = new MaskFormatter("##/##/####");
        }
        catch(ParseException excp){}
        //


        //JTextField's
        tCodigo = new JTextField();
        tCodigo.setBounds(130,60,70,20);
        
        tNome = new JTextField();
        tNome.setBounds(130,90,150,20);

        tData = new JFormattedTextField(mData);
        tData.setBounds(130,120,100,20);
        
        tTel = new JFormattedTextField(mTel);
        tTel.setBounds(130,150,120,20);
        
        tEmail = new JTextField();
        tEmail.setBounds(130,180,160,20);

        tPesquisa = new JTextField();
        //

        //Botões
        buttonFirst = new JButton("Primeiro");
        buttonFirst.setBounds(60,230,100,20);
        
        buttonPrevious = new JButton("Anterior");
        buttonPrevious.setBounds(160,230,100,20);

        buttonNext = new JButton("Próximo");
        buttonNext.setBounds(260,230,100,20);

        buttonLast = new JButton("Último");
        buttonLast.setBounds(360,230,100,20);

        buttonClean = new JButton("Limpar");
        buttonClean.setBounds(560,230,100,20);

        buttonRegister = new JButton("Gravar");
        buttonRegister.setBounds(660,230,100,20);

        buttonUpdate = new JButton("Alterar");
        buttonUpdate.setBounds(760,230,100,20);

        buttonDelete = new JButton("Deletar");
        buttonDelete.setBounds(860,230,100,20);
            //Ações dos botões
            buttonFirst.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        con_cliente.resultset.first();
                        mostrar_Dados();
                    }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Não foi possível aceessar o primeiro registro"+erro,"\nMensagem do programa ",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            );

            buttonPrevious.addActionListener( new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        con_cliente.resultset.previous();
                        mostrar_Dados();
                    }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Não foi possível aceessar o registro anterior"+erro,"\nMensagem do programa ",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            );

            buttonNext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        con_cliente.resultset.next();
                        mostrar_Dados();
                    }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Não foi possível acessar o próximo registro "+erro,"\nMensagem  do programa",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            );

            buttonLast.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        con_cliente.resultset.last();
                        mostrar_Dados();
                    }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Não foi possível acessar o último registro "+erro,"\n Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }   
            );

            buttonClean.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    tCodigo.setText("");
                    tNome.setText("");
                    tData.setText("");
                    tTel.setText("");
                    tEmail.setText("");
                    tCodigo.requestFocus();

                    }
                }
            );

            buttonRegister.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String nome = tNome.getText();
                    String telefone = tNome.getText();
                    String email = tNome.getText();
                    String data = tNome.getText();
                        try{
                            String insert_sql = "insert into tbclientes (nome,telefone,email,dt_nasc) values('"+nome+"','"+telefone+"','"+email+"','"+data+"')";
                            con_cliente.statement.executeUpdate(insert_sql);
                            JOptionPane.showMessageDialog(null,"Gravação realizada com sucesso!!","Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);

                            con_cliente.executaSQL("select*from tbclientes order by cod");
                            preencherTabela();
                        }catch(SQLException erro) {
                                JOptionPane.showMessageDialog(null,"\nErro na gravação:\n"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
                        }



                    }

                }
            );
            buttonUpdate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String nome = tNome.getText();
                    String data_nasc = tData.getText();
                    String telefone = tTel.getText();
                    String email = tEmail.getText();
                    String sql;
                    String msg ="";

                        try{
                            if(tCodigo.getText().equals("")) {
                                sql="insert into tbclientes (nome,telefone,email,dt_nasc) values('"+nome+","+telefone+","+email+","+data_nasc+"')";
                                msg="Gravação de um novo registro";
                            }
                            else {
                                sql="update tbclientes set nome"+nome+"',telefone='"+telefone+"',email='"+email+"'dt_nasc='"+data_nasc+"'where cod ="+tCodigo.getText();
                                msg="Alteração do Registro";
                            }

                            con_cliente.statement.executeUpdate(sql);
                            JOptionPane.showMessageDialog(null,"Gravação realizada com sucesso!!","Mensagem do programa",JOptionPane.INFORMATION_MESSAGE);
                        }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Erro na Gravação:\n"+erro," Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            );
            buttonDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String sql="";
                        
                        try {
                            int resposta = JOptionPane.showConfirmDialog(rootPane,"Deseja Excluir o registro:","Confirmar Exclusão",JOptionPane.YES_NO_OPTION,3);
                            if(resposta==0) {
                                sql="delete from tbclientes where cod ="+tCodigo.getText();
                                int excluir =con_cliente.statement.executeUpdate(sql);
                                if(excluir == 1) {

                                    JOptionPane.showMessageDialog(null,"Exclusão realizada com sucesso!!","Mensagem do programa",JOptionPane.INFORMATION_MESSAGE);
                                    con_cliente.executaSQL(sql);
                                    con_cliente.resultset.first();
                                    preencherTabela();
                                    posicionarRegistro();
                                }   
                                else {
                                    JOptionPane.showMessageDialog(null,"Operação cancelada pelo usuário!!","Mensagem do programa",JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }catch(SQLException erro) {
                            JOptionPane.showMessageDialog(null,"Erro na execução",erro+"\nMensagem do programa ",JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                }
            );

        //Configuração do JTable
        tbclientes = new JTable();
        scp_tabela = new JScrollPane();
        
        tbclientes.setBounds(50,400,650,100);
        scp_tabela.setBounds(50,400,650,100);

        tbclientes.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0,0,0)));
        
        tbclientes.setFont(new Font("Arial",1,12));
        
        tbclientes.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][]{
        
            {null,null,null,null,null},
            {null,null,null,null,null},
            {null,null,null,null,null},
            {null,null,null,null,null}
            
        },
        new String[]{"Código","Nome","Data Nascimento","Telefone","E-mail"})
        {
            boolean[] canEdit = new boolean[]{false,false,false,false,false};
            
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return canEdit[columnIndex];
            }

        
        });
        
        scp_tabela.setViewportView(tbclientes);
        tbclientes.setAutoCreateRowSorter(true);
        //

    
        tela.add(rCodigo);
        tela.add(rNome);
        tela.add(rData);
        tela.add(rEmail);
        tela.add(rTel);

        
        tela.add(tCodigo);
        tela.add(tNome);
        tela.add(tData);
        tela.add(tTel);
        tela.add(tEmail);


        tela.add(buttonFirst);
        tela.add(buttonPrevious);
        tela.add(buttonNext);
        tela.add(buttonLast);
        tela.add(buttonClean);
        tela.add(buttonRegister);
        tela.add(buttonUpdate);
        tela.add(buttonDelete);


        tela.add(tbclientes); 
        tela.add(scp_tabela);
        
            setSize(1000,600);
            setLocationRelativeTo(null);
            setVisible(true);

            con_cliente.executaSQL("select * from tbclientes order by cod");
            preencherTabela();
            posicionarRegistro();


        }
    




        //Metodos
        public void preencherTabela() {
            
            tbclientes.getColumnModel().getColumn(0).setPreferredWidth(4);
            tbclientes.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbclientes.getColumnModel().getColumn(2).setPreferredWidth(11);
            tbclientes.getColumnModel().getColumn(3).setPreferredWidth(14);
            tbclientes.getColumnModel().getColumn(4).setPreferredWidth(100);

            DefaultTableModel modelo = (DefaultTableModel) tbclientes.getModel();
            modelo.setNumRows(0);

            try {
                con_cliente.resultset.beforeFirst();
                while (con_cliente.resultset.next()) {
                    modelo.addRow(new Object[]{
                        con_cliente.resultset.getString("cod"),
                        con_cliente.resultset.getString("nome"),
                        con_cliente.resultset.getString("dt_nasc"),
                        con_cliente.resultset.getString("telefone"),
                        con_cliente.resultset.getString("email")
                    });
                    
                }
            
            }catch(SQLException erro) {
                JOptionPane.showMessageDialog(null,"Erro ao lista dados da tabela!!\n"+erro,"Mensagem do programa",JOptionPane.INFORMATION_MESSAGE);
            }

        }


        public void posicionarRegistro() {

            try {
                con_cliente.resultset.first();
                mostrar_Dados();
            }catch(SQLException erro) {
                JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro: "+erro,"Mensagem do programa ",JOptionPane.INFORMATION_MESSAGE);
            }

        }
        public void mostrar_Dados() {
                try {
                    tCodigo.setText(con_cliente.resultset.getString("cod"));
                    tNome.setText(con_cliente.resultset.getString("nome"));
                    tData.setText(con_cliente.resultset.getString("dt_nasc"));
                    tTel.setText(con_cliente.resultset.getString("telefone"));
                    tEmail.setText(con_cliente.resultset.getString("email"));
                }catch(SQLException erro) {
                    JOptionPane.showMessageDialog(null,"Não foi possível localizar os dados: "+erro,"\nMensagem do programa ",JOptionPane.INFORMATION_MESSAGE);
                }
        }


    public static void main(String[]args)
    {
        TelaCad app = new TelaCad();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
