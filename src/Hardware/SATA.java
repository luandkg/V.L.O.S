package Hardware;

public class SATA implements Dispositivo {

    private String mModelo;
    private long mTamanho;

    // IMPLEMENTACAO DA SATA UTILIZANDO A CLASSE ABSTRATA DISPOSITIVO
    // TIPO = SATA

    public SATA(String eModelo, long eTamanho) {

        mModelo = eModelo;
        mTamanho = eTamanho;

    }

    public String getModelo() {
        return mModelo;
    }

    public long getTamanho() {
        return mTamanho;
    }

    @Override
    public String getTipo() {
        return "SATA";
    }

    @Override
    public boolean mesmoTipo(String eTipo) {
        return eTipo.contentEquals("SATA");
    }

}
