package VLOS;

public class CPU {

    private boolean mExecutando;

    public CPU() {
        mExecutando = false;
    }

    public boolean estaOciosa() {
        return mExecutando == false;
    }

    public boolean estaExecutando() {
        return mExecutando == true;
    }


    public void setExecutando(boolean e) {
        mExecutando = e;
    }
}
