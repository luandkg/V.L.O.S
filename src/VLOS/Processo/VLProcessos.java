package VLOS.Processo;

import VLOS.CPU;
import VLOS.Memoria.MemoriaAlocada;

import java.util.ArrayList;

public class VLProcessos {

    // VLPROCESSOS : CLASSE RESPONSAVEL GERENCIAR OS PROCESSOS DO VLOS

    private int mPID;

    private ArrayList<Processo> mKernel_Processos;

    private ArrayList<Processo> mUsuario_Processos_01;
    private ArrayList<Processo> mUsuario_Processos_02;
    private ArrayList<Processo> mUsuario_Processos_03;

    private CPU mCPU;
    private int mPID_Executando;
    private Processo mEscalonado;
    private boolean mTemEscalonado;

    public VLProcessos(CPU eCPU) {

        mCPU = eCPU;

        mKernel_Processos = new ArrayList<Processo>();

        mUsuario_Processos_01 = new ArrayList<Processo>();
        mUsuario_Processos_02 = new ArrayList<Processo>();
        mUsuario_Processos_03 = new ArrayList<Processo>();

        mPID_Executando = -1;
        mEscalonado = null;
        mTemEscalonado = false;

    }

    public Processo criarProcessoKernel(MemoriaAlocada eMemoriaAlocada, int eTamanho) {

        Processo mProcesso = new Processo(mPID, ProcessoTipo.KERNEL, 0, eTamanho, eMemoriaAlocada);
        mKernel_Processos.add(mProcesso);

        mPID += 1;

        return mProcesso;
    }

    public Processo criarProcessoUsuario(int ePrioridade, MemoriaAlocada eMemoriaAlocada, int eTamanho) {

        Processo mProcesso = new Processo(mPID, ProcessoTipo.USUARIO, ePrioridade, eTamanho, eMemoriaAlocada);

        if (ePrioridade == 0) {
            mUsuario_Processos_01.add(mProcesso);
        } else if (ePrioridade == 1) {
            mUsuario_Processos_02.add(mProcesso);
        } else {
            mUsuario_Processos_03.add(mProcesso);
        }

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

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_01) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_02) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_03) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        return mProcesso;

    }

    public Processo envelhecer(int ePID) {

        boolean enc = false;
        Processo mProcesso = null;


        for (Processo eProcesso : mKernel_Processos) {
            if (eProcesso.getPID() == ePID) {
                enc = true;
                mProcesso = eProcesso;
                break;
            }
        }

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_01) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_02) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        if (!enc) {

            for (Processo eProcesso : mUsuario_Processos_03) {
                if (eProcesso.getPID() == ePID) {
                    enc = true;
                    mProcesso = eProcesso;
                    break;
                }
            }

        }

        mProcesso.setPrioridade(mProcesso.getPrioridade() + 1);

        return mProcesso;

    }


    public ArrayList<Processo> getProcessosKernel() {
        return mKernel_Processos;
    }

    public ArrayList<Processo> getProcessosUsuario() {

        ArrayList<Processo> mUsuarioTodos = new ArrayList<>();
        mUsuarioTodos.addAll(mUsuario_Processos_01);
        mUsuarioTodos.addAll(mUsuario_Processos_02);
        mUsuarioTodos.addAll(mUsuario_Processos_03);

        return mUsuarioTodos;
    }


    public ArrayList<Processo> getProcessosUsuario_Fila1() {
        return mUsuario_Processos_01;
    }

    public ArrayList<Processo> getProcessosUsuario_Fila2() {
        return mUsuario_Processos_02;
    }

    public ArrayList<Processo> getProcessosUsuario_Fila3() {
        return mUsuario_Processos_03;
    }


    public boolean temProcessoKernelEsperando() {

        boolean tem = false;

        for (Processo mProcesso : mKernel_Processos) {

            if (mProcesso.getConcluido() == false) {
                tem = true;
                break;
            }

        }

        return tem;

    }


    public boolean temProcessoUsuarioEsperando() {

        boolean tem = false;

        for (Processo mProcesso : mUsuario_Processos_01) {

            if (mProcesso.getConcluido() == false) {
                tem = true;
                break;
            }

        }

        if (!tem) {

            for (Processo mProcesso : mUsuario_Processos_02) {

                if (mProcesso.getConcluido() == false) {
                    tem = true;
                    break;
                }

            }

        }

        if (!tem) {

            for (Processo mProcesso : mUsuario_Processos_03) {

                if (mProcesso.getConcluido() == false) {
                    tem = true;
                    break;
                }

            }

        }

        return tem;

    }

    public boolean temEscalonado() {
        return mTemEscalonado;
    }

    public Processo escalonarProcessoKernel() {

        mTemEscalonado = false;

        EscalonadorFIFO mEscalonadorFIFO = new EscalonadorFIFO();
        mEscalonadorFIFO.escalone(mKernel_Processos);

        if (mEscalonadorFIFO.temProcesso()) {

            mTemEscalonado = true;
            mEscalonado = mEscalonadorFIFO.getEscalonado();

        }

        return mEscalonado;
    }

    public Processo escalonarProcessoUsuario() {

        mTemEscalonado = false;

        EscalonadorRobinRoundPrioritario mERRP = new EscalonadorRobinRoundPrioritario();
        mERRP.escalone(mEscalonado,mUsuario_Processos_01, mUsuario_Processos_02, mUsuario_Processos_03);

        if (mERRP.temProcesso()) {

            mTemEscalonado = true;
            mEscalonado = mERRP.getEscalonado();

        }


        if (mTemEscalonado) {
            mPID_Executando = mEscalonado.getPID();
        }

        return mEscalonado;
    }


    public Processo getEscalonado() {
        return mEscalonado;
    }

    public void retirarProcesso() {
        mTemEscalonado = false;
    }

}
