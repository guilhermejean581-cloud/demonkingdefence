package model;

import java.util.ArrayList;
import java.util.List;

public class Onda {
    
    private int numeroonda;
    private List<Heroi> inimigos;
    private Chefe boss;

    public Onda(int numeroonda, Chefe boss) {
        this.numeroonda = numeroonda;
        this.inimigos = new ArrayList<>();
        this.boss = boss;
    }

    public void adicionarinimigo(Heroi inimigo) {
        this.inimigos.add(inimigo);
    }
}