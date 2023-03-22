package dao;

import Classes.*;
import conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
//import static jdk.internal.org.jline.utils.AttributedStringBuilder.append;

// Ver depois como apagar o agropecuario com o produto vinculado

public class MarketDAOJDBC implements MarketDAO{
    
    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;

    @Override
    public int cadastrarMerceeiro(Merceeiro merceeiro) {
        
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO merceeiros(CNPJ, Razao_Social, NomeM, Endereco, Telefone, Email, Senha, Saldo, ativo) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(insert);
            sql.setString(1, merceeiro.getCNPJ());
            sql.setString(2, merceeiro.getRazao_Social());
            sql.setString(3, merceeiro.getNomeM());
            sql.setString(4, merceeiro.getEndereco());
            sql.setString(5, merceeiro.getTelefone());
            sql.setString(6, merceeiro.getEmail());
            sql.setString(7, merceeiro.getSenha());
            sql.setDouble(8, merceeiro.getSaldo());
            sql.setBoolean(9, merceeiro.isAtivo());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }
    
    @Override
    public int editarMerceeiro(Merceeiro merceeiro) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE merceeiros SET ")
                .append("Razao_Social = ?,")
                .append("NomeM = ?,")
                .append("Endereco = ?,")
                .append("Telefone = ?,")
                .append("Email = ?,")
                .append("Senha = ?,")
                .append("Saldo = ?")
                .append("WHERE cod_M = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setString(1, merceeiro.getRazao_Social());
            sql.setString(2, merceeiro.getNomeM());
            sql.setString(3, merceeiro.getEndereco());
            sql.setString(4, merceeiro.getTelefone());
            sql.setString(5, merceeiro.getEmail());
            sql.setString(6, merceeiro.getSenha());
            sql.setDouble(7, merceeiro.getSaldo());
            sql.setInt(8, merceeiro.getCod_M());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagarMerceeiro(Merceeiro merceeiro) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE merceeiros SET ")
                .append("ativo = ? ")
                .append("WHERE cod_M = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setBoolean(1, merceeiro.isAtivo());
            sql.setInt(2, merceeiro.getCod_M());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<Merceeiro> listarMerceeiro() {
        
        String select = "SELECT * FROM merceeiros";

        List<Merceeiro> merceeiros = new ArrayList<Merceeiro>();

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            rset = sql.executeQuery();

            while (rset.next()) {

                Merceeiro merceeiro = new Merceeiro();
                
                merceeiro.setCod_M(rset.getInt("cod_M"));
                merceeiro.setCNPJ(rset.getString("CNPJ"));
                merceeiro.setRazao_Social(rset.getString("Razao_Social"));
                merceeiro.setNomeM(rset.getString("NomeM"));
                merceeiro.setEndereco(rset.getString("Endereco"));
                merceeiro.setTelefone(rset.getString("Telefone"));
                merceeiro.setEmail(rset.getString("Email"));
                merceeiro.setSenha(rset.getString("Senha"));
                merceeiro.setSaldo(rset.getDouble("Saldo"));
                merceeiro.setAtivo(rset.getBoolean("ativo"));
                
                merceeiros.add(merceeiro);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return merceeiros;
    }
    
    @Override
    public Merceeiro encontrarMerceeiro(String email) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM merceeiros ")
                .append("WHERE email = ?");
        
        String select = sqlBuilder.toString();

        Merceeiro merceeiro = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setString(1, email);

            rset = sql.executeQuery();

            while (rset.next()) {

                merceeiro = new Merceeiro();

                merceeiro.setCod_M(rset.getInt("cod_M"));
                merceeiro.setCNPJ(rset.getString("CNPJ"));
                merceeiro.setRazao_Social(rset.getString("Razao_Social"));
                merceeiro.setNomeM(rset.getString("NomeM"));
                merceeiro.setEndereco(rset.getString("Endereco"));
                merceeiro.setTelefone(rset.getString("Telefone"));
                merceeiro.setEmail(rset.getString("Email"));
                merceeiro.setSenha(rset.getString("Senha"));
                merceeiro.setSaldo(rset.getDouble("Saldo"));
                merceeiro.setAtivo(rset.getBoolean("ativo"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return merceeiro;
    }    
    
    
    public Merceeiro enMC(String nomeM) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM merceeiros ")
                .append("WHERE nomeM = ? ");
        
        String select = sqlBuilder.toString();

        Merceeiro merceeiro = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setString(1, nomeM);

            rset = sql.executeQuery();

            while (rset.next()) {

                merceeiro = new Merceeiro();

                merceeiro.setCod_M(rset.getInt("cod_M"));
                merceeiro.setCNPJ(rset.getString("CNPJ"));
                merceeiro.setRazao_Social(rset.getString("Razao_Social"));
                merceeiro.setNomeM(rset.getString("NomeM"));
                merceeiro.setEndereco(rset.getString("Endereco"));
                merceeiro.setTelefone(rset.getString("Telefone"));
                merceeiro.setEmail(rset.getString("Email"));
                merceeiro.setSenha(rset.getString("Senha"));
                merceeiro.setSaldo(rset.getDouble("Saldo"));
                merceeiro.setAtivo(rset.getBoolean("ativo"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return merceeiro;
    }    
    
    
    @Override
    public int cadastrarAgropecuario(Agropecuario agropecuario) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO agropecuarios(CNPJ, Atividade, Razao_Social, NomeAP, Endereco, Telefone, Email, Senha, Saldo, ativo) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(insert);
            sql.setString(1, agropecuario.getCNPJ());
            sql.setString(2, agropecuario.getAtividade());
            sql.setString(3, agropecuario.getRazao_Social());
            sql.setString(4, agropecuario.getNomeAP());
            sql.setString(5, agropecuario.getEndereco());
            sql.setString(6, agropecuario.getTelefone());
            sql.setString(7, agropecuario.getEmail());
            sql.setString(8, agropecuario.getSenha());
            sql.setDouble(9, agropecuario.getSaldo());
            sql.setBoolean(10, agropecuario.isAtivo());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editarAgropecuario(Agropecuario agropecuario) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE agropecuarios SET ")
                .append("Atividade = ?,")
                .append("Razao_Social = ?,")
                .append("NomeAP = ?,")
                .append("Endereco = ?,")
                .append("Telefone = ?,")
                .append("Email = ?,")
                .append("Senha = ?,")
                .append("Saldo = ?")
                .append("WHERE cod_AP = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setString(1, agropecuario.getAtividade());
            sql.setString(2, agropecuario.getRazao_Social());
            sql.setString(3, agropecuario.getNomeAP());
            sql.setString(4, agropecuario.getEndereco());
            sql.setString(5, agropecuario.getTelefone());
            sql.setString(6, agropecuario.getEmail());
            sql.setString(7, agropecuario.getSenha());
            sql.setDouble(8, agropecuario.getSaldo());
            sql.setInt(9, agropecuario.getCod_AP());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagarAgropecuario(Agropecuario agropecuario) { // Ver depois como apagar agropecuario junto com o produto vinculado
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE agropecuarios SET ")
                .append("ativo = ? ")
                .append("WHERE cod_AP = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setBoolean(1, agropecuario.isAtivo());
            sql.setInt(2, agropecuario.getCod_AP());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<Agropecuario> listarAgropecuario() {
        String select = "SELECT * FROM agropecuarios";

        List<Agropecuario> agropecuarios = new ArrayList<Agropecuario>();

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            rset = sql.executeQuery();

            while (rset.next()) {

                Agropecuario agropecuario = new Agropecuario();
                
                agropecuario.setCod_AP(rset.getInt("cod_AP"));
                agropecuario.setCNPJ(rset.getString("CNPJ"));
                agropecuario.setAtividade(rset.getString("Atividade"));
                agropecuario.setRazao_Social(rset.getString("Razao_Social"));
                agropecuario.setNomeAP(rset.getString("NomeAP"));
                agropecuario.setEndereco(rset.getString("Endereco"));
                agropecuario.setTelefone(rset.getString("Telefone"));
                agropecuario.setEmail(rset.getString("Email"));
                agropecuario.setSenha(rset.getString("Senha"));
                agropecuario.setSaldo(rset.getDouble("Saldo"));
                agropecuario.setAtivo(rset.getBoolean("ativo"));
                
                agropecuarios.add(agropecuario);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return agropecuarios;
    }
    
    @Override
    public Agropecuario encontrarAgropecuario(String email) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM agropecuarios ")
                .append("WHERE email = ?");
        
        String select = sqlBuilder.toString();

        Agropecuario agropecuario = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setString(1, email);

            rset = sql.executeQuery();

            while (rset.next()) {

                agropecuario = new Agropecuario();

                agropecuario.setCod_AP(rset.getInt("cod_AP"));
                agropecuario.setCNPJ(rset.getString("CNPJ"));
                agropecuario.setAtividade(rset.getString("Atividade"));
                agropecuario.setRazao_Social(rset.getString("Razao_Social"));
                agropecuario.setNomeAP(rset.getString("NomeAP"));
                agropecuario.setEndereco(rset.getString("Endereco"));
                agropecuario.setTelefone(rset.getString("Telefone"));
                agropecuario.setEmail(rset.getString("Email"));
                agropecuario.setSenha(rset.getString("Senha"));
                agropecuario.setSaldo(rset.getDouble("Saldo"));
                agropecuario.setAtivo(rset.getBoolean("ativo"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return agropecuario;
    }
    
    @Override
    public Agropecuario encontrarAgropecuarioPeloProduto(int cod_AP) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM agropecuarios ")
                .append("WHERE cod_AP = ?");
        
        String select = sqlBuilder.toString();

        Agropecuario agropecuario = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setInt(1, cod_AP);

            rset = sql.executeQuery();

            while (rset.next()) {

                agropecuario = new Agropecuario();

                agropecuario.setCod_AP(rset.getInt("cod_AP"));
                agropecuario.setCNPJ(rset.getString("CNPJ"));
                agropecuario.setAtividade(rset.getString("Atividade"));
                agropecuario.setRazao_Social(rset.getString("Razao_Social"));
                agropecuario.setNomeAP(rset.getString("NomeAP"));
                agropecuario.setEndereco(rset.getString("Endereco"));
                agropecuario.setTelefone(rset.getString("Telefone"));
                agropecuario.setEmail(rset.getString("Email"));
                agropecuario.setSenha(rset.getString("Senha"));
                agropecuario.setSaldo(rset.getDouble("Saldo"));
                agropecuario.setAtivo(rset.getBoolean("ativo"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return agropecuario;
    }
    
    @Override
    public Agropecuario enAgp(String nomeAP) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM agropecuarios ")
                .append("WHERE nomeAP = ?");
        
        String select = sqlBuilder.toString();

        Agropecuario agropecuario = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setString(1, nomeAP);

            rset = sql.executeQuery();

            while (rset.next()) {

                agropecuario = new Agropecuario();

                agropecuario.setCod_AP(rset.getInt("cod_AP"));
                agropecuario.setCNPJ(rset.getString("CNPJ"));
                agropecuario.setAtividade(rset.getString("Atividade"));
                agropecuario.setRazao_Social(rset.getString("Razao_Social"));
                agropecuario.setNomeAP(rset.getString("NomeAP"));
                agropecuario.setEndereco(rset.getString("Endereco"));
                agropecuario.setTelefone(rset.getString("Telefone"));
                agropecuario.setEmail(rset.getString("Email"));
                agropecuario.setSenha(rset.getString("Senha"));
                agropecuario.setSaldo(rset.getDouble("Saldo"));
                agropecuario.setAtivo(rset.getBoolean("ativo"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return agropecuario;
    }
    
    @Override
    public int cadastrarProduto(Produto produto) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO produtos(cod_AP, Categoria, nomeAP, NomePD, Valor, Quantidade, ativo) ")
                .append("VALUES (?,?, ?, ?, ?, ?, ?)");
        
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(insert);
            sql.setInt(1, produto.getCod_AP());
            sql.setString(2, produto.getCategoria());
            sql.setString(3, produto.getNomeAP());
            sql.setString(4, produto.getNomePD());
            sql.setFloat(5, produto.getValor());
            sql.setInt(6, produto.getQuantidade());
            sql.setBoolean(7, produto.isAtivo());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editarProduto(Produto produto) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE produtos SET ")
                .append("Quantidade = ?,")
                .append("Valor = ?")
                .append("WHERE cod_PD = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setInt(1, produto.getQuantidade());
            sql.setFloat(2, produto.getValor());
            sql.setInt(3, produto.getCod_P());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagarProduto(Produto produto) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE produtos SET ")
                .append("ativo = ? ")
                .append("WHERE cod_PD = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(update);
            sql.setBoolean(1, produto.isAtivo());
            sql.setInt(2, produto.getCod_P());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<Produto> listarProduto() {
        String select = "SELECT * FROM produtos";

        List<Produto> produtos = new ArrayList<Produto>();

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            rset = sql.executeQuery();

            while (rset.next()) {

                Produto produto = new Produto();
                
                produto.setCod_P(rset.getInt("cod_PD"));
                produto.setCod_AP(rset.getInt("cod_AP"));
                produto.setCategoria(rset.getString("Categoria"));
                produto.setNomeAP(rset.getString("NomeAP"));
                produto.setNomePD(rset.getString("NomePD"));
                produto.setValor(rset.getFloat("Valor"));
                produto.setQuantidade(rset.getInt("Quantidade"));
                produto.setAtivo(rset.getBoolean("ativo"));
                
                produtos.add(produto);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return produtos;
    }

    @Override
    public List<Produto> encontrarProduto(String nomePD) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM produtos ")
                .append("WHERE nomePD LIKE ? ");
        
        List<Produto> produtos = new ArrayList<Produto>();
        
        String select = sqlBuilder.toString();

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setString(1, nomePD + "%");

            rset = sql.executeQuery();

            while (rset.next()) {

                Produto produto = new Produto();

                produto.setCod_P(rset.getInt("cod_PD"));
                produto.setCod_AP(rset.getInt("cod_AP"));
                produto.setNomePD(rset.getString("nomePD"));
                produto.setNomeAP(rset.getString("NomeAP"));
                produto.setCategoria(rset.getString("Categoria"));
                produto.setValor(rset.getFloat("Valor"));
                produto.setQuantidade(rset.getInt("Quantidade"));
                produto.setAtivo(rset.getBoolean("ativo"));

                produtos.add(produto);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return produtos;
    }
    
    @Override
    public Produto encontrarProdutoCompra(int cod_PD) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT * FROM produtos ")
                .append("WHERE cod_PD = ? ");
        
        String select = sqlBuilder.toString();

        Produto produto = null;

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            sql.setInt(1, cod_PD);

            rset = sql.executeQuery();

            while (rset.next()) {

                produto = new Produto();
                
                produto.setCod_P(rset.getInt("cod_P"));
                produto.setCod_AP(rset.getInt("cod_AP"));
                produto.setCategoria(rset.getString("Categoria"));
                produto.setNomeAP(rset.getString("nomeAP"));
                produto.setNomePD(rset.getString("nomePD"));
                produto.setValor(rset.getFloat("Valor"));
                produto.setQuantidade(rset.getInt("Quantidade"));
                produto.setAtivo(rset.getBoolean("ativo"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return produto;
    }
    
    
    @Override
    public int registrarComissao(Comissao comissao) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO comissoes(cod_P, cod_AP, cod_M, custo_comissao, quantPD, nomeAP, nomeM, nomePD, comissaoADM) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(insert);
            sql.setInt(1, comissao.getCod_P());
            sql.setInt(2, comissao.getCod_AP());
            sql.setInt(3, comissao.getCod_M());
            sql.setDouble(4, comissao.getCustoComissao());
            sql.setInt(5, comissao.getQuantidadePD());
            sql.setString(6, comissao.getNomeAP());
            sql.setString(7, comissao.getNomeM());
            sql.setString(8, comissao.getNomePD());
            sql.setDouble(9, comissao.getComissaoADM());
            
            linha = sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }
    
    @Override
    public List<Comissao> listarComissaoMerceeiroAgropecuario() {
        String select = "SELECT * FROM comissoes";

        List<Comissao> comissoes = new ArrayList<Comissao>();

        try {
            conexao = ConexaoMySQL.getConexao();

            sql = (PreparedStatement) conexao.prepareStatement(select);

            rset = sql.executeQuery();

            while (rset.next()) {

                Comissao comissao = new Comissao();
                
                
                comissao.setCod_C(rset.getInt("cod_C"));
                comissao.setCod_P(rset.getInt("cod_P"));
                comissao.setNomePD(rset.getString("nomePD"));
                comissao.setCustoComissao(rset.getDouble("custo_comissao"));
                comissao.setQuantidadePD(rset.getInt("QuantPD"));
                comissao.setNomeAP(rset.getString("nomeAP"));
                comissao.setNomeM(rset.getString("nomeM"));
                comissao.setNomePD(rset.getString("nomePD"));
                comissao.setComissaoADM(rset.getDouble("comissaoADM"));
                
                comissoes.add(comissao);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return comissoes;
    }
    
    private void fecharConexao() {
        try {
            if (rset != null) {
                rset.close();
            }
            if (sql != null) {
                sql.close();
            }

            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
