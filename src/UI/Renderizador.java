package UI;

import java.awt.*;

public class Renderizador {

    private Graphics g;
    private int mLargura;
    private int mAltura;

    public Renderizador(Graphics eGraphics, int eLargura, int eAltura) {

        g = eGraphics;
        mLargura = eLargura;
        mAltura = eAltura;

    }

    public void limpar(Color eCor) {

        g.setColor(eCor);
        g.fillRect(0, 0, mLargura, mAltura);

    }

    public Graphics getG() {
        return g;
    }

    public void drawQuad(int eX, int eY, int eLargura, int eAltura, Color eCor) {

        g.setColor(eCor);
        g.fillRect(eX, eY, eLargura, eAltura);

    }


}
