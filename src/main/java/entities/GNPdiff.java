package entities;

public class GNPdiff extends GNP{
    private String action;
    public String getAction(){
        return this.action;
    }
    public void setAction(String action){
        this.action = action;
    }
    public GNP toGNP(GNPdiff gnpDiff){
        GNP gnp;
        gnp = gnpDiff;
        return gnp;
    }
}
