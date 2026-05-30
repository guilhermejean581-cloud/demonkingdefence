package model;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeOndas {
    
    private List<Onda> ondas;
    private int ondaatual;

    public GerenciadorDeOndas() {
        this.ondas = new ArrayList<>();
        this.ondaatual = 1;
        inicializarondas();
    }

    public void inicializarondas() {
        
        Chefe berserk = new Chefe("berserk", 500, 50, "great sword abencoada", 25);
        Onda onda1 = new Onda(1, berserk);
        onda1.adicionarinimigo(new Heroi("guerreiro", 100, 10));
        onda1.adicionarinimigo(new Heroi("arqueiro", 80, 15));
        this.ondas.add(onda1);

        Chefe highelf = new Chefe("high elf", 600, 60, "arco magico", 25);
        Onda onda2 = new Onda(2, highelf);
        onda2.adicionarinimigo(new Heroi("guerreiro", 120, 15));
        onda2.adicionarinimigo(new Heroi("arqueiro", 100, 20));
        this.ondas.add(onda2);

        Chefe bosscura = new Chefe("boss suporte", 700, 70, "espada sagrada", 25);
        Onda onda3 = new Onda(3, bosscura);
        onda3.adicionarinimigo(new Heroi("sacerdote", 90, 5));
        onda3.adicionarinimigo(new Heroi("paladino", 150, 15));
        this.ondas.add(onda3);

        Chefe gigante = new Chefe("gigante do inferno", 1000, 80, "escudo do gigante", 25);
        Onda onda4 = new Onda(4, gigante);
        onda4.adicionarinimigo(new Heroi("mago", 80, 40));
        onda4.adicionarinimigo(new Heroi("ladino", 90, 30));
        this.ondas.add(onda4);

        Chefe lich = new Chefe("lich", 1200, 100, "cajado do lich", 25);
        Onda onda5 = new Onda(5, lich);
        onda5.adicionarinimigo(new Heroi("guerreiro", 200, 30));
        onda5.adicionarinimigo(new Heroi("mago", 150, 50));
        this.ondas.add(onda5);
    }

    public Onda getondaatual() {
        return this.ondas.get(this.ondaatual - 1);
    }

    public void proximaonda() {
        if (this.ondaatual < 5) {
            this.ondaatual++;
        } else {
            iniciarbossfinal();
        }
    }

    public void iniciarbossfinal() {
        Chefe sungoddess = new Chefe("sun goddess", 5000, 300, "coracao de deus", 100);
    }
}