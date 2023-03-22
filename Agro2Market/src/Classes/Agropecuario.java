
package Classes;


public class Agropecuario {

    private int cod_AP;
    private String CNPJ;
    private String Atividade;
    private String Razao_Social; 
    private String NomeAP;
    private String Endereco;
    private String Telefone;
    private String Email;
    private String Senha;
    private double Saldo;
    private boolean ativo;
    
    public String Login(){
        return "Nome: "+NomeAP + "   Email: "+Email + "\n "+Razao_Social + "   Endereço: "+Endereco + " Saldo= "+Saldo;
    }

    public String Status() {
        return "Agropecuário{" + "CNPJ=" + CNPJ + ", Atividade=" + Atividade + ", Razão Social=" + Razao_Social + ", Nome=" + NomeAP + ", Endereço=" + Endereco + ", Telefone=" + Telefone + ", Email=" + Email + ", Senha=" + Senha + '}';
    }

    public int getCod_AP() {
        return cod_AP;
    }

    public void setCod_AP(int cod_AP) {
        this.cod_AP = cod_AP;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getAtividade() {
        return Atividade;
    }

    public void setAtividade(String Ativade) {
        this.Atividade = Ativade;
    }

    public String getRazao_Social() {
        return Razao_Social;
    }

    public void setRazao_Social(String Razao_Social) {
        this.Razao_Social = Razao_Social;
    }

    public String getNomeAP() {
        return NomeAP;
    }

    public void setNomeAP(String NomeAP) {
        this.NomeAP = NomeAP;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
}
