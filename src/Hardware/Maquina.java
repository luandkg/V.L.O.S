package Hardware;

public class Maquina {

    private Processador mProcessador;
    private Memoria mMemoria;
    private HD mHD;

    public Maquina() {

        mProcessador = new Processador("X86");
        mMemoria = new Memoria("HX434C16FB3AK4", 10 * 1024 * 1024 );
        mHD = new HD("ST2000DM008", 500 * 1024 * 1024 );

    }

    public Processador getProcessador() {
        return mProcessador;
    }

    public Memoria getMemoria() {
        return mMemoria;
    }

    public HD getHD() {
        return mHD;
    }

}
