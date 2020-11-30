package VLOS.UI;

import UI.Cena;
import UI.Renderizador;
import UI.Escritor;
import UI.Windows;
import VLOS.Arquivador.BlocoFS;
import VLOS.Arquivador.BlocoStatus;
import VLOS.Processo.Processo;
import VLOS.Processo.ProcessoStatus;
import VLOS.Recurso.ControladorRecurso;
import VLOS.Utils.BlocoSlice;
import VLOS.VLOS;
import VLOS.Etapa;

import java.awt.*;
import java.util.ArrayList;


public class CenaVLOS extends Cena {

    private VLOS mVLOS;

    private Escritor TextoPequeno;
    private Escritor TextoSubPequeno;


    public CenaVLOS(VLOS eATLOS, String eNome, int eLargura, int eAltura) {

        this.setConfigurar(eNome, eLargura, eAltura);

        TextoPequeno = new Escritor(15, Color.BLACK);
        TextoSubPequeno = new Escritor(12, Color.BLACK);

        mVLOS = eATLOS;

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


    @Override
    public void draw(Graphics g) {

        Renderizador mRenderizador = new Renderizador(g, this.getLargura(), this.getAltura());

        mRenderizador.limpar(Color.WHITE);

        if (!mVLOS.estaLigado()) {
            return;
        }

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Luan Freitas          17/0003191", 460, 70);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Vinicius Martins     17/0157962", 460, 100);

        if (mVLOS.getEtapa() == Etapa.DETECTANDO_HARDWARE) {

            mRenderizador.limpar(Color.RED);

        } else if (mVLOS.getEtapa() == Etapa.EXECUTANDO) {


            drawCPU(mRenderizador);

            drawRecursos(mRenderizador);

            drawProcessos(mRenderizador);

            drawSistemaDeArquivos(mRenderizador);

            drawMemoria(mRenderizador);

        }

    }

    public void drawRecursos(Renderizador mRenderizador) {

        int xRec = 150;
        int yRec = 70;

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), "RECURSOS", xRec, yRec);
        mRenderizador.drawQuad(xRec - 15, yRec + 6, 120, 2, Color.BLACK);

        yRec += 20;

        int eColuna = 0;
        int maxPorColuna = 5;

        int oxRec = xRec;
        int oyRec = yRec;

        for (ControladorRecurso eRecurso : mVLOS.getVLRecursos().getControladores()) {


            TextoPequeno.EscreveNegrito(mRenderizador.getG(), eRecurso.getRecurso().getNome(), xRec - 70, yRec + 20);

            Color eRecursoCor = colorHexadecimal("#8bc34a");
            if (eRecurso.isBloqueado()) {
                eRecursoCor = colorHexadecimal("#e53935");
            }

            mRenderizador.drawQuad(xRec - 100, yRec + 3, 20, 20, eRecursoCor);

            mRenderizador.drawQuad((xRec - 100)+7, (yRec + 3)+7, 5, 5, colorHexadecimal("#f5f5f5"));


            yRec += 30;
            eColuna += 1;
            if (eColuna >= maxPorColuna) {
                eColuna = 0;
                yRec = oyRec;
                xRec += 150;
            }

        }


    }

    public void drawCPU(Renderizador mRenderizador) {

        int xCPU = 350;
        int yCPU = 50;


        mRenderizador.drawQuad(xCPU, yCPU, 100, 250, colorHexadecimal("#90a4ae"));

        String eCPUEstado = "OCIOSA";

        if (mVLOS.getCPU().estaExecutando()) {

            eCPUEstado = "EXECUTANDO";

            mRenderizador.drawQuad(xCPU + 10, yCPU + 10, 80, 200, colorHexadecimal("#f9a825"));

            mRenderizador.drawQuad(xCPU + 10, yCPU + 220, 80, 20, colorHexadecimal("#f9a825"));

        }


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> CPU : " + eCPUEstado, 460, 160);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Tempo : " + mVLOS.getTempo() + "s", 460, 190);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Trocas de Contextos : " + mVLOS.getContextos(), 460, 220);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Memoria : " + mVLOS.getMemoriaStatus(), 460, 250);
        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> Recursos : " + mVLOS.getRecursosStatus(), 460, 280);

    }

    public void drawProcessos(Renderizador mRenderizador) {

        int xProc = 150;
        int yProc = 280;

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), "ESCALONADOR", xProc, yProc);
        mRenderizador.drawQuad(xProc - 15, yProc + 6, 140, 2, Color.BLACK);

        int mEspacoAltura = 110;

        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS KERNEL : ", 50, yProc + 50);
        colocarProcessos(mRenderizador, mVLOS.getProcessosKernel(), 120, yProc + 50 + 20);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 1 : ", 50, yProc + 50 + mEspacoAltura);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila1(), 120, yProc + 50 + mEspacoAltura + 20);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 2 : ", 50, yProc + 50 + (2 * mEspacoAltura) + 10);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila2(), 120, yProc + 50 + (2 * mEspacoAltura) + 30);


        TextoPequeno.EscreveNegrito(mRenderizador.getG(), " -->> PROCESSOS FILA 3 : ", 50, yProc + 50 + (3 * mEspacoAltura) + 10);
        colocarProcessos(mRenderizador, mVLOS.getProcessosUsuario_Fila3(), 120, yProc + 50 + (3 * mEspacoAltura) + 30);


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

        int mVLS_BLOCOS_LINHA = 5;


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
