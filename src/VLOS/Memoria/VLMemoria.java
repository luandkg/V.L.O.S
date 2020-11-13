package VLOS.Memoria;

import Hardware.Memoria;

import java.util.ArrayList;

public class VLMemoria {

    private Memoria mMemoria;
    private long mTamanhoBloco;

    private long mQuantidadeDeBlocos;
    private ArrayList<Bloco> mBlocos;

    private long KERNEL_OFFSET;
    private long USUARIO_OFFSET;

    public VLMemoria(Memoria eMemoria, long eTamanhoDoBloco) {

        mMemoria = eMemoria;
        mTamanhoBloco = eTamanhoDoBloco;
        mQuantidadeDeBlocos = mMemoria.getTamanho() / mTamanhoBloco;

        mBlocos = new ArrayList<Bloco>();

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            mBlocos.add(new Bloco(this, i, BlocoStatus.LIVRE));
        }

        KERNEL_OFFSET = 0;
        USUARIO_OFFSET = 0;

    }


    public long getTamanho() {
        return mMemoria.getTamanho();
    }

    public long getTamanhoBloco() {
        return mTamanhoBloco;
    }

    public long getBlocos() {
        return mQuantidadeDeBlocos;
    }

    public long getBlocos_KernelReservados() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.RESERVADO_KERNEL) {
                eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO_KERNEL) {
                eContagem += 1;
            }
        }

        return eContagem;
    }

    public long getBlocos_KernelReservados_Livre() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.RESERVADO_KERNEL) {
                eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO_KERNEL) {
                //  eContagem += 1;
            }
        }

        return eContagem;
    }


    public long getBlocos_KernelReservados_Ocupado() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.RESERVADO_KERNEL) {
                // eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO_KERNEL) {
                eContagem += 1;
            }
        }

        return eContagem;
    }


    public long getBlocos_Usuarios() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO) {
                eContagem += 1;
            }
        }

        return eContagem;
    }

    public long getBlocos_Usuarios_Livre() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO) {
                //eContagem += 1;
            }
        }

        return eContagem;
    }

    public long getBlocos_Usuarios_Ocupado() {

        long eContagem = 0;

        for (int i = 0; i < mQuantidadeDeBlocos; i++) {
            Bloco eBloco = mBlocos.get(i);
            if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                // eContagem += 1;
            } else if (eBloco.getStatus() == BlocoStatus.OCUPADO) {
                eContagem += 1;
            }
        }

        return eContagem;
    }

    public void reservarKernel(long eTamanho) {

        ArrayList<Bloco> eAlocando = new ArrayList<Bloco>();


        if (eTamanho > 0) {

            int eReservar = 1;
            eTamanho -= mTamanhoBloco;

            while (eTamanho >= mTamanhoBloco) {
                eReservar += 1;
                eTamanho -= mTamanhoBloco;
            }


            long eReservando = 0;

            for (int i = 0; i < mQuantidadeDeBlocos; i++) {

                Bloco eBloco = mBlocos.get(i);
                if (eBloco.getStatus() == BlocoStatus.LIVRE) {

                    eBloco.setStatus(BlocoStatus.RESERVADO_KERNEL);

                    eAlocando.add(eBloco);
                    eReservando += 1;
                    if (eReservando >= eReservar) {
                        break;
                    }
                }

            }

            // System.out.println("Reservando : " + eReservar + " -->> " + eReservando);

            if (eReservar == eReservando) {

            } else {
                throw new IllegalArgumentException("Nao existem blocos disponiveis !");
            }

        }


    }

    public Segmento alocarSegmentoDeKernel(long eTamanho) {

        ArrayList<Bloco> eAlocando = new ArrayList<Bloco>();

        long mTamanho = eTamanho;
        long eOffset = KERNEL_OFFSET;

        if (eTamanho > 0) {

            int eReservar = 1;
            eTamanho -= mTamanhoBloco;

            while (eTamanho >= mTamanhoBloco) {
                eReservar += 1;
                eTamanho -= mTamanhoBloco;
            }


            long eReservando = 0;

            for (int i = 0; i < mQuantidadeDeBlocos; i++) {


                Bloco eBloco = mBlocos.get(i);
                if (eBloco.getStatus() == BlocoStatus.RESERVADO_KERNEL) {

                    eBloco.setStatus(BlocoStatus.OCUPADO_KERNEL);

                    eAlocando.add(eBloco);
                    eReservando += 1;
                    if (eReservando >= eReservar) {
                        break;
                    }

                    eOffset += 1;
                }

            }

            // System.out.println("Reservando : " + eReservar + " -->> " + eReservando);

            if (eReservar == eReservando) {

            } else {
                throw new IllegalArgumentException("Nao existem blocos disponiveis !");
            }

        }

        return new Segmento(mTamanho, eOffset, eAlocando);


    }


    public Segmento alocarSegmentoDeUsuario(long eTamanho) {

        ArrayList<Bloco> eAlocando = new ArrayList<Bloco>();

        long mTamanho = eTamanho;
        long eOffset = USUARIO_OFFSET;

        if (eTamanho > 0) {

            int eReservar = 1;
            eTamanho -= mTamanhoBloco;

            while (eTamanho >= mTamanhoBloco) {
                eReservar += 1;
                eTamanho -= mTamanhoBloco;
            }


            long eReservando = 0;

            for (int i = 0; i < mQuantidadeDeBlocos; i++) {

                Bloco eBloco = mBlocos.get(i);
                if (eBloco.getStatus() == BlocoStatus.LIVRE) {

                    eBloco.setStatus(BlocoStatus.OCUPADO);

                    eAlocando.add(eBloco);
                    eReservando += 1;
                    if (eReservando >= eReservar) {
                        break;
                    }
                } else if (eBloco.getStatus() == BlocoStatus.OCUPADO) {
                    eOffset += 1;
                }

            }

            // System.out.println("Reservando : " + eReservar + " -->> " + eReservando);

            if (eReservar == eReservando) {

            } else {
                throw new IllegalArgumentException("Nao existem blocos disponiveis !");
            }

        }

        return new Segmento(mTamanho, eOffset, eAlocando);


    }

    public void definirOffsets(int eKernel, int eUsuario) {
        KERNEL_OFFSET = eKernel;
        USUARIO_OFFSET = eUsuario;
    }

}
