package Hardware;

public class Processador {

    private Arquitetura mArquitetura;
    private String mProcessador;
    private int mNucleos;

    public Processador(Arquitetura eArquitetura,String eProcessador,int eNucleos) {

        mArquitetura = eArquitetura;
        mProcessador = eProcessador;
        mNucleos = eNucleos;

    }

    public Arquitetura getArquitetura() {
        return mArquitetura;
    }
    public String getProcessador() {
        return mProcessador;
    }
    public int getNucleos() {
        return mNucleos;
    }

}
