package model;

import java.util.ArrayList;
import java.util.List;

public class onda {
    private int numeroonda;
    private List<heroi> inimigos;

    public onda(int numeroonda) {
        this.numeroonda = numeroonda;
        this.inimigos = new ArrayList<>();
    }

    public void adicionarinimigo(heroi inimigo) {
        this.inimigos.add(inimigo);
    }

    public List<heroi> getinimigos() {
        return inimigos;
    }
}