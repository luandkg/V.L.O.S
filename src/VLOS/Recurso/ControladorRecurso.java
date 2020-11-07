package VLOS.Recurso;

public class ControladorRecurso {

    private Recurso mRecurso;
    private boolean mDisponivel;

    public ControladorRecurso(Recurso eRecurso) {
        mRecurso = eRecurso;
        mDisponivel = true;
    }

    public Recurso getRecurso() {
        return mRecurso;
    }

    public void usar() {
        mDisponivel = false;
    }

    public void liberar() {
        mDisponivel = true;
    }


    public boolean isDisponivel() {
        return mDisponivel == true;
    }

    public boolean isBloqueado() {
        return mDisponivel == false;
    }
}
