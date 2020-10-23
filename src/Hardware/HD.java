package Hardware;

public class HD {

    private String mModelo;
    private long mTamanho;

    public HD(String eModelo,long eTamanho){

        mModelo=eModelo;
        mTamanho=eTamanho;

    }

    public String getModelo(){return mModelo;}
    public long getTamanho(){return mTamanho;}


}
