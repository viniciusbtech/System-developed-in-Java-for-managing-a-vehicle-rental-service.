package viniciusNunes_locadora;

import java.sql.Date;
import java.util.Calendar;
import java.util.Calendar;


public class Uti {
	
	
    // Método para ajustar a data para o início do dia

	
    public static java.sql.Date ajustarParaInicioDoDia(java.util.Date inicio) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inicio);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTimeInMillis());
    }
    // Método para ajustar a data para o final do dia
    

    public static java.sql.Date ajustarParaFinalDoDia(java.util.Date fim) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fim);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return new java.sql.Date(cal.getTimeInMillis());
    }	
   
	}
