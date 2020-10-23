package VLOS;

import Hardware.*;

public class VLOS {

    private Maquina mMaquina;

    public VLOS(Maquina eMaquina) {
        mMaquina = eMaquina;
    }

    public void ligar() {

        Utils mUtils = new Utils();

        System.out.println("\t DETECCAO DE HARDWARE");

        System.out.println("\t\t Processador : " + mMaquina.getProcessador().getProcessador() + " -> " + mUtils.texto_nucleo(mMaquina.getProcessador().getNucleos()));

        for (Dispositivo mDispositivo : mMaquina.getDispositivos()) {

            if (mDispositivo instanceof Memoria) {
                Memoria mMemoria = (Memoria) mDispositivo;
                System.out.println("\t\t Memoria : " + mMemoria.getModelo() + " -> " + mUtils.texto_tamanho(mMemoria.getTamanho()));
            } else if (mDispositivo instanceof SATA) {
                SATA mSATA = (SATA) mDispositivo;
                System.out.println("\t\t HD : " + mSATA.getModelo() + " -> " + mUtils.texto_tamanho(mSATA.getTamanho()));
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


    }


}
