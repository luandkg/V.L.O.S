package VLOS.Recurso;

public class ControladorRecurso {

    // CONTROLADOR_RECURSO CONTROLA O ACESSO DO RECURSO
    // STATUS = BLOQUEADO OU DISPONIVEL


    private Recurso mRecurso;
    private boolean mDisponivel;
    private String mTipo;

    public ControladorRecurso(String eTipo, Recurso eRecurso) {
        mRecurso = eRecurso;
        mDisponivel = true;
        mTipo = eTipo;
    }

    public String getTipo() {
        return mTipo;
    }

    public int getRID() {
        return mRecurso.getRID();
    }

    public Recurso getRecurso() {
        return mRecurso;
    }

    public boolean mesmoTipo(String eTipo) {
        return mTipo.contentEquals(eTipo);
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

    public String getStatus() {
        if (isBloqueado()) {
            return "BLOQUEADO";
        } else {
            return "DISPONIVEL";
        }
    }


}
