package model;

public class chefe extends heroi {
    
    private String itemdrop;
    private int chancedrop;

    public chefe(String classe, int vida, int dano, String itemdrop, int chancedrop) {
        super(classe, vida, dano);
        this.itemdrop = itemdrop;
        this.chancedrop = chancedrop;
    }
}
