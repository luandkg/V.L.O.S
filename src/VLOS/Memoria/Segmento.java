package VLOS.Memoria;

import java.util.ArrayList;

public class Segmento {

    private ArrayList<Bloco> mBlocos;
    private long mTamanho;
    private long mOffset;

    public Segmento(long eTamanho,long eOffset, ArrayList<Bloco> eBlocosAlocados) {

        mTamanho = eTamanho;
        mBlocos = eBlocosAlocados;
        mOffset=eOffset;
    }

    public long getOffset() {
        return mOffset;
    }


    public long getTamanho() {
        return mTamanho;
    }

    public int getBlocos() {
        return mBlocos.size();
    }

}
