package dao;

import Classes.*;
import java.util.List;


public interface MarketDAO {
    
    public int cadastrarMerceeiro(Merceeiro merceeiro);
    public int editarMerceeiro(Merceeiro merceeiro);
    public int apagarMerceeiro(Merceeiro merceeiro);
    public List<Merceeiro> listarMerceeiro();
    public Merceeiro encontrarMerceeiro(String email);
    public Merceeiro enMC(String nomeM);
    
    public int cadastrarAgropecuario(Agropecuario agropecuario);
    public int editarAgropecuario(Agropecuario agropecuario);
    public int apagarAgropecuario(Agropecuario agropecuario);
    public List<Agropecuario> listarAgropecuario();
    public Agropecuario encontrarAgropecuario(String email);
    public Agropecuario encontrarAgropecuarioPeloProduto(int cod_AP);
    public Agropecuario enAgp(String nomeAP);
    
    public int cadastrarProduto(Produto produto);
    public int editarProduto(Produto produto);
    public int apagarProduto(Produto produto);
    public List<Produto> listarProduto();
    public List<Produto> encontrarProduto(String nomePD);
    public Produto encontrarProdutoCompra(int cod_PD);
    
    public int registrarComissao(Comissao comissao);
    public List<Comissao> listarComissaoMerceeiroAgropecuario(); 
    
}