package AppVLOS;

import UI.Cena;
import UI.Renderizador;
import UI.Escritor;
import UI.Windows;
import VLOS.Arquivador.BlocoFS;
import VLOS.Arquivador.BlocoStatus;
import VLOS.Processo.Processo;
import VLOS.Processo.ProcessoStatus;
import VLOS.Utils.BlocoSlice;
import VLOS.VLOS;
import VLOS.Etapa;

import java.awt.*;
import java.util.ArrayList;


public class CenaVLOS extends Cena {

    private VLOS mVLOS;

    private int mLargura;
    private int mAltura;

    private Escritor TextoPequeno;
    private Escritor TextoSubPequeno;

    private int mVLS_BLOCOS_LINHA;

    public CenaVLOS(VLOS eATLOS, int eLargura, int eAltura) {

        mLargura = eLargura;
        mAltura = eAltura;

        TextoPequeno = new Escritor(15, Color.BLACK);
        TextoSubPequeno = new Escritor(12, Color.BLACK);

        mVLOS = eATLOS;
        mVLS_BLOCOS_LINHA = 5;


    }


    @Override
    public void iniciar(Windows eWindows) {

        mVLOS.ligarApenas();

        if (!mVLOS.estaLigado()) {
            eWindows.encerrar();
        }

    }

    @Override
    public void update(double dt) {

        if (mVLOS.estaLigado()) {
            mVLOS.executarCiclo();
        }

    }

