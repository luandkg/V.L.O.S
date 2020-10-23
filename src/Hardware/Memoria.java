package Hardware;

public class Memoria extends Dispositivo {

    private String mModelo;
    private long mTamanho;

    public Memoria(String eModelo, long eTamanho) {

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
        return "MEMORY";
    }
}
