package VLOS.Despachante;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DespachadorDeOperacoes {

    private int mBlocos;
    private int mSegmentosOcupados;
    private ArrayList<DespachanteArquivo> mItens;
    private ArrayList<DespachanteOperacao> mOperacoes;

    public DespachadorDeOperacoes() {

        mBlocos = 0;
        mSegmentosOcupados = 0;
        mItens = new ArrayList<DespachanteArquivo>();
        mOperacoes = new ArrayList<DespachanteOperacao>();

    }

    public ArrayList<DespachanteArquivo> getItens() {
        return mItens;
    }

    public ArrayList<DespachanteOperacao> getOperacoes() {
        return mOperacoes;
    }

    public void setBlocos(int eBlocos) {
        mBlocos = eBlocos;
    }

    public int getBlocos() {
        return mBlocos;
    }

    public int getSegmentosOcupados() {
        return mSegmentosOcupados;
    }

    public void setSegmentosOcupados(int eSegmentosOcupados) {
        mSegmentosOcupados = eSegmentosOcupados;
    }

    public void adicionarOperacao(int ePID, int eCodigoOperacao, String eNomeArquivo, int eNumeroBlocos) {

        DespachanteOperacao eDespachanteOperacao = new DespachanteOperacao();
        eDespachanteOperacao.setPID(ePID);
        eDespachanteOperacao.setCodigoOperacao(eCodigoOperacao);
        eDespachanteOperacao.setNomeArquivo(eNomeArquivo);
        eDespachanteOperacao.setNumeroBlocos(eNumeroBlocos);


        mOperacoes.add(eDespachanteOperacao);

    }

    public void adicionarItemDisco(String eNome, int eBlocoInicial, int eBlocos) {

        DespachanteArquivo eDespachanteArquivo = new DespachanteArquivo();
        eDespachanteArquivo.setNomeArquivo(eNome);
        eDespachanteArquivo.setBlocoInicial(eBlocoInicial);
        eDespachanteArquivo.setBlocos(eBlocos);

        mItens.add(eDespachanteArquivo);

    }

    public static boolean isNumero(String v) {
        boolean ret = true;

        int i = 0;
        int o = v.length();

        while (i < o) {
            String c = String.valueOf(v.charAt(i));
            if (c.contentEquals("0")) {
            } else if (c.contentEquals("1")) {
            } else if (c.contentEquals("2")) {
            } else if (c.contentEquals("3")) {
            } else if (c.contentEquals("4")) {
            } else if (c.contentEquals("5")) {
            } else if (c.contentEquals("6")) {
            } else if (c.contentEquals("7")) {
            } else if (c.contentEquals("8")) {
            } else if (c.contentEquals("9")) {
            } else {
                ret = false;
                break;
            }
            i += 1;
        }


        return ret;
    }


    public static DespachadorDeOperacoes carregar(String eArquivo) {

        DespachadorDeOperacoes eDespachadorDeOperacoes = new DespachadorDeOperacoes();

        String eConteudo = Ler(eArquivo);

       // System.out.println("---------------------------------------------");
      //  System.out.println(eConteudo);
       // System.out.println("---------------------------------------------");

        int i = 0;
        int o = eConteudo.length();

        String mPegando = "";

        int eCampoID = 0;

        while (i < o) {
            String c = String.valueOf(eConteudo.charAt(i));
            if (c.contentEquals("\n")) {
                i += 1;
                break;
            } else {
                mPegando += c;
            }
            i += 1;
        }

        if (mPegando.length() > 0) {
            eDespachadorDeOperacoes.setBlocos(Integer.parseInt(mPegando));
        }

        mPegando = "";


        while (i < o) {
            String c = String.valueOf(eConteudo.charAt(i));
            if (c.contentEquals("\n")) {
                i += 1;
                break;
            } else {
                mPegando += c;
            }
            i += 1;
        }

        if (mPegando.length() > 0) {
            eDespachadorDeOperacoes.setSegmentosOcupados(Integer.parseInt(mPegando));
        }


     //   System.out.println("BLOCOS             :: " + eDespachadorDeOperacoes.getBlocos());
      ///  System.out.println("SEGMENTOS OCUPADOS :: " + eDespachadorDeOperacoes.getSegmentosOcupados());

        mPegando = "";

        DespachanteArquivo mNovo = new DespachanteArquivo();
        DespachanteOperacao mNovaOperacao = new DespachanteOperacao();

        int mTipo = 0;

        while (i < o) {
            String c = String.valueOf(eConteudo.charAt(i));


            if (c.contentEquals(" ")) {

            } else if (c.contentEquals(",")) {

                //System.out.println("Passando : " + mPegando);

                if (eCampoID == 0) {

                    if (isNumero(mPegando)) {
                        mTipo = 0;
                        mNovaOperacao.setPID(Integer.parseInt(mPegando));
                    } else {
                        mTipo = 1;
                        mNovo.setNomeArquivo(mPegando);
                    }

                } else if (eCampoID == 1) {
                    if (mTipo == 0) {
                        mNovaOperacao.setCodigoOperacao(Integer.parseInt(mPegando));
                    } else {
                        mNovo.setBlocoInicial(Integer.parseInt(mPegando));
                    }
                } else if (eCampoID == 2) {

                    if (mTipo == 0) {
                        mNovaOperacao.setNomeArquivo(mPegando);

                    } else {
                        mNovo.setBlocos(Integer.parseInt(mPegando));

                    }

                } else if (eCampoID == 3) {

                    mNovaOperacao.setNumeroBlocos(Integer.parseInt(mPegando));

                }

                eCampoID += 1;
                mPegando = "";
            } else if (c.contentEquals("\n")) {

                //   System.out.println("Passando : " +mPegando );

                if (eCampoID == 2) {

                    if (mTipo == 0) {
                        mNovaOperacao.setNomeArquivo(mPegando);

                    } else {
                        mNovo.setBlocos(Integer.parseInt(mPegando));

                    }
                } else if (eCampoID == 3) {

                    if (mTipo == 0) {
                        mNovaOperacao.setNumeroBlocos(Integer.parseInt(mPegando));
                    }

                }

                eCampoID = 0;
                mPegando = "";

                if (mTipo == 0) {
                    eDespachadorDeOperacoes.getOperacoes().add(mNovaOperacao);

                } else {
                    eDespachadorDeOperacoes.getItens().add(mNovo);

                }

                mNovo = new DespachanteArquivo();
                mNovaOperacao = new DespachanteOperacao();
                mTipo = 0;


            } else if (c.contentEquals("\t")) {
            } else {
                mPegando += c;
            }

            i += 1;
        }

        if (eCampoID > 0) {
            if (mTipo == 0) {
                eDespachadorDeOperacoes.getOperacoes().add(mNovaOperacao);

            } else {
                eDespachadorDeOperacoes.getItens().add(mNovo);

            }
        }




        return eDespachadorDeOperacoes;
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
