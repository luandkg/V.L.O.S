package VLOS;

import Hardware.*;
import Testes.Teste_Alpha;
import VLOS.Arquivador.VLVFS;
import VLOS.Despachante.*;
import VLOS.Memoria.MemoriaAlocada;
import VLOS.Memoria.VLMemoria;
import VLOS.Processo.Processo;
import VLOS.Processo.ProcessoStatus;
import VLOS.Processo.ProcessoTipo;
import VLOS.Processo.VLProcessos;
import VLOS.Recurso.VLRecursos;
import VLOS.Utils.BlocoSlice;
import VLOS.Utils.Dumper;
import VLOS.Utils.Utils;

import java.util.ArrayList;

public class VLOS {

    private Maquina mMaquina;

    private long mBloco;

    private VLMemoria mVLMemoria;
    private VLProcessos mVLProcessos;
    private VLRecursos mVLRecursos;
    private VLVFS mVLVFS;


    private boolean mTemMemoria;
    private boolean mTemHD;

    private CPU mCPU;
    private Memoria mMemoria;
    private ArrayList<Dispositivo> mRecursosDispositivos;
    private DespachadorDeProcessos mDespachadorDeProcessos;
    private DespachadorDeOperacoes mDespachadorDeOperacoes;

    private long mTempo;
    private int mCicloContagem;
    private int mCicloMaximo;

    private int mQuantum;
    private int mQuantizando;
    private boolean mLigado;
    private Etapa mEtapa;

    private Utils mUtils;
    private Dumper mDumper;
    private Teste_Alpha mTeste_Alpha;

    private boolean mDEBUG_PROCESSOS;
    private boolean mDEBUG_ESCALONADOR;
    private boolean mDEBUG_MEMORIA;
    private boolean mDEBUG_PROCESSO_CORRENTE;
    private boolean mDEBUG_DESPACHANTE;
    private boolean mDILATADOR_TEMPORAL;

    private int mDILACAO;

    public VLOS(Maquina eMaquina) {

        mMaquina = eMaquina;

        mUtils = new Utils();

        mTempo = 0;
        mCicloContagem = 0;
        mCicloMaximo = 0;
        mQuantum = 0;
        mQuantizando = 0;
        mBloco = 0;
        mEtapa = Etapa.DESLIGADO;

        mRecursosDispositivos = new ArrayList<Dispositivo>();
        mDespachadorDeProcessos = new DespachadorDeProcessos();

        mDumper = new Dumper();

        mTemMemoria = false;
        mTemHD = false;
        mLigado = false;

        mTeste_Alpha = new Teste_Alpha();

        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = true;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_DESPACHANTE = true;
        mDILATADOR_TEMPORAL = true;

        mDILACAO = 50;

        debugarTudo();

    }

    public void debugarTudo() {
        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = true;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_DESPACHANTE = true;
    }

    public void debugarEscalonador() {
        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = false;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_DESPACHANTE = false;
    }

    public Etapa getEtapa() {
        return mEtapa;
    }

    public void ligar() {

        mEtapa = Etapa.LIGANDO;

        mLigado = true;
        mTempo = 0;
        mQuantum = 0;
        mCicloContagem = 0;
        mCicloMaximo = 10;
        mQuantizando = 0;
        mBloco = 0;

        // BOOT HARDWARE DE SISTEMA
        detectarHardware();

        esperar();

        if (mTemMemoria && mTemHD) {

            // INICIAR VLOS
            iniciar();

            //  LOOP NUCLEO DO SISTEMA OPERACIONAL
            mEtapa = Etapa.EXECUTANDO;

            while (mLigado) {
                executarCiclo();
            }

            // O SISTEMA SERA DESLIGADO
            desligar();

        }

        mEtapa = Etapa.DESLIGADO;

    }

    public void desligar() {
        mEtapa = Etapa.DESLIGANDO;
        mLigado = false;
    }

