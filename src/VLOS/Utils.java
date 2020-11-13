package VLOS;

import java.text.DecimalFormat;

public class Utils {


    public String texto_nucleo(int e) {

        if (e == 0) {
            return "Nenhum Nucleo";
        } else if (e == 1) {
            return "1 Nucleo";
        } else {
            return e + " Nucleos";
        }

    }

    public String texto_tamanho(long eEspaco) {

        String ePrefixos = "KMGT";

        int i = 0;
        int o = ePrefixos.length();

        float deEspaco = (float) eEspaco;


        while ((deEspaco >= 1024) && (i < o)) {

            deEspaco = deEspaco / 1024;

            i += 1;
        }

        if (i == 0) {
            return deEspaco + " bytes";
        } else {

            DecimalFormat formatter = new DecimalFormat("#.00");
            String sTamanho = "";

            try {
                sTamanho = formatter.format(deEspaco);
            } catch (Exception ex) {
            }

            return (sTamanho + " " + ePrefixos.charAt(i - 1) + "b").replace(",", ".");
        }


    }


    public String getIntCasas(int e, int c) {

        String ret = String.valueOf(e);
        while (ret.length() < c) {
            ret = "0" + ret;
        }
        return ret;
    }

    public String getLongCasas(long e, int c) {

        String ret = String.valueOf(e);
        while (ret.length() < c) {
            ret = "0" + ret;
        }
        return ret;
    }

    public String getOrganizado(String e, int n) {
        String ret = e;

        while (ret.length() < n) {
            ret += " ";
        }
        return ret;

    }

}
