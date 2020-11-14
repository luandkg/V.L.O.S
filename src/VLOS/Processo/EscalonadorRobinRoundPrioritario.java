package VLOS.Processo;

import java.util.ArrayList;

public class EscalonadorRobinRoundPrioritario {

    // ESCALONADOR ROUND ROBIN COM PRIORIDADE
    //
    // 3 FILAS DE PRIORIDADES

    private boolean mTemProcesso;
    private Processo mProcesso;

    public EscalonadorRobinRoundPrioritario() {

        mTemProcesso = false;
        mProcesso = null;

    }

    public boolean temProcesso() {
        return mTemProcesso;
    }

    public Processo getEscalonado() {
        return mProcesso;
    }

    public void escalone(Processo mProcessoAnterior, ArrayList<Processo> mFila1, ArrayList<Processo> mFila2, ArrayList<Processo> mFila3) {

        ArrayList<Processo> mProntos = new ArrayList<>();


        // COLOCA PROCESSOS PRONTOS DA FILA 1 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        for (Processo eProcesso : mFila1) {
            if (eProcesso.getConcluido() == false) {
                mProntos.add(eProcesso);
            }
        }

        // COLOCA PROCESSOS PRONTOS DA FILA 2 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        for (Processo eProcesso : mFila2) {
            if (eProcesso.getConcluido() == false) {
                mProntos.add(eProcesso);
            }
        }

        // COLOCA PROCESSOS PRONTOS DA FILA 3 NA FILA GERAL DE USUARIO DE PROCESSOS DE PRONTO
        for (Processo eProcesso : mFila3) {
            if (eProcesso.getConcluido() == false) {
                mProntos.add(eProcesso);
            }
        }


        // ESCALONA PROCESSO DA FILA DE PRONTO GERAL COM ROUND ROBIN PRIORITARIO

        if (mProntos.size() > 0) {

            Processo mPrimeiro = null;
            int mContador = 0;
            int mPIDAnterior = -1;

            if (mProcessoAnterior != null) {
                mPIDAnterior = mProcessoAnterior.getPID();
                System.out.println("\t - Processo Anterior :: " + mPIDAnterior);
            }

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

}
