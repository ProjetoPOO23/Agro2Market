package Classes;

public class Comissao {
    
    private int cod_C;
    private int cod_P;
    private int cod_AP;
    private int cod_M;
    private double custoComissao;
    private int QuantidadePD;
    private String nomeAP;
    private String nomeM;
    private String nomePD;
    private double ComissaoADM;
    
    public String Historico_Merceeiro()
    {
        return "\nNome: "+nomePD + "  Custo: "+custoComissao + "\nQuantidade: "+QuantidadePD + "  Fornecedor: "+nomeAP;
    }

    public String Historico_Agropecuario()
    {
        return "\nNome: "+nomePD + "  Custo: "+custoComissao + "\nQuantidade: "+QuantidadePD + "  Merceeiro: "+nomeM;
    }

    public String Historico_Compras()
    {
        return "\nNome: "+nomePD + "  Custo: "+custoComissao + "\nQuantidade: "+QuantidadePD + "\nFornecedor: "+nomeAP + "  Mercceiro: "+nomeM;
    }
    
    public int getCod_AP() {
        return cod_AP;
    }

    public void setCod_AP(int cod_AP) {
        this.cod_AP = cod_AP;
    }

    public int getCod_M() {
        return cod_M;
    }

    public void setCod_M(int cod_M) {
        this.cod_M = cod_M;
    }

    public double getCustoComissao() {
        return custoComissao;
    }

    public void setCustoComissao(double custoComissao) {
        this.custoComissao = custoComissao;
    }

    public int getQuantidadePD() {
        return QuantidadePD;
    }

    public void setQuantidadePD(int QuantidadePD) {
        this.QuantidadePD = QuantidadePD;
    }
    
    public int getCod_P() {
        return cod_P;
    }

    public void setCod_P(int cod_P) {
        this.cod_P = cod_P;
    }
    
    public String getNomeAP() {
        return nomeAP;
    }

    public void setNomeAP(String nomeAP) {
        this.nomeAP = nomeAP;
    }

    public String getNomeM() {
        return nomeM;
    }

    public void setNomeM(String nomeM) {
        this.nomeM = nomeM;
    }

    public String getNomePD() {
        return nomePD;
    }

    public void setNomePD(String nomePD) {
        this.nomePD = nomePD;
    }

    public int getCod_C() {
        return cod_C;
    }

    public void setCod_C(int cod_C) {
        this.cod_C = cod_C;
    }

    public double getComissaoADM() {
        return ComissaoADM;
    }

    public void setComissaoADM(double ComissaoADM) {
        this.ComissaoADM = ComissaoADM;
    }

}
