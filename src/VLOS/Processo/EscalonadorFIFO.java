package VLOS.Processo;

import java.util.ArrayList;

public class EscalonadorFIFO {

    private boolean mTemProcesso;
    private Processo mProcesso;

    public EscalonadorFIFO() {

        mTemProcesso = false;
        mProcesso = null;

    }

    public boolean temProcesso() {
        return mTemProcesso;
    }

    public Processo getEscalonado() {
        return mProcesso;
    }


    public void escalone(ArrayList<Processo> mProcessos) {

        mTemProcesso = false;

        ArrayList<Processo> mProntos = new ArrayList<>();

        // COLOCANDO PROCESSOS PRONTOS NA FILA DE EXECUCAO
        for (Processo eProcesso : mProcessos) {
            if (eProcesso.getConcluido() == false) {
                mProntos.add(eProcesso);
            }
        }

        // OBTEM O PRIMEIRO PROCESSO PRONTO NA FILA
        if (mProntos.size() > 0) {
            for (Processo eProcesso : mProntos) {
                mTemProcesso = true;
                mProcesso = eProcesso;
            }

        }

    }


}
