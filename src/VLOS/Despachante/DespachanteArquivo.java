package VLOS.Despachante;

public class DespachanteArquivo {

    private String mNomeArquivo;
    private int mBlocoInicial;
    private int mBlocos;


    public DespachanteArquivo() {

    }


    public void setNomeArquivo(String eNomeArquivo) {
        mNomeArquivo = eNomeArquivo;
    }

    public void setBlocoInicial(int eBlocoInicial) {
        mBlocoInicial = eBlocoInicial;
    }

    public void setBlocos(int eBlocos) {
        mBlocos = eBlocos;
    }

    public int getBlocos() {
        return mBlocos;
    }

    public String getNomeArquivo() {
        return mNomeArquivo;
    }

    public int getBlocoInicial() {
        return mBlocoInicial;
    }
}
