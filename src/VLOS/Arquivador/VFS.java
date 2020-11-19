package VLOS.Arquivador;

import Hardware.SATA;
import VLOS.Despachante.DespachanteArquivo;

import java.util.ArrayList;

public class VFS {

    private SATA mSATA;
    private int mNumeroDeBlocos;
    private String mTipo;

    private MartinsFS mMartinsFS;

    public VFS(SATA eSATA) {
        mSATA = eSATA;
        mNumeroDeBlocos = 0;
        mTipo = "NULL";
        mMartinsFS = null;
    }

    public VFS(SATA eSATA, int eNumeroDeBlocos, ArrayList<DespachanteArquivo> mDespachanteArquivo) {
        mSATA = eSATA;
        mNumeroDeBlocos = eNumeroDeBlocos;
        mTipo = "MartinsFS";
        mMartinsFS = new MartinsFS(eNumeroDeBlocos, mDespachanteArquivo);
    }


    public ArrayList<BlocoFS> getBlocos() {

        if (mTipo.contentEquals("MartinsFS")) {
            return mMartinsFS.getBlocos();
        } else {
            throw new IllegalArgumentException("Sistema de Arquivos Desconhecido : " + mTipo);
        }

    }

    public int criarArquivo(String eNome, int ePrecisaDeQuantosBlocos) {

        if (mTipo.contentEquals("MartinsFS")) {
            return mMartinsFS.criarArquivo(eNome, ePrecisaDeQuantosBlocos);
        } else {
            throw new IllegalArgumentException("Sistema de Arquivos Desconhecido : " + mTipo);
        }

    }

    public int removerArquivo(String eNome) {

        if (mTipo.contentEquals("MartinsFS")) {
            return mMartinsFS.removerArquivo(eNome);
        } else {
            throw new IllegalArgumentException("Sistema de Arquivos Desconhecido : " + mTipo);
        }

    }


    public boolean existeArquivo(String eNome) {


        if (mTipo.contentEquals("MartinsFS")) {


            return false;

        } else {
            throw new IllegalArgumentException("Sistema de Arquivos Desonhecido : " + mTipo);
        }

    }


}
