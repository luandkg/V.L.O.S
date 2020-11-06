package VLOS;

import Hardware.*;

public class VLOS {

    private Maquina mMaquina;

    private final long MEMORIA_BLOCO = 1024 * 1024;

    private  VLMemoria mVLMemoria;

    public VLOS(Maquina eMaquina) {
        mMaquina = eMaquina;
    }

    public void ligar() {

        Utils mUtils = new Utils();

        System.out.println("\t DETECCAO DE HARDWARE");

        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));


        boolean sistemaOK = false;

        boolean mTemMemoria = false;
        boolean mTemHD = false;


         mVLMemoria = null;

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

            } else if (mDispositivo instanceof Scanner) {

                Scanner mScanner = (Scanner) mDispositivo;
                System.out.println("\t\t Scanner : " + mScanner.getModelo());

            } else if (mDispositivo instanceof Modem) {

                Modem mModem = (Modem) mDispositivo;
                System.out.println("\t\t Modem : " + mModem.getModelo());

            }

        }


        if (mTemMemoria) {
            if (mTemHD) {
                sistemaOK = true;
            }
        }


        if (sistemaOK) {

            System.out.println("");
            System.out.println("\t\t                         VLOS                        ");


            dump_memoria();

            System.out.println("\t\t -->> Reservando 64 Blocos para KERNEL");
            mVLMemoria.reservarKernel(64 * MEMORIA_BLOCO);

            dump_memoria();


        } else {

            System.out.println("");

            if (!mTemMemoria) {
                System.out.println("\t\t VLOS : Nao pode iniciar -->> NAO TEM MEMORIA !");
            }

            if (!mTemHD) {
                System.out.println("\t\t VLOS : Nao pode iniciar -->> NAO TEM HD !");
            }

        }

    }


    public void dump_memoria(){

        System.out.println("\t\t-----------------------------------------------------");
        System.out.println("\t\tMEMORIA");
        System.out.println("\t\t\t - Disponivel : " + mVLMemoria.getTamanho());
        System.out.println("\t\t\t - Bloco Tamanho : " + mVLMemoria.getTamanhoBloco() + " bytes :: " + (mVLMemoria.getTamanhoBloco() / 1024) + " kb");
        System.out.println("\t\t\t - Blocos : " + mVLMemoria.getBlocos());

        System.out.println("\t\t\t - KERNEL Reservado : " + mVLMemoria.getBlocos_KernelReservados());
        System.out.println("\t\t\t\t  Livre : " + mVLMemoria.getBlocos_KernelReservados_Livre());
        System.out.println("\t\t\t\t  Ocupado : " + mVLMemoria.getBlocos_KernelReservados_Ocupado());

        System.out.println("\t\t\t - Blocos de Usuario : " + mVLMemoria.getBlocos_Usuarios());
        System.out.println("\t\t\t\t Livre : " + mVLMemoria.getBlocos_Usuarios_Livre());
        System.out.println("\t\t\t\t Ocupado : " + mVLMemoria.getBlocos_Usuarios_Ocupado());

        System.out.println("\t\t-----------------------------------------------------");



    }


}
