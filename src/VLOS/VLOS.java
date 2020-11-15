package VLOS;

import Hardware.*;
import Testes.Teste_Alpha;
import VLOS.Despachante.ArquivoDespachante;
import VLOS.Despachante.ItemDespachante;
import VLOS.Memoria.MemoriaAlocada;
import VLOS.Memoria.VLMemoria;
import VLOS.Processo.Processo;
import VLOS.Processo.ProcessoStatus;
import VLOS.Processo.ProcessoTipo;
import VLOS.Processo.VLProcessos;
import VLOS.Recurso.VLRecursos;

import java.util.ArrayList;

public class VLOS {

    private Maquina mMaquina;

    private long mMEMORIA_BLOCO;

    private VLMemoria mVLMemoria;
    private VLProcessos mVLProcessos;
    private VLRecursos mVLRecursos;

    private boolean mTemMemoria;
    private boolean mTemHD;

    private CPU mCPU;
    private Memoria mMemoria;
    private ArrayList<Dispositivo> mRecursosDispositivos;

    private long mTempo;
    private int mCicloContagem;
    private int mCicloMaximo;

    private int mQuantum;
    private int mQuantizando;
    private boolean mLigado;

    private Utils mUtils;
    private Dumper mDumper;
    private Teste_Alpha mTeste_Alpha;

    private boolean mDEBUG_PROCESSOS;
    private boolean mDEBUG_ESCALONADOR;
    private boolean mDEBUG_MEMORIA;
    private boolean mDEBUG_PROCESSO_CORRENTE;
    private boolean mDEBUG_DESPACHANTE;
    private boolean mDILATADOR_TEMPORAL;

    public VLOS(Maquina eMaquina) {

        mMaquina = eMaquina;

        mUtils = new Utils();

        mTempo = 0;
        mCicloContagem = 0;
        mCicloMaximo = 0;
        mQuantum = 0;
        mQuantizando = 0;
        mMEMORIA_BLOCO = 0;

        mRecursosDispositivos = new ArrayList<Dispositivo>();

        mCPU = new CPU();
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


    public void ligar() {

        mLigado = true;
        mTempo = 0;
        mQuantum = 0;
        mCicloContagem = 0;
        mCicloMaximo = 10;
        mQuantizando = 0;
        mMEMORIA_BLOCO = 0;

        // BOOT HARDWARE DE SISTEMA
        detectarHardware();

        esperar();

        if (mTemMemoria && mTemHD) {

            print_inicializacao();

            mQuantum = 1 * 10; // 1 SEGUNDO = 10 CICLOS DE PROCESSAMENTO DA CPU
            mMEMORIA_BLOCO = 1024 * 1024;

            mVLProcessos = new VLProcessos(mCPU);
            mVLMemoria = new VLMemoria(mMemoria, mMEMORIA_BLOCO);
            mVLRecursos = new VLRecursos();

            for (Dispositivo mDispositivoRecurso : mRecursosDispositivos) {
                mVLRecursos.adicionarRecurso(mDispositivoRecurso);
            }


            // CARREGAR PROCESSOS DE USUARIO
            ArrayList<ItemDespachante> mDespachantes = ArquivoDespachante.carregar("res/proccesses.txt");

            mTeste_Alpha.testeProcessosSimultaneosMultiplasFilasPrioritarias(mDespachantes);


            iniciar();

            // LOOP NUCLEO DO SISTEMA OPERACIONAL
            // A CADA CICLO DO LOOP DO SISTEMA :
            //
            //  - Contador de Ciclo +1
            //  - Se o contador de Ciclo == 10 entao Tempo +1 e Ciclo = 0
            //  - Se existir despachantes de tempo igual ao mTempo entao dispara
            //  - Se a CPU estiver OCIOSA entao escalona processos
            //  - Se a CPU estiver executando e o quantum tiver atingido entao INTERROMPRE processo e escalona
            //  - Para efeito de visualizacao e DEBUG coloca-se um espacador de tempo entre cada ciclo de 300 millisegundos

            while (mLigado) {

                despachar(mDespachantes);

                if (mCPU.estaOciosa()) {
                    ociosa();
                } else {
                    executar();
                }

                esperar();

                temporizar();

            }


        }

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

        System.out.println("\t-----------------------------------------------------");

        System.out.println("\t DETECCAO DE HARDWARE");
        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));


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

