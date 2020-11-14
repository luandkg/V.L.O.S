package VLOS;

public class CPU {

    // CLASSE CPU : Representa a Unidade Central de Processamento
    
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
