package Validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaNome {
    
    public static boolean isNome(String nome)
    {
        boolean isNomeValid = false;
        if (nome != null && nome.length() > 0) 
        {
            String expression = "^[A-Za-z]\\{5, 29}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nome);
            if (matcher.matches())
            {
                isNomeValid = true;
            }
        }
        return isNomeValid;
    }
}