    public void drawCPU(Renderizador mRenderizador) {

        int xCPU = 350;
        int yCPU = 50;


        mRenderizador.drawQuad(xCPU, yCPU, 100, 200, colorHexadecimal("#90a4ae"));

        String eCPUEstado = "OCIOSA";
        if (mVLOS.getCPU().estaExecutando()) {
            eCPUEstado = "EXECUTANDO";

            mRenderizador.drawQuad(xCPU + 10, yCPU + 10, 80, 180, colorHexadecimal("#f9a825"));

        }

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> CPU : " + eCPUEstado, 500, 200);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Tempo : " + mVLOS.getTempo() + "s", 500, 250);


    }

    public void drawProcessos(Renderizador mRenderizador) {

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS KERNEL : ", 80, 300);
        colocarProcessos(mRenderizador, mVLOS.getProcessosKernel(), 120, 320);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 1 : ", 80, 400);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila1(), 120, 420);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 2 : ", 80, 500);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila2(), 120, 520);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 3 : ", 80, 600);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila3(), 120, 620);


    }

    private void drawMemoria(Renderizador mRenderizador) {


        int xMem = 100;
        int yMem = 800;


        int memoriaLargura = 600;
        double taxa = (double) memoriaLargura / 100.0F;

        ///   System.out.println("TAXA : " + taxa);

        mRenderizador.drawQuad(xMem, yMem, memoriaLargura, 100, colorHexadecimal("#90a4ae"));


        BlocoSlice eKernel = mVLOS.getAreas().get(0);

        double eBlocosTotal = (double) mVLOS.getBlocos();


        // System.out.println("Blocos T : " + eBlocosTotal);
        //  System.out.println("Blocos K : " + eKernel.getTamanho());


        double eKernelPor = ((double) eKernel.getTamanho() / eBlocosTotal) * 100.0F;

        // System.out.println("Kernel Por : " + eKernelPor);
        //  System.out.println("IMG Largura : " + memoriaLargura);
        //  System.out.println("IMG Taxa : " + taxa);

        double eKernelPorIMG = eKernelPor * taxa;

        // System.out.println("Kernel img : " + eKernelPorIMG);

        // System.out.println("Memoria Blocos : " + mVLOS.getBlocos());
        //  System.out.println("Memoria Kernel Blocos : " + mVLOS.getKernelBlocos());
        // System.out.println("Memoria Usuario Blocos : " + mVLOS.getUsuarioBlocos());

        mRenderizador.drawQuad(xMem, yMem, (int) eKernelPorIMG, 100, colorHexadecimal("#c6a700"));

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), "KERNEL", xMem, yMem - 30);
        mRenderizador.drawQuad(xMem, yMem - 20, (int) eKernelPorIMG, 10, colorHexadecimal("#c6a700"));

        mRenderizador.drawQuad(xMem + (int) eKernelPorIMG, yMem + 110, (memoriaLargura - (int) eKernelPorIMG), 10, colorHexadecimal("#90a4ae"));
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), "USUARIO", xMem + (int) eKernelPorIMG, yMem + 140);


        for (BlocoSlice bs : mVLOS.getOcupados()) {
            //   System.out.println("BS : " + bs.getInicio() + " com " + bs.getTamanho());
            double eFatia = ((double) bs.getInicio() / eBlocosTotal) * 100.0F;
            double eFatiaIMG = eFatia * taxa;

            double eFatiaTam = ((double) bs.getTamanho() / eBlocosTotal) * 100.0F;
            double eFatiaTamIMG = eFatiaTam * taxa;

            if (bs.getInicio() < mVLOS.getUsuarioOffste()) {
                mRenderizador.drawQuad(xMem + (int) eFatiaIMG, yMem, (int) eFatiaTamIMG, 100, colorHexadecimal("#f44336"));
            } else {
                mRenderizador.drawQuad(xMem + (int) eFatiaIMG, yMem, (int) eFatiaTamIMG, 100, colorHexadecimal("#f44336"));
            }

        }

    }

    private void drawSistemaDeArquivos(Renderizador mRenderizador) {

        int ePosicaoInicialX = 450;
        int ePosicaoInicialY = 350;

        int eTamanhoFaixaFS = 155;

        int eFaixa_Altura = ePosicaoInicialY + 20;
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), "MartinsFS - SATA 1", ePosicaoInicialX, ePosicaoInicialY);
        mRenderizador.drawQuad(ePosicaoInicialX, eFaixa_Altura, eTamanhoFaixaFS, 10, colorHexadecimal("#66bb6a"));

        ePosicaoInicialY += 70;

        int adicionandoNaLinha = 0;

        int mPosicaoX = ePosicaoInicialX;
        int mPosicaoY = ePosicaoInicialY;

        int eTotal = 0;
        int eUsados = 0;

        for (BlocoFS eBloco : mVLOS.getVLVFS().get(0).getBlocos()) {

            if (eBloco.getStatus() == BlocoStatus.LIVRE) {
                mRenderizador.drawQuad(mPosicaoX, mPosicaoY, 25, 25, colorHexadecimal("#66bb6a"));
            } else {
                mRenderizador.drawQuad(mPosicaoX, mPosicaoY, 25, 25, colorHexadecimal("#f44336"));
                eUsados += 1;
            }

            TextoSubPequeno.EscreveNegrito(mRenderizador.getG(), String.valueOf(eBloco.getBlocoID()), mPosicaoX + 6, mPosicaoY + 20);

            mPosicaoX += 50;
            adicionandoNaLinha += 1;

            if (adicionandoNaLinha > mVLS_BLOCOS_LINHA) {
                adicionandoNaLinha = 0;
                mPosicaoY += 50;
                mPosicaoX = ePosicaoInicialX;
            }

            eTotal += 1;
        }


        double taxaFS = (double) eTamanhoFaixaFS / 100.0F;
        double eBlocosFS = (double) eTotal;

        double eUsadoPorcentagem = ((double) eUsados / eBlocosFS) * 100.0F;
        double eUsadoTamanhoIMG = eUsadoPorcentagem * taxaFS;


        mRenderizador.drawQuad(ePosicaoInicialX, eFaixa_Altura, (int) eUsadoTamanhoIMG, 10, colorHexadecimal("#f44336"));


    }

    @Override
    public void draw(Graphics g) {

        Renderizador mRenderizador = new Renderizador(g, mLargura, mAltura);

        mRenderizador.limpar(Color.WHITE);

        if (!mVLOS.estaLigado()) {
            return;
        }


        if (mVLOS.getEtapa() == Etapa.DETECTANDO_HARDWARE) {

            mRenderizador.limpar(Color.RED);

        } else if (mVLOS.getEtapa() == Etapa.EXECUTANDO) {

            drawCPU(mRenderizador);

            drawProcessos(mRenderizador);

            drawMemoria(mRenderizador);

            drawSistemaDeArquivos(mRenderizador);

        }

    }


    public void colocarProcessos(Renderizador mRenderizador, ArrayList<Processo> eProcessos, int eX, int eY) {

        int eContador = 0;
        int eMaximoProcessosLinha = 5;

        int oX = eX;

        for (Processo eProcesso : eProcessos) {

            mRenderizador.drawQuad(eX, eY, 25, 25, getProcessoCor(eProcesso));
            TextoSubPequeno.EscreveNegrito(mRenderizador.getG(), String.valueOf(eProcesso.getPID()), eX + 6, eY + 20);

            eX += 50;
            eContador += 1;

            if (eContador >= eMaximoProcessosLinha) {
                eContador = 0;
                eX = oX;
                eY += 40;
            }
        }

    }

    public Color getProcessoCor(Processo eProcesso) {

        Color eCor = Color.BLACK;

        if (eProcesso.getStatus() == ProcessoStatus.PRONTO) {
            eCor = colorHexadecimal("#64b5f6");
        } else if (eProcesso.getStatus() == ProcessoStatus.ESPERANDO) {
            eCor = colorHexadecimal("#dce775");
        } else if (eProcesso.getStatus() == ProcessoStatus.EXECUTANDO) {
            eCor = colorHexadecimal("#66bb6a");
        } else if (eProcesso.getStatus() == ProcessoStatus.CONCLUIDO) {
            eCor = colorHexadecimal("#3949ab");
        }


        if (mVLOS.temEscalonado()) {
            if (mVLOS.getEscalonado().getPID() == eProcesso.getPID()) {
                eCor = colorHexadecimal("#66bb6a");
            }
        }

        return eCor;

    }

    public static Color colorHexadecimal(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }


}
