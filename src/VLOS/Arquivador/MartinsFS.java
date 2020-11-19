package VLOS.Arquivador;


import VLOS.Despachante.DespachanteArquivo;

import java.util.ArrayList;

public class MartinsFS {

    private ArrayList<BlocoFS> mBlocos;
    private ArrayList<Arquivo> mArquivos;

    private int mNumeroBlocos;

    public MartinsFS(int eNumeroBlocos, ArrayList<DespachanteArquivo> eItens) {

        mBlocos = new ArrayList<BlocoFS>();
        mArquivos = new ArrayList<Arquivo>();

        mNumeroBlocos = eNumeroBlocos;

        for (int i = 0; i < eNumeroBlocos; i++) {

            BlocoFS eBloco = new BlocoFS(i);
            eBloco.setStatus(BlocoStatus.LIVRE);
            mBlocos.add(eBloco);

        }

        for (DespachanteArquivo eDespachanteArquivo : eItens) {

            //  exibirFS("ANTES");

            // System.out.println("Arquivo Existente : " + eDespachanteArquivo.getNomeArquivo());
            montarArquivoExistente(eDespachanteArquivo);

            // exibirFS("DEPOIS");

        }

    }

    public ArrayList<BlocoFS> getBlocos() {
        return mBlocos;
    }

    public void exibirFS(String eFrase) {

        System.out.println("\t - MAPA DE BLOCOS DO MARTINS_FS - " + eFrase);


        int e = 5;
        int a = 0;
        String eLinha = "";

        for (int i = 0; i < mNumeroBlocos; i++) {

            BlocoFS eBloco = mBlocos.get(i);

            if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                eLinha += "L ";
            } else {
                eLinha += "O ";
            }

            a += 1;
            if (a >= e) {
                System.out.println("\t\t - " + eLinha);
                eLinha = "";
                a = 0;
            }
        }

        if (a >= e) {
            System.out.println("\t\t - " + eLinha);
        }


    }

    private void montarArquivoExistente(DespachanteArquivo eDespachanteArquivo) {


        ArrayList<BlocoFS> eBlocosFS = new ArrayList<BlocoFS>();


        for (int i = eDespachanteArquivo.getBlocoInicial(); i < (eDespachanteArquivo.getBlocoInicial() + eDespachanteArquivo.getBlocos()); i++) {
            mBlocos.get(i).setStatus(BlocoStatus.OCUPADO);
        }

        for (int i = eDespachanteArquivo.getBlocoInicial() + 1; i < (eDespachanteArquivo.getBlocoInicial() + eDespachanteArquivo.getBlocos()); i++) {
            eBlocosFS.add(mBlocos.get(i));
        }

        Arquivo mArquivo = new Arquivo(eDespachanteArquivo.getNomeArquivo(), mBlocos.get(eDespachanteArquivo.getBlocoInicial()), eBlocosFS);
        mArquivos.add(mArquivo);


    }


    private ArrayList<BlocoFS> procurarBlocosSequenciais(int eQuantos) {

        ArrayList<BlocoFS> mAlocando = new ArrayList<BlocoFS>();

        int iContando = 0;


        for (int i = 0; i < mNumeroBlocos; i++) {

            BlocoFS eBlocoFS = mBlocos.get(i);

            if (eBlocoFS.getStatus() == BlocoStatus.LIVRE) {

                mAlocando.add(eBlocoFS);

                iContando += 1;
                if (iContando == eQuantos) {
                    break;
                }

            } else {
                iContando = 0;
                mAlocando.clear();
            }

        }


        return mAlocando;
    }


    public int getQuantidadeBlocos() {
        return mBlocos.size();
    }

    public static MartinsFS montar(int eNumeroBlocos, ArrayList<DespachanteArquivo> eItens) {

        MartinsFS eMartinsFS = new MartinsFS(eNumeroBlocos, eItens);


        return eMartinsFS;

    }

    public boolean existe(String eNome) {

        boolean ret = false;

        for (Arquivo eItem : mArquivos) {
            if (eItem.getNome().contentEquals(eNome)) {
                ret = true;
                break;
            }
        }

        return ret;

    }

    public int criarArquivo(String eNome, int ePrecisaDeQuantosBlocos) {

        exibirFS("ANTES");

        int mStatusOperacao = 0;

        int eQuantos = ePrecisaDeQuantosBlocos;

        ArrayList<BlocoFS> eBlocosFS = procurarBlocosSequenciais(eQuantos);

        if (eBlocosFS.size() == eQuantos) {

            BlocoFS eBlocoEstruturaArquivo = eBlocosFS.get(0);

            ArrayList<BlocoFS> eBlocoConteudoArquivo = new ArrayList<BlocoFS>();


            int i = 1;
            int o = eBlocosFS.size();
            while (i < o) {
                eBlocoConteudoArquivo.add(eBlocosFS.get(i));
                i += 1;
            }

            Arquivo mArquivo = new Arquivo(eNome, eBlocoEstruturaArquivo, eBlocoConteudoArquivo);
            mArquivos.add(mArquivo);

            for (BlocoFS eBloco : eBlocosFS) {
                eBloco.setStatus(BlocoStatus.OCUPADO);
            }

        } else {
            if (eQuantos == 1) {
                mStatusOperacao = -1;
            } else {
                mStatusOperacao = -2;
            }
        }

        exibirFS("DEPOIS");


        return mStatusOperacao;

    }


    public int removerArquivo(String eNome) {

        exibirFS("ANTES");

        int mStatusOperacao = 0;

        boolean enc = false;

        for (Arquivo eItem : mArquivos) {
            if (eItem.getNome().contentEquals(eNome)) {
                enc = true;

                eItem.remover();
                mArquivos.remove(eItem);
                mStatusOperacao = 0;

                break;
            }
        }

        if (!enc) {
            mStatusOperacao = -1;
        }

        exibirFS("DEPOIS");


        return mStatusOperacao;
    }

}
