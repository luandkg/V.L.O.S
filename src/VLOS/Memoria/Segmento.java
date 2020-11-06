package VLOS.Memoria;

import java.util.ArrayList;

public class Segmento {

    private ArrayList<Bloco> mBlocos;
    private long mTamanho;

    public Segmento(long eTamanho, ArrayList<Bloco> eBlocosAlocados) {

        mTamanho = eTamanho;
        mBlocos = eBlocosAlocados;

    }

    public long getTamanho() {
        return mTamanho;
    }

}
