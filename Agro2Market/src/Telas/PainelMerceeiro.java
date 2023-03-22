package Telas;

import Classes.Agropecuario;
import Classes.Comissao;
import Classes.Merceeiro;
import Classes.Produto;
import dao.DAOFactory;
import dao.MarketDAO;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class PainelMerceeiro extends javax.swing.JFrame {
    
    MarketDAO marketDAO = DAOFactory.criarMarketDAO();
    private Agropecuario agropecuario;
    private Produto produto;
    
    private final DefaultTableModel modelo;
    private final DefaultTableModel modelo2;
    
    public static String mail;

    public PainelMerceeiro(String email) {
        initComponents();
        
        Merceeiro m = marketDAO.encontrarMerceeiro(email);
        mail = m.getEmail();
        
        PainelPrincipal.setVisible(true);
        PainelCompra.setVisible(false);
        MinhaConta.setVisible(false);
        JanelaCompra.setVisible(false);
        JanelaConfirma.setVisible(false);
        
        Deposito.setVisible(false);
        Retirada.setVisible(false);
        
        jNomeUSER.setText(""+m.getRazao_Social());
                
        modelo = (DefaultTableModel) jProd.getModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        modelo.addColumn("Valor");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Fornecedor");
        modelo.addColumn("Codigo");
        
        modelo2 = (DefaultTableModel) tabN.getModel();
        modelo2.addColumn("Produto");
        modelo2.addColumn("Quantidade");
        modelo2.addColumn("Valor total");
        modelo2.addColumn("Fornecedor");
        modelo2.addColumn("Endereco");
        modelo2.addColumn("Telefone");
        modelo2.addColumn("Email");
        
        compras(email);
        AtualizarStatus(email);
    }
   
        private void AtualizarStatus(String email) {        
        Merceeiro m = marketDAO.encontrarMerceeiro(email);
        BigDecimal bd = new BigDecimal(m.getSaldo()).setScale(2, RoundingMode.HALF_EVEN);
        
        SaldoUSER.setText("R$ "+bd.doubleValue());
        SaldoUSER2.setText("R$ "+bd.doubleValue());
        SaldoUSER3.setText("R$ "+bd.doubleValue());
        SaldoUSERC.setText("R$ "+bd.doubleValue());
        CompradosUSER.setText(""+QuantidadeVezesCompra());
        CompraUSER.setText(""+UltimaCompra());
                                            
    }
        private void DepositarSaldo() {     
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);
        double valor, adicionar;
        
        valor = Double.parseDouble(jValorD.getText());
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        
        try {
            adicionar = m.getSaldo() + bd.doubleValue();
            m.setSaldo(adicionar);
            marketDAO.editarMerceeiro(m);

            JOptionPane.showMessageDialog(null,"Deposito efetuado com sucesso.");
            AtualizarStatus(mail);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro.");
        }
    }
        private void RetirarSaldo() {
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);

        double valor, retirar;
        valor = Double.parseDouble(jValorD1.getText());
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);

        if(valor > m.getSaldo())
        {
            JOptionPane.showMessageDialog(null,"Erro. A quantidade solicitada é maior do que saldo disponivel.");
        }
        else
        {
            retirar = m.getSaldo() - bd.doubleValue();
            try {
                m.setSaldo(retirar);
                marketDAO.editarMerceeiro(m);
                JOptionPane.showMessageDialog(null,"Retirada realizada com sucesso.");
                AtualizarStatus(mail);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Erro.");
            }
        }
    }
    
    @SuppressWarnings("empty-statement")
        private void compras(String email) {            
        ((DefaultTableModel) tabN. getModel()). setRowCount(0);
        Merceeiro m = marketDAO.encontrarMerceeiro(email);
        List<Comissao> comissoes = marketDAO.listarComissaoMerceeiroAgropecuario();
        
        for(Comissao c: comissoes)
        {
            if(c.getNomeM().equals(m.getNomeM()))
            {
                Agropecuario ap = marketDAO.enAgp(c.getNomeAP());
                modelo2.addRow(new Object[]{c.getNomePD(), c.getQuantidadePD(), c.getCustoComissao(), c.getNomeAP(), ap.getEndereco(), ap.getTelefone(), ap.getEmail()});
            }
        }
            
    }
    
        @SuppressWarnings("empty-statement")
    private void prodT() 
    {
        List<Produto> pds = marketDAO.listarProduto();
        
        ((DefaultTableModel) jProd. getModel()). setRowCount(0);
        for (Produto PD : pds) 
        {
            if(PD.isAtivo()==true && PD.getQuantidade()>0)
            {
                modelo.addRow(new Object[]{PD.getNomePD(), PD.getCategoria(), PD.getValor(), PD.getQuantidade(), PD.getNomeAP(),PD.getCod_P()});
            }
        }                  
    }
    
    private void produtoPesquisar(String nomePD) // nomePD pegando o jPesquisa(jTextField)
    {        
        ((DefaultTableModel) jProd. getModel()). setRowCount(0);
        List<Produto> pdts = marketDAO.encontrarProduto(nomePD);
        
        
        for(Produto PD: pdts)
        {
            if(PD.isAtivo()==true && PD.getQuantidade()>0)
            {                   
               modelo.addRow(new Object[]{PD.getNomePD(), PD.getCategoria(), PD.getValor(), PD.getQuantidade(), PD.getNomeAP(),PD.getCod_P()});
            }            
        }
    }
    
    private void comprarProduto(String quantidade, Agropecuario ap, Produto pd)
    {
        int quant;
        double totalCompra,comissaoADM,descontar;
        quant = Integer.parseInt(quantidade);
        Comissao comissao = new Comissao();
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);
        
        totalCompra = pd.getValor() * quant;
        BigDecimal bd = new BigDecimal(totalCompra).setScale(2, RoundingMode.HALF_EVEN);
        
                
            if(bd.doubleValue()>m.getSaldo())
            {
                BigDecimal bd2 = new BigDecimal(m.getSaldo()).setScale(2, RoundingMode.HALF_EVEN);
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.\nValor total: "+bd.doubleValue() + " Seu saldo: "+bd2.doubleValue());
            }
            if(bd.doubleValue()<=m.getSaldo())
            {
                                
                descontar = (bd.doubleValue()*20)/100; 
                comissaoADM = descontar;
                totalCompra = bd.doubleValue()-descontar;
                
                pd.setQuantidade(pd.getQuantidade() - quant);
                marketDAO.editarProduto(pd);
                
                ap.setSaldo(totalCompra);
                marketDAO.editarAgropecuario(ap);
                
                m.setSaldo(m.getSaldo() - bd.doubleValue());
                marketDAO.editarMerceeiro(m);
                
                JOptionPane.showMessageDialog(this,"Compra bem sucedida");
                comissao.setCod_P(pd.getCod_P());
                comissao.setCod_AP(ap.getCod_AP());
                comissao.setCod_M(m.getCod_M());
                comissao.setCustoComissao(bd.doubleValue());
                comissao.setQuantidadePD(quant);
                comissao.setNomeAP(ap.getNomeAP());
                comissao.setNomeM(m.getNomeM());
                comissao.setNomePD(pd.getNomePD());
                comissao.setComissaoADM(comissao.getComissaoADM()+comissaoADM);
                marketDAO.registrarComissao(comissao);
                
                prodT();
                JanelaConfirma.setVisible(false);
                JanelaCompra.setVisible(false);
                jPesquisa.setVisible(true);
                bVoltarPainel1.setVisible(true);
                bComprarP.setVisible(true);
                tValorCompra.setText("0.0");
                tQuantidadeCompra.setText("");
            }
        
        
    }
    

    private void statusCompra(String quantidade, Produto pd)
    {
        int quant;
        double totalCompra;
        quant = Integer.parseInt(quantidade);
                        
        
        if(pd.getQuantidade()>= quant && quant>0)
        {
            totalCompra = pd.getValor() * quant;
        
            BigDecimal bd = new BigDecimal(totalCompra).setScale(2, RoundingMode.HALF_EVEN);
            tValorCompra.setText("R$ "+bd.doubleValue());
            tQuantCompra.setText(""+quant);         
            JanelaConfirma.setVisible(true);
        }
        if(pd.getQuantidade()< quant)
        {
            JOptionPane.showMessageDialog(this,"Quantidade acima do disponivel."
                                                + "\nSera alterado para quantidade maxima: "+pd.getQuantidade()+"   ");
            tQuantidadeCompra.setText(""+pd.getQuantidade());
        }
        if(quant<=0)
        {
            JOptionPane.showMessageDialog(this,"Defina uma quantidade.");
        }
    }
    
    private int QuantidadeVezesCompra()
    {
        int i=0;
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);
        List<Comissao> comissoes = marketDAO.listarComissaoMerceeiroAgropecuario();
        for(Comissao c: comissoes)
        {
            if(c.getNomeM().toUpperCase().equals(m.getNomeM().toUpperCase()))
            {
                i++;
            }
        }
        
        return i;
    }
    
    private String UltimaCompra()
    {
        // Duvida nessa funcao se coloca apenas que produto comprou
        // ou se tambem coloca o preco e a quantidade 
        // Ai vcs me dizem
        String nomeProdT="Nao ha";
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);
        List<Comissao> comissoes = marketDAO.listarComissaoMerceeiroAgropecuario();
        for(Comissao c: comissoes)
        {
            if(c.getNomeM().toUpperCase().equals(m.getNomeM().toUpperCase()))
            {
                nomeProdT = c.getQuantidadePD()+"x "+c.getNomePD();
            }
        }
        
        return nomeProdT;
    }
    
    
    
    private int JanelaCompra() {
        int idP = -1; //melhorar
        try {                        
            Integer id = (Integer) modelo.getValueAt(jProd.getSelectedRow(), 5);
            idP=id;
            Merceeiro m = marketDAO.encontrarMerceeiro(mail);
            List<Produto> produtos = marketDAO.listarProduto();
            
            for (Produto PD: produtos)
            {
              if(PD.getCod_P()==id)
              {
                 prodStatus.setText("Nome: "+PD.getNomePD()+" - Valor: R$"+PD.getValor()+" - Disponivel: "+PD.getQuantidade()+" - Fornecedor: "+PD.getNomeAP());
                 produto = PD;
              }
            }
            
            agropecuario = marketDAO.encontrarAgropecuarioPeloProduto(produto.getCod_AP());
            
            JanelaCompra.setVisible(true);
            jPesquisa.setVisible(false);        
            bVoltarPainel1.setVisible(false);        
            bComprarP.setVisible(false);
                        
            enderecoMOSTRAR.setText(m.getEndereco());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Por favor, selecionar um produto");
        }
        return idP;
    }
    
    
        private void miConta(String email){      
                
        Merceeiro m = marketDAO.encontrarMerceeiro(email);
        
        try {

            m.setRazao_Social(tRS.getText());
            m.setNomeM(tNome.getText());
            m.setEndereco(tEnd.getText());
            m.setTelefone(tTel.getText());
            m.setEmail(tMail.getText());
            m.setSenha(tSenha.getText());
            marketDAO.editarMerceeiro(m);
            
            JOptionPane.showMessageDialog(null, "Alterado com sucesso. Entre novamente em sua conta.");
            new LoginAgro().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro");

        }
    }    

    private void excluirM(String email){
       Merceeiro m = marketDAO.encontrarMerceeiro(email);
        
        try {

            m.setAtivo(false);
            marketDAO.apagarMerceeiro(m);
            
            JOptionPane.showMessageDialog(null, "Conta excluida com sucesso. Sentiremos saudades :(");
            new MenuInicial().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro");

        }
    }
    
    
    private void Menu() {
        new MenuInicial().setVisible(true);
        this.dispose();
        JOptionPane.showMessageDialog(null,"Sessao encerrada com sucesso.");           
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bDeslogar = new javax.swing.JButton();
        PainelPrincipal = new javax.swing.JPanel();
        MinhaConta = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        bExcluirConta = new javax.swing.JButton();
        tSenha = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tMail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tTel = new javax.swing.JFormattedTextField();
        tEnd = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tRS = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        bAlterar = new javax.swing.JButton();
        tMostrar = new javax.swing.JLabel();
        tOcultar = new javax.swing.JLabel();
        fundo1 = new javax.swing.JLabel();
        Deposito = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jValorD = new javax.swing.JTextField();
        jDepositarD = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        SaldoUSER2 = new javax.swing.JLabel();
        fundo4 = new javax.swing.JLabel();
        Retirada = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jValorD1 = new javax.swing.JTextField();
        jRetirarD = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        SaldoUSER3 = new javax.swing.JLabel();
        fundo5 = new javax.swing.JLabel();
        HistoricoCompras = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabN = new javax.swing.JTable();
        jNomeUSER = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        SaldoUSER = new javax.swing.JLabel();
        CompraUSER = new javax.swing.JLabel();
        CompradosUSER = new javax.swing.JLabel();
        AtualizarSatus1 = new javax.swing.JLabel();
        AtualizarSatus = new javax.swing.JLabel();
        jDepositar = new javax.swing.JLabel();
        jRetirar = new javax.swing.JLabel();
        bSair = new javax.swing.JButton();
        bComprar = new javax.swing.JButton();
        bResumo = new javax.swing.JButton();
        bMinhaConta = new javax.swing.JButton();
        fundoP = new javax.swing.JLabel();
        PainelCompra = new javax.swing.JPanel();
        JanelaCompra = new javax.swing.JPanel();
        JanelaConfirma = new javax.swing.JPanel();
        tValorCompra3 = new javax.swing.JLabel();
        tQuantCompra = new javax.swing.JLabel();
        tValorCompra = new javax.swing.JLabel();
        tValorCompra2 = new javax.swing.JLabel();
        bComprarDEF2 = new javax.swing.JButton();
        bComprarDEF3 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        enderecoMOSTRAR = new javax.swing.JLabel();
        prodStatus = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tQuantidadeCompra = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        bComprarDEF1 = new javax.swing.JButton();
        bComprarDEF = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        bVoltarPainel1 = new javax.swing.JButton();
        bComprarP = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPesquisar = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tSaldo1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jProd = new javax.swing.JTable();
        SaldoUSERC = new javax.swing.JLabel();
        jPesquisa = new javax.swing.JTextField();
        fundocompra = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Painel Merceeiro - agro2market");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setResizable(false);
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

        PainelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MinhaConta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel9.setText("Excluir conta");
        MinhaConta.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, 134, -1));

        bExcluirConta.setText("Excluir");
        bExcluirConta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bExcluirConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirContaActionPerformed(evt);
            }
        });
        MinhaConta.add(bExcluirConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 590, -1, -1));
        MinhaConta.add(tSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, 452, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Senha");
        MinhaConta.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("E-mail");
        MinhaConta.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 470, -1, -1));

        tMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tMailActionPerformed(evt);
            }
        });
        MinhaConta.add(tMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 460, 452, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel13.setText("Dados de Acesso");
        MinhaConta.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, -1, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Endereço");
        MinhaConta.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Telefone");
        MinhaConta.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        try {
            tTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        MinhaConta.add(tTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 452, -1));

        tEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEndActionPerformed(evt);
            }
        });
        MinhaConta.add(tEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 452, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Nome");
        MinhaConta.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        tNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNomeActionPerformed(evt);
            }
        });
        MinhaConta.add(tNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 452, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Razão Social");
        MinhaConta.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, -1, -1));

        tRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tRSActionPerformed(evt);
            }
        });
        MinhaConta.add(tRS, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 452, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel17.setText("Dados Cadastrais");
        MinhaConta.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        bAlterar.setText("Alterar");
        bAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAlterarActionPerformed(evt);
            }
        });
        MinhaConta.add(bAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 570, -1, -1));

        tMostrar.setText("Mostrar");
        tMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMostrarMouseClicked(evt);
            }
        });
        MinhaConta.add(tMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 490, -1, -1));

        tOcultar.setText("Ocultar");
        tOcultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tOcultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tOcultarMouseClicked(evt);
            }
        });
        MinhaConta.add(tOcultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 490, -1, -1));

        fundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        MinhaConta.add(fundo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        PainelPrincipal.add(MinhaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        Deposito.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel11.setText("Deposito");
        Deposito.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 170, 315, 70));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel10.setText("Valor: R$");
        Deposito.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, -1, -1));
        Deposito.add(jValorD, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 218, -1));

        jDepositarD.setText("Depositar");
        jDepositarD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jDepositarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDepositarDActionPerformed(evt);
            }
        });
        Deposito.add(jDepositarD, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 520, -1, -1));

        jLabel27.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel27.setText("Meu saldo:");
        Deposito.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 315, 70));

        SaldoUSER2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        SaldoUSER2.setText("R$ 0.0");
        Deposito.add(SaldoUSER2, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 170, 310, 70));

        fundo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        Deposito.add(fundo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        PainelPrincipal.add(Deposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        Retirada.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel24.setText("Retirada");
        Retirada.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 170, 315, 70));

        jLabel26.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel26.setText("Valor: R$");
        Retirada.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));
        Retirada.add(jValorD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 218, -1));

        jRetirarD.setText("Retirar");
        jRetirarD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRetirarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRetirarDActionPerformed(evt);
            }
        });
        Retirada.add(jRetirarD, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 530, -1, -1));

        jLabel28.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel28.setText("Meu saldo:");
        Retirada.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 315, 70));

        SaldoUSER3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        SaldoUSER3.setText("R$ 0.0");
        Retirada.add(SaldoUSER3, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 170, 310, 70));

        fundo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        Retirada.add(fundo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        PainelPrincipal.add(Retirada, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        HistoricoCompras.setBackground(new java.awt.Color(255, 255, 255));
        HistoricoCompras.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabN);

        HistoricoCompras.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 350));

        PainelPrincipal.add(HistoricoCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 940, 350));

        jNomeUSER.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jNomeUSER.setForeground(new java.awt.Color(255, 255, 255));
        jNomeUSER.setText("Merceeiro");
        PainelPrincipal.add(jNomeUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 280, 90));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel6.setText("Produtos comprados:");
        PainelPrincipal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel5.setText("Histórico de compra:");
        PainelPrincipal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, -1, -1));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel4.setText("Ultima compra:");
        PainelPrincipal.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel7.setText("Saldo:");
        PainelPrincipal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        SaldoUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        SaldoUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SaldoUSER.setText("R$ 0.0");
        PainelPrincipal.add(SaldoUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 260, -1));

        CompraUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 22)); // NOI18N
        CompraUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CompraUSER.setText("Nao ha");
        PainelPrincipal.add(CompraUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, 280, -1));

        CompradosUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        CompradosUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        CompradosUSER.setText("0");
        PainelPrincipal.add(CompradosUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 170, 260, -1));

        AtualizarSatus1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        AtualizarSatus1.setText("atualizar");
        AtualizarSatus1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AtualizarSatus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizarSatus1MouseClicked(evt);
            }
        });
        PainelPrincipal.add(AtualizarSatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 300, -1, -1));

        AtualizarSatus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        AtualizarSatus.setForeground(new java.awt.Color(255, 255, 255));
        AtualizarSatus.setText("atualizar");
        AtualizarSatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AtualizarSatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizarSatusMouseClicked(evt);
            }
        });
        PainelPrincipal.add(AtualizarSatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 90, -1, -1));

        jDepositar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jDepositar.setText("Depositar");
        jDepositar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jDepositar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDepositarMouseClicked(evt);
            }
        });
        PainelPrincipal.add(jDepositar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, -1, -1));

        jRetirar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jRetirar.setText("Retirar");
        jRetirar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRetirar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRetirarMouseClicked(evt);
            }
        });
        PainelPrincipal.add(jRetirar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, -1, -1));

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
        PainelPrincipal.add(bSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 240, 60));

        bComprar.setBackground(new java.awt.Color(0, 153, 153));
        bComprar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bComprar.setForeground(new java.awt.Color(255, 255, 255));
        bComprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/3s.png"))); // NOI18N
        bComprar.setText("Comprar");
        bComprar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarActionPerformed(evt);
            }
        });
        PainelPrincipal.add(bComprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 240, 60));

        bResumo.setBackground(new java.awt.Color(0, 153, 153));
        bResumo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bResumo.setForeground(new java.awt.Color(255, 255, 255));
        bResumo.setText("Resumo");
        bResumo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bResumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResumoActionPerformed(evt);
            }
        });
        PainelPrincipal.add(bResumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 240, 60));

        bMinhaConta.setBackground(new java.awt.Color(0, 153, 153));
        bMinhaConta.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bMinhaConta.setForeground(new java.awt.Color(255, 255, 255));
        bMinhaConta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2s.png"))); // NOI18N
        bMinhaConta.setText(" Minha conta");
        bMinhaConta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bMinhaConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMinhaContaActionPerformed(evt);
            }
        });
        PainelPrincipal.add(bMinhaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 240, 60));

        fundoP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo1.png"))); // NOI18N
        PainelPrincipal.add(fundoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        getContentPane().add(PainelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        PainelCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JanelaCompra.setBackground(new java.awt.Color(255, 255, 255));
        JanelaCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JanelaCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JanelaConfirma.setBackground(new java.awt.Color(255, 255, 255));
        JanelaConfirma.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        JanelaConfirma.setForeground(new java.awt.Color(255, 255, 255));
        JanelaConfirma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tValorCompra3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
        tValorCompra3.setForeground(new java.awt.Color(0, 153, 153));
        tValorCompra3.setText("Quantidade:");
        tValorCompra3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tValorCompra3FocusLost(evt);
            }
        });
        JanelaConfirma.add(tValorCompra3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 100, -1));

        tQuantCompra.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
        tQuantCompra.setForeground(new java.awt.Color(0, 153, 153));
        tQuantCompra.setText("0");
        tQuantCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tQuantCompraFocusLost(evt);
            }
        });
        JanelaConfirma.add(tQuantCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 180, -1));

        tValorCompra.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
        tValorCompra.setForeground(new java.awt.Color(0, 153, 153));
        tValorCompra.setText("R$ 0.0");
        tValorCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tValorCompraFocusLost(evt);
            }
        });
        JanelaConfirma.add(tValorCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 180, -1));

        tValorCompra2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
        tValorCompra2.setForeground(new java.awt.Color(0, 153, 153));
        tValorCompra2.setText("Valor total:");
        tValorCompra2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tValorCompra2FocusLost(evt);
            }
        });
        JanelaConfirma.add(tValorCompra2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 90, -1));

        bComprarDEF2.setBackground(new java.awt.Color(0, 102, 0));
        bComprarDEF2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        bComprarDEF2.setForeground(new java.awt.Color(255, 255, 255));
        bComprarDEF2.setText("Comprar");
        bComprarDEF2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprarDEF2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarDEF2ActionPerformed(evt);
            }
        });
        JanelaConfirma.add(bComprarDEF2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        bComprarDEF3.setBackground(new java.awt.Color(153, 0, 0));
        bComprarDEF3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        bComprarDEF3.setForeground(new java.awt.Color(255, 255, 255));
        bComprarDEF3.setText("Cancelar");
        bComprarDEF3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprarDEF3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarDEF3ActionPerformed(evt);
            }
        });
        JanelaConfirma.add(bComprarDEF3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        JanelaCompra.add(JanelaConfirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 450, 190));

        jLabel29.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel29.setText("CONFIRME SEU ENDERECO ANTES DE EFETUAR A COMPRA");
        JanelaCompra.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, -1));

        enderecoMOSTRAR.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        JanelaCompra.add(enderecoMOSTRAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 550, 20));

        prodStatus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        prodStatus.setText("Produto:");
        JanelaCompra.add(prodStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 630, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 16)); // NOI18N
        jLabel19.setText("Confirmar compra");
        JanelaCompra.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 16, 160, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 51, 0));
        jLabel20.setText("Fechar (X)");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        JanelaCompra.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, -1, -1));

        tQuantidadeCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tQuantidadeCompraActionPerformed(evt);
            }
        });
        tQuantidadeCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tQuantidadeCompraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tQuantidadeCompraKeyReleased(evt);
            }
        });
        JanelaCompra.add(tQuantidadeCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 140, -1));

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel22.setText("Quantidade:");
        JanelaCompra.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, -1));

        bComprarDEF1.setBackground(new java.awt.Color(153, 0, 0));
        bComprarDEF1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        bComprarDEF1.setForeground(new java.awt.Color(255, 255, 255));
        bComprarDEF1.setText("Cancelar");
        bComprarDEF1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprarDEF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarDEF1ActionPerformed(evt);
            }
        });
        JanelaCompra.add(bComprarDEF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, -1, -1));

        bComprarDEF.setBackground(new java.awt.Color(0, 102, 0));
        bComprarDEF.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        bComprarDEF.setForeground(new java.awt.Color(255, 255, 255));
        bComprarDEF.setText("Avançar");
        bComprarDEF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprarDEF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarDEFActionPerformed(evt);
            }
        });
        JanelaCompra.add(bComprarDEF, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, -1, -1));

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel25.setText("Endereço:");
        JanelaCompra.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        PainelCompra.add(JanelaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 760, 370));

        bVoltarPainel1.setBackground(new java.awt.Color(204, 51, 0));
        bVoltarPainel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bVoltarPainel1.setForeground(new java.awt.Color(255, 255, 255));
        bVoltarPainel1.setText("Voltar");
        bVoltarPainel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bVoltarPainel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVoltarPainel1ActionPerformed(evt);
            }
        });
        PainelCompra.add(bVoltarPainel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 340, 240, 60));

        bComprarP.setBackground(new java.awt.Color(0, 153, 153));
        bComprarP.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bComprarP.setForeground(new java.awt.Color(255, 255, 255));
        bComprarP.setText("Comprar");
        bComprarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bComprarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bComprarPActionPerformed(evt);
            }
        });
        PainelCompra.add(bComprarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 260, 240, 60));

        jLabel21.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 153, 102));
        jLabel21.setText("Exibir todos");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        PainelCompra.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 150, 130, -1));

        jPesquisar.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        jPesquisar.setForeground(new java.awt.Color(0, 153, 102));
        jPesquisar.setText("IR");
        jPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPesquisarMouseClicked(evt);
            }
        });
        PainelCompra.add(jPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel3.setText("Pesquisar:");
        PainelCompra.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Produtos disponiveis:");
        PainelCompra.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        tSaldo1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        tSaldo1.setForeground(new java.awt.Color(255, 255, 255));
        tSaldo1.setText("Meu saldo:");
        PainelCompra.add(tSaldo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 120, -1, -1));

        jProd.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
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

        PainelCompra.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 930, 500));

        SaldoUSERC.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        SaldoUSERC.setForeground(new java.awt.Color(255, 255, 255));
        SaldoUSERC.setText("R$ 0.0");
        PainelCompra.add(SaldoUSERC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 160, -1, -1));

        jPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPesquisaKeyPressed(evt);
            }
        });
        PainelCompra.add(jPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 400, -1));

        fundocompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo2.png"))); // NOI18N
        fundocompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fundocompraFocusGained(evt);
            }
        });
        PainelCompra.add(fundocompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(PainelCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 740));

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo0.png"))); // NOI18N
        getContentPane().add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bDeslogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeslogarActionPerformed
        Menu();
    }//GEN-LAST:event_bDeslogarActionPerformed

    private void bSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSairActionPerformed
        Menu();
    }//GEN-LAST:event_bSairActionPerformed

    private void bComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarActionPerformed
        PainelCompra.setVisible(true);
        PainelPrincipal.setVisible(false);
        MinhaConta.setVisible(false);
        HistoricoCompras.setVisible(false);        
        Deposito.setVisible(false);
        Retirada.setVisible(false);
        
        AtualizarStatus(mail);
        prodT();
        
        bComprar.setVisible(false);
    }//GEN-LAST:event_bComprarActionPerformed

    private void bResumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResumoActionPerformed
        PainelPrincipal.setVisible(true);
        HistoricoCompras.setVisible(true);
        PainelCompra.setVisible(false);
        MinhaConta.setVisible(false);        
        Deposito.setVisible(false);
        Retirada.setVisible(false);
        
        AtualizarStatus(mail);
    }//GEN-LAST:event_bResumoActionPerformed

    private void bMinhaContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMinhaContaActionPerformed
        MinhaConta.setVisible(true);        
        tOcultar.setVisible(false);
        
        Merceeiro m = marketDAO.encontrarMerceeiro(mail);
        tRS.setText(m.getRazao_Social());
        tEnd.setText(m.getEndereco());
        tMail.setText(m.getEmail());
        tNome.setText(m.getNomeM());
        tSenha.setText(m.getSenha());
        tTel.setText(m.getTelefone());
                
        PainelPrincipal.setVisible(true);
        HistoricoCompras.setVisible(false);
        PainelCompra.setVisible(false);
        Deposito.setVisible(false);
        Retirada.setVisible(false);
    }//GEN-LAST:event_bMinhaContaActionPerformed

    private void bComprarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarPActionPerformed
        
        JanelaCompra();
    }//GEN-LAST:event_bComprarPActionPerformed

    private void bExcluirContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirContaActionPerformed
        Object[] opcao = {"Sim", "Não"};
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja realmente excluir sua conta?", "Aviso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
        if (opcaoSelecionada == 0) {
            excluirM(mail);        
        }
    }//GEN-LAST:event_bExcluirContaActionPerformed

    private void fundocompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fundocompraFocusGained
