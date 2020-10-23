package Hardware;

public class Processador {

    private String mProcessador;
    private int mNucleos;

    public Processador(String eProcessador,int eNucleos) {

        mProcessador = eProcessador;
        mNucleos = eNucleos;

    }

    public String getProcessador() {
        return mProcessador;
    }
    public int getNucleos() {
        return mNucleos;
    }

}