    public void desligarTodo() {

        // O SISTEMA SERA DESLIGADO
        desligar();

        mEtapa = Etapa.DESLIGADO;

    }

    public void ligarApenas() {

        mEtapa = Etapa.LIGANDO;

        mLigado = true;
        mTempo = 0;
        mQuantum = 0;
        mCicloContagem = 0;
        mCicloMaximo = 10;
        mQuantizando = 0;
        mBloco = 0;

        // BOOT HARDWARE DE SISTEMA
        detectarHardware();

        esperar();

        if (mTemMemoria && mTemHD) {

            // INICIAR VLOS
            iniciar();

            //  LOOP NUCLEO DO SISTEMA OPERACIONAL
            mEtapa = Etapa.EXECUTANDO;
        }

    }


    public void executarCiclo() {


        //  A CADA CICLO DO LOOP DO SISTEMA :
        //
        //  - Contador de Ciclo +1
        //  - Se o contador de Ciclo == 10 entao Tempo +1 e Ciclo = 0
        //  - Se existir despachantes de tempo igual ao mTempo entao dispara
        //  - Se a CPU estiver OCIOSA entao escalona processos
        //  - Se a CPU estiver executando e o quantum tiver atingido entao INTERROMPRE processo e escalona
        //  - Se a CPU estiver executando processo de USUARIO e chegar um processo de KERNEL INTERROMPE O PROCESSO e escalona
        //  - Para efeito de visualizacao e DEBUG coloca-se um espacador de tempo entre cada ciclo de 300 millisegundos

        despachar();

        if (mCPU.estaOciosa()) {
            ociosa();
        } else {
            executar();
        }

        temporizar();
    }

    public void print_inicializacao() {
        System.out.println("");
        System.out.println("");
        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t--                                                 --");
        System.out.println("\t--                        VLOS                     --");
        System.out.println("\t--                                                 --");
        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t-----------------------------------------------------");
        System.out.println("");
    }


