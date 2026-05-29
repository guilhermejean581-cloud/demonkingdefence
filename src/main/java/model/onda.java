package model;

import java.util.ArrayList;
import java.util.List;

public class onda {
    
    private int numeroonda;
    private List<heroi> inimigos;
    private chefe boss;

    public onda(int numeroonda, chefe boss) {
        this.numeroonda = numeroonda;
        this.inimigos = new ArrayList<>();
        this.boss = boss;
    }

    public void adicionarinimigo(heroi inimigo) {
        this.inimigos.add(inimigo);
    }
}