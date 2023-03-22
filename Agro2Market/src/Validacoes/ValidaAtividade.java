package Validacoes;

public class ValidaAtividade {
    
    public static boolean isAtividade(String Atividade)
    {
        if(Atividade.equals("Agricultura") || Atividade.equals("agricultura") ||
           Atividade.equals("AGRICULTURA") || Atividade.equals("Pecuaria") ||
           Atividade.equals("pecuaria") || Atividade.equals("PECUARIA"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