    public void detectarHardware() {

        mEtapa = Etapa.DETECTANDO_HARDWARE;

        System.out.println("\t-----------------------------------------------------");

        System.out.println("\t DETECCAO DE HARDWARE");
        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));

        mCPU = new CPU();

        mTemMemoria = false;
        mTemHD = false;


        for (Dispositivo mDispositivo : mMaquina.getDispositivos()) {

            if (mDispositivo instanceof Memoria) {

                Memoria eMemoria = (Memoria) mDispositivo;
                System.out.println("\t\t Memoria : " + eMemoria.getModelo() + " -> " + mUtils.texto_tamanho(eMemoria.getTamanho()));

                mTemMemoria = true;
                mMemoria = eMemoria;

            } else if (mDispositivo instanceof SATA) {

                SATA eSATA = (SATA) mDispositivo;
                System.out.println("\t\t HD : " + eSATA.getModelo() + " -> " + mUtils.texto_tamanho(eSATA.getTamanho()));

                mTemHD = true;
                mRecursosDispositivos.add(mDispositivo);

            } else if (mDispositivo instanceof Impressora) {

                Impressora mImpressora = (Impressora) mDispositivo;
                System.out.println("\t\t Impressora : " + mImpressora.getModelo());

                mRecursosDispositivos.add(mDispositivo);

            } else if (mDispositivo instanceof Scanner) {

                Scanner mScanner = (Scanner) mDispositivo;
                System.out.println("\t\t Scanner : " + mScanner.getModelo());

                mRecursosDispositivos.add(mDispositivo);

            } else if (mDispositivo instanceof Modem) {

                Modem mModem = (Modem) mDispositivo;
                System.out.println("\t\t Modem : " + mModem.getModelo());

                mRecursosDispositivos.add(mDispositivo);

            }
            esperar();
        }


        System.out.println("\t-----------------------------------------------------");

        System.out.println("");

        if (!mTemMemoria) {
            System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM MEMORIA !");
        }

        if (!mTemHD) {
            System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM HD !");
        }


    }

    public void despachar() {

        // INICIAR PROCESSOS DE USUARIO

        ArrayList<DespachanteProcesso> mAdicionar = new ArrayList<DespachanteProcesso>();

        // OBTEM DESPACHANTES DO TEMPO REQUERIDO
        for (DespachanteProcesso mItem : mDespachadorDeProcessos.getProcessos()) {
            if (!mItem.isDespachado()) {
                if (mItem.getInicializacao() == mTempo) {
                    mAdicionar.add(mItem);
                }
            }
        }

        // SE POSSUIR DESPACHANTES ADICIONA NA FILA DE ESCALONAMENTO DE USUARIO DE ACORDO A PRIORIDADE
        // EXISTEM 3 PRIORIDADES DE USUARIO

        // KERNEL - PRIORIDADE 0
        // FILA 1 - PRIORIDADE 1
        // FILA 2 - PRIORIDADE 2
        // FILA 3 - PRIORIDADE 3 OU SUPERIOR

        // QUANDO UM PROCESSO SOFRE ENVELHECIMENTO ELE AUMENTA O NUMERO DE PRIORIDADE DO PROCESSO QUE RESULTA NO EFEITO CONTRARIO
        // QUANTO MENOR O NUMERO DE PRIORIDADE MAIOR A PRIORIDADE

        if (mAdicionar.size() > 0) {


            if (mDEBUG_DESPACHANTE) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
                System.out.println("\t -->> VLOS ADICIONAR PROCESSO { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " }");
            }

            for (DespachanteProcesso mItem : mAdicionar) {

                // EXISTE PROCESSO PARA SER ADICIONADO A FILA DE USUARIO
                mItem.despachar();

                if (mDEBUG_DESPACHANTE) {
                    mDumper.dump_despachante(mItem);
                }

                // VERIFICA A PRIORIDADE DO PROCESSO E COLOCA NA FILA ADEQUADA
                // ALOCA RECURSOS PARA INICIAR O PROCESSO
                // ALOCA MEMORIA PARA O PROCESSO

                if (mItem.getPrioridade() == 0) {

                    MemoriaAlocada eMemoriaAlocada = mVLMemoria.alocarBlocosDeKernel(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());
                    Processo mProcessoCorrente = mVLProcessos.criarProcessoKernel(mTempo, eMemoriaAlocada, mItem.getTempoProcessador());


                    for (DespachanteOperacao eOperacao : mDespachadorDeOperacoes.getOperacoes()) {
                        if (mProcessoCorrente.getPID() == eOperacao.getPID()) {
                            mProcessoCorrente.adicionarOperacao(eOperacao);
                        }
                    }


                    if (mDEBUG_PROCESSO_CORRENTE) {
                        mDumper.dump_processoCompleto(mTempo, mProcessoCorrente);
                    }

                } else {
                    MemoriaAlocada eMemoriaAlocada = mVLMemoria.alocarBlocosDeUsuario(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());
                    Processo mProcessoCorrente = mVLProcessos.criarProcessoUsuario(mTempo, mItem.getPrioridade(), eMemoriaAlocada, mItem.getTempoProcessador());

                    for (DespachanteOperacao eOperacao : mDespachadorDeOperacoes.getOperacoes()) {
                        if (mProcessoCorrente.getPID() == eOperacao.getPID()) {
                            mProcessoCorrente.adicionarOperacao(eOperacao);
                        }
                    }


                    if (mDEBUG_PROCESSO_CORRENTE) {
                        mDumper.dump_processoCompleto(mTempo, mProcessoCorrente);
                    }


                }


            }

            if (mDEBUG_DESPACHANTE) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
            }

            if (mDEBUG_MEMORIA) {
                mDumper.dump_memoria(mVLMemoria);
            }

        }


    }

    public void iniciar() {

        mEtapa = Etapa.INICIANDO;

        print_inicializacao();

        mQuantum = 1 * 10; // 1 SEGUNDO = 10 CICLOS DE PROCESSAMENTO DA CPU
        mBloco = 1024 * 1024;

        mVLProcessos = new VLProcessos(mCPU);
        mVLMemoria = new VLMemoria(mMemoria, mBloco);
        mVLRecursos = new VLRecursos();
        mVLVFS = new VLVFS();


        // ORGANIZADOR DE PROCESSOS PARA DESPACHAR EM TEMPO ADEQUADO
        mDespachadorDeProcessos = DespachadorDeProcessos.carregar("res/proccesses.txt");
        mDespachadorDeOperacoes = DespachadorDeOperacoes.carregar("res/files.txt");

        //  mTeste_Alpha.testeProcessosSimultaneosMultiplasFilasPrioritarias(mDespachantes);
        //mTeste_Alpha.testeProcessosEmTempos(mDespachadorDeProcessos, mDespachadorDeOperacoes);
        mTeste_Alpha.testeProcessosSimultaneosMultiplasFilasPrioritarias(mDespachadorDeProcessos, mDespachadorDeOperacoes);

        System.out.println("");
        System.out.println("DESPACHAR PROCESSOS");
        for (DespachanteProcesso eItem : mDespachadorDeProcessos.getProcessos()) {
            System.out.println("\t - Tempo :: " + eItem.getTempoProcessador() + " Prioridade = " + eItem.getPrioridade());
        }


        System.out.println("");
        System.out.println("DESPACHAR ARQUIVOS");
        for (DespachanteArquivo eItem : mDespachadorDeOperacoes.getItens()) {
            System.out.println("\t - Arquivo :: " + eItem.getNomeArquivo() + " Inicio = " + eItem.getBlocoInicial() + " Blocos = " + eItem.getBlocos());
        }

        System.out.println("");
        System.out.println("DESPACHAR OPERACOES");
        for (DespachanteOperacao eItem : mDespachadorDeOperacoes.getOperacoes()) {
            if (eItem.getCodigoOperacao() == 0) {
                System.out.println("\t - Operacao :: PID = " + eItem.getPID() + "    Tipo = CRIAR     Nome = " + eItem.getNomeArquivo() + "    Tamanho = " + eItem.getNumeroBlocos());
            } else if (eItem.getCodigoOperacao() == 1) {
                System.out.println("\t - Operacao :: PID = " + eItem.getPID() + "    Tipo = REMOVER   Nome = " + eItem.getNomeArquivo() + " ");
            }
        }




        int mDiscos = 0;

        for (Dispositivo mDispositivoRecurso : mRecursosDispositivos) {
            if (mDispositivoRecurso.mesmoTipo("SATA")) {
                if (mDiscos == 0) {
                    mVLVFS.montarCom((SATA) mDispositivoRecurso, mDespachadorDeOperacoes.getBlocos(), mDespachadorDeOperacoes.getItens());
                } else {
                    mVLVFS.montar((SATA) mDispositivoRecurso);
                }
                mDiscos += 1;
            } else {
                mVLRecursos.adicionarRecurso(mDispositivoRecurso);
            }
        }


        if (mDEBUG_MEMORIA) {
            mDumper.dump_memoria(mVLMemoria);
        }


        System.out.println("\t -->> Reservando 64 Blocos para KERNEL");

        esperar();

        // O RESERVADOR DO KERNEL - RESERVA OS PRIMEIROS BLOCOS PARA KERNEL E O RESTO PARA USUARIO
        mVLMemoria.reservarKernel(64 * mBloco);

        if (mDEBUG_MEMORIA) {
            mDumper.dump_memoria(mVLMemoria);
        }


        esperar();

    }

    public void ociosa() {

        // REALIZA PROCEDIMENTOS DE ESCOLANAMENTO QUANDO A CPU ESTIVER OCIOSA

        System.out.println("\t -->> CPU OCIOSA { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " } ");

        if (mVLProcessos.temProcessoProntoKernel()) {

            if (mDEBUG_ESCALONADOR) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
                System.out.println("\t -->> ESCALONADOR : PROCESSAR PROCESSOS DO KERNEL");
                System.out.println("\t - Processos : " + mVLProcessos.getProcessosKernel().size());
                System.out.println("\t - Prontos   : " + mVLProcessos.getProcessosKernel_Prontos().size());
                System.out.println("\t - Concludos : " + mVLProcessos.getProcessosKernel_Conclucidos().size());
            }

            Processo mEscalonado = mVLProcessos.escalonarProcessoKernel();

            if (mEscalonado.getProcessado() == 0) {
                mEscalonado.setTempoExecucaoInicio(mTempo);
            }

            if (mDEBUG_ESCALONADOR) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
            }

            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processosCompleto(mTempo, mVLProcessos);
            }

            if (mDEBUG_ESCALONADOR) {
                System.out.println("\tEscalonando Processo de Kernel");
                System.out.println("\t\t PID         = " + mEscalonado.getPID());
                System.out.println("\t\t Prioridade  = " + mEscalonado.getPrioridade());
            }

            mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);

            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processoCompleto(mTempo, mEscalonado);
            }

            mCPU.setExecutando(true);

            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processosCompleto(mTempo, mVLProcessos);
            }

        } else {

            if (mVLProcessos.temProcessoProntoUsuario()) {

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\t ---------------------------------------------------------------------------------------------------");
                    System.out.println("\t -->> ESCALONADOR : PROCESSAR PROCESSOS DO USUARIO");
                    System.out.println("\t - Processos : " + mVLProcessos.getProcessosUsuario().size());
                    System.out.println("\t - Prontos   : " + mVLProcessos.getProcessosUsuario_Prontos().size());
                    System.out.println("\t - Concludos : " + mVLProcessos.getProcessosUsuario_Conclucidos().size());
                }

                Processo mEscalonado = mVLProcessos.escalonarProcessoUsuario();

                if (mEscalonado.getProcessado() == 0) {
                    mEscalonado.setTempoExecucaoInicio(mTempo);
                }

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\t ---------------------------------------------------------------------------------------------------");
                }

                if (mDEBUG_PROCESSOS) {
                    mDumper.dump_processosCompleto(mTempo, mVLProcessos);
                }

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\tEscalonando Processo de Usuario");
                    System.out.println("\t\t PID         = " + mEscalonado.getPID());
                    System.out.println("\t\t Prioridade  = " + mEscalonado.getPrioridade());

                    if (mEscalonado.getProcessado() == 0) {
                        System.out.println("\t\t Iniciar Processo");
                    } else {
                        System.out.println("\t\t Continuar Processo");
                    }
                }

                mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);

                if (mDEBUG_PROCESSO_CORRENTE) {
                    mDumper.dump_processoCompleto(mTempo, mEscalonado);
                }

                mCPU.setExecutando(true);


                if (mDEBUG_PROCESSOS) {
                    mDumper.dump_processosCompleto(mTempo, mVLProcessos);
                }

            }
        }

    }

    public void executar() {

        // EXECUTA PARTE DO PROCESSO QUE ESTA NA CPU

        System.out.println("\t -->> CPU EXECUTANDO { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " } -->> PID = " + mVLProcessos.getEscalonado().getPID());

        esperar();


        boolean mProcessoConcluiu = false;

        mQuantizando += 1;
        if (mQuantizando >= mQuantum) {


            if (mVLProcessos.temEscalonado()) {

                if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.KERNEL) {

                    mVLProcessos.getEscalonado().processar(mCPU,mVLVFS);
                    mVLProcessos.getEscalonado().verificar();

                    if (mVLProcessos.getEscalonado().isConcluido()) {

                        mVLProcessos.getEscalonado().setTempoConclusao(mTempo);

                        System.out.println("\t -->> PROCESSO DE KERNEL CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());

                        mCPU.setExecutando(false);
                        mVLProcessos.retirarProcesso();
                        mProcessoConcluiu = true;
                    }

                } else if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.USUARIO) {

                    mVLProcessos.getEscalonado().processar(mCPU,mVLVFS);
                    mVLProcessos.getEscalonado().mudarStatus(ProcessoStatus.PRONTO);
                    mVLProcessos.getEscalonado().verificar();

                    if (mVLProcessos.getEscalonado().isConcluido()) {

                        mVLProcessos.getEscalonado().setTempoConclusao(mTempo);

                        System.out.println("\t -->> PROCESSO DE USUARIO CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());
                        mProcessoConcluiu = true;

                    }


                    mCPU.setExecutando(false);
                    mVLProcessos.retirarProcesso();

                }

            }

            mQuantizando = 0;

        } else {

            if (mVLProcessos.temEscalonado()) {
                if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.USUARIO) {
                    if (mVLProcessos.temProcessoProntoKernel()) {

                        // INTERROMPE O PROCESSO DE USUARIO PORQUE CHEGOU PROCESSO DE KERNEL

                        System.out.println("\t -->> PROCESSO DE USUARIO INTERROMPIDO : PID = " + mVLProcessos.getEscalonado().getPID());

                        mVLProcessos.getEscalonado().mudarStatus(ProcessoStatus.PRONTO);
                        mCPU.setExecutando(false);
                        mVLProcessos.retirarProcesso();


                    }
                }
            }

        }

        if (mProcessoConcluiu) {
            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processosCompleto(mTempo, mVLProcessos);
            }
        }

    }

    public boolean temEscalonado() {
        return mVLProcessos.temEscalonado();
    }

    public Processo getEscalonado() {
        return mVLProcessos.getEscalonado();
    }


    public void esperar() {

        if (mDILATADOR_TEMPORAL) {
            try {
                Thread.sleep(mDILACAO);
            } catch (
                    InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void temporizar() {

        esperar();

        mCicloContagem += 1;

        if (mCicloContagem >= mCicloMaximo) {
            mTempo += 1;
            mCicloContagem = 0;
        }
    }

    public ArrayList<BlocoSlice> getAreas() {

        ArrayList<BlocoSlice> mLista = new ArrayList<BlocoSlice>();

        mLista.add(new BlocoSlice(mVLMemoria.getOffsetKernel(), mVLMemoria.getTamanhoKernel()));
        mLista.add(new BlocoSlice(mVLMemoria.getOffsetUsuario(), mVLMemoria.getTamanhoUsuario()));


        return mLista;
    }

    public ArrayList<BlocoSlice> getOcupados() {
        return mVLMemoria.getOcupadosSlices();
    }

    public long getBlocos() {
        return mVLMemoria.getBlocos();
    }

    public long getKernelBlocos() {
        return mVLMemoria.getBlocos_KernelReservados();
    }

    public long getUsuarioBlocos() {
        return mVLMemoria.getBlocos_Usuarios();
    }

    public long getUsuarioOffste() {
        return mVLMemoria.getOffsetUsuario();
    }

    public boolean estaLigado() {
        return mLigado;
    }

    public ArrayList<Processo> getProcessosKernel() {
        return mVLProcessos.getProcessosKernel();
    }

    public ArrayList<Processo> getProcessosUsuario_Fila1() {
        return mVLProcessos.getProcessosUsuario_Fila1();
    }

    public ArrayList<Processo> getProcessosUsuario_Fila2() {
        return mVLProcessos.getProcessosUsuario_Fila2();
    }

    public ArrayList<Processo> getProcessosUsuario_Fila3() {
        return mVLProcessos.getProcessosUsuario_Fila3();
    }

    public long getTempo() {
        return mTempo;
    }

    public CPU getCPU() {
        return mCPU;
    }

    public VLVFS getVLVFS() {
        return mVLVFS;
    }
}
