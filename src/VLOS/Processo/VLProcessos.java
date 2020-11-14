package VLOS.Processo;

import VLOS.CPU;
import VLOS.Memoria.Segmento;

import java.util.ArrayList;

public class VLProcessos {

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

    public Processo criarProcessoKernel(Segmento eSegmento, int eTamanho) {

        Processo mProcesso = new Processo(mPID, ProcessoTipo.KERNEL, 0, eTamanho, eSegmento);
        mKernel_Processos.add(mProcesso);

        mPID += 1;

        return mProcesso;
    }

    public Processo criarProcessoUsuario(int ePrioridade, Segmento eSegmento, int eTamanho) {

        Processo mProcesso = new Processo(mPID, ProcessoTipo.USUARIO, ePrioridade, eTamanho, eSegmento);

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

        ArrayList<Processo> mProntos = new ArrayList<>();
        for (Processo mProcesso : mKernel_Processos) {
            if (mProcesso.getConcluido() == false) {
                mProntos.add(mProcesso);
            }
        }
        if (mProntos.size() > 0) {
            for (Processo mProcesso : mProntos) {
                mTemEscalonado = true;
                mEscalonado = mProcesso;
            }

        }

        return mEscalonado;
    }

    public Processo escalonarProcessoUsuario() {

        mTemEscalonado = false;

        ArrayList<Processo> mProntos = new ArrayList<>();


        for (Processo mProcesso : mUsuario_Processos_01) {
            if (mProcesso.getConcluido() == false) {
                mProntos.add(mProcesso);
            }
        }
        for (Processo mProcesso : mUsuario_Processos_02) {
            if (mProcesso.getConcluido() == false) {
                mProntos.add(mProcesso);
            }
        }
        for (Processo mProcesso : mUsuario_Processos_03) {
            if (mProcesso.getConcluido() == false) {
                mProntos.add(mProcesso);
            }
        }

        if (mProntos.size() > 0) {

            Processo mPrimeiro = null;
            int mContador = 0;
            int mPIDAnterior = -1;

            if (mEscalonado != null) {
                mPIDAnterior = mEscalonado.getPID();
                System.out.println("\t - Processo Anterior :: " + mPIDAnterior);
            }

            for (Processo mProcesso : mProntos) {

                if (mContador == 0) {
                    mPrimeiro = mProcesso;
                    if (mProcesso.getPID() > mPIDAnterior) {
                        mTemEscalonado = true;
                        mEscalonado = mProcesso;
                        break;
                    }
                } else {
                    if (mProcesso.getPID() > mPIDAnterior) {
                        mTemEscalonado = true;
                        mEscalonado = mProcesso;
                        break;
                    }
                }
                mContador += 1;
            }

            if (!mTemEscalonado) {
                mEscalonado = mPrimeiro;
                mTemEscalonado = true;
            } else {

                if (mEscalonado != null) {
                    if (mPIDAnterior == mEscalonado.getPID()) {
                        mEscalonado = mPrimeiro;
                        mTemEscalonado = true;
                    }
                }


            }

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
