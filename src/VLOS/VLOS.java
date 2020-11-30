package VLOS;

import VLOS.UI.CenaVLOS;
import Hardware.*;
import Testes.Testes;
import UI.Windows;
import VLOS.Arquivador.VLVFS;
import VLOS.Despachante.*;
import VLOS.Memoria.MemoriaAlocada;
import VLOS.Memoria.VLMemoria;
import VLOS.Processo.*;
import VLOS.Recurso.VLRecursos;
import VLOS.Utils.BlocoSlice;
import VLOS.Utils.Dumper;
import VLOS.Utils.Utils;

import java.util.ArrayList;

public class VLOS {

    private Maquina mMaquina;


    private VLMemoria mVLMemoria;
    private VLProcessos mVLProcessos;
    private VLRecursos mVLRecursos;
    private VLVFS mVLVFS;


    private boolean mTemMemoria;
    private boolean mTemHD;
    private boolean mTemProcessador;
    private boolean mTudoOK;

    private CPU mCPU;
    private Memoria mMemoria;
    private ArrayList<Dispositivo> mRecursosDispositivos;
    private DespachadorDeProcessos mDespachadorDeProcessos;
    private DespachadorDeOperacoes mDespachadorDeOperacoes;

    private long mTempo;
    private int mCicloContagem;
    private int mCicloMaximo;
    private long mContextos;

    private int mQuantum;
    private int mQuantizando;
    private boolean mLigado;
    private Etapa mEtapa;

    private Utils mUtils;
    private Dumper mDumper;
    private Testes mTestes;

    private boolean mDEBUG_PROCESSOS;
    private boolean mDEBUG_ESCALONADOR;
    private boolean mDEBUG_MEMORIA;

    private boolean mDEBUG_PROCESSO_CORRENTE;
    private boolean mDEBUG_PROCESSO_CONCLUSAO;

    private boolean mDEBUG_DESPACHANTE_PROCESSO;
    private boolean mDEBUG_DESPACHANTE_OPERACOES;
    private boolean mDEBUG_DESPACHANTE_ARQUIVOS;
    private boolean mDEBUG_ESPERANDO;
    private boolean mDEBUG_RECURSOS;
    private boolean mDEBUG_CPU_OCIOSA;
    private boolean mDEBUG_CPU_EXECUTANDO;
    private boolean mDEBUG_TROCA_DE_CONTEXTOS;

    private boolean mDILATADOR_TEMPORAL;

    private int mDILACAO;

    public VLOS(Maquina eMaquina) {

        mMaquina = eMaquina;

        mUtils = new Utils();

        mTempo = 0;
        mContextos = 0;

        mCicloContagem = 0;
        mCicloMaximo = 0;
        mQuantum = 0;
        mQuantizando = 0;
        mEtapa = Etapa.DESLIGADO;

        mRecursosDispositivos = new ArrayList<Dispositivo>();
        mDespachadorDeProcessos = new DespachadorDeProcessos();

        mDumper = new Dumper();

        mTemMemoria = false;
        mTemHD = false;
        mTemProcessador = false;
        mTudoOK = false;

        mLigado = false;

        mTestes = new Testes();

        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = true;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_PROCESSO_CONCLUSAO = true;

        mDEBUG_DESPACHANTE_PROCESSO = true;
        mDEBUG_DESPACHANTE_OPERACOES = true;
        mDEBUG_DESPACHANTE_ARQUIVOS = true;
        mDEBUG_RECURSOS = true;
        mDEBUG_ESPERANDO = true;

        mDEBUG_CPU_OCIOSA = true;
        mDEBUG_CPU_EXECUTANDO = true;
        mDEBUG_TROCA_DE_CONTEXTOS = true;

        mDILATADOR_TEMPORAL = true;

        mDILACAO = 20;

        //debugarTudo();

    }

    public void debugarTudo() {
        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = true;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_DESPACHANTE_PROCESSO = true;
    }

    public void debugarEscalonador() {
        mDEBUG_PROCESSOS = true;
        mDEBUG_ESCALONADOR = true;
        mDEBUG_MEMORIA = false;
        mDEBUG_PROCESSO_CORRENTE = true;
        mDEBUG_DESPACHANTE_PROCESSO = false;
    }

    public Etapa getEtapa() {
        return mEtapa;
    }

