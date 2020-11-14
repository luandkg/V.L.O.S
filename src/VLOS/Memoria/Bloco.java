package VLOS.Memoria;

public class Bloco {

    private VLMemoria mVLMemoria;
    private BlocoStatus mBlocoStatus;
    private long mID;

    // BLOCO : CLASSE RESPONSAVEL POR REPRESENTAR UM BLOCO DE MEMORIA

    public Bloco(VLMemoria eVLMemoria, long eID, BlocoStatus eBlocoStatus) {

        mVLMemoria = eVLMemoria;
        mID = eID;
        mBlocoStatus = eBlocoStatus;

    }

    public long getOffset() {
        return mID;
    }

    public void setStatus(BlocoStatus eBlocoStatus) {
        mBlocoStatus = eBlocoStatus;
    }

    public BlocoStatus getStatus() {
        return mBlocoStatus;
    }

}
