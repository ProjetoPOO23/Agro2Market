package Telas;

import Classes.Agropecuario;
import Classes.Comissao;
import Classes.Merceeiro;
import Classes.Produto;
import dao.DAOFactory;
import dao.MarketDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class Adm extends javax.swing.JFrame {
    
    private Agropecuario agropecuario;
    private Merceeiro merceeiro;
    private Produto produto;
    MarketDAO marketDAO = DAOFactory.criarMarketDAO();
    
    private final DefaultTableModel modelo;
    private final DefaultTableModel modelo1;
    private final DefaultTableModel modelo2;
    private final DefaultTableModel modelo3;

    public Adm() {
        initComponents();
        
        
        prodON.setVisible(false);
        mercON.setVisible(false);
        agroON.setVisible(true);
        
        PainelUsers.setVisible(true); //agro
        PainelUsers1.setVisible(false); //merceeiros
        PainelProdutos.setVisible(false);
        
        modelo = (DefaultTableModel) jTab.getModel(); // agro
        modelo.addColumn("CNPJ");
        modelo.addColumn("Razao Social");
        modelo.addColumn("Atividade");
        modelo.addColumn("Nome");
        modelo.addColumn("Endereco");
        modelo.addColumn("Telefone");
        modelo.addColumn("Email");
        modelo.addColumn("Saldo");
        modelo.addColumn("Ativo");
        
        modelo1 = (DefaultTableModel) jTab1.getModel(); // merceiros
        modelo1.addColumn("CNPJ");
        modelo1.addColumn("Razao Social");
        modelo1.addColumn("Nome");
        modelo1.addColumn("Endereco");
        modelo1.addColumn("Telefone");
        modelo1.addColumn("Email");
        modelo1.addColumn("Saldo");
        modelo1.addColumn("Ativo");
        
        modelo2 = (DefaultTableModel) jProdd.getModel();
        modelo2.addColumn("Codigo Produto");
        modelo2.addColumn("Nome");
        modelo2.addColumn("Fornecedor");
        modelo2.addColumn("Codigo Fornecedor");
        modelo2.addColumn("Categoria");
        modelo2.addColumn("Valor");
        modelo2.addColumn("Quantidade");
        modelo2.addColumn("Ativo");
        
        modelo3 = (DefaultTableModel) jFin.getModel(); // financeiro
        modelo3.addColumn("Codigo");
        modelo3.addColumn("Nome Prod.");
        modelo3.addColumn("Quantidade");
        modelo3.addColumn("Valor total");
        modelo3.addColumn("Merceeiro");
        modelo3.addColumn("Agropecuario");
        
        AtualizarStatus();
        
    }
    private void AtualizarStatus() {        

        AgrosUSER.setText(""+agroT());
        MerceeirosUSER.setText(""+mT());
        ProdutosUSER.setText(""+prodT());
                                            
    }
    private void Menu() {
            new MenuInicial().setVisible(true);
            this.dispose();
    }
    
    
    private void finT() 
     {
        ((DefaultTableModel) jFin. getModel()). setRowCount(0);
        List<Comissao> c = marketDAO.listarComissaoMerceeiroAgropecuario();
        double comissoes = 0;
        
        for(Comissao d: c)
        {            
                modelo3.addRow(new Object[]{d.getCod_C(),d.getNomePD(),d.getQuantidadePD(),d.getCustoComissao(),d.getNomeM(),d.getNomeAP()});
                comissoes+=d.getComissaoADM();
        }
        BigDecimal bd = new BigDecimal(comissoes).setScale(2, RoundingMode.HALF_EVEN);
        
       GanhosADM.setText("R$ "+bd.doubleValue());
     }
    
    @SuppressWarnings("empty-statement")
    
     private int agroT() 
     {
        int i=0;
        ((DefaultTableModel) jTab. getModel()). setRowCount(0);
        List<Agropecuario> agros = marketDAO.listarAgropecuario();
        for (Agropecuario a: agros) 
        {
            modelo.addRow(new Object[]{a.getCNPJ(),a.getRazao_Social(), a.getAtividade(),
                a.getNomeAP(), a.getEndereco(), a.getTelefone(), a.getEmail(), a.getSaldo(), a.isAtivo()});
        i++;
        }
        return i;       
    }
     
     @SuppressWarnings("empty-statement")
     private int mT() 
     {
        int i=0;
        ((DefaultTableModel) jTab1. getModel()). setRowCount(0);
        List<Merceeiro> mercs = marketDAO.listarMerceeiro();
        for (Merceeiro m: mercs) 
        {
            modelo1.addRow(new Object[]{m.getCNPJ(), m.getRazao_Social(), m.getNomeM(), m.getEndereco(), m.getTelefone(), m.getEmail(), m.getSaldo(), m.isAtivo()});
        i++;
        }
        return i;        
    }
     
     @SuppressWarnings("empty-statement")
     private int prodT() 
     {
        int i=0;
        ((DefaultTableModel) jProdd. getModel()). setRowCount(0);
        List<Produto> produtos = marketDAO.listarProduto();
        for (Produto PD: produtos) 
        {
            modelo2.addRow(new Object[]{PD.getCod_P(),PD.getNomePD(),PD.getNomeAP(),PD.getCod_AP(), PD.getCategoria(), PD.getValor(), PD.getQuantidade(), PD.isAtivo()});
            i++;
        }
        return i;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jProd = new javax.swing.JTable();
        bDeslogar = new javax.swing.JButton();
        Gerencial = new javax.swing.JPanel();
        AgrosUSER = new javax.swing.JLabel();
        MerceeirosUSER = new javax.swing.JLabel();
        ProdutosUSER = new javax.swing.JLabel();
        PainelProdutos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jProdd = new javax.swing.JTable();
        PainelUsers1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTab1 = new javax.swing.JTable();
        PainelUsers = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTab = new javax.swing.JTable();
        prodON = new javax.swing.JLabel();
        mercON = new javax.swing.JLabel();
        agroON = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bFinanceiro = new javax.swing.JButton();
        bProd = new javax.swing.JButton();
        bMerc = new javax.swing.JButton();
        bAgro = new javax.swing.JButton();
        bSair = new javax.swing.JButton();
        AtualizarSatus = new javax.swing.JLabel();
        fundoinicial = new javax.swing.JLabel();
        Financeiro = new javax.swing.JPanel();
        bAgro1 = new javax.swing.JButton();
        bSair1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        GanhosADM = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jFin = new javax.swing.JTable();
        fundoinicial1 = new javax.swing.JLabel();

        jProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jProdFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(jProd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adm - agro2market");
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bDeslogar.setBackground(new java.awt.Color(204, 0, 0));
        bDeslogar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bDeslogar.setForeground(new java.awt.Color(255, 255, 255));
        bDeslogar.setText("Deslogar");
        bDeslogar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDeslogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeslogarActionPerformed(evt);
            }
        });
        getContentPane().add(bDeslogar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1167, 24, -1, -1));

        Gerencial.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AgrosUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        AgrosUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        AgrosUSER.setText("0");
        Gerencial.add(AgrosUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 260, -1));

        MerceeirosUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        MerceeirosUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        MerceeirosUSER.setText("0");
        Gerencial.add(MerceeirosUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, 260, -1));

        ProdutosUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        ProdutosUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProdutosUSER.setText("0");
        Gerencial.add(ProdutosUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 170, 260, -1));

        jProdd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jProdd);

        javax.swing.GroupLayout PainelProdutosLayout = new javax.swing.GroupLayout(PainelProdutos);
        PainelProdutos.setLayout(PainelProdutosLayout);
        PainelProdutosLayout.setHorizontalGroup(
            PainelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        PainelProdutosLayout.setVerticalGroup(
            PainelProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelProdutosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Gerencial.add(PainelProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 940, 350));

        jTab1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTab1);

        javax.swing.GroupLayout PainelUsers1Layout = new javax.swing.GroupLayout(PainelUsers1);
        PainelUsers1.setLayout(PainelUsers1Layout);
        PainelUsers1Layout.setHorizontalGroup(
            PainelUsers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        PainelUsers1Layout.setVerticalGroup(
            PainelUsers1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        Gerencial.add(PainelUsers1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 940, 350));

        jTab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTab);

        javax.swing.GroupLayout PainelUsersLayout = new javax.swing.GroupLayout(PainelUsers);
        PainelUsers.setLayout(PainelUsersLayout);
        PainelUsersLayout.setHorizontalGroup(
            PainelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        PainelUsersLayout.setVerticalGroup(
            PainelUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        Gerencial.add(PainelUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 940, 350));

        prodON.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        prodON.setText("Produtos");
        Gerencial.add(prodON, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, -1));

        mercON.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        mercON.setText("Merceeiros");
        Gerencial.add(mercON, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, -1));

        agroON.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        agroON.setText("Agropecuarios ");
        Gerencial.add(agroON, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ADM");
        Gerencial.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 150, 90));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel6.setText("Produtos cadastrados:");
        Gerencial.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel5.setText("Status:");
        Gerencial.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, -1, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel7.setText("Agros cadastrados:");
        Gerencial.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel4.setText("Merceeiros cadastrados:");
        Gerencial.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, -1, -1));

        bFinanceiro.setBackground(new java.awt.Color(0, 153, 153));
        bFinanceiro.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bFinanceiro.setForeground(new java.awt.Color(255, 255, 255));
        bFinanceiro.setText("Financeiro");
        bFinanceiro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bFinanceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bFinanceiroActionPerformed(evt);
            }
        });
        Gerencial.add(bFinanceiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 240, 60));

        bProd.setBackground(new java.awt.Color(0, 153, 153));
        bProd.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bProd.setForeground(new java.awt.Color(255, 255, 255));
        bProd.setText("Produtos");
        bProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProdActionPerformed(evt);
            }
        });
        Gerencial.add(bProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 240, 60));

        bMerc.setBackground(new java.awt.Color(0, 153, 153));
        bMerc.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bMerc.setForeground(new java.awt.Color(255, 255, 255));
        bMerc.setText("Merceeiros");
        bMerc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bMerc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMercActionPerformed(evt);
            }
        });
        Gerencial.add(bMerc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 240, 60));

        bAgro.setBackground(new java.awt.Color(0, 153, 153));
        bAgro.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bAgro.setForeground(new java.awt.Color(255, 255, 255));
        bAgro.setText("Agropecuarios");
        bAgro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAgro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgroActionPerformed(evt);
            }
        });
        Gerencial.add(bAgro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 240, 60));

        bSair.setBackground(new java.awt.Color(0, 153, 153));
        bSair.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bSair.setForeground(new java.awt.Color(255, 255, 255));
        bSair.setText("Sair");
        bSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSairActionPerformed(evt);
            }
        });
        Gerencial.add(bSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 240, 60));

        AtualizarSatus.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        AtualizarSatus.setText("Atualizar");
        AtualizarSatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AtualizarSatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizarSatusMouseClicked(evt);
            }
        });
        Gerencial.add(AtualizarSatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 290, -1, -1));

        fundoinicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo1.png"))); // NOI18N
        Gerencial.add(fundoinicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        getContentPane().add(Gerencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        Financeiro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bAgro1.setBackground(new java.awt.Color(0, 153, 153));
        bAgro1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bAgro1.setForeground(new java.awt.Color(255, 255, 255));
        bAgro1.setText("Gerencial");
        bAgro1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAgro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgro1ActionPerformed(evt);
            }
        });
        Financeiro.add(bAgro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 240, 60));

        bSair1.setBackground(new java.awt.Color(0, 153, 153));
        bSair1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bSair1.setForeground(new java.awt.Color(255, 255, 255));
        bSair1.setText("Sair");
        bSair1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSair1ActionPerformed(evt);
            }
        });
        Financeiro.add(bSair1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 240, 60));

        jLabel8.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        jLabel8.setText("Ganhos:");
        Financeiro.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, -1, -1));

        GanhosADM.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        GanhosADM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        GanhosADM.setText("0");
        Financeiro.add(GanhosADM, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 260, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ADM");
        Financeiro.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 150, 90));

        jFin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jFin);

        Financeiro.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 940, 410));

        fundoinicial1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3.png"))); // NOI18N
        Financeiro.add(fundoinicial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        getContentPane().add(Financeiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
    
    }//GEN-LAST:event_formWindowGainedFocus

    private void bDeslogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeslogarActionPerformed
        Menu();
    }//GEN-LAST:event_bDeslogarActionPerformed

    private void jProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jProdFocusGained
        prodT();
    }//GEN-LAST:event_jProdFocusGained

    private void bAgroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgroActionPerformed
    Gerencial.setVisible(true);
    Financeiro.setVisible(false);
        
    PainelUsers1.setVisible(false);//merceeiros
    PainelUsers.setVisible(true); //agropecuarios
    PainelProdutos.setVisible(false);
    
    prodON.setVisible(false);
    mercON.setVisible(false);
    agroON.setVisible(true);       
    }//GEN-LAST:event_bAgroActionPerformed

    private void bSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSairActionPerformed
        Menu();
    }//GEN-LAST:event_bSairActionPerformed

    private void bMercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMercActionPerformed
    Gerencial.setVisible(true);
    Financeiro.setVisible(false);
    PainelUsers1.setVisible(true);//merceeiros
    PainelUsers.setVisible(false); //agropecuarios
    PainelProdutos.setVisible(false);
    
    prodON.setVisible(false);
    mercON.setVisible(true);
    agroON.setVisible(false);
    }//GEN-LAST:event_bMercActionPerformed

    private void bProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProdActionPerformed
    Gerencial.setVisible(true);
    Financeiro.setVisible(false);
    PainelUsers1.setVisible(false);//merceeiros
    PainelUsers.setVisible(false);//agropecuarios
    PainelProdutos.setVisible(true);
    
    prodON.setVisible(true);
    mercON.setVisible(false);
    agroON.setVisible(false);
    }//GEN-LAST:event_bProdActionPerformed

    private void AtualizarSatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtualizarSatusMouseClicked
        AtualizarStatus();
    }//GEN-LAST:event_AtualizarSatusMouseClicked

    private void bFinanceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bFinanceiroActionPerformed
        Gerencial.setVisible(false);
        Financeiro.setVisible(true);

        finT();
    }//GEN-LAST:event_bFinanceiroActionPerformed

    private void bAgro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgro1ActionPerformed
    Gerencial.setVisible(true);
    Financeiro.setVisible(false);
        
    PainelUsers1.setVisible(false);//merceeiros
    PainelUsers.setVisible(true); //agropecuarios
    PainelProdutos.setVisible(false);
    
    prodON.setVisible(false);
    mercON.setVisible(false);
    agroON.setVisible(true);
    }//GEN-LAST:event_bAgro1ActionPerformed

    private void bSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSair1ActionPerformed
        Menu();
    }//GEN-LAST:event_bSair1ActionPerformed

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
            java.util.logging.Logger.getLogger(Adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Adm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AgrosUSER;
    private javax.swing.JLabel AtualizarSatus;
    private javax.swing.JPanel Financeiro;
    private javax.swing.JLabel GanhosADM;
    private javax.swing.JPanel Gerencial;
    private javax.swing.JLabel MerceeirosUSER;
    private javax.swing.JPanel PainelProdutos;
    private javax.swing.JPanel PainelUsers;
    private javax.swing.JPanel PainelUsers1;
    private javax.swing.JLabel ProdutosUSER;
    private javax.swing.JLabel agroON;
    private javax.swing.JButton bAgro;
    private javax.swing.JButton bAgro1;
    private javax.swing.JButton bDeslogar;
    private javax.swing.JButton bFinanceiro;
    private javax.swing.JButton bMerc;
    private javax.swing.JButton bProd;
    private javax.swing.JButton bSair;
    private javax.swing.JButton bSair1;
    private javax.swing.JLabel fundoinicial;
    private javax.swing.JLabel fundoinicial1;
    private javax.swing.JTable jFin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTable jProd;
    private javax.swing.JTable jProdd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTab;
    private javax.swing.JTable jTab1;
    private javax.swing.JLabel mercON;
    private javax.swing.JLabel prodON;
    // End of variables declaration//GEN-END:variables
}
