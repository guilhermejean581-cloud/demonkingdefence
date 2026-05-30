package model;

public class Chefe extends Heroi {
    
    private String itemdrop;
    private int chancedrop;

    public Chefe(String classe, int vida, int dano, String itemdrop, int chancedrop) {
        super(classe, vida, dano);
        this.itemdrop = itemdrop;
        this.chancedrop = chancedrop;
    }
}