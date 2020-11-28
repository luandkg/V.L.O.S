package VLOS.Processo;

public class RecursoAlocado {

    private boolean mPrecisa;
    private boolean mConseguiu;
    private int mRID;

    public RecursoAlocado() {

        mRID = -1;
        mPrecisa = false;
        mConseguiu = false;

    }

    public void setPrecisa(boolean e) {
        mPrecisa = e;
    }

    public boolean getPrecisa() {
        return mPrecisa;
    }

    public void setConseguiu(boolean e) {
        mConseguiu = e;
    }

    public boolean getConseguiu() {
        return mConseguiu;
    }

    public void setRID(int e) {
        mRID = e;
    }

    public int getRID() {
        return mRID;
    }

}
