package VLOS.Despachante;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Despachante {


    public ArrayList<ItemDespachante> carregar(String eArquivo) {

        String eConteudo = Ler(eArquivo);

        // System.out.println("---------------------------------------------");
        // System.out.println(eConteudo);
        //  System.out.println("---------------------------------------------");

        ArrayList<ItemDespachante> mLista = new ArrayList<ItemDespachante>();

        int i = 0;
        int o = eConteudo.length();

        String mPegando = "";
        ItemDespachante mNovo = new ItemDespachante();

        int e = 0;
        boolean mInserido = false;

        while (i < o) {
            String c = String.valueOf(eConteudo.charAt(i));

            if (c.contentEquals(" ")) {

            } else if (c.contentEquals(",")) {

                if (e == 0) {
                    mNovo.setInicializacao(Integer.parseInt(mPegando));
                } else if (e == 1) {
                    mNovo.setPrioridade(Integer.parseInt(mPegando));
                } else if (e == 2) {
                    mNovo.setTempoProcessador(Integer.parseInt(mPegando));
                } else if (e == 3) {
                    mNovo.setBlocos(Integer.parseInt(mPegando));
                } else if (e == 4) {
                    mNovo.setCodigoImpressora(Integer.parseInt(mPegando));
                } else if (e == 5) {
                    mNovo.setCodigoScanner(Integer.parseInt(mPegando));
                } else if (e == 6) {
                    mNovo.setCodigoModem(Integer.parseInt(mPegando));
                } else if (e == 7) {
                    mNovo.setCodigoDisco(Integer.parseInt(mPegando));

                }

                e += 1;
                mPegando = "";
            } else if (c.contentEquals("\n")) {

                e = 0;
                mPegando = "";
                mLista.add(mNovo);
                mNovo = new ItemDespachante();

            } else if (c.contentEquals("\t")) {
            } else {
                mPegando += c;
            }

            i += 1;
        }

        if (e > 0) {
            mLista.add(mNovo);
        }


        return mLista;
    }

    private String Ler(String eArquivo) {

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