    private void carregar_Despachadores() {

        // ORGANIZADOR DE PROCESSOS PARA DESPACHAR EM TEMPO ADEQUADO
        mDespachadorDeProcessos = DespachadorDeProcessos.carregar("res/processes.txt");
        mDespachadorDeOperacoes = DespachadorDeOperacoes.carregar("res/files.txt");

        // mTeste_Alpha.testeProcessosSimultaneosMultiplasFilasPrioritarias(mDespachantes);
        //mTeste_Alpha.testeProcessosEmTempos(mDespachadorDeProcessos, mDespachadorDeOperacoes);
        //mTestes.testeProcessosSimultaneosMultiplasFilasPrioritarias(mDespachadorDeProcessos, mDespachadorDeOperacoes);


    }

    public void executarInterface() {

        Windows mWindows = new Windows(new CenaVLOS(this, "VLOS", 820, 1000));
        Thread mThread = new Thread(mWindows);
        mThread.start();

    }

    public void executarTerminal() {


        // LIGAR MAQUINA e INSTALAR VLOS
        ligarApenas();

        if (mTudoOK) {

            //  LOOP NUCLEO DO SISTEMA OPERACIONAL
            mEtapa = Etapa.EXECUTANDO;

            while (mLigado) {
                executarCiclo();
            }

        }

        // O SISTEMA SERA DESLIGADO
        desligarTodo();


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
        mContextos = 0;

        mQuantum = 0;
        mCicloContagem = 0;
        mCicloMaximo = 10;
        mQuantizando = 0;

        // BOOT HARDWARE DE SISTEMA
        detectarHardware();

        esperar();

        if (mTudoOK) {

            // INICIAR VLOS
            iniciar();

            //  INICIAR LOOP NUCLEO DO SISTEMA OPERACIONAL
            mEtapa = Etapa.EXECUTANDO;
        } else {
            desligar();
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

        mTemMemoria = false;
        mTemHD = false;
        mTemProcessador = false;
        mTudoOK = false;

        boolean temIncompativel = false;

        System.out.println("\t-----------------------------------------------------");

        System.out.println("\t DETECCAO DE HARDWARE");
        System.out.println("\t\t Arquitetura : " + mMaquina.getArquitetura().toString());
        System.out.println("\t\t Tipo de Processamento : " + mMaquina.getTipoDeProcessador().toString());

        for (Processador eProcessador : mMaquina.getProcessadores()) {

            if (eProcessador.getArquitetura() == mMaquina.getArquitetura()) {
                System.out.println("\t\t Processador : " + eProcessador.getProcessador() + " -- " + eProcessador.getArquitetura() + " -> " + mUtils.texto_nucleo(eProcessador.getNucleos()) + " -->> OK ");
            } else {
                System.out.println("\t\t Processador : " + eProcessador.getProcessador() + " -- " + eProcessador.getArquitetura() + " -> " + mUtils.texto_nucleo(eProcessador.getNucleos()) + " -->> PROCESSADOR INCOMPATIVEL ");
                temIncompativel = true;
            }

        }

        if (mMaquina.getTipoDeProcessador() == TipoDeProcessador.MONOPROCESSADOR) {
            mTemProcessador = true;
        } else if (mMaquina.getTipoDeProcessador() == TipoDeProcessador.MULTIPROCESSADOR) {
            mTemProcessador = true;
        }


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

        boolean processadorOk = false;

        if (!mTemProcessador) {
            System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM PROCESSADOR !");
        } else {

            mCPU = new CPU(mMaquina.getProcessadores().get(0));

            if (mMaquina.getProcessadores().size() == 1) {
                processadorOk = true;
            } else if (mMaquina.getProcessadores().size() > 1) {
                processadorOk = true;
                System.out.println("\t VLOS : -->> SO UM PROCESSADOR SERA UTILIZADO !");
            }
            if (temIncompativel) {
                processadorOk = false;
                System.out.println("\t VLOS : Nao pode iniciar -->> PROCESSADOR INCOMPATIVEL INSTALADO !");
            }
        }

        if (!mTemMemoria) {
            System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM MEMORIA !");
        }

        if (!mTemHD) {
            System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM HD !");
        }

        if (mTemMemoria && mTemHD && processadorOk) {
            mTudoOK = true;
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


            if (mDEBUG_DESPACHANTE_PROCESSO) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
                System.out.println("\t -->> VLOS ADICIONAR PROCESSO { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " }");
            }

            for (DespachanteProcesso mItem : mAdicionar) {

                // EXISTE PROCESSO PARA SER ADICIONADO A FILA DE USUARIO
                mItem.despachar();

                if (mDEBUG_DESPACHANTE_PROCESSO) {
                    mDumper.dump_despachante(mItem);
                }

                // VERIFICA A PRIORIDADE DO PROCESSO E COLOCA NA FILA ADEQUADA
                // ALOCA RECURSOS PARA INICIAR O PROCESSO
                // ALOCA MEMORIA PARA O PROCESSO

                if (mItem.getPrioridade() == 0) {


                    boolean eStatus = true;

                    if (mItem.getCodigoImpressora() > 0) {
                        System.out.println("\t\t -->> NAO SE PODE DESPACHAR UM PROCESSO KERNEL QUE USE IMPRESSORA !");
                        eStatus = false;
                    }

                    if (mItem.getCodigoModem() > 0) {
                        System.out.println("\t\t -->> NAO SE PODE DESPACHAR UM PROCESSO KERNEL QUE USE MODEM !");
                        eStatus = false;
                    }

                    if (mItem.getCodigoScanner() > 0) {
                        System.out.println("\t\t -->> NAO SE PODE DESPACHAR UM PROCESSO KERNEL QUE USE SCANNER !");
                        eStatus = false;
                    }

                    if (mItem.getCodigoSata() > 0) {
                        System.out.println("\t\t -->> NAO SE PODE DESPACHAR UM PROCESSO KERNEL QUE USE SATA !");
                        eStatus = false;
                    }

                    if (eStatus) {

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


                    }


                } else {

                    MemoriaAlocada eMemoriaAlocada = mVLMemoria.alocarBlocosDeUsuario(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());

                    Processo mProcessoCorrente = mVLProcessos.criarProcessoUsuario(mTempo, mItem.getPrioridade(), eMemoriaAlocada, mItem.getTempoProcessador());

                    if (mItem.getCodigoImpressora() > 0) {
                        mProcessoCorrente.adicionarDependencia(new Dependencia("PRINTER"));
                    }
                    if (mItem.getCodigoScanner() > 0) {
                        mProcessoCorrente.adicionarDependencia(new Dependencia("SCANNER"));
                    }
                    if (mItem.getCodigoModem() > 0) {
                        mProcessoCorrente.adicionarDependencia(new Dependencia("MODEM"));
                    }
                    if (mItem.getCodigoSata() > 0) {
                        mProcessoCorrente.adicionarDependencia(new Dependencia("SATA"));
                    }

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

            if (mDEBUG_DESPACHANTE_PROCESSO) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
            }

            if (mDEBUG_MEMORIA) {
                mDumper.dump_memoria(mVLMemoria);
            }

        }


    }

    private void debugar_Despachadores() {
        if (mDEBUG_DESPACHANTE_PROCESSO) {
            System.out.println("");
            System.out.println("DESPACHAR PROCESSOS");
            for (DespachanteProcesso eItem : mDespachadorDeProcessos.getProcessos()) {
                System.out.println("\t - Tempo :: " + eItem.getTempoProcessador() + " Prioridade = " + eItem.getPrioridade());
            }
        }

        if (mDEBUG_DESPACHANTE_ARQUIVOS) {
            System.out.println("");
            System.out.println("DESPACHAR ARQUIVOS");
            for (DespachanteArquivo eItem : mDespachadorDeOperacoes.getItens()) {
                System.out.println("\t - Arquivo :: " + eItem.getNomeArquivo() + " Inicio = " + eItem.getBlocoInicial() + " Blocos = " + eItem.getBlocos());
            }
        }

        if (mDEBUG_DESPACHANTE_OPERACOES) {
            System.out.println("");
            System.out.println("DESPACHAR OPERACOES");
            for (DespachanteOperacao eItem : mDespachadorDeOperacoes.getOperacoes()) {
                if (eItem.getCodigoOperacao() == 0) {

                    String eP1 = "Operacao :: PID = " + eItem.getPID() + "      Tipo = CRIAR       Nome = " + eItem.getNomeArquivo();
                    while (eP1.length() < 63) {
                        eP1 += " ";
                    }

                    String eP2 = "    Tamanho = " + eItem.getNumeroBlocos();

                    System.out.println("\t - " + eP1 + eP2);

                } else if (eItem.getCodigoOperacao() == 1) {
                    System.out.println("\t - Operacao :: PID = " + eItem.getPID() + "      Tipo = REMOVER     Nome = " + eItem.getNomeArquivo() + " ");
                }
            }
        }
    }

    public void iniciar() {

        mEtapa = Etapa.INICIANDO;

        print_inicializacao();

        mQuantum = 1 * 10; // 1 SEGUNDO = 10 CICLOS DE PROCESSAMENTO DA CPU

        mVLProcessos = new VLProcessos(mCPU);
        mVLMemoria = new VLMemoria(mMemoria, 1024 * 1024);
        mVLRecursos = new VLRecursos();
        mVLVFS = new VLVFS();


        carregar_Despachadores();

        debugar_Despachadores();

        int mDiscos = 0;

        for (Dispositivo mDispositivoRecurso : mRecursosDispositivos) {

            if (mDispositivoRecurso.mesmoTipo("SATA")) {
                if (mDiscos == 0) {
                    mVLVFS.montarCom((SATA) mDispositivoRecurso, mDespachadorDeOperacoes.getBlocos(), mDespachadorDeOperacoes.getItens());
                } else {
                    mVLVFS.montar((SATA) mDispositivoRecurso);
                }
                mDiscos += 1;
            }

            mVLRecursos.adicionarRecurso(mDispositivoRecurso);

        }


        if (mDEBUG_MEMORIA) {
            mDumper.dump_memoria(mVLMemoria);
        }


        esperar();


        // O RESERVADOR DO KERNEL - RESERVA OS PRIMEIROS BLOCOS PARA KERNEL E O RESTO PARA USUARIO
        if (mDEBUG_MEMORIA) {
            System.out.println("\t -->> Reservando 64 Blocos para KERNEL");
        }
        mVLMemoria.reservarKernel(64 * mVLMemoria.getTamanhoBloco());


        if (mDEBUG_MEMORIA) {
            mDumper.dump_memoria(mVLMemoria);
        }


        esperar();

    }

    public void ociosa() {

        // REALIZA PROCEDIMENTOS DE ESCOLANAMENTO QUANDO A CPU ESTIVER OCIOSA

        if (mDEBUG_CPU_OCIOSA) {
            System.out.println("\t -->> CPU OCIOSA { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " } ");
        }

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

            mEscalonado.enviarContextos(mCPU);
            mContextos += 1;

            mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);

            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processoCompleto(mTempo, mEscalonado);
            }

