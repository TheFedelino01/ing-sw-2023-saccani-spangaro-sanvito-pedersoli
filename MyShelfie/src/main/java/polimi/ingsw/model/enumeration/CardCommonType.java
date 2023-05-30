package polimi.ingsw.model.enumeration;

public enum CardCommonType implements CardType {
    CommonSixGroups("cc4"),
    CommonVertex("cc8"),
    CommonFourGroups("cc3"),
    CommonSquares("cc1"),
    CommonVertical0("cc5"),
    CommonEight("cc9"),
    CommonSameDiagonal("cc11"),
    CommonHorizontal0("cc7"),
    CommonVertical1("cc2"),
    CommonHorizontal1("cc6"),
    CommonX("cc10"),
    CommonStair("cc12");

    private final String backgroundClass;
    CardCommonType(String backgroundClass){
        this.backgroundClass=backgroundClass;
    }

    public String getBackgroundClass(){
        return backgroundClass;
    }



}
