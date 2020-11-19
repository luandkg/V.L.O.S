package VLOS.Despachante;

public class DespachanteOperacao {

    private int mPID;
    private int mCodigoOperacao;
    private String mNomeArquivo;
    private int mNumeroBlocos;


    private boolean misRealizado;

    public DespachanteOperacao() {
        misRealizado = false;
    }

    public void setPID(int ePID) {
        mPID = ePID;
    }

    public void setCodigoOperacao(int eCodigoOperacao) {
        mCodigoOperacao = eCodigoOperacao;
    }

    public void setNomeArquivo(String eNomeArquivo) {
        mNomeArquivo = eNomeArquivo;
    }

    public void setNumeroBlocos(int eNumeroBlocos) {
        mNumeroBlocos = eNumeroBlocos;
    }

    public int getPID() {
        return mPID;
    }

    public int getCodigoOperacao() {
        return mCodigoOperacao;
    }

    public String getNomeArquivo() {
        return mNomeArquivo;
    }

    public int getNumeroBlocos(){ return mNumeroBlocos;}

    public void realizar() {
        misRealizado = true;
    }

    public boolean isRealizado() {
        return misRealizado;
    }


}
