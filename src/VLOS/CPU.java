package VLOS;

public class CPU {

    // CLASSE CPU : Representa a Unidade Central de Processamento

    private boolean mExecutando;

    private int mPID;

    private int mRegistrador_R1;
    private int mRegistrador_R2;
    private int mRegistrador_R3;


    public CPU() {

        mExecutando = false;


        mPID = -1;

        mRegistrador_R1=0;
        mRegistrador_R2=0;
        mRegistrador_R3=0;

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

    public int getPID(){return mPID;}
    public int getRegistrador_R1(){return mRegistrador_R1;}
    public int getRegistrador_R2(){return mRegistrador_R2;}
    public int getRegistrador_R3(){return mRegistrador_R3;}


    public void setPID(int ePID){ mPID = ePID;}
    public void setRegistrador_R1(int eRegistrador_R1){ mRegistrador_R1 = eRegistrador_R1;}
    public void setRegistrador_R2(int eRegistrador_R2){ mRegistrador_R2 = eRegistrador_R2;}
    public void setRegistrador_R3(int eRegistrador_R3){ mRegistrador_R3 = eRegistrador_R3;}

}
