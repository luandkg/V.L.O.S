package VLOS.Escalonadores;

import VLOS.Processo.Processo;

import java.util.ArrayList;

public class EscalonadorRobinRoundPrioritario implements Escalonador {

    // ESCALONADOR ROUND ROBIN COM PRIORIDADE
    //
    // 3 FILAS DE PRIORIDADES

    private boolean mTemProcesso;
    private Processo mProcesso;
    private int mFila;

    public EscalonadorRobinRoundPrioritario() {

        mTemProcesso = false;
        mProcesso = null;
        mFila = -1;
    }

    public String getNome() {
        return "RoundRobinPrioritario";
    }

    public boolean temProcesso() {
        return mTemProcesso;
    }

    public Processo getEscalonado() {
        return mProcesso;
    }

    public int getFila() {
        return mFila;
    }


    public ArrayList<Processo> getProntosPrioritarios(ArrayList<Processo> mProcessos) {

        ArrayList<Processo> mProntos = new ArrayList<Processo>();

        // OBTER PROCESSOS PRONTOS DA FILA
        for (Processo eProcesso : mProcessos) {
            if (eProcesso.isPronto() ) {
                mProntos.add(eProcesso);
            }
        }


        // OBTER MAIOR PRIORIDADE DA FILA DE PRONTO
        int eExecutarFilaComPrioridade = getMaiorPrioridade(mProntos);

        ArrayList<Processo> mProntosPrioritarios = new ArrayList<Processo>();

        // FILTRAR PROCESSOS COM PRIOPRIDADE IGUAL A MAIOR PRIORIDADE
        for (Processo eProcesso : ordenar(mProntos)) {
            if (eProcesso.getPrioridade() == eExecutarFilaComPrioridade) {
                mProntosPrioritarios.add(eProcesso);
            }
        }


        return mProntosPrioritarios;
    }


    public int getMaiorPrioridade(ArrayList<Processo> mProcessos) {

        int ePrioridade = 0;

        if (mProcessos.size() > 0) {
            ePrioridade = mProcessos.get(0).getPrioridade();

            for (Processo eProcesso : mProcessos) {
                if (eProcesso.getPrioridade() < ePrioridade) {
                    ePrioridade = eProcesso.getPrioridade();
                }
            }

        }


        return ePrioridade;
    }

    public void escalone(int eFila, int mPIDAnterior, ArrayList<Processo> mFila1, ArrayList<Processo> mFila2, ArrayList<Processo> mFila3) {

        ArrayList<Processo> mProntos = new ArrayList<>();

        mFila = eFila;

        // COLOCA PROCESSOS PRONTOS DA FILA 1 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        for (Processo eProcesso : getProntosPrioritarios(mFila1)) {
            mProntos.add(eProcesso);
            if (mFila != 0) {
                mFila = 0;
                mPIDAnterior = -1;
            }
        }

        // SE NAO TIVER PROCESSOS PRONTOS NA FILA COLOCA PROCESSOS PRONTOS DA FILA 2 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        if (mProntos.size() == 0) {
            for (Processo eProcesso : getProntosPrioritarios(mFila2)) {
                mProntos.add(eProcesso);
                if (mFila != 1) {
                    mFila = 1;
                    mPIDAnterior = -1;
                }
            }
        }


        // SE NAO TIVER PROCESSOS PRONTOS NA FILA COLOCA PROCESSOS PRONTOS DA FILA 3 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        if (mProntos.size() == 0) {
            for (Processo eProcesso : getProntosPrioritarios(mFila3)) {
                //  System.out.println(" PROC ->> " + eProcesso.getPID() + " :: " + eProcesso.getPrioridade());
                mProntos.add(eProcesso);

                if (mFila != 2) {
                    mFila = 2;
                    mPIDAnterior = -1;

                }
            }
        }

        // ESCALONA PROCESSO DA FILA DE PRONTO GERAL COM ROUND ROBIN PRIORITARIO

        if (mProntos.size() > 0) {

            Processo mPrimeiro = null;
            int mContador = 0;


            for (Processo eProcesso : mProntos) {

                if (mContador == 0) {

                    // GUARDA O PRIMEIRO PROCESSO PRONTO

                    mPrimeiro = eProcesso;
                    if (eProcesso.getPID() > mPIDAnterior) {
                        mTemProcesso = true;
                        mProcesso = eProcesso;
                        break;
                    }


                } else {

                    // PROCURA O PROCESSO PRONTO COM PID MAIOR QUE O PID DO PROCESSO ANTERIOR
                    // SE NAO EXISTIR PROCESSO ANTERIOR O PIDANTERIOR = -1

                    if (eProcesso.getPID() > mPIDAnterior) {
                        mTemProcesso = true;
                        mProcesso = eProcesso;
                        break;
                    }


                }
                mContador += 1;
            }

            // SE NAO EXISTIR PROCESSO COM PID MAIOR QUE O ANTERIOR ENTAO O PROCESSO ESCALONADO SERA O PRIMEIRO

            if (!mTemProcesso) {
                mProcesso = mPrimeiro;
                mTemProcesso = true;
            } else {

                // SE O PROCESSO ESCALONADO TIVER PID == PIDANTERIOR ENTAO O PROCESSO ESCALONADO SERA O PRIMEIRO

                if (mProcesso != null) {
                    if (mPIDAnterior == mProcesso.getPID()) {
                        mProcesso = mPrimeiro;
                        mTemProcesso = true;
                    }
                }


            }

        }


    }


    public ArrayList<Processo> ordenar(ArrayList<Processo> mProcessos) {

        ArrayList<Processo> mCopia = new ArrayList<Processo>();

        for (Processo eProcesso : mProcessos) {
            mCopia.add(eProcesso);
        }

        // BUBBLE SORT PARA ORDENAR UMA LISTA DE INTEIROS
        int tam = mCopia.size();

        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam - 1; j++) {
                if (mCopia.get(j).getPrioridade() > mCopia.get(j + 1).getPrioridade()) {
                    Processo mTemp = mCopia.get(j);
                    mCopia.set(j, mCopia.get(j + 1));
                    mCopia.set(j + 1, mTemp);
                }
            }
        }

        return mCopia;
    }


}
