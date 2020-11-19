package VLOS.Memoria;

import Hardware.Memoria;
import VLOS.Utils.BlocoSlice;

import java.util.ArrayList;

public class VLMemoria {

    private Memoria mMemoria;
    private long mTamanhoBloco;

    private long mQuantidadeDeBlocos;
    private ArrayList<Bloco> mBlocos;

    private long KERNEL_OFFSET;
    private long USUARIO_OFFSET;
    private long KERNEL_TAMANHO;
    private long USUARIO_TAMANHO;


    // VLMEMORIA : CLASSE RESPONSAVEL POR GERENCIAR A MEMORIA
    //
    // DIVIDE A MEMORIA EM PAGINAS FISICAS : BLOCOS
    // RESERVA PAGINAS PARA KERNEL
    // ALOCA PAGINAS PARA PROCESSO KERNEL
    // ALOCA PAGINAS PARA PROCESSO USUARIO


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

        KERNEL_TAMANHO = 0;
        USUARIO_TAMANHO = 0;

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


        int eKernel_Offset = 0;
        int eKernel_Tamanho = 0;

        int eUsuario_Offset = 0;
        int eUsuario_Tamanho = 0;

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
                eBloco.setStatus(BlocoStatus.LIVRE);
            }

            for (int i = 0; i < mQuantidadeDeBlocos; i++) {

                Bloco eBloco = mBlocos.get(i);
                if (eBloco.getStatus() == BlocoStatus.LIVRE) {


                    if (eReservando >= eReservar) {

                        eUsuario_Tamanho += 1;

                    } else {
                        eUsuario_Offset += 1;
                        eKernel_Tamanho += 1;

                        eBloco.setStatus(BlocoStatus.RESERVADO_KERNEL);

                        eAlocando.add(eBloco);
                        eReservando += 1;


                    }
                }

            }

            // System.out.println("Reservando : " + eReservar + " -->> " + eReservando);

            if (eReservar == eReservando) {

            } else {
                throw new IllegalArgumentException("Nao existem blocos disponiveis !");
            }

        }


        KERNEL_OFFSET = eKernel_Offset;
        USUARIO_OFFSET = eUsuario_Offset;

        KERNEL_TAMANHO = eKernel_Tamanho;
        USUARIO_TAMANHO = eUsuario_Tamanho;

    }

    public MemoriaAlocada alocarBlocosDeKernel(long eTamanho) {

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
                } else if (eBloco.getStatus() == BlocoStatus.LIVRE) {

                    eBloco.setStatus(BlocoStatus.OCUPADO_KERNEL);

                    eAlocando.add(eBloco);
                    eReservando += 1;
                    if (eReservando >= eReservar) {
                        break;
                    }
                } else if (eBloco.getStatus() == BlocoStatus.OCUPADO_KERNEL) {
                    eOffset += 1;
                }

            }

            // System.out.println("Reservando : " + eReservar + " -->> " + eReservando);

            if (eReservar == eReservando) {

            } else {
                 throw new IllegalArgumentException("Nao existem blocos disponiveis !");
            }

        }

        return new MemoriaAlocada(mTamanho, eOffset, eAlocando);


    }


    public MemoriaAlocada alocarBlocosDeUsuario(long eTamanho) {

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

        return new MemoriaAlocada(mTamanho, eOffset, eAlocando);


    }

    public ArrayList<BlocoSlice> getOcupadosSlices() {
        ArrayList<BlocoSlice> mLista = new ArrayList<BlocoSlice>();

        long eOcupado = 0;
        boolean isOcupado = false;
        long mContando = 0;

        for (int i = 0; i < USUARIO_OFFSET; i++) {

            Bloco eBloco = mBlocos.get(i);

            if (eBloco.getStatus() == BlocoStatus.OCUPADO_KERNEL) {

                if (isOcupado) {
                    mContando += 1;
                } else {
                    mContando = 1;
                    isOcupado = true;
                    eOcupado = i;
                }

            } else if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                break;
            } else if (eBloco.getStatus() == BlocoStatus.RESERVADO_KERNEL) {

                if (isOcupado) {
                    mLista.add(new BlocoSlice(eOcupado, mContando));
                    isOcupado = false;
                    mContando = 0;
                }

            }

        }

        if (isOcupado) {
            mLista.add(new BlocoSlice(eOcupado, mContando));
            isOcupado = false;
            mContando = 0;
        }


        for (int i = (int) USUARIO_OFFSET; i < mQuantidadeDeBlocos; i++) {

            Bloco eBloco = mBlocos.get(i);

            if (eBloco.getStatus() == BlocoStatus.OCUPADO ) {

                if (isOcupado) {
                    mContando += 1;
                } else {
                    mContando = 1;
                    isOcupado = true;
                    eOcupado = i;
                }

            } else if (eBloco.getStatus() == BlocoStatus.LIVRE) {

                if (isOcupado) {
                    mLista.add(new BlocoSlice(eOcupado, mContando));
                    isOcupado = false;
                    mContando = 0;
                }

            }

        }

        if (isOcupado) {
            mLista.add(new BlocoSlice(eOcupado, mContando));
        }


        return mLista;
    }

    public long getOffsetKernel() {
        return KERNEL_OFFSET;
    }

    public long getTamanhoKernel() {
        return KERNEL_TAMANHO;
    }

    public long getOffsetUsuario() {
        return USUARIO_OFFSET;
    }

    public long getTamanhoUsuario() {
        return USUARIO_TAMANHO;
    }



}
