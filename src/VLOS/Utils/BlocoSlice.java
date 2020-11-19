package VLOS.Utils;

public class BlocoSlice {


    private long mInicio;
    private long mTamanho;

    public BlocoSlice(long eInicio, long eTamanho) {


        mInicio = eInicio;
        mTamanho = eTamanho;

    }

    public long getInicio() {
        return mInicio;
    }

    public long getTamanho() {
        return mTamanho;
    }

}
