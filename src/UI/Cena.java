package UI;

import java.awt.Graphics;

public abstract class Cena {

    private String mNome;
    private int mLargura;
    private int mAltura;


    public void setNome(String eNome) {
        this.mNome = eNome;
    }

    public void setConfigurar(String eNome, int eLargura, int eAltura) {
        this.mNome = eNome;
        this.mLargura = eLargura;
        this.mAltura = eAltura;
    }

    // Propriedades Importantes

    public String getNome() {
        return mNome;
    }

    public int getLargura() {
        return mLargura;
    }

    public int getAltura() {
        return mAltura;
    }


    // Metodos Importantes

    public abstract void iniciar(Windows eWindows);

    public abstract void update(double dt);

    public abstract void draw(Graphics g);

}
