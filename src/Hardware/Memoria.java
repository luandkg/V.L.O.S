package Hardware;

public class Memoria {

    private String mModelo;
    private long mTamanho;

    public Memoria(String eModelo,long eTamanho){

        mModelo=eModelo;
        mTamanho=eTamanho;

    }

    public String getModelo(){return mModelo;}
    public long getTamanho(){return mTamanho;}

}
