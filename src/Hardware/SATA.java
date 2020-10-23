package Hardware;

public class SATA extends Dispositivo  {

    private String mModelo;
    private long mTamanho;

    public SATA(String eModelo, long eTamanho){

        mModelo=eModelo;
        mTamanho=eTamanho;

    }

    public String getModelo(){return mModelo;}
    public long getTamanho(){return mTamanho;}

    @Override
    public String getTipo() {
        return "SATA";
    }

}
