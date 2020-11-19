package VLOS.Arquivador;

import Hardware.SATA;
import VLOS.Despachante.DespachanteArquivo;

import java.util.ArrayList;

public class VLVFS {

    // GERENCIADOR DE SISTEMAS DE ARQUIVOS VIRTUAIS

    private ArrayList<VFS> mVFSS;
    private VFS mPrimario;
    private boolean mTemPrimario;

    public VLVFS() {
        mVFSS = new ArrayList<VFS>();
        mTemPrimario = false;
    }

    public void montar(SATA eSATA) {

        VFS eVFS = new VFS(eSATA);

        if (!mTemPrimario) {
            mTemPrimario = true;
            mPrimario = eVFS;
        }

        mVFSS.add(eVFS);
    }


    public VFS get(int n) {
        return mVFSS.get(n);
    }

    public void montarCom(SATA eSATA, int eNumeroDeBlocos, ArrayList<DespachanteArquivo> mDespachanteArquivo) {

        VFS eVFS = new VFS(eSATA, eNumeroDeBlocos, mDespachanteArquivo);

        if (!mTemPrimario) {
            mTemPrimario = true;
            mPrimario = eVFS;
        }

        mVFSS.add(eVFS);
    }


    public int criarArquivo(String eNome, int ePrecisaDeQuantosBlocos) {

        if (mTemPrimario) {
            return mPrimario.criarArquivo(eNome, ePrecisaDeQuantosBlocos);
        } else {
            throw new IllegalArgumentException("Nao existe disco primario !");
        }

    }


    public int removerArquivo(String eNome) {

        if (mTemPrimario) {
            return mPrimario.removerArquivo(eNome);
        } else {
            throw new IllegalArgumentException("Nao existe disco primario !");
        }

    }

    public boolean existeArquivo(String eNome) {

        if (mTemPrimario) {

            return mPrimario.existeArquivo(eNome);

        } else {
            throw new IllegalArgumentException("Nao existe disco primario !");
        }

    }


}
