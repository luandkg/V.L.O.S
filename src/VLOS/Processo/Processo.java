package VLOS.Processo;

import VLOS.Memoria.Segmento;

public class Processo {

    private int mPID;
    private int mTipo;
    private int mPrioridade;

    private Segmento mSegmento;


    public Processo(int ePID,  int eTipo, int ePrioridade,Segmento eSegmento) {

        mPID = ePID;
        mTipo = eTipo;

        mPrioridade = ePrioridade;
        mSegmento = eSegmento;
    }

    public int getPID() {
        return mPID;
    }

    public int getPrioridade() {
        return mPrioridade;
    }

    public void setPrioridade(int ePrioridade) {
        mPrioridade = ePrioridade;
    }

    public long getOffset() {
        return mSegmento.getOffset();
    }

    public int getBlocos() {
        return mSegmento.getBlocos();
    }

    public boolean isKernel() {
        return mTipo == 0;
    }

    public boolean isUsuario() {
        return mTipo == 1;
    }

    public String getTipoFormatado() {
        if (mTipo == 0) {
            return "Kernel";
        } else {
            return "Usuario";
        }
    }
}
