package VLOS.Processo;

import VLOS.Memoria.MemoriaAlocada;

public class Processo {

    // PROCESSO : CLASSE RESPONSAVEL POR REPRESENTAR UM PROCESSO


    private int mPID;
    private ProcessoTipo mProcessoTipo;
    private int mPrioridade;

    private long mTempoCriacao;
    private long mTempoConclusao;
    private long mTempoExecucaoInicio;

    private MemoriaAlocada mMemoriaAlocada;

    private ProcessoStatus mProcessoStatus;

    private int mProcessado;
    private int mTamanho;

    public Processo(int ePID, ProcessoTipo eTipo, int ePrioridade, long eTempoCriacao, int eTamanho, MemoriaAlocada eMemoriaAlocada) {

        mPID = ePID;
        mProcessoTipo = eTipo;

        mPrioridade = ePrioridade;

        mTempoCriacao = eTempoCriacao;
        mTempoConclusao = 0;
        mTempoExecucaoInicio = 0;

        mMemoriaAlocada = eMemoriaAlocada;

        mProcessado = 0;
        mTamanho = eTamanho;
        mProcessoStatus = ProcessoStatus.PRONTO;

    }

    public ProcessoStatus getStatus() {
        return mProcessoStatus;
    }

    public boolean isConcluido() {
        return mProcessoStatus == ProcessoStatus.CONCLUIDO;
    }

    public boolean isPronto() {
        return mProcessoStatus == ProcessoStatus.PRONTO;
    }

    public boolean isEsperando() {
        return mProcessoStatus == ProcessoStatus.ESPERANDO;
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
            return "KERNEL";
        } else {
            return "USUARIO";
        }
    }

    public String getProcessadoStatus() {
        return mProcessado + " de " + mTamanho;
    }

    public int getProcessado() {
        return mProcessado;
    }

    public void processar() {

        if (mProcessado < mTamanho) {
            mProcessado += 1;
        }

        if (mProcessado >= mTamanho) {
            mProcessoStatus = ProcessoStatus.CONCLUIDO;
        }

    }

    public void verificar() {
        if (mProcessado >= mTamanho) {
            mProcessoStatus = ProcessoStatus.CONCLUIDO;
        }
    }


    public long getTempoCriacao() {
        return mTempoCriacao;
    }

    public long getTempoConclusao() {
        return mTempoConclusao;
    }

    public long getTempoExecucaoInicio() {
        return mTempoExecucaoInicio;
    }

    public void setTempoConclusao(long eTempoConclusao) {
        mTempoConclusao = eTempoConclusao;
    }

    public void setTempoExecucaoInicio(long eTempoExecucaoInicio) {
        mTempoExecucaoInicio = eTempoExecucaoInicio;
    }
}
