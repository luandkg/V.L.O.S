package VLOS.Processo;

import VLOS.CPU;

public class ContextoDeHardware {

    private int mRegistrador_R1;
    private int mRegistrador_R2;
    private int mRegistrador_R3;

    public ContextoDeHardware() {

        mRegistrador_R1 = 0;
        mRegistrador_R2 = 0;
        mRegistrador_R3 = 0;

    }

    public void enviar(CPU mCPU) {

        mCPU.setRegistrador_R1(mRegistrador_R1);
        mCPU.setRegistrador_R2(mRegistrador_R2);
        mCPU.setRegistrador_R3(mRegistrador_R3);

    }

    public void receber(CPU mCPU) {

        mRegistrador_R1 = mCPU.getRegistrador_R1();
        mRegistrador_R2 = mCPU.getRegistrador_R2();
        mRegistrador_R3 = mCPU.getRegistrador_R3();

    }


    public int getRegistrador_R1() {
        return mRegistrador_R1;
    }

    public int getRegistrador_R2() {
        return mRegistrador_R2;
    }

    public int getRegistrador_R3() {
        return mRegistrador_R3;
    }


    public void setRegistrador_R1(int eRegistrador_R1) {
        mRegistrador_R1 = eRegistrador_R1;
    }

    public void setRegistrador_R2(int eRegistrador_R2) {
        mRegistrador_R2 = eRegistrador_R2;
    }

    public void setRegistrador_R3(int eRegistrador_R3) {
        mRegistrador_R3 = eRegistrador_R3;
    }


}
