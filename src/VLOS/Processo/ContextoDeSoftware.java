package VLOS.Processo;

import VLOS.CPU;

public class ContextoDeSoftware {

    private int mPID;

    public ContextoDeSoftware(int ePID) {
        mPID = ePID;
    }

    public void enviar(CPU mCPU) {
        mCPU.setPID(mPID);
    }

    public void receber(CPU mCPU) {
        mPID = mCPU.getPID();
    }

    public int getPID() {
        return mPID;
    }

    public void setPID(int ePID) {
        mPID = ePID;
    }

}
