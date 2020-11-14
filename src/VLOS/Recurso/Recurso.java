package VLOS.Recurso;

public class Recurso {

    // CLASSE RECURSO REPRESENTA ALGUM RECURSO DO SISTEMA

    private int mRID;
    private String mNome;
    private ControladorRecurso mControlador;

    public Recurso(int eRID, String eNome) {
        mRID = eRID;
        mNome = eNome;
    }

    public int getRID() {
        return mRID;
    }

    public String getNome() {
        return mNome;
    }

    public void setControlador(ControladorRecurso eControlador) {
        mControlador = eControlador;
    }

    public boolean isBloqueado() {
        return mControlador.isBloqueado();
    }

    public boolean isDisponivel() {
        return mControlador.isDisponivel();
    }

    public String getStatus() {
        return mControlador.getStatus();
    }
}
