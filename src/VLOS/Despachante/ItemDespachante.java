package VLOS.Despachante;

import VLOS.Utils;

public class ItemDespachante {

    private int mInicializacao;
    private int mPrioridade;
    private int mTempoProcessador;
    private int mBlocos;
    private int mCodigoImpressora;
    private int mCodigoScanner;
    private int mCodigoModem;
    private int mCodigoDisco;

    private boolean misDespachado;

    public ItemDespachante() {
        misDespachado = false;
    }

    public void setInicializacao(int eInicializacao) {
        mInicializacao = eInicializacao;
    }

    public void setPrioridade(int ePrioridade) {
        mPrioridade = ePrioridade;
    }

    public void setTempoProcessador(int eTempoProcessador) {
        mTempoProcessador = eTempoProcessador;
    }

    public void setBlocos(int eBlocos) {
        mBlocos = eBlocos;
    }

    public void setCodigoImpressora(int eCodigoImpressora) {
        mCodigoImpressora = eCodigoImpressora;
    }

    public void setCodigoScanner(int eCodigoScanner) {
        mCodigoScanner = eCodigoScanner;
    }

    public void setCodigoModem(int eCodigoModem) {
        mCodigoModem = eCodigoModem;
    }

    public void setCodigoDisco(int eCodigoDisco) {
        mCodigoDisco = eCodigoDisco;
    }

    public int getInicializacao() {
        return mInicializacao;
    }

    public int getPrioridade() {
        return mPrioridade;
    }

    public int getTempoProcessador() {
        return mTempoProcessador;
    }

    public int getBlocos() {
        return mBlocos;
    }

    public int getCodigoImpressora() {
        return mCodigoImpressora;
    }

    public int getCodigoScanner() {
        return mCodigoScanner;
    }

    public int getCodigoModem() {
        return mCodigoModem;
    }

    public int getCodigoDisco() {
        return mCodigoDisco;
    }


    public void despachar() {

        misDespachado = true;

        //   System.out.println("\t -->> DESPACHAR");

        //  System.out.println("\t\t - Inicializacao  : " + this.getInicializacao());
        //  System.out.println("\t\t - Prioridade     : " + this.getPrioridade());
        //   System.out.println("\t\t - Tempo          : " + this.getTempoProcessador());
        //  System.out.println("\t\t - Blocos         : " + this.getBlocos());
        //  System.out.println("\t\t - Impressora     : " + this.getCodigoImpressora());
        //   System.out.println("\t\t - Scanner        : " + this.getCodigoScanner());
        //   System.out.println("\t\t - Modem          : " + this.getCodigoModem());
        //   System.out.println("\t\t - Disco          : " + this.getCodigoDisco());

        Utils mUtils = new Utils();

        String mP0 = " - Inicializacao  : " + mUtils.getIntCasas(this.getInicializacao(), 2);
        String mP1 = " | Prioridade : " + mUtils.getIntCasas(this.getPrioridade(), 2);
        String mP2 = " | Tempo Processamento : " + mUtils.getIntCasas(this.getTempoProcessador(), 2);
        String mP3 = " | Blocos Alocados : " + mUtils.getLongCasas(this.getBlocos(), 8);
        String mP4 = " | Impressora : " + mUtils.getIntCasas(this.getCodigoImpressora(), 2);
        String mP5 = " | Scanner : " + mUtils.getIntCasas(this.getCodigoScanner(), 2);
        String mP6 = " | Modem : " + mUtils.getIntCasas(this.getCodigoModem(), 2);
        String mP7 = " | Disco : " + mUtils.getIntCasas(this.getCodigoDisco(), 2);

        System.out.println("\t -->> DESPACHAR " + mP0 + mP1 + mP2 + mP3 + mP4 + mP5 + mP6 + mP7);


    }

    public boolean isDespachado() {
        return misDespachado;
    }
}
