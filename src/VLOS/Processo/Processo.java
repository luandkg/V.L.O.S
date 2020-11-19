package VLOS.Processo;

import VLOS.Arquivador.VLVFS;
import VLOS.Despachante.DespachanteOperacao;
import VLOS.Memoria.MemoriaAlocada;

import java.util.ArrayList;

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

    private ArrayList<DespachanteOperacao> mOperacoes;

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

        mOperacoes = new ArrayList<DespachanteOperacao>();

    }

    public void adicionarOperacao(DespachanteOperacao eDespachanteOperacao) {
        mOperacoes.add(eDespachanteOperacao);
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

    public void processar(VLVFS mVLVFS) {

        if (mProcessado < mTamanho) {
            mProcessado += 1;
        }

        for (DespachanteOperacao eOperacao : mOperacoes) {
            if (!eOperacao.isRealizado()) {
                if (eOperacao.getPID() == eOperacao.getPID()) {
                    eOperacao.realizar();


                    System.out.println("\t-------------------------------------------------");
                    System.out.println("\t - PID = " + this.getPID());
                    System.out.println();

                    if (eOperacao.getCodigoOperacao() == 0) {

                        System.out.println("\t - OPERACAO CRIAR ARQUIVO - " + eOperacao.getNomeArquivo() + " com " + eOperacao.getNumeroBlocos() + " blocos");

                        if (mVLVFS.existeArquivo(eOperacao.getNomeArquivo())) {
                            System.out.println("\t - NAO PODE SER REALIZADA : JA EXISTE UM ARQUIVO COM ESSE NOME !");
                        } else {
                            int mStatusOperacao = mVLVFS.criarArquivo(eOperacao.getNomeArquivo(), eOperacao.getNumeroBlocos());
                            if (mStatusOperacao == 0) {
                                System.out.println("\t - STATUS = ARQUIVO CRIADO :: " + eOperacao.getNomeArquivo());
                            } else {
                                System.out.println("\t - STATUS = ARQUIVO NAO CRIADO :: " + eOperacao.getNomeArquivo() + " -->> NAO EXISTEM BLOCOS DISPONIVEIS !");
                            }
                        }

                    } else if (eOperacao.getCodigoOperacao() == 1) {

                        System.out.println("\t - OPERACAO REMOVER ARQUIVO - " + eOperacao.getNomeArquivo());

                        if (mVLVFS.existeArquivo(eOperacao.getNomeArquivo())) {
                            System.out.println("\t - STATUS = NAO PODE SER REALIZADA : NAO EXISTE UM ARQUIVO COM ESSE NOME !");
                        } else {
                            int mStatusOperacao = mVLVFS.removerArquivo(eOperacao.getNomeArquivo());
                            if (mStatusOperacao == 0) {
                                System.out.println("\t - STATUS = ARQUIVO REMOVIDO :: " + eOperacao.getNomeArquivo());
                            } else {
                                System.out.println("\t - STATUS = ARQUIVO NAO ENCONTRADO :: " + eOperacao.getNomeArquivo());
                            }
                        }

                    } else {

                    }

                    System.out.println("\t-------------------------------------------------");


                    break;
                }
            }

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
