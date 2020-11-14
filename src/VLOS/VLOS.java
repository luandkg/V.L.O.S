package VLOS;

import Hardware.*;
import Testes.Teste_Alpha;
import VLOS.Despachante.Despachante;
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

    private final long MEMORIA_BLOCO = 1024 * 1024;

    private VLMemoria mVLMemoria;
    private VLProcessos mVLProcessos;
    private VLRecursos mVLRecursos;

    private boolean mTemMemoria;
    private boolean mTemHD;

    private CPU mCPU;

    private long mTempoContagem;
    private int mCicloContagem;
    private int mCicloMaximo;

    private int mQuantum;
    private int mQuantizando;
    private boolean mLigado;

    private Utils mUtils;
    private Dumper mDumper;
    private Teste_Alpha mTeste_Alpha;

    public VLOS(Maquina eMaquina) {

        mMaquina = eMaquina;

        mUtils = new Utils();

        mTempoContagem = 0;
        mCicloContagem = 0;
        mCicloMaximo = 10;

        mQuantum = 1 * 10; // 1 SEGUNDO = 10 CICLOS DE PROCESSAMENTO DA CPU
        mQuantizando = 0;

        mCPU = new CPU();
        mDumper = new Dumper();

        mTemMemoria = false;
        mTemHD = false;
        mLigado = false;

        mTeste_Alpha = new Teste_Alpha();

    }

    public void ligar() {

        mLigado = true;

        detectarHardware();

        if (mTemMemoria && mTemHD) {


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


            // CARREGAR PROCESSOS DE USUARIO
            Despachante mDespachante = new Despachante();
            ArrayList<ItemDespachante> mDespachantes = mDespachante.carregar("res/proccesses.txt");

            mTeste_Alpha.testeProcessosSimultaneosMultiplasFilas(mDespachantes);

            mTempoContagem = 0;
            mCicloContagem = 0;
            mQuantizando = 0;
            mQuantum = 1 * 10; // 1 SEGUNDO = 10 CICLOS DE PROCESSAMENTO DA CPU

            mVLProcessos = new VLProcessos(mCPU);

            mDumper.dump_memoria(mVLMemoria);

            System.out.println("\t -->> Reservando 64 Blocos para KERNEL");

            esperar();

            mVLMemoria.reservarKernel(64 * MEMORIA_BLOCO);

            mVLMemoria.definirOffsets(0, 64);

            mDumper.dump_memoria(mVLMemoria);

            mVLProcessos.criarProcessoKernel(mVLMemoria.alocarBlocosDeKernel(3 * mVLMemoria.getTamanhoBloco()), 3);
            //   mVLProcessos.criarProcessoKernel(mVLMemoria.alocarBlocosDeKernel(5 * mVLMemoria.getTamanhoBloco()));

            esperar();


            // LOOP NUCLEO DO SISTEMA OPERACIONAL

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


    public void detectarHardware() {

        System.out.println("\t-----------------------------------------------------");

        System.out.println("\t DETECCAO DE HARDWARE");
        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));


        mTemMemoria = false;
        mTemHD = false;


        mVLMemoria = null;
        mVLRecursos = new VLRecursos();


        for (Dispositivo mDispositivo : mMaquina.getDispositivos()) {

            if (mDispositivo instanceof Memoria) {

                Memoria mMemoria = (Memoria) mDispositivo;
                System.out.println("\t\t Memoria : " + mMemoria.getModelo() + " -> " + mUtils.texto_tamanho(mMemoria.getTamanho()));

                mTemMemoria = true;
                mVLMemoria = new VLMemoria(mMemoria, MEMORIA_BLOCO);

            } else if (mDispositivo instanceof SATA) {

                SATA mSATA = (SATA) mDispositivo;
                System.out.println("\t\t HD : " + mSATA.getModelo() + " -> " + mUtils.texto_tamanho(mSATA.getTamanho()));

                mTemHD = true;

            } else if (mDispositivo instanceof Impressora) {

                Impressora mImpressora = (Impressora) mDispositivo;
                System.out.println("\t\t Impressora : " + mImpressora.getModelo());

                mVLRecursos.adicionarRecurso(mImpressora.getModelo());

            } else if (mDispositivo instanceof Scanner) {

                Scanner mScanner = (Scanner) mDispositivo;
                System.out.println("\t\t Scanner : " + mScanner.getModelo());

                mVLRecursos.adicionarRecurso(mScanner.getModelo());

            } else if (mDispositivo instanceof Modem) {

                Modem mModem = (Modem) mDispositivo;
                System.out.println("\t\t Modem : " + mModem.getModelo());

                mVLRecursos.adicionarRecurso(mModem.getModelo());

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


        esperar();

    }


    public void esperar() {

        try {

            Thread.sleep(800);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void temporizar() {
        mCicloContagem += 1;

        if (mCicloContagem >= mCicloMaximo) {
            mTempoContagem += 1;
            mCicloContagem = 0;
        }
    }


    public void despachar(ArrayList<ItemDespachante> mDespachantes) {

        // INICIAR PROCESSOS DE USUARIO

        ArrayList<ItemDespachante> mAdicionar = new ArrayList<ItemDespachante>();

        // OBTEM DESPACHANTES DO TEMPO REQUERIDO
        for (ItemDespachante mItem : mDespachantes) {
            if (!mItem.isDespachado()) {
                if (mItem.getInicializacao() == mTempoContagem) {
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

            System.out.println("\t ---------------------------------------------------------------------------------------------------");
            System.out.println("\t -->> VLOS ADICIONAR PROCESSO { TEMPO :: " + mTempoContagem + "s CICLO :: " + mCicloContagem + " }");

            for (ItemDespachante mItem : mAdicionar) {

                // EXISTE PROCESSO PARA SER ADICIONADO A FILA DE USUARIO
                mItem.despachar();

                // ALOCA RECURSOS PARA INICIAR O PROCESSO

                // ALOCA MEMORIA PARA O PROCESSO
                MemoriaAlocada eMemoriaAlocada = mVLMemoria.alocarBlocosDeUsuario(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());


                Processo mProcessoCorrente = mVLProcessos.criarProcessoUsuario(mItem.getPrioridade(), eMemoriaAlocada, mItem.getTempoProcessador());
                mProcessoCorrente.mostrar();

            }

            System.out.println("\t ---------------------------------------------------------------------------------------------------");


        }


    }


    public void ociosa() {

        // REALIZA PROCEDIMENTOS DE ESCOLANAMENTO QUANDO A CPU ESTIVER OCIOSA

        System.out.println("\t -->> CPU OCIOSA { TEMPO :: " + mTempoContagem + "s CICLO :: " + mCicloContagem + " } ");

        if (mVLProcessos.temProcessoKernelEsperando()) {

            System.out.println("\t ---------------------------------------------------------------------------------------------------");
            System.out.println("\t -->> KERNEL PROCESSAR");

            System.out.println("\t Tem processos do Kernel prontos na fila de Tempo de Execucao Real");

            Processo mEscalonado = mVLProcessos.escalonarProcessoKernel();
            System.out.println("\t ---------------------------------------------------------------------------------------------------");

            mDumper.dump_processos(mVLProcessos);

            System.out.println("\tEscalonando Processo de Kernel");
            System.out.println("\t\t PID         = " + mEscalonado.getPID());
            System.out.println("\t\t Prioridade  = " + mEscalonado.getPrioridade());

            mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);
            mEscalonado.mostrar();
            mCPU.setExecutando(true);

            mDumper.dump_processos(mVLProcessos);

        } else {

            if (mVLProcessos.temProcessoUsuarioEsperando()) {

                System.out.println("\t ---------------------------------------------------------------------------------------------------");
                System.out.println("\t -->> USUARIO ESCALONAR");

                System.out.println("\t Tem processos do Usuario prontos na fila de Escalonamento  .... ");

                Processo mEscalonado = mVLProcessos.escalonarProcessoUsuario();
                System.out.println("\t ---------------------------------------------------------------------------------------------------");

                mDumper.dump_processos(mVLProcessos);

                System.out.println("\tEscalonando Processo de Usuario");
                System.out.println("\t\t PID         = " + mEscalonado.getPID());
                System.out.println("\t\t Prioridade  = " + mEscalonado.getPrioridade());

                if (mEscalonado.getProcessado() == 0) {
                    System.out.println("\t\t Iniciar Processo");
                } else {
                    System.out.println("\t\t Continuar Processo");
                }

                mEscalonado.mudarStatus(ProcessoStatus.EXECUTANDO);
                mEscalonado.mostrar();
                mCPU.setExecutando(true);

                mDumper.dump_processos(mVLProcessos);

            }
        }

    }

    public void executar() {

        // EXECUTA PARTE DO PROCESSO QUE ESTA NA CPU

        System.out.println("\t -->> CPU EXECUTANDO { TEMPO :: " + mTempoContagem + "s CICLO :: " + mCicloContagem + " } -->> PID = " + mVLProcessos.getEscalonado().getPID());

        esperar();


        mQuantizando += 1;
        if (mQuantizando >= mQuantum) {


            if (mVLProcessos.temEscalonado()) {

                if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.KERNEL) {

                    mVLProcessos.getEscalonado().aumentarProcessado();
                    if (mVLProcessos.getEscalonado().getConcluido()) {
                        System.out.println("\t -->> PROCESSO DE KERNEL CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());

                        mCPU.setExecutando(false);
                        mVLProcessos.retirarProcesso();
                    }

                } else if (mVLProcessos.getEscalonado().getTipo() == ProcessoTipo.USUARIO) {

                    mVLProcessos.getEscalonado().mudarStatus(ProcessoStatus.PRONTO);
                    mVLProcessos.getEscalonado().aumentarProcessado();

                    if (mVLProcessos.getEscalonado().getConcluido()) {
                        System.out.println("\t -->> PROCESSO DE USUARIO CONCLUIDO : PID = " + mVLProcessos.getEscalonado().getPID());
                    }


                    mCPU.setExecutando(false);
                    mVLProcessos.retirarProcesso();

                }

            }

            mQuantizando = 0;

        }


        //  mDumper.dump_recursos(mVLRecursos);
        //   mDumper.dump_memoria(mVLMemoria);
        //  mDumper.dump_processos(mVLProcessos);


    }

}
