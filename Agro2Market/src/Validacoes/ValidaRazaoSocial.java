package Validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaRazaoSocial {
    
    public static boolean isRS(String rs)
    {
        boolean isRSValid = false;
        if (rs != null && rs.length() > 0) 
        {
            String expression = "^[A-Za-z]\\{5, 24}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(rs);
            if (matcher.matches())
            {
                isRSValid = true;
            }
        }
        return isRSValid;
    }
}