            mCPU.setExecutando(true);

            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processosCompleto(mTempo, mVLProcessos);
            }

        } else {

            mQuantizando = 0;

            verificarProcessosEmEspera();

            if (mVLProcessos.temProcessoProntoUsuario()) {

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\t ---------------------------------------------------------------------------------------------------");
                    System.out.println("\t -->> ESCALONADOR : PROCESSAR PROCESSOS DO USUARIO");
                    System.out.println("\t - Processos : " + mVLProcessos.getProcessosUsuario().size());
                    System.out.println("\t - Prontos   : " + mVLProcessos.getProcessosUsuario_Prontos().size());
                    System.out.println("\t - Concluidos : " + mVLProcessos.getProcessosUsuario_Conclucidos().size());
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

                if (mDEBUG_PROCESSOS) {
                    mDumper.dump_processosEsperando(mVLProcessos);
                }

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\tEscalonando Processo de Usuario");
                    System.out.println("\t\t PID         = " + mEscalonado.getPID());
                    System.out.println("\t\t Prioridade  = " + mEscalonado.getPrioridade());

                    if (mEscalonado.getProcessado() == 0) {
                        System.out.println("\t\t Status      = Iniciar Processo");
                    } else if (mEscalonado.getProcessado() > 0) {
                        System.out.println("\t\t Status      = Continuar Processo");
                    } else {

                    }

                }

                ArrayList<String> mParaDebugRecursos = new ArrayList<String>();

                int mAlocados = 0;
                boolean precisaEsperar = false;
                for (Dependencia mDependencia : mEscalonado.getDependencias()) {

                    String mAlocandoDependencia = mDependencia.getPrecisa();

                    if (mDependencia.getStatus()) {
                        mAlocandoDependencia += " " + mDependencia.getRID() + " -->> OK";
                        mAlocados += 1;
                    } else {

                        if (mVLRecursos.temDisponivel(mDependencia.getPrecisa())) {
                            int mRID = mVLRecursos.getRecursoEBloqueio(mDependencia.getPrecisa());
                            if (mDEBUG_RECURSOS) {
                                System.out.println("\t - BLOQUEANDO RECURSO -- " + mRID + " :: " + mDependencia.getPrecisa());
                            }
                            mDependencia.conseguir(mRID);

                            mAlocandoDependencia += " " + mDependencia.getRID() + " -->> OK";
                            mAlocados += 1;

                        } else {
                            precisaEsperar = true;

                            mAlocandoDependencia += " " + " -->> NAO CONSEGUIU AINDA !";

                        }

                    }

                    mParaDebugRecursos.add(mAlocandoDependencia);
                }

                if (mDEBUG_ESCALONADOR) {
                    if (mEscalonado.getDependencias().size() == 0) {
                        System.out.println("\t\t Recurso     = 0");
                    } else {
                        System.out.println("\t\t Recurso     = " + mAlocados + " de " + mEscalonado.getDependencias().size());
                    }
                    int i = 1;
                    for (String eDebug : mParaDebugRecursos) {
                        System.out.println("\t\t              " + i + " - " + eDebug);
                        i += 1;
                    }
                }

                if (precisaEsperar) {
                    if (mDEBUG_ESCALONADOR) {
                        System.out.println("\t\t Status      = Colocar Processo em Espera");
                    }


                    mEscalonado.mudarStatus(ProcessoStatus.ESPERANDO);

                    mVLProcessos.retirarProcesso();
                    mCPU.setExecutando(false);

                } else {
                    mEscalonado.enviarContextos(mCPU);
                    mContextos += 1;

                    mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);
                    mCPU.setExecutando(true);

                }

                if (mDEBUG_ESCALONADOR) {
                    System.out.println("\t ---------------------------------------------------------------------------------------------------");
                }

                if (mDEBUG_PROCESSO_CORRENTE) {
                    mDumper.dump_processoCompleto(mTempo, mEscalonado);
                }


                if (mDEBUG_PROCESSOS) {
                    mDumper.dump_processosCompleto(mTempo, mVLProcessos);
                }

            }
        }

    }

    public void verificarProcessosEmEspera() {

        // SE EXISTIR ALGUM PROCESSO EM ESPERA VERIFICA SE ELE PODE VOLTAR PARA FILA DE PRONTO

        if (mVLProcessos.temProcessoUsuarioEmEspera()) {

            boolean mTinhaEsperando = false;

            for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila1()) {
                if (eProcesso.isEsperando()) {
                    mTinhaEsperando = true;
                    verificarProcessoQueEstaEsperando(eProcesso);
                }
            }

            if (!mTinhaEsperando) {
                for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila2()) {
                    if (eProcesso.isEsperando()) {
                        mTinhaEsperando = true;
                        verificarProcessoQueEstaEsperando(eProcesso);
                    }
                }
            }

            if (!mTinhaEsperando) {
                for (Processo eProcesso : mVLProcessos.getProcessosUsuario_Fila3()) {
                    if (eProcesso.isEsperando()) {
                        mTinhaEsperando = true;
                        verificarProcessoQueEstaEsperando(eProcesso);
                    }
                }
            }

        }

        if (mDEBUG_ESPERANDO) {
            mDumper.dump_processosEsperando(mVLProcessos);
        }

    }

    private void verificarProcessoQueEstaEsperando(Processo eProcesso) {

        int mPrecisa = 0;
        int mConseguiu = 0;

        for (Dependencia mDependencia : eProcesso.getDependencias()) {

            mPrecisa += 1;

            if (mDependencia.getStatus()) {

                mConseguiu += 1;

            } else {

                if (mVLRecursos.temDisponivel(mDependencia.getPrecisa())) {
                    int mRID = mVLRecursos.getRecursoEBloqueio(mDependencia.getPrecisa());

                    if (mDEBUG_RECURSOS) {
                        System.out.println("\t - BLOQUEANDO RECURSO -- " + mRID + " :: " + mDependencia.getPrecisa());
                    }

                    mDependencia.conseguir(mRID);

                    mConseguiu += 1;

                }

            }
        }


        if (mPrecisa == mConseguiu) {
            eProcesso.mudarStatus(ProcessoStatus.PRONTO);
            if (mDEBUG_ESPERANDO) {
                System.out.println("Retirando processo de espera : PID = " + eProcesso.getPID());
            }
        }

    }


    public void executar() {

        // EXECUTA PARTE DO PROCESSO QUE ESTA NA CPU

        if (mDEBUG_CPU_EXECUTANDO) {
            System.out.println("\t -->> CPU EXECUTANDO { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " } -->> PID = " + mVLProcessos.getEscalonado().getPID());
        }

        esperar();


        boolean mProcessoConcluiu = false;


        if (mVLProcessos.temEscalonado()) {

            if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.KERNEL) {

                mVLProcessos.getEscalonado().processar(mCPU, mVLVFS);
                mVLProcessos.getEscalonado().verificar();

                if (mVLProcessos.getEscalonado().isConcluido()) {

                    mVLProcessos.getEscalonado().setTempoConclusao(mTempo);

                    if (mDEBUG_PROCESSO_CONCLUSAO) {
                        System.out.println("\t -->> PROCESSO DE KERNEL CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());
                    }

                    mVLProcessos.getEscalonado().receberContextos(mCPU);

                    mCPU.setExecutando(false);
                    mVLProcessos.retirarProcesso();
                    mProcessoConcluiu = true;
                }

            } else if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.USUARIO) {

                mQuantizando += 1;

                boolean mChegouTempoReal = false;

                if (mVLProcessos.temProcessoProntoKernel()) {

                    // INTERROMPE O PROCESSO DE USUARIO PORQUE CHEGOU PROCESSO DE KERNEL


                    System.out.println("\t -->> PROCESSO DE USUARIO INTERROMPIDO : PID = " + mVLProcessos.getEscalonado().getPID());

                    mVLProcessos.getEscalonado().receberContextos(mCPU);
                    mVLProcessos.getEscalonado().mudarStatus(ProcessoStatus.PRONTO);

                    mCPU.setExecutando(false);
                    mVLProcessos.retirarProcesso();

                    mChegouTempoReal = true;
                }

                if (!mChegouTempoReal) {

                    mVLProcessos.getEscalonado().processar(mCPU, mVLVFS);
                    mVLProcessos.getEscalonado().mudarStatus(ProcessoStatus.PRONTO);
                    mVLProcessos.getEscalonado().verificar();

                    boolean jaRecebeuContexto = false;

                    if (mVLProcessos.getEscalonado().isConcluido()) {

                        mVLProcessos.getEscalonado().setTempoConclusao(mTempo);

                        mVLProcessos.getEscalonado().receberContextos(mCPU);
                        jaRecebeuContexto = true;

                        if (mDEBUG_PROCESSO_CONCLUSAO) {
                            System.out.println("\t -->> PROCESSO DE USUARIO CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());
                        }

                        mProcessoConcluiu = true;

                        // DESALOCAR RECURSOS

                        for (Dependencia mDependencia : mVLProcessos.getEscalonado().getDependencias()) {
                            if (mDependencia.getStatus()) {

                                if (mDEBUG_RECURSOS) {
                                    System.out.println("\t - LIBERANDO RECURSO  -- " + mDependencia.getRID() + " :: " + mDependencia.getPrecisa());
                                }

                                mVLRecursos.liberarRecurso(mDependencia.getRID());
                                mDependencia.liberar();
                            }
                        }


                    }

                    if (mQuantizando >= mQuantum) {

                        if (!jaRecebeuContexto) {
                            mVLProcessos.getEscalonado().receberContextos(mCPU);
                        }

                        mCPU.setExecutando(false);
                        mVLProcessos.retirarProcesso();
                        mQuantizando = 0;
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

        if (mTempo % 10 == 0 && mCicloContagem == 0) {
            if (mDEBUG_TROCA_DE_CONTEXTOS) {
                mDumper.dump_processosTrocaDeContexto(mVLProcessos);
            }
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

    public long getContextos() {
        return mContextos;
    }

    public CPU getCPU() {
        return mCPU;
    }

    public VLVFS getVLVFS() {
        return mVLVFS;
    }

    public VLRecursos getVLRecursos() {
        return mVLRecursos;
    }

    public String getMemoriaStatus() {
        return mVLMemoria.getMemoriaStatus();
    }

    public String getRecursosStatus() {
        return mVLRecursos.getRecursosStatus();
    }
}