// TODO add your handling code here:
    }//GEN-LAST:event_fundocompraFocusGained

    private void jProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jProdFocusGained
        prodT();
    }//GEN-LAST:event_jProdFocusGained

    private void tMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tMailActionPerformed

    private void tEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tEndActionPerformed

    private void tNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNomeActionPerformed

    private void tRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tRSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tRSActionPerformed

    private void bAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlterarActionPerformed
        miConta(mail);
    }//GEN-LAST:event_bAlterarActionPerformed

    private void bVoltarPainel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVoltarPainel1ActionPerformed
        PainelPrincipal.setVisible(true);
        PainelCompra.setVisible(false);
        HistoricoCompras.setVisible(true);
        MinhaConta.setVisible(false);
        Deposito.setVisible(false);
        Retirada.setVisible(false);
        
        AtualizarStatus(mail);
        compras(mail);
        bComprar.setVisible(true);
    }//GEN-LAST:event_bVoltarPainel1ActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        JanelaCompra.setVisible(false);
        jPesquisa.setVisible(true);
        
        bVoltarPainel1.setVisible(true);        
        bComprarP.setVisible(true);

    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        prodT();
    }//GEN-LAST:event_jLabel21MouseClicked

    private void tQuantidadeCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tQuantidadeCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tQuantidadeCompraActionPerformed

    private void bComprarDEF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarDEF1ActionPerformed
        JanelaCompra.setVisible(false);
        jPesquisa.setVisible(true);
        
        bVoltarPainel1.setVisible(true);        
        bComprarP.setVisible(true);        
    }//GEN-LAST:event_bComprarDEF1ActionPerformed

    private void tValorCompra2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tValorCompra2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tValorCompra2FocusLost

    private void jDepositarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDepositarDActionPerformed
        DepositarSaldo();
    }//GEN-LAST:event_jDepositarDActionPerformed

    private void jRetirarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRetirarDActionPerformed
        RetirarSaldo();
    }//GEN-LAST:event_jRetirarDActionPerformed

    private void jDepositarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDepositarMouseClicked
        PainelPrincipal.setVisible(true);
        Deposito.setVisible(true);
        Retirada.setVisible(false);
        HistoricoCompras.setVisible(false);
        MinhaConta.setVisible(false);
    }//GEN-LAST:event_jDepositarMouseClicked

    private void jRetirarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRetirarMouseClicked
        PainelPrincipal.setVisible(true);
        Retirada.setVisible(true);
        MinhaConta.setVisible(false);
        HistoricoCompras.setVisible(false);
        Deposito.setVisible(false);
    }//GEN-LAST:event_jRetirarMouseClicked

    private void tMostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMostrarMouseClicked
        tSenha.setEchoChar((char)0);
        tMostrar.setVisible(false);
        tOcultar.setVisible(true);
    }//GEN-LAST:event_tMostrarMouseClicked

    private void tOcultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tOcultarMouseClicked
        tSenha.setEchoChar('•');
        tOcultar.setVisible(false);
        tMostrar.setVisible(true);
    }//GEN-LAST:event_tOcultarMouseClicked

    private void bComprarDEFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarDEFActionPerformed
        statusCompra(tQuantidadeCompra.getText(),produto);
    }//GEN-LAST:event_bComprarDEFActionPerformed

    private void jPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPesquisarMouseClicked
        produtoPesquisar(jPesquisa.getText());        
    }//GEN-LAST:event_jPesquisarMouseClicked

    private void tValorCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tValorCompraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tValorCompraFocusLost

    private void AtualizarSatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtualizarSatusMouseClicked
        AtualizarStatus(mail);
        compras(mail);
    }//GEN-LAST:event_AtualizarSatusMouseClicked

    private void tQuantidadeCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuantidadeCompraKeyPressed
        
    }//GEN-LAST:event_tQuantidadeCompraKeyPressed

    private void tQuantidadeCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tQuantidadeCompraKeyReleased
        
    }//GEN-LAST:event_tQuantidadeCompraKeyReleased

    private void bComprarDEF2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarDEF2ActionPerformed
        comprarProduto(tQuantidadeCompra.getText(),agropecuario,produto);
    }//GEN-LAST:event_bComprarDEF2ActionPerformed

    private void bComprarDEF3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bComprarDEF3ActionPerformed
        JanelaConfirma.setVisible(false);
    }//GEN-LAST:event_bComprarDEF3ActionPerformed

    private void tValorCompra3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tValorCompra3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tValorCompra3FocusLost

    private void tQuantCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tQuantCompraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tQuantCompraFocusLost

    private void AtualizarSatus1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtualizarSatus1MouseClicked
       AtualizarStatus(mail);
       compras(mail);
        
    }//GEN-LAST:event_AtualizarSatus1MouseClicked

    private void jPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPesquisaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){

            try {
                produtoPesquisar(jPesquisa.getText());
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jPesquisaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], String email) {
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
            java.util.logging.Logger.getLogger(PainelMerceeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PainelMerceeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PainelMerceeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PainelMerceeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PainelMerceeiro(email).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AtualizarSatus;
    private javax.swing.JLabel AtualizarSatus1;
    private javax.swing.JLabel CompraUSER;
    private javax.swing.JLabel CompradosUSER;
    private javax.swing.JPanel Deposito;
    private javax.swing.JPanel HistoricoCompras;
    private javax.swing.JPanel JanelaCompra;
    private javax.swing.JPanel JanelaConfirma;
    private javax.swing.JPanel MinhaConta;
    private javax.swing.JPanel PainelCompra;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel Retirada;
    private javax.swing.JLabel SaldoUSER;
    private javax.swing.JLabel SaldoUSER2;
    private javax.swing.JLabel SaldoUSER3;
    private javax.swing.JLabel SaldoUSERC;
    private javax.swing.JButton bAlterar;
    private javax.swing.JButton bComprar;
    private javax.swing.JButton bComprarDEF;
    private javax.swing.JButton bComprarDEF1;
    private javax.swing.JButton bComprarDEF2;
    private javax.swing.JButton bComprarDEF3;
    private javax.swing.JButton bComprarP;
    private javax.swing.JButton bDeslogar;
    private javax.swing.JButton bExcluirConta;
    private javax.swing.JButton bMinhaConta;
    private javax.swing.JButton bResumo;
    private javax.swing.JButton bSair;
    private javax.swing.JButton bVoltarPainel1;
    private javax.swing.JLabel enderecoMOSTRAR;
    private javax.swing.JLabel fundo;
    private javax.swing.JLabel fundo1;
    private javax.swing.JLabel fundo4;
    private javax.swing.JLabel fundo5;
    private javax.swing.JLabel fundoP;
    private javax.swing.JLabel fundocompra;
    private javax.swing.JLabel jDepositar;
    private javax.swing.JButton jDepositarD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jNomeUSER;
    private javax.swing.JTextField jPesquisa;
    private javax.swing.JLabel jPesquisar;
    private javax.swing.JTable jProd;
    private javax.swing.JLabel jRetirar;
    private javax.swing.JButton jRetirarD;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jValorD;
    private javax.swing.JTextField jValorD1;
    private javax.swing.JLabel prodStatus;
    private javax.swing.JTextField tEnd;
    private javax.swing.JTextField tMail;
    private javax.swing.JLabel tMostrar;
    private javax.swing.JTextField tNome;
    private javax.swing.JLabel tOcultar;
    private javax.swing.JLabel tQuantCompra;
    private javax.swing.JTextField tQuantidadeCompra;
    private javax.swing.JTextField tRS;
    private javax.swing.JLabel tSaldo1;
    private javax.swing.JPasswordField tSenha;
    private javax.swing.JFormattedTextField tTel;
    private javax.swing.JLabel tValorCompra;
    private javax.swing.JLabel tValorCompra2;
    private javax.swing.JLabel tValorCompra3;
    private javax.swing.JTable tabN;
    // End of variables declaration//GEN-END:variables
}
