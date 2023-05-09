package polimi.ingsw.Model.Enumeration;

public enum CardCommonType implements CardType {
    //TODO MODIFICARE LINK IMG!
    CommonSixGroups("cc1"),
    CommonVertix("cc1"),
    CommonFourGroups("cc1"),
    CommonSquares("cc1"),
    CommonVertical0("cc1"),
    CommonEight("cc1"),
    CommonSameDiagonal("cc1"),
    CommonHorizontal0("cc1"),
    CommonVertical1("cc1"),
    CommonHorizontal1("cc1"),
    CommonX("cc1"),
    CommonStair("cc1");

    private String backgroundClass="";
    CardCommonType(String backgroundClass){
        this.backgroundClass=backgroundClass;
    }

    public String getBackgroundClass(){
        return backgroundClass;
    }



}
