package VLOS.Despachante;

public class ItemDespachante {

    private int mInicializacao;
    private int mPrioridade;
    private int mTempoProcessador;
    private int mBlocos;
    private int mCodigoImpressora;
    private int mCodigoScanner;
    private int mCodigoModem;
    private int mCodigoDisco;


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


    public void debug(){

        System.out.println(" Inicializacao :: " + this.getInicializacao());
        System.out.println("\t - Prioridade : " + this.getPrioridade());
        System.out.println("\t - Tempo : " + this.getTempoProcessador());
        System.out.println("\t - Blocos : " + this.getBlocos());
        System.out.println("\t - Impressora : " + this.getCodigoImpressora());
        System.out.println("\t - Scanner : " + this.getCodigoScanner());
        System.out.println("\t - Modem : " + this.getCodigoModem());
        System.out.println("\t - Disco : " + this.getCodigoDisco());


    }
}
