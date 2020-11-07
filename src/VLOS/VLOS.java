package VLOS;

import Hardware.*;
import VLOS.Despachante.Despachante;
import VLOS.Despachante.ItemDespachante;
import VLOS.Memoria.Segmento;
import VLOS.Memoria.VLMemoria;
import VLOS.Processo.Processo;
import VLOS.Processo.VLProcessos;
import VLOS.Recurso.Recurso;
import VLOS.Recurso.VLRecursos;

public class VLOS {

    private Maquina mMaquina;

    private final long MEMORIA_BLOCO = 1024 * 1024;

    private VLMemoria mVLMemoria;
    private VLProcessos mVLProcessos;
    private VLRecursos mVLRecursos;

    private Despachante mDespachante;

    public VLOS(Maquina eMaquina) {
        mMaquina = eMaquina;
    }

    public void ligar() {

        Utils mUtils = new Utils();

        System.out.println("\t-----------------------------------------------------");

        System.out.println("\t DETECCAO DE HARDWARE");
        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));


        boolean sistemaOK = false;

        boolean mTemMemoria = false;
        boolean mTemHD = false;


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

        }


        System.out.println("\t-----------------------------------------------------");


        if (mTemMemoria) {
            if (mTemHD) {
                sistemaOK = true;
            }
        }

        if (!sistemaOK) {


            System.out.println("");

            if (!mTemMemoria) {
                System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM MEMORIA !");
            }

            if (!mTemHD) {
                System.out.println("\t VLOS : Nao pode iniciar -->> NAO TEM HD !");
            }

        }


        if (sistemaOK) {

            System.out.println("");
            System.out.println("\t                         VLOS                        ");

            mVLProcessos = new VLProcessos();
            Despachante mDespachante = new Despachante();

            dump_memoria();

            System.out.println("\t -->> Reservando 64 Blocos para KERNEL");
            mVLMemoria.reservarKernel(64 * MEMORIA_BLOCO);

            mVLMemoria.definirOffsets(0, 64);

            dump_memoria();

            Segmento eSegmentoKernel = mVLMemoria.alocarSegmentoDeKernel(12 * mVLMemoria.getTamanhoBloco());
            Processo mProcesso1 = mVLProcessos.criarProcessoKernel(eSegmentoKernel);

            mostrarProcesso(mProcesso1.getPID());

            // CARREGAR PROCESSOS DE USUARIO

            for (ItemDespachante mItem : mDespachante.carregar("res/proccesses.txt")) {

                //mItem.mostrarDebug();

                Segmento eSegmento = mVLMemoria.alocarSegmentoDeUsuario(mItem.getBlocos() * mVLMemoria.getTamanhoBloco());

                Processo mProcessoCorrente = mVLProcessos.criarProcessoUsuario(mItem.getPrioridade(), eSegmento);

                mostrarProcesso(mProcessoCorrente.getPID());

                dump_recursos();

            }


        }

    }

    public void mostrarProcesso(int ePID) {

        Processo eProcesso = getProcesso(ePID);

        System.out.println("---------------------------------------------");

        System.out.println(" -->> PID : " + eProcesso.getPID());
        System.out.println("");
        System.out.println("\t - TIPO        : " + eProcesso.getTipoFormatado());
        System.out.println("\t - PRIORIDADE  : " + eProcesso.getPrioridade());
        System.out.println("\t - OFFSET      : " + eProcesso.getOffset());
        System.out.println("\t - BLOCOS      : " + eProcesso.getBlocos());
        System.out.println("\t - IMPRESSORA  : " + "");
        System.out.println("\t - SCANNER     : " + "");
        System.out.println("\t - DRIVER      : " + "");

        System.out.println("---------------------------------------------");

    }

    public Processo getProcesso(int ePID) {
        return mVLProcessos.getProcesso(ePID);
    }

    public void dump_memoria() {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\tMEMORIA");
        System.out.println("\t\t - Disponivel : " + mVLMemoria.getTamanho());
        System.out.println("\t\t - Bloco Tamanho : " + mVLMemoria.getTamanhoBloco() + " bytes :: " + (mVLMemoria.getTamanhoBloco() / 1024) + " kb");
        System.out.println("\t\t - Blocos : " + mVLMemoria.getBlocos());

        System.out.println("\t\t - KERNEL Reservado : " + mVLMemoria.getBlocos_KernelReservados());
        System.out.println("\t\t\t  Livre : " + mVLMemoria.getBlocos_KernelReservados_Livre());
        System.out.println("\t\t\t  Ocupado : " + mVLMemoria.getBlocos_KernelReservados_Ocupado());

        System.out.println("\t\t - Blocos de Usuario : " + mVLMemoria.getBlocos_Usuarios());
        System.out.println("\t\t\t Livre : " + mVLMemoria.getBlocos_Usuarios_Livre());
        System.out.println("\t\t\t Ocupado : " + mVLMemoria.getBlocos_Usuarios_Ocupado());

        System.out.println("\t-----------------------------------------------------");


    }


    public void dump_recursos() {

        System.out.println("\t-----------------------------------------------------");
        System.out.println("\t - RECURSOS ");

        for (Recurso eRecurso : mVLRecursos.getRecursos()) {

            System.out.println("\t\t - RECURSO : " + eRecurso.getRID() + " ->> " + eRecurso.getStatus());

        }

        System.out.println("\t-----------------------------------------------------");

    }

}
