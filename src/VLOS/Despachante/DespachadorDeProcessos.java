package VLOS.Despachante;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DespachadorDeProcessos {

    private ArrayList<DespachanteProcesso> mProcessos;

    public DespachadorDeProcessos() {

        mProcessos = new ArrayList<DespachanteProcesso>();

    }

    public ArrayList<DespachanteProcesso> getProcessos() {
        return mProcessos;
    }

    public static DespachadorDeProcessos carregar(String eArquivo) {

        String eConteudo = Ler(eArquivo);

        // System.out.println("---------------------------------------------");
        // System.out.println(eConteudo);
        //  System.out.println("---------------------------------------------");

        DespachadorDeProcessos mDespachadorDeProcessos = new DespachadorDeProcessos();

        int i = 0;
        int o = eConteudo.length();

        String mPegando = "";
        DespachanteProcesso mNovo = new DespachanteProcesso();

        int eCampoID = 0;

        while (i < o) {
            String c = String.valueOf(eConteudo.charAt(i));

            if (c.contentEquals(" ")) {

            } else if (c.contentEquals(",")) {

                if (eCampoID == 0) {
                    mNovo.setInicializacao(Integer.parseInt(mPegando));
                } else if (eCampoID == 1) {
                    mNovo.setPrioridade(Integer.parseInt(mPegando));
                } else if (eCampoID == 2) {
                    mNovo.setTempoProcessador(Integer.parseInt(mPegando));
                } else if (eCampoID == 3) {
                    mNovo.setBlocos(Integer.parseInt(mPegando));
                } else if (eCampoID == 4) {
                    mNovo.setCodigoImpressora(Integer.parseInt(mPegando));
                } else if (eCampoID == 5) {
                    mNovo.setCodigoScanner(Integer.parseInt(mPegando));
                } else if (eCampoID == 6) {
                    mNovo.setCodigoModem(Integer.parseInt(mPegando));
                } else if (eCampoID == 7) {
                    mNovo.setCodigoDisco(Integer.parseInt(mPegando));

                }

                eCampoID += 1;
                mPegando = "";
            } else if (c.contentEquals("\n")) {

                eCampoID = 0;
                mPegando = "";
                mDespachadorDeProcessos.getProcessos().add(mNovo);
                mNovo = new DespachanteProcesso();

            } else if (c.contentEquals("\t")) {
            } else {
                mPegando += c;
            }

            i += 1;
        }

        if (eCampoID > 0) {
            mDespachadorDeProcessos.getProcessos().add(mNovo);
        }


        return mDespachadorDeProcessos;
    }

    private static String Ler(String eArquivo) {

        String ret = "";

        try {
            FileReader arq = new FileReader(eArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            ret += linha;

            while (linha != null) {

                linha = lerArq.readLine();
                if (linha != null) {
                    ret += "\n" + linha;
                }

            }

            arq.close();
        } catch (IOException e) {

        }

        return ret;
    }


}
