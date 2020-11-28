package VLOS.Processo;

public class Dependencia {


    private String mPrecisa;
    private boolean mConseguiu;
    private int mRID;

    public Dependencia(String ePrecisa) {

        mRID = -1;
        mPrecisa = ePrecisa;
        mConseguiu = false;

    }


    public String getPrecisa() {
        return mPrecisa;
    }

    public boolean getStatus() {
        return mConseguiu;
    }

    public void conseguir(int eRID) {
        mConseguiu = true;
        mRID = eRID;
    }

    public void liberar() {
        mConseguiu = false;
        mRID = -1;
    }


    public int getRID() {
        return mRID;
    }

}
