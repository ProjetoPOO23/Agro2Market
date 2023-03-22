package Validacoes;

public class ValidaSenha {
    
    public static boolean iSenha(String senha)
    {
        int contSe=0;
        int i;
        
        for(i=0; i<senha.length(); i++)
        {
            contSe++;
        }
        
        if(contSe < 5) //
        {
            System.out.println("Senha muito curta");
            return false;
        }
        else
        {
            return true;
        }
    }
    
}
