package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

public class Windows extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private volatile boolean mExecutando;

    private int mLargura;
    private int mAltura;

    private Cena mCena;

    private Image mImagem;
    private Graphics2D mGraficos;

    public Windows(String eTitulo, int eLargura, int eAltura, Cena eCena) {

        mLargura = eLargura;
        mAltura = eAltura;

        this.setSize(eLargura, eAltura);
        this.setTitle(eTitulo);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        mExecutando = false;

        mCena = eCena;
        mCena.iniciar(this);

    }


    public void encerrar() {
        mExecutando = false;
    }


    @Override
    public void run() {

        mExecutando = true;

        // GAME LOOP FREITAS - 2020 10 28

        final int GAME_HERTIZ = 30;
        final double GAME_QUADRO = 1000000000 / (double) GAME_HERTIZ;


        int mAtualizador = 0;
        int mDesenhador = 0;
        int mDilatador = 0;
        int mVerificador = 0;
        int mAcumulador = 0;

        double mPassado = System.nanoTime();
        double mFuturo = mPassado + GAME_QUADRO;

        double mDilatacao = 0.0;
        double mDilatando = 0.0;
        double mDilatadorMin = mPassado;
        double mDilatadorMax = mPassado + ((double) (GAME_HERTIZ) * GAME_QUADRO);

        while (mExecutando) {


            double mAgora = System.nanoTime();

            if (mAgora >= mFuturo) {
                mAtualizador += 1;
                //System.out.println("\t - Desenhando " + mDesenhador);
                mDesenhador += 1;
                mFuturo = (mAgora - (mAgora - mFuturo)) + GAME_QUADRO;

                mCena.update(mAgora - mPassado);
                draw(getGraphics());

            } else {

            }

            if (mAgora >= mDilatadorMax) {

                mDilatacao = mAgora - mDilatadorMin;
                mDilatando = mDilatacao;
                mVerificador = 0;

                while (mDilatando > GAME_QUADRO) {
                    mDilatando -= GAME_QUADRO;
                    mVerificador += 1;
                }

                //System.out.println("GAME LOOP : " + (mDilatacao) + " << " + mAtualizador + "," + mVerificador + ","+ mDilatador + " >>  :: " + mDesenhador);
                mDilatadorMin = mAgora;
                mDilatadorMax = mAgora + ((double) (GAME_HERTIZ) * GAME_QUADRO);

                mAcumulador += mDilatador;

                if (mAcumulador >= GAME_HERTIZ) {
                    //System.out.println("\t - ACUMULADO : " + (mAcumulador) + " - Desacumulando " + GAME_HERTIZ);
                    mAcumulador -= GAME_HERTIZ;
                    mCena.update(mAgora - mPassado);
                    draw(getGraphics());
                }

                mDilatador = 0;
                mAtualizador = 0;
                mDesenhador = 0;
            } else {

                if (mAtualizador >= GAME_HERTIZ) {
                    mDilatador += 1;
                }

            }


            try {
                Thread.yield();
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println("GAME LOOP - PROBLEMA !");
            }

            mPassado = System.nanoTime();

        }

        mExecutando = false;
    }

    public int getLargura() {
        return mLargura;
    }

    public int getAltura() {
        return mAltura;
    }


    public void draw(Graphics g) {

        if (mImagem == null) {
            mImagem = createImage(this.getLargura(), this.getAltura());
            mGraficos = (Graphics2D) mImagem.getGraphics();
        }

        mCena.draw(mGraficos);
        g.drawImage(mImagem, 0, 7, getLargura(), getAltura(), null);

    }



}
