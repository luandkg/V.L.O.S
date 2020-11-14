package VLOS.Processo;

import VLOS.Memoria.MemoriaAlocada;

public class Processo {

    // PROCESSO : CLASSE RESPONSAVEL POR REPRESENTAR UM PROCESSO


    private int mPID;
    private ProcessoTipo mProcessoTipo;
    private int mPrioridade;

    private MemoriaAlocada mMemoriaAlocada;

    private ProcessoStatus mProcessoStatus;

    private int mProcessado;
    private int mTamanho;

    public Processo(int ePID, ProcessoTipo eTipo, int ePrioridade, int eTamanho, MemoriaAlocada eMemoriaAlocada) {

        mPID = ePID;
        mProcessoTipo = eTipo;

        mPrioridade = ePrioridade;
        mMemoriaAlocada = eMemoriaAlocada;

        mProcessado = 0;
        mTamanho = eTamanho;
        mProcessoStatus = ProcessoStatus.PRONTO;

    }

    public ProcessoStatus getStatus() {
        return mProcessoStatus;
    }

    public boolean getConcluido() {
        return mProcessoStatus == ProcessoStatus.CONCLUIDO;
    }

    public void mudarStatus(ProcessoStatus e) {
        mProcessoStatus = e;
    }

    public void terminar() {
        mProcessoStatus = ProcessoStatus.CONCLUIDO;
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
        return mMemoriaAlocada.getOffset();
    }

    public int getBlocos() {
        return mMemoriaAlocada.getBlocos();
    }

    public boolean isKernel() {
        return mProcessoTipo == ProcessoTipo.KERNEL;
    }

    public boolean isUsuario() {
        return mProcessoTipo == ProcessoTipo.USUARIO;
    }

    public ProcessoTipo getTipo() {
        return mProcessoTipo;
    }

    public String getTipoFormatado() {
        if (mProcessoTipo == ProcessoTipo.KERNEL) {
            return "Kernel";
        } else {
            return "Usuario";
        }
    }

    public String getProcessadoStatus() {
        return mProcessado + " de " + mTamanho;
    }

    public int getProcessado() {
        return mProcessado;
    }

    public void mostrar() {


        System.out.println("\t---------------------------------------------");

        System.out.println("\t -->> PID : " + this.getPID());
        System.out.println("\t");
        System.out.println("\t\t - TIPO        : " + this.getTipoFormatado());
        System.out.println("\t\t - PRIORIDADE  : " + this.getPrioridade());
        System.out.println("\t\t - STATUS      : " + this.getStatus());
        System.out.println("\t\t - OFFSET      : " + this.getOffset());
        System.out.println("\t\t - BLOCOS      : " + this.getBlocos());
        System.out.println("\t\t - IMPRESSORA  : " + "");
        System.out.println("\t\t - SCANNER     : " + "");
        System.out.println("\t\t - DRIVER      : " + "");
        System.out.println("\t\t - PROCESSADO  : " + this.getProcessadoStatus());

        System.out.println("\t---------------------------------------------");


    }

    public void aumentarProcessado() {
        mProcessado += 1;
        if (mProcessado >= mTamanho) {
            mProcessoStatus = ProcessoStatus.CONCLUIDO;
        }
    }
}
