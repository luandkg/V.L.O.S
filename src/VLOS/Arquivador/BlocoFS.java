package VLOS.Arquivador;

public class BlocoFS {

    private int mBlocoID;
    private BlocoStatus mStatus;

    public BlocoFS(int eBlocoID) {

        mBlocoID = eBlocoID;
        mStatus = BlocoStatus.LIVRE;

    }


    public int getBlocoID() {
        return mBlocoID;
    }

    public BlocoStatus getStatus() {
        return mStatus;
    }

    public void setStatus(BlocoStatus eBlocoStatus) {
        mStatus = eBlocoStatus;
    }

}