    public void despachar(ArrayList<ItemDespachante> mDespachantes) {

        // INICIAR PROCESSOS DE USUARIO

        ArrayList<ItemDespachante> mAdicionar = new ArrayList<ItemDespachante>();

        // OBTEM DESPACHANTES DO TEMPO REQUERIDO
        for (ItemDespachante mItem : mDespachantes) {
            if (!mItem.isDespachado()) {
                if (mItem.getInicializacao() == mTempo) {
                    mAdicionar.add(mItem);
                }
            }
        }

        // SE POSSUIR DESPACHANTES ADICIONA NA FILA DE ESCALONAMENTO DE USUARIO DE ACORDO A PRIORIDADE
        // EXISTEM 3 PRIORIDADES DE USUARIO

        // FILA 1 - PRIOPRIDADE 0
        // FILA 2 - PRIOPRIDADE 1
        // FILA 3 - PRIOPRIDADE 2 OU SUPERIOR

        // QUANDO UM PROCESSO SOFRE ENVELHECIMENTO ELE AUMENTA O NUMERO DE PRIORIDADE DO PROCESSO QUE RESULTA NO EFEITO CONTRARIO
        // QUANTO MENOR O NUMERO DE PRIORIDADE MAIOR A PRIORIDADE

        if (mAdicionar.size() > 0) {

            mVLProcessos.criarProcessoKernel(mTempo, mVLMemoria.alocarBlocosDeKernel(2 * mVLMemoria.getTamanhoBloco()), 2);

            if (mDEBUG_DESPACHANTE) {
                System.out.println("\t ---------------------------------------------------------------------------------------------------");
                System.out.println("\t -->> VLOS ADICIONAR PROCESSO { TEMPO :: " + mTempo + "s CICLO :: " + mCicloContagem + " }");
            }

            for (ItemDespachante mItem : mAdicionar) {

                // EXISTE PROCESSO PARA SER ADICIONADO A FILA DE USUARIO
                mItem.despachar();

                if (mDEBUG_DESPACHANTE) {
                    mDumper.dump_despachante(mItem);
                }

                // ALOCA RECURSOS PARA INICIAR O PROCESSO

                // ALOCA MEMORIA PARA O PROCESSO
                MemoriaAlocada eMemoriaAlocada = mVLMemoria.alocarBlocosDeUsuario(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());


                Processo mProcessoCorrente = mVLProcessos.criarProcessoUsuario(mTempo, mItem.getPrioridade(), eMemoriaAlocada, mItem.getTempoProcessador());
                //mProcessoCorrente.mostrar();

                if (mDEBUG_PROCESSO_CORRENTE) {
                    mDumper.dump_processoCompleto(mTempo, mProcessoCorrente);
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

        if (mDEBUG_MEMORIA) {
            mDumper.dump_memoria(mVLMemoria);
        }


        System.out.println("\t -->> Reservando 64 Blocos para KERNEL");

        esperar();

        mVLMemoria.reservarKernel(64 * mMEMORIA_BLOCO);
        mVLMemoria.definirOffsets(0, 64);

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

                    mVLProcessos.getEscalonado().processar();
                    mVLProcessos.getEscalonado().verificar();

                    if (mVLProcessos.getEscalonado().isConcluido()) {

                        mVLProcessos.getEscalonado().setTempoConclusao(mTempo);

                        System.out.println("\t -->> PROCESSO DE KERNEL CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());

                        mCPU.setExecutando(false);
                        mVLProcessos.retirarProcesso();
                        mProcessoConcluiu = true;
                    }

                } else if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.USUARIO) {

                    mVLProcessos.getEscalonado().processar();
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

        }

        if (mProcessoConcluiu) {
            if (mDEBUG_PROCESSOS) {
                mDumper.dump_processosCompleto(mTempo, mVLProcessos);
            }
        }

    }


    public void esperar() {

        if (mDILATADOR_TEMPORAL) {
            try {
                Thread.sleep(200);
            } catch (
                    InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void temporizar() {
        mCicloContagem += 1;

        if (mCicloContagem >= mCicloMaximo) {
            mTempo += 1;
            mCicloContagem = 0;
        }
    }

}
