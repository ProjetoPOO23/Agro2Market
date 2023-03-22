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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
   
public class PainelAgro extends javax.swing.JFrame {
    
    private Produto produto;
    private String nomePDT;
    MarketDAO marketDAO = DAOFactory.criarMarketDAO();


     private final DefaultTableModel modelo;
    
    public static String mail;
    
    public PainelAgro(String email) {
        initComponents();
        
        Agropecuario a = marketDAO.encontrarAgropecuario(email);
        mail=a.getEmail();
        
        PainelCadastrarProduto.setVisible(false);
        MinhaConta.setVisible(false);
        Retirada.setVisible(false);
        
        jNomeUSER.setText(""+a.getRazao_Social());
        
        
        modelo = (DefaultTableModel) tabN.getModel();
        modelo.addColumn("Produto");
        modelo.addColumn("Valor total");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Merceeiro");
        modelo.addColumn("CNPJ");
        modelo.addColumn("Endereço");
        modelo.addColumn("Telefone");
        modelo.addColumn("Email");       
              
        
        vendas();
        AtualizarStatus(email);      
        
    }
   
        private void AtualizarStatus(String email) {        
        Agropecuario a = marketDAO.encontrarAgropecuario(email);
        
        BigDecimal bd = new BigDecimal(a.getSaldo()).setScale(2, RoundingMode.HALF_EVEN);
        
        
        SaldoUSER.setText("R$ "+bd.doubleValue());
        SaldoUSER3.setText("R$ "+bd.doubleValue());
        EstoqueUSER.setText(""+Estoque());        
        NomePUSER.setText(""+nomePDD());
        VendaUSER.setText(""+UltimaVenda(1));        
        VendaUSER2.setText(""+UltimaVenda(2));
        
        if(nomePDD()!= ""){
            bCadastrar.setText(" Editar Produto");            
        }else{
            bCadastrar.setText(" Cadastrar Produto");            
        }
    }
        
        private void temProduto() {        
        
        
        if(nomePDD()!= ""){
            
            jLabelTitulo.setText("Edição de produto");
            tNome.setVisible(false);
            tCategoria.setVisible(false);
            jLabel26.setVisible(false);
            bCadastrar1.setVisible(false);
            jLabel12.setVisible(false);
            tCategoria.setVisible(false);
            bEditar.setVisible(true);
            
            jLabelNOME.setVisible(true);
            jLabelNOME.setText(""+nomePDD());
            jLabelCAT.setVisible(true);
            jLabel10.setVisible(true);
            bExcluirProd.setVisible(true);
        }else{
            
            jLabelTitulo.setText("Cadastro de produto");
            tNome.setVisible(true);
            tCategoria.setVisible(true);
            jLabel26.setVisible(true);
            
            bCadastrar1.setVisible(true);
            jLabel12.setVisible(true);
            tCategoria.setVisible(true);
            bEditar.setVisible(false);
            
            jLabelNOME.setVisible(false);
            jLabelCAT.setVisible(false);
            jLabel10.setVisible(false);
            bExcluirProd.setVisible(false);
        }
    }
        
