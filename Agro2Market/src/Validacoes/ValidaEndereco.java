package Validacoes;

public class ValidaEndereco {
    
    public static boolean isEndereco(String Endereco)
    {
        int contEnd=0;
        int i;
        
        for(i=0; i<Endereco.length(); i++)
        {
            contEnd++;
        }
        
        if(contEnd < 16) //rua da gloria,96
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
