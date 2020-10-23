package VLOS;

public class Utils {


    public String texto_nucleo(int e) {

        if (e == 0) {
            return "Nenhum Nucleo";
        } else if (e == 0) {
            return "1 Nucleo";
        } else {
            return e + " Nucleos";
        }

    }

    public String texto_tamanho(long eEspaco) {

        long PARCELA = 1024;

        String eUnidade = "";
        long eTamanho = 0;

        if (eEspaco == 0) {
            eUnidade = "byte";
        } else {
            eUnidade = "bytes";
        }

        if (eEspaco >= PARCELA) {

            eUnidade = "KB";
            eTamanho=0;

            while (eEspaco >= PARCELA) {
                eEspaco -= PARCELA;
                eTamanho += 1;
            }

            if (eTamanho >= PARCELA) {

                eUnidade = "MB";
                eEspaco=eTamanho;
                eTamanho=0;

                while (eEspaco >= PARCELA) {
                    eEspaco -= PARCELA;
                    eTamanho += 1;
                }
            }


            if (eTamanho >= PARCELA) {

                eUnidade = "GB";
                eEspaco=eTamanho;
                eTamanho=0;

                while (eEspaco >= PARCELA) {
                    eEspaco -= PARCELA;
                    eTamanho += 1;
                }
            }

        } else {
            eTamanho = eEspaco;
        }


        return eTamanho + " " + eUnidade;


    }

}