        private String nomePDD() 
        {
            Agropecuario ap = marketDAO.encontrarAgropecuario(mail);
            List<Produto> pdts = marketDAO.listarProduto();
            String nome="";
            for(Produto p: pdts)
            {
                if(p.getCod_AP()==ap.getCod_AP() && p.isAtivo()==true)
                {
                    nome = p.getNomePD();
                }
            }
            
            return nome;
        }
    
        
        private void RetirarSaldo() {
        Agropecuario a = marketDAO.encontrarAgropecuario(mail);
            
        double valor, retirar;
        valor = Double.parseDouble(jValorD1.getText());
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        
            if(valor > a.getSaldo()){
                JOptionPane.showMessageDialog(null,"Erro!!. A retirada é maior do que está depositado");
            }
            else{
                retirar = a.getSaldo() - bd.doubleValue();
                try {
                a.setSaldo(retirar);
                marketDAO.editarAgropecuario(a);        
                JOptionPane.showMessageDialog(null,"Retirada realizada com sucesso.");
                AtualizarStatus(mail);
                } catch (Exception e) {            
                    JOptionPane.showMessageDialog(null,"Erro");
                }
            }
        }
        
    
     @SuppressWarnings("empty-statement")
    private void vendas() {

        ((DefaultTableModel) tabN.getModel()).setRowCount(0);
        List<Comissao> c = marketDAO.listarComissaoMerceeiroAgropecuario();
        Agropecuario ap = marketDAO.encontrarAgropecuario(mail);

        for (Comissao d : c) {
            if (d.getNomeAP().equals(ap.getNomeAP())) {
                Merceeiro m = marketDAO.enMC(d.getNomeM());
                modelo.addRow(new Object[]{d.getNomePD(), d.getCustoComissao(), d.getQuantidadePD(), d.getNomeM(), m.getCNPJ(), m.getEndereco(), m.getTelefone(), m.getEmail()});
            }

        }
    }
    
    
    private void cadastrartP(String email) {
        //JOptionPane.showMessageDialog(null, "Apertando botao: "+condicao);
        Produto produtoCadastrado = new Produto();
        Produto pds = null;
        Agropecuario ap = marketDAO.encontrarAgropecuario(email);
        List<Produto> produtos = marketDAO.listarProduto();
        
        for(Produto p: produtos)
        {
            if(p.getCod_AP()==ap.getCod_AP() && p.isAtivo()==true)
            {
                pds = p;
            }
        }
        
        if(pds==null)
        {
            produtoCadastrado.setCod_AP(ap.getCod_AP());
            produtoCadastrado.setNomePD(tNome.getText());
            produtoCadastrado.setCategoria((String) tCategoria.getSelectedItem());
            produtoCadastrado.setValor(Float.parseFloat(tValor.getText()));
            produtoCadastrado.setQuantidade(Integer.parseInt(tQuant.getText()));
            produtoCadastrado.setNomeAP(ap.getNomeAP());
            produtoCadastrado.setAtivo(true);
            
            nomePDT = tNome.getText();
            
            int linha = marketDAO.cadastrarProduto(produtoCadastrado);
            if (linha > 0) 
            {
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso.");
            }  
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
            }
        }
        if(pds!=null)
        {
            JOptionPane.showMessageDialog(this, "Voce ja tem um produto cadastrado.");
        }
        
    } 
    
    private int Estoque()
    {
        int estoque=0;
        Agropecuario ap = marketDAO.encontrarAgropecuario(mail);
        Produto pdt = new Produto();
        List<Produto> produtos = marketDAO.listarProduto();
        
        for(Produto pd: produtos)
        {
            if(pd.getCod_AP()==ap.getCod_AP() && pd.isAtivo()==true)
            {
                estoque = pd.getQuantidade();
            }
        }
        
        return estoque;
    }
    
    private String UltimaVenda(int a) // Mostrar por quanto vendeu e a quantidade
    {
        
        String venda="Nao ha";
        if(a==2){
            venda="";
        }
        
        Agropecuario ap = marketDAO.encontrarAgropecuario(mail);
        List<Comissao> comissoes = marketDAO.listarComissaoMerceeiroAgropecuario();
        
        for(Comissao c: comissoes)
        {
            if(c.getNomeAP().toUpperCase().equals(ap.getNomeAP().toUpperCase()))
            {
                if(a==1)
                {
                venda = "Valor: R$"+c.getCustoComissao();
                }
                if(a==2){
                venda = "Quantidade: "+c.getQuantidadePD();
                    
                }
            }
        }
        
        return venda;
    }
    
    private void miConta(String email)
    {      
        Agropecuario a = marketDAO.encontrarAgropecuario(email);
        
        try {
          
            a.setRazao_Social(tRS.getText());
            a.setAtividade(tAtividade.getText());
            a.setNomeAP(tNome1.getText());
            a.setEndereco(tEnd.getText());
            a.setTelefone(tTel.getText());
            a.setEmail(tMail.getText());
            a.setSenha(tSenha.getText());
            marketDAO.editarAgropecuario(a);
        
            JOptionPane.showMessageDialog(null,"Alterado com sucesso. Entre novamente em sua conta.");
            new LoginAgro().setVisible(true);
            this.dispose();
            } catch (Exception e) {            
                JOptionPane.showMessageDialog(null,"Erro");
            }
    }     
    
    private void miProdT()
    {
        Agropecuario a = marketDAO.encontrarAgropecuario(mail);
        Produto pdt = new Produto();
        List<Produto> pdts = marketDAO.listarProduto();
        for(Produto pd: pdts)
        {
            if(pd.getCod_AP()==a.getCod_AP() && pd.isAtivo()==true)
            {
                pdt = pd;
            }
        }
        
        try {
          
            pdt.setQuantidade(Integer.parseInt(tQuant.getText()));
            pdt.setValor(Float.parseFloat(tValor.getText()));
            marketDAO.editarProduto(pdt);
        
            JOptionPane.showMessageDialog(null,"Alterado com sucesso.");
            new PainelAgro(mail).setVisible(true);
            this.dispose();
            } catch (Exception e) {            
                JOptionPane.showMessageDialog(null,"Erro");
            }
    }
            
        
    private void excluirAP(String email){
        Agropecuario ap = marketDAO.encontrarAgropecuario(email);
        List<Produto> pds = marketDAO.listarProduto();
        Produto pt = new Produto();
        pt=null;
        
        for(Produto pd: pds)
        {
            if(pd.getCod_AP()==ap.getCod_AP() && pd.isAtivo()==true)
            {
                pt=pd;
            }
        }
        
        try {

            ap.setAtivo(false);
            marketDAO.apagarAgropecuario(ap);
            if(pt!=null)
            {
                pt.setAtivo(false);
                marketDAO.apagarProduto(pt);
            }
            
            JOptionPane.showMessageDialog(null, "Conta excluida com sucesso. Sentiremos saudades :(");
            new MenuInicial().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro");
        }
        
        /*
        if (pt != null) {
            produtos.remove(pt);
        } else {
            System.out.println("Produto inexistente");
        }

        if (ap != null) {
            ProjetoPOO.agropecuarios.remove(ap);
            JOptionPane.showMessageDialog(null, "Conta excluida com sucesso. Sentiremos saudades :(");
            new MenuInicial().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Conta nao encontrada.");
        }
        */
    }
    
    private void apagarPDT(String email)
    {
        
        Object[] opcao = {"Sim", "Não"};
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja realmente excluir o produto?", "Aviso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
        if (opcaoSelecionada == 0) {            
            }
        Agropecuario ap = marketDAO.encontrarAgropecuario(email);
        List<Produto> pds = marketDAO.listarProduto();
        Produto pt = new Produto();
        
        
        for(Produto pd: pds)
        {
            if(pd.getCod_AP()==ap.getCod_AP() && pd.isAtivo()==true)
            {
                pt=pd;
            }
        }
        
        try {

            pt.setAtivo(false);
            marketDAO.apagarProduto(pt);            
            JOptionPane.showMessageDialog(null, "Produto excluido com sucesso.");
            
            PainelPrincipal.setVisible(true);
            PainelCadastrarProduto.setVisible(false);
            MinhaConta.setVisible(false);
            Retirada.setVisible(false);
                
            AtualizarStatus(mail);
            vendas();
            
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
        Retirada = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jValorD1 = new javax.swing.JTextField();
        jRetirarD = new javax.swing.JButton();
        SaldoUSER3 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        fundo5 = new javax.swing.JLabel();
        PainelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabN = new javax.swing.JTable();
        MinhaConta = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tRS = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tNome1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tTel = new javax.swing.JFormattedTextField();
        tEnd = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tMail = new javax.swing.JTextField();
        tSenha = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        bExcluirConta = new javax.swing.JButton();
        bAlterar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tAtividade = new javax.swing.JTextField();
        tOcultar = new javax.swing.JLabel();
        tMostrar = new javax.swing.JLabel();
        fundo1 = new javax.swing.JLabel();
        PainelCadastrarProduto = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabelTitulo = new javax.swing.JLabel();
        tCategoria = new javax.swing.JComboBox<>();
        jLabelCAT = new javax.swing.JLabel();
        jLabelNOME = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tValor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tQuant = new javax.swing.JTextField();
        bEditar = new javax.swing.JButton();
        bCadastrar1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tNome = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        bExcluirProd = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        bSair = new javax.swing.JButton();
        jNomeUSER = new javax.swing.JLabel();
        bCadastrar = new javax.swing.JButton();
        bResumo = new javax.swing.JButton();
        bMinhaConta = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        VendaUSER2 = new javax.swing.JLabel();
        VendaUSER = new javax.swing.JLabel();
        SaldoUSER = new javax.swing.JLabel();
        EstoqueUSER = new javax.swing.JLabel();
        NomePUSER = new javax.swing.JLabel();
        AtualizarSatus1 = new javax.swing.JLabel();
        AtualizarSatus = new javax.swing.JLabel();
        jRetirar = new javax.swing.JLabel();
        fundoinicial = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Painel Agropecuario - agro2market");
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

        Retirada.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabel22.setText("Retirada");
        Retirada.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 170, 315, 70));

        jLabel24.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel24.setText("Valor: R$");
        Retirada.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));
        Retirada.add(jValorD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 218, -1));

        jRetirarD.setText("Retirar");
        jRetirarD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRetirarD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRetirarDActionPerformed(evt);
            }
        });
        Retirada.add(jRetirarD, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 530, -1, -1));

        SaldoUSER3.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        SaldoUSER3.setText("R$ 0.0");
        Retirada.add(SaldoUSER3, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 170, 310, 70));

        jLabel25.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel25.setText("Meu saldo:");
        Retirada.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 315, 70));

        fundo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        Retirada.add(fundo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        getContentPane().add(Retirada, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        tabN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabN);

        javax.swing.GroupLayout PainelPrincipalLayout = new javax.swing.GroupLayout(PainelPrincipal);
        PainelPrincipal.setLayout(PainelPrincipalLayout);
        PainelPrincipalLayout.setHorizontalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        PainelPrincipalLayout.setVerticalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );

        getContentPane().add(PainelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 940, 350));

        MinhaConta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Razão Social");
        MinhaConta.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, -1, -1));

        jLabel17.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel17.setText("Dados Cadastrais");
        MinhaConta.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        tRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tRSActionPerformed(evt);
            }
        });
        MinhaConta.add(tRS, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 452, -1));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Nome");
        MinhaConta.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        tNome1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNome1ActionPerformed(evt);
            }
        });
        MinhaConta.add(tNome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 452, -1));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Telefone");
        MinhaConta.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Endereço");
        MinhaConta.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        try {
            tTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        MinhaConta.add(tTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 452, -1));

        tEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEndActionPerformed(evt);
            }
        });
        MinhaConta.add(tEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 452, -1));

        jLabel19.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel19.setText("Dados de Acesso");
        MinhaConta.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, -1, -1));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Senha");
        MinhaConta.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, -1, -1));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("E-mail");
        MinhaConta.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

        tMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tMailActionPerformed(evt);
            }
        });
        MinhaConta.add(tMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 452, -1));
        MinhaConta.add(tSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 452, -1));

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

        bAlterar.setText("Alterar");
        bAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAlterarActionPerformed(evt);
            }
        });
        MinhaConta.add(bAlterar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 570, -1, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Atividade");
        MinhaConta.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        tAtividade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tAtividadeActionPerformed(evt);
            }
        });
        MinhaConta.add(tAtividade, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 452, -1));

        tOcultar.setText("Ocultar");
        tOcultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tOcultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tOcultarMouseClicked(evt);
            }
        });
        MinhaConta.add(tOcultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 510, -1, -1));

        tMostrar.setText("Mostrar");
        tMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMostrarMouseClicked(evt);
            }
        });
        MinhaConta.add(tMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 510, -1, -1));

        fundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        MinhaConta.add(fundo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(MinhaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        PainelCadastrarProduto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Categoria ");
        PainelCadastrarProduto.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 379, -1, -1));

        jLabelTitulo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24)); // NOI18N
        jLabelTitulo.setText("Cadastro de produto");
        PainelCadastrarProduto.add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 170, 315, 70));

        tCategoria.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        tCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Perecivel", "Nao perecivel" }));
        tCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tCategoriaActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(tCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 375, 453, 20));

        jLabelCAT.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        PainelCadastrarProduto.add(jLabelCAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, 450, 20));

        jLabelNOME.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        PainelCadastrarProduto.add(jLabelNOME, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 450, 20));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Preço");
        PainelCadastrarProduto.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 419, -1, -1));

        tValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tValorActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(tValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 414, 452, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Quantidade");
        PainelCadastrarProduto.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 452, -1, -1));

        tQuant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tQuantActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(tQuant, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 456, 452, -1));

        bEditar.setText("Alterar");
        bEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditarActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(bEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 550, 80, -1));

        bCadastrar1.setText("Cadastrar");
        bCadastrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCadastrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCadastrar1ActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(bCadastrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(711, 550, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Nome/KG/L");
        PainelCadastrarProduto.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 339, -1, -1));

        tNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNomeActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(tNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 336, 452, -1));

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Ex: Leite 1L");
        PainelCadastrarProduto.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 335, -1, -1));

        bExcluirProd.setText("Excluir");
        bExcluirProd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bExcluirProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirProdActionPerformed(evt);
            }
        });
        PainelCadastrarProduto.add(bExcluirProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 560, -1, -1));

        jLabel10.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel10.setText("Excluir produto");
        PainelCadastrarProduto.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, 134, -1));

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3s.png"))); // NOI18N
        PainelCadastrarProduto.add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        getContentPane().add(PainelCadastrarProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 970, 710));

        jLabel4.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel4.setText("Ultimas vendas:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel5.setText("Minhas vendas:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, -1, -1));

        jLabel6.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel6.setText("Estoque:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 130, -1, -1));

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
        getContentPane().add(bSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 240, 60));

        jNomeUSER.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jNomeUSER.setForeground(new java.awt.Color(255, 255, 255));
        jNomeUSER.setText("AGRO");
        getContentPane().add(jNomeUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 280, 90));

        bCadastrar.setBackground(new java.awt.Color(0, 153, 153));
        bCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        bCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        bCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/3s.png"))); // NOI18N
        bCadastrar.setText("Cadastrar");
        bCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCadastrarActionPerformed(evt);
            }
        });
        getContentPane().add(bCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 240, 60));

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
        getContentPane().add(bResumo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 240, 60));

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
        getContentPane().add(bMinhaConta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 240, 60));

        jLabel7.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 20)); // NOI18N
        jLabel7.setText("Saldo:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        VendaUSER2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        VendaUSER2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        VendaUSER2.setText("x");
        getContentPane().add(VendaUSER2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 280, -1));

        VendaUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        VendaUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(VendaUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, 280, 30));

        SaldoUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 24)); // NOI18N
        SaldoUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SaldoUSER.setText("R$ 0.0");
        getContentPane().add(SaldoUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 260, -1));

        EstoqueUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        EstoqueUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        EstoqueUSER.setText("0");
        getContentPane().add(EstoqueUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 200, 260, -1));

        NomePUSER.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        NomePUSER.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(NomePUSER, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 170, 260, 30));

        AtualizarSatus1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        AtualizarSatus1.setText("atualizar");
        AtualizarSatus1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AtualizarSatus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizarSatus1MouseClicked(evt);
            }
        });
        getContentPane().add(AtualizarSatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 300, -1, -1));

        AtualizarSatus.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        AtualizarSatus.setForeground(new java.awt.Color(255, 255, 255));
        AtualizarSatus.setText("atualizar");
        AtualizarSatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AtualizarSatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizarSatusMouseClicked(evt);
            }
        });
        getContentPane().add(AtualizarSatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 90, -1, -1));

        jRetirar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 12)); // NOI18N
        jRetirar.setText("Retirar");
        jRetirar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jRetirar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRetirarMouseClicked(evt);
            }
        });
        getContentPane().add(jRetirar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, -1, -1));

        fundoinicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo1.png"))); // NOI18N
        getContentPane().add(fundoinicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bDeslogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeslogarActionPerformed
        Menu();
    }//GEN-LAST:event_bDeslogarActionPerformed

    private void bSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSairActionPerformed
        Menu();
    }//GEN-LAST:event_bSairActionPerformed

    private void bCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCadastrarActionPerformed
        temProduto();
        
        PainelCadastrarProduto.setVisible(true);
        Agropecuario ap = marketDAO.encontrarAgropecuario(mail);
        List<Produto> pdts = marketDAO.listarProduto();
        Produto p = new Produto();
        for(Produto pd: pdts)
        {
            if(pd.getCod_AP()==ap.getCod_AP())
            {
                p = pd;
            }
        }
        tValor.setText(Float.toString(p.getValor()));
        tQuant.setText(Integer.toString(p.getQuantidade()));
        tCategoria.setSelectedItem(p.getCategoria());
        tNome.setText(p.getNomePD());
        
        MinhaConta.setVisible(false);
        PainelPrincipal.setVisible(false);
        Retirada.setVisible(false);
    }//GEN-LAST:event_bCadastrarActionPerformed

    private void bResumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResumoActionPerformed
        PainelPrincipal.setVisible(true);
        PainelCadastrarProduto.setVisible(false);
        MinhaConta.setVisible(false);
        Retirada.setVisible(false);
                
        AtualizarStatus(mail);
        vendas();
    }//GEN-LAST:event_bResumoActionPerformed

    private void bMinhaContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMinhaContaActionPerformed
        MinhaConta.setVisible(true);        
        tOcultar.setVisible(false);
        
        Agropecuario a = marketDAO.encontrarAgropecuario(mail);
        tAtividade.setText(a.getAtividade());
        tRS.setText(a.getRazao_Social());
        tNome1.setText(a.getNomeAP());
        tTel.setText(a.getTelefone());
        tEnd.setText(a.getEndereco());
        tMail.setText(a.getEmail());
        tSenha.setText(a.getSenha());
        
        PainelCadastrarProduto.setVisible(false);
        PainelPrincipal.setVisible(false);
        Retirada.setVisible(false);
        
    }//GEN-LAST:event_bMinhaContaActionPerformed

    private void tValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tValorActionPerformed

    private void bCadastrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCadastrar1ActionPerformed
        cadastrartP(mail);
        
    }//GEN-LAST:event_bCadastrar1ActionPerformed

    private void tNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNomeActionPerformed

    private void tQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tQuantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tQuantActionPerformed

    private void tRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tRSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tRSActionPerformed

    private void tNome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNome1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNome1ActionPerformed

    private void tEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tEndActionPerformed

    private void tMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tMailActionPerformed

    private void bExcluirContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirContaActionPerformed
        Object[] opcao = {"Sim", "Não"};
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja realmente excluir sua conta?", "Aviso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
        if (opcaoSelecionada == 0) {
            excluirAP(mail);
        }
        
    }//GEN-LAST:event_bExcluirContaActionPerformed

    private void bAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlterarActionPerformed
        miConta(mail);
    }//GEN-LAST:event_bAlterarActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        
    }//GEN-LAST:event_formWindowGainedFocus

    private void tAtividadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tAtividadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tAtividadeActionPerformed

    private void AtualizarSatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtualizarSatusMouseClicked
        AtualizarStatus(mail);
        vendas();
    }//GEN-LAST:event_AtualizarSatusMouseClicked

    private void jRetirarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRetirarMouseClicked

        Retirada.setVisible(true);
        PainelPrincipal.setVisible(false);
        PainelCadastrarProduto.setVisible(false);
        MinhaConta.setVisible(false);
    }//GEN-LAST:event_jRetirarMouseClicked

    private void jRetirarDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRetirarDActionPerformed
        RetirarSaldo();
    }//GEN-LAST:event_jRetirarDActionPerformed

    private void tOcultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tOcultarMouseClicked
        tSenha.setEchoChar('•');
        tOcultar.setVisible(false);
        tMostrar.setVisible(true);
    }//GEN-LAST:event_tOcultarMouseClicked

    private void tMostrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMostrarMouseClicked
        tSenha.setEchoChar((char)0);
        tMostrar.setVisible(false);
        tOcultar.setVisible(true);
    }//GEN-LAST:event_tMostrarMouseClicked

    private void AtualizarSatus1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AtualizarSatus1MouseClicked
        AtualizarStatus(mail);
        vendas();
    }//GEN-LAST:event_AtualizarSatus1MouseClicked

    private void tCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tCategoriaActionPerformed

    private void bEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditarActionPerformed
        miProdT();
    }//GEN-LAST:event_bEditarActionPerformed

    private void bExcluirProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirProdActionPerformed
       apagarPDT(mail);
    }//GEN-LAST:event_bExcluirProdActionPerformed

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
            java.util.logging.Logger.getLogger(PainelAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PainelAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PainelAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PainelAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         /* Create and display the form */        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PainelAgro(email).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AtualizarSatus;
    private javax.swing.JLabel AtualizarSatus1;
    private javax.swing.JLabel EstoqueUSER;
    private javax.swing.JPanel MinhaConta;
    private javax.swing.JLabel NomePUSER;
    private javax.swing.JPanel PainelCadastrarProduto;
    private javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel Retirada;
    private javax.swing.JLabel SaldoUSER;
    private javax.swing.JLabel SaldoUSER3;
    private javax.swing.JLabel VendaUSER;
    private javax.swing.JLabel VendaUSER2;
    private javax.swing.JButton bAlterar;
    private javax.swing.JButton bCadastrar;
    private javax.swing.JButton bCadastrar1;
    private javax.swing.JButton bDeslogar;
    private javax.swing.JButton bEditar;
    private javax.swing.JButton bExcluirConta;
    private javax.swing.JButton bExcluirProd;
    private javax.swing.JButton bMinhaConta;
    private javax.swing.JButton bResumo;
    private javax.swing.JButton bSair;
    private javax.swing.JLabel fundo;
    private javax.swing.JLabel fundo1;
    private javax.swing.JLabel fundo5;
    private javax.swing.JLabel fundoinicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCAT;
    private javax.swing.JLabel jLabelNOME;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jNomeUSER;
    private javax.swing.JLabel jRetirar;
    private javax.swing.JButton jRetirarD;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jValorD1;
    private javax.swing.JTextField tAtividade;
    private javax.swing.JComboBox<String> tCategoria;
    private javax.swing.JTextField tEnd;
    private javax.swing.JTextField tMail;
    private javax.swing.JLabel tMostrar;
    private javax.swing.JTextField tNome;
    private javax.swing.JTextField tNome1;
    private javax.swing.JLabel tOcultar;
    private javax.swing.JTextField tQuant;
    private javax.swing.JTextField tRS;
    private javax.swing.JPasswordField tSenha;
    private javax.swing.JFormattedTextField tTel;
    private javax.swing.JTextField tValor;
    private javax.swing.JTable tabN;
    // End of variables declaration//GEN-END:variables
}
