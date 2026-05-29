package model;

import java.util.ArrayList;
import java.util.List;

public class gerenciadordeondas {
    
    private List<onda> ondas;
    private int ondaatual;

    public gerenciadordeondas() {
        this.ondas = new ArrayList<>();
        this.ondaatual = 1;
        inicializarondas();
    }

    public void inicializarondas() {
        
        chefe berserk = new chefe("berserk", 500, 50, "great sword abencoada", 25);
        onda onda1 = new onda(1, berserk);
        onda1.adicionarinimigo(new heroi("guerreiro", 100, 10));
        onda1.adicionarinimigo(new heroi("arqueiro", 80, 15));
        this.ondas.add(onda1);

        chefe highelf = new chefe("high elf", 600, 60, "arco magico", 25);
        onda onda2 = new onda(2, highelf);
        onda2.adicionarinimigo(new heroi("guerreiro", 120, 15));
        onda2.adicionarinimigo(new heroi("arqueiro", 100, 20));
        this.ondas.add(onda2);

        chefe bosscura = new chefe("boss suporte", 700, 70, "espada sagrada", 25);
        onda onda3 = new onda(3, bosscura);
        onda3.adicionarinimigo(new heroi("sacerdote", 90, 5));
        onda3.adicionarinimigo(new heroi("paladino", 150, 15));
        this.ondas.add(onda3);

        chefe gigante = new chefe("gigante do inferno", 1000, 80, "escudo do gigante", 25);
        onda onda4 = new onda(4, gigante);
        onda4.adicionarinimigo(new heroi("mago", 80, 40));
        onda4.adicionarinimigo(new heroi("ladino", 90, 30));
        this.ondas.add(onda4);

        chefe lich = new chefe("lich", 1200, 100, "cajado do lich", 25);
        onda onda5 = new onda(5, lich);
        onda5.adicionarinimigo(new heroi("guerreiro", 200, 30));
        onda5.adicionarinimigo(new heroi("mago", 150, 50));
        this.ondas.add(onda5);
    }

    public onda getondaatual() {
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
        chefe sungoddess = new chefe("sun goddess", 5000, 300, "coracao de deus", 100);
    }
}