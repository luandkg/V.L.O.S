package VLOS.Processo;

import VLOS.Memoria.Segmento;

import java.util.ArrayList;

public class VLProcessos {

    private int mPID;

    private ArrayList<Processo> mKernel_Processos;
    private ArrayList<Processo> mUsuario_Processos;

    public VLProcessos() {

        mKernel_Processos = new ArrayList<Processo>();
        mUsuario_Processos = new ArrayList<Processo>();

    }

    public Processo criarProcessoKernel(Segmento eSegmento) {

        Processo mProcesso = new Processo(mPID, 0,0,eSegmento);
        mKernel_Processos.add(mProcesso);

        mPID += 1;

        return mProcesso;
    }

    public Processo criarProcessoUsuario(int ePrioridade,Segmento eSegmento) {

        Processo mProcesso = new Processo(mPID,1, ePrioridade,eSegmento);
        mUsuario_Processos.add(mProcesso);

        mPID += 1;

        return mProcesso;
    }

    public Processo getProcesso(int ePID) {

        boolean enc = false;
        Processo mProcesso = null;


        for (Processo eProcesso : mKernel_Processos) {
            if (eProcesso.getPID() == ePID) {
                enc = true;
                mProcesso = eProcesso;
                break;
            }
        }

        if(!enc){

            for (Processo eProcesso : mUsuario_Processos) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }


        }

        return mProcesso;

    }

}
