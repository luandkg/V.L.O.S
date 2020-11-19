package VLOS.Arquivador;

import java.util.ArrayList;

public class Arquivo {

    private String mNome;
    private BlocoFS mBlocoInicial;
    private ArrayList<BlocoFS> mBlocos;

    public Arquivo(String eNome, BlocoFS eBlocoInicial, ArrayList<BlocoFS> eBlocos) {
        mNome = eNome;
        mBlocoInicial = eBlocoInicial;
        mBlocos = eBlocos;
    }

    public String getNome() {
        return mNome;
    }


    public void remover() {

        mBlocoInicial.setStatus(BlocoStatus.LIVRE);
        for (BlocoFS eBloco : mBlocos) {
            eBloco.setStatus(BlocoStatus.LIVRE);
        }

        mBlocoInicial = null;
        mBlocos.clear();

    }

}
