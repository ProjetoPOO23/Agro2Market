package Telas;

import Aplicação.ProjetoPOO;
import Classes.Agropecuario;
import Validacoes.*;
import dao.*;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;


public class CadastroAgro extends javax.swing.JFrame {
    
    private Agropecuario agropecuario;
    MarketDAO marketDAO = DAOFactory.criarMarketDAO();
    
    public CadastroAgro() 
    {
        initComponents();       
        
        pPolitica.setVisible(false);
        tOcultar.setVisible(false);
    }
    
        private void cadastrartAP() 
        {
            int i;
            boolean Email,tel,cnpj,nome,senha;
            
            Agropecuario agropecuarioCadastrado = new Agropecuario();
           
            agropecuarioCadastrado.setRazao_Social(tRS.getText());
            
            agropecuarioCadastrado.setCNPJ(tCNPJ.getText());
            cnpj = ValidaCNPJ.isCNPJ(tCNPJ.getText());
            
            agropecuarioCadastrado.setAtividade((String) tAT.getSelectedItem());
            
            agropecuarioCadastrado.setNomeAP(tNome.getText());
            nome = ValidaString.isName(tNome.getText());
            
            agropecuarioCadastrado.setEndereco(tEnd.getText());
            
            
            agropecuarioCadastrado.setTelefone(tTel.getText());
            tel = ValidaTelefone.isTelefone(tTel.getText());
            
            agropecuarioCadastrado.setEmail(tMail.getText());
            i = MesmoEmail(tMail.getText());
            Email = ValidacaoEmail.isEmail(tMail.getText());
            
            agropecuarioCadastrado.setSenha(tSenha.getText());
            senha = ValidaSenha.iSenha(tSenha.getText());
            
            agropecuarioCadastrado.setSaldo(0.0);
            agropecuarioCadastrado.setAtivo(true);
            
            //JOptionPane.showConfirmDialog(this,"Email: "+tMail.getText() + " e: "+Email);
            //JOptionPane.showConfirmDialog(this,"CNPJ: "+tCNPJ.getText() + " e: "+cnpj);
            //JOptionPane.showConfirmDialog(this,"Tel: "+tTel.getText() + " e: "+tel);
            //JOptionPane.showConfirmDialog(this,"Senha: "+tSenha.getText() + " e: "+senha);
            // nome
            
            if(cnpj==true && tel==true && nome==true)
            {
                if(Email == true && senha==true)
                {
                    if(i==0)
                    {
                        int linha = marketDAO.cadastrarAgropecuario(agropecuarioCadastrado);
                        if (linha > 0) 
                        {
                            JOptionPane.showMessageDialog(this, "Cadastro efetuado com sucesso. Entre em sua conta.");
                            new LoginAgro().setVisible(true);
                            this.dispose();
                        }    
                        else 
                        {
                            JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
                        }
                    }
                    if(i==1)
                    {
                        JOptionPane.showMessageDialog(this, "Já existe uma conta com esse email");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Email ou senha fora do padrão.");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Erro no cadastro. Alguma informação inserida está errada");
            }
        }
        
        public int MesmoEmail(String email)
        {
            int i=0;
            List<Agropecuario> agp = marketDAO.listarAgropecuario();
            
            for(Agropecuario ap: agp)
            {
                if(ap.getEmail().equals(email))
                {
                    i=1;
                }
            }
            
            return i; 
        }
        
    /*private void termos() {
        JOptionPane.showMessageDialog(null,"Blabla                               "
                + "\n\nAo utilizar nosso aplicativo você está de acordo com tudo aqui escrito e não escrito");
    }*/
    
    
    private void politica() {
        pPolitica.setVisible(true);
        bCancelar1.setVisible(false);
        bCadastrar.setVisible(false);
        tEntrar.setVisible(false);
        jLabel12.setVisible(false);
    }
      
                
    
    private void cancelar() {
        Object[] opcao = {"Sim", "Não"};
        int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja realmente voltar ao menu principal?\n(todos os dados preenchidos serão perdidos)", "Aviso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
        if (opcaoSelecionada == 0) {            
            new MenuInicial().setVisible(true);
            this.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bCancelar1 = new javax.swing.JButton();
        pPolitica = new javax.swing.JPanel();
        fechar = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel32 = new javax.swing.JLabel();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        tEntrar = new javax.swing.JLabel();
        bCadastrar = new javax.swing.JButton();
        tPolitica = new javax.swing.JLabel();
        tTermos = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tRS = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tMail = new javax.swing.JTextField();
        tEnd = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tSenha = new javax.swing.JPasswordField();
        titulocadastro1 = new javax.swing.JLabel();
        tOcultar = new javax.swing.JLabel();
        tMostrar = new javax.swing.JLabel();
        tCNPJ = new javax.swing.JFormattedTextField();
        tTel = new javax.swing.JFormattedTextField();
        tAT = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro Agropecuario - agro2market");
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bCancelar1.setText("Voltar");
        bCancelar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelar1ActionPerformed(evt);
            }
        });
        getContentPane().add(bCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 620, -1, -1));

        pPolitica.setBackground(new java.awt.Color(255, 255, 255));
        pPolitica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        fechar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        fechar.setForeground(new java.awt.Color(255, 0, 51));
        fechar.setText("Fechar (X)");
        fechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fecharMouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel31.setText("Política de privacidade");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 16)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("É política do agro2market respeitar a sua privacidade em relação a qualquer informação sua que \npossamos coletar no programa Projeto POO, e outros programas que possuímos e operamos. \nSolicitamos informações pessoais apenas quando realmente precisamos delas para lhe fornecer\num serviço. Fazemo-lo por meios justos e legais, com o seu conhecimento e consentimento.\nTambém informamos por que estamos coletando e como será usado. \n\nApenas retemos as informações coletadas pelo tempo necessário para fornecer o serviço solicitado.\nQuando armazenamos dados, protegemos dentro de meios comercialmente aceitáveis ​​para evitar\nperdas, roubos, bem como acesso, divulgação, cópia, uso ou modificação não autorizados. \n\nNão compartilhamos informações de identificação pessoal publicamente\" ou com terceiros, exceto \nquando exigido por lei. Você é livre para recusar a nossa solicitação de informações pessoais, \nentendendo que talvez não possamos fornecer alguns dos serviços desejados. O uso continuado\nde nosso programa será considerado como aceitação de nossas práticas em torno de privacidade\ne informações pessoais. Se você tiver alguma dúvida sobre como lidamos com dados do usuário \ne informações pessoais, entre em contato conosco. \n\nEsta política é efetiva a partir de Março/2023.");

        jLabel32.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        jLabel32.setText("Tarifas");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 17)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setText("Para abrir uma conta, anunciar e retirar dinheiro: Grátis\nTarifa da venda: 20%\n");

        javax.swing.GroupLayout pPoliticaLayout = new javax.swing.GroupLayout(pPolitica);
        pPolitica.setLayout(pPoliticaLayout);
        pPoliticaLayout.setHorizontalGroup(
            pPoliticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPoliticaLayout.createSequentialGroup()
                .addGroup(pPoliticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pPoliticaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fechar))
                    .addGroup(pPoliticaLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pPoliticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(0, 41, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pPoliticaLayout.setVerticalGroup(
            pPoliticaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPoliticaLayout.createSequentialGroup()
                .addComponent(fechar)
                .addGap(31, 31, 31)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pPolitica, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 780, 560));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Já possui uma conta?");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 170, -1, 20));

        tEntrar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tEntrar.setText("Entrar");
        tEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tEntrarMouseClicked(evt);
            }
        });
        getContentPane().add(tEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 190, -1, 20));

        bCadastrar.setText("Cadastrar");
        bCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCadastrarActionPerformed(evt);
            }
        });
        getContentPane().add(bCadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 620, -1, -1));

        tPolitica.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tPolitica.setText("e Política de Privacidade.");
        tPolitica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tPolitica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tPoliticaMouseClicked(evt);
            }
        });
        getContentPane().add(tPolitica, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 610, 180, 20));

        tTermos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tTermos.setText("Termos");
        tTermos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tTermos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tTermosMouseClicked(evt);
            }
        });
        getContentPane().add(tTermos, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 610, -1, 20));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Ao clicar em Cadastrar, você concorda com nossos");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 610, -1, 20));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Razão Social");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, -1, -1));

        tRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tRSActionPerformed(evt);
            }
        });
        getContentPane().add(tRS, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 452, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("CNPJ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Atividade");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, -1, -1));

        tNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNomeActionPerformed(evt);
            }
        });
        getContentPane().add(tNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 390, 452, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("E-mail");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 530, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Senha");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 560, -1, -1));

        tMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tMailActionPerformed(evt);
            }
        });
        getContentPane().add(tMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, 452, -1));

        tEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEndActionPerformed(evt);
            }
        });
        getContentPane().add(tEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, 452, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Endereço");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, -1, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Telefone");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, -1, -1));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Dados Cadastrais");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Dados de Acesso");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, -1, -1));

        tSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tSenhaKeyPressed(evt);
            }
        });
        getContentPane().add(tSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 560, 452, -1));

        titulocadastro1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        titulocadastro1.setForeground(new java.awt.Color(255, 255, 255));
        titulocadastro1.setText("Cadastro Agropecuário");
        getContentPane().add(titulocadastro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        tOcultar.setText("Ocultar");
        tOcultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tOcultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tOcultarMouseClicked(evt);
            }
        });
        getContentPane().add(tOcultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 560, -1, -1));

        tMostrar.setText("Mostrar");
        tMostrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tMostrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMostrarMouseClicked(evt);
            }
        });
        getContentPane().add(tMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 560, -1, -1));

        try {
            tCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(tCNPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 452, -1));

        try {
            tTel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(tTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, 452, 20));

        tAT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tAT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agricultura", "Pecuaria" }));
        getContentPane().add(tAT, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, 450, -1));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Nome Responsavel");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 390, -1, -1));

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Ex: João Santos");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 390, -1, -1));

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fundo3m.png"))); // NOI18N
        getContentPane().add(fundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCadastrarActionPerformed
        cadastrartAP();
    }//GEN-LAST:event_bCadastrarActionPerformed

    private void bCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelar1ActionPerformed
        cancelar();
    }//GEN-LAST:event_bCancelar1ActionPerformed

    private void tRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tRSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tRSActionPerformed

    private void tNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNomeActionPerformed

    private void tMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tMailActionPerformed

    private void tEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tEndActionPerformed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
     
    }//GEN-LAST:event_formMouseReleased

    private void tTermosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tTermosMouseClicked
        politica();
    }//GEN-LAST:event_tTermosMouseClicked

    private void tPoliticaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tPoliticaMouseClicked
        politica();
    }//GEN-LAST:event_tPoliticaMouseClicked

    private void tEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tEntrarMouseClicked
        new LoginAgro().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tEntrarMouseClicked

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

    private void fecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fecharMouseClicked
        pPolitica.setVisible(false);
        bCancelar1.setVisible(true);
        bCadastrar.setVisible(true);
        tEntrar.setVisible(true);
        jLabel12.setVisible(true);
    }//GEN-LAST:event_fecharMouseClicked

    private void tSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tSenhaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            
            try {
                cadastrartAP();
            } catch (Exception e) {
            }
        } 
    }//GEN-LAST:event_tSenhaKeyPressed

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
            java.util.logging.Logger.getLogger(CadastroAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroAgro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroAgro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCadastrar;
    private javax.swing.JButton bCancelar1;
    private javax.swing.JLabel fechar;
    private javax.swing.JLabel fundo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel pPolitica;
    private javax.swing.JComboBox<String> tAT;
    private javax.swing.JFormattedTextField tCNPJ;
    private javax.swing.JTextField tEnd;
    private javax.swing.JLabel tEntrar;
    private javax.swing.JTextField tMail;
    private javax.swing.JLabel tMostrar;
    private javax.swing.JTextField tNome;
    private javax.swing.JLabel tOcultar;
    private javax.swing.JLabel tPolitica;
    private javax.swing.JTextField tRS;
    private javax.swing.JPasswordField tSenha;
    private javax.swing.JFormattedTextField tTel;
    private javax.swing.JLabel tTermos;
    private javax.swing.JLabel titulocadastro1;
    // End of variables declaration//GEN-END:variables
}
