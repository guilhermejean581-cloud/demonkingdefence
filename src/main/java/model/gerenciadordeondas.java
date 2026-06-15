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
        onda onda1 = new onda(1);
        onda1.adicionarinimigo(criarheroi("guerreiro", 1));
        onda1.adicionarinimigo(criarheroi("guerreiro", 1));
        onda1.adicionarinimigo(criarheroi("arqueiro", 1));
        onda1.adicionarinimigo(criarheroi("guerreiro", 1));
        onda1.adicionarinimigo(criarheroi("arqueiro", 1));
        onda1.adicionarinimigo(criarheroi("arqueiro", 1));
        onda1.adicionarinimigo(criarchefe("cavaleiro nobre"));
        this.ondas.add(onda1);

        onda onda2 = new onda(2);
        onda2.adicionarinimigo(criarheroi("cavaleiro", 1));
        onda2.adicionarinimigo(criarheroi("arqueiro", 2));
        onda2.adicionarinimigo(criarheroi("arqueiro", 2));
        onda2.adicionarinimigo(criarheroi("cavaleiro", 1));
        onda2.adicionarinimigo(criarheroi("arqueiro", 2));
        onda2.adicionarinimigo(criarheroi("arqueiro", 2));
        onda2.adicionarinimigo(criarheroi("cavaleiro", 2));
        onda2.adicionarinimigo(criarchefe("principe elfico"));
        this.ondas.add(onda2);

        onda onda3 = new onda(3);
        onda3.adicionarinimigo(criarheroi("guerreiro", 2));
        onda3.adicionarinimigo(criarheroi("guerreiro", 2));
        onda3.adicionarinimigo(criarheroi("guerreiro", 3));
        onda3.adicionarinimigo(criarheroi("cavaleiro", 3));
        onda3.adicionarinimigo(criarheroi("arqueiro", 3));
        onda3.adicionarinimigo(criarheroi("arqueiro", 3));
        onda3.adicionarinimigo(criarheroi("cavaleiro", 3));
        onda3.adicionarinimigo(criarchefe("santa"));
        onda3.adicionarinimigo(criarchefe("heroi da profecia"));
        this.ondas.add(onda3);

        onda onda4 = new onda(4);
        onda4.adicionarinimigo(criarheroi("cavaleiro", 4));
        onda4.adicionarinimigo(criarheroi("mago de fogo", 4));
        onda4.adicionarinimigo(criarheroi("mago de gelo", 4));
        onda4.adicionarinimigo(criarheroi("mago de fogo", 4));
        onda4.adicionarinimigo(criarheroi("mago de vento", 4));
        onda4.adicionarinimigo(criarheroi("cavaleiro", 4));
        onda4.adicionarinimigo(criarheroi("mago de gelo", 5));
        onda4.adicionarinimigo(criarchefe("arquimago"));
        this.ondas.add(onda4);

        onda onda5 = new onda(5);
        onda5.adicionarinimigo(criarheroi("guerreiro", 5));
        onda5.adicionarinimigo(criarheroi("arqueiro", 5));
        onda5.adicionarinimigo(criarheroi("cavaleiro", 5));
        onda5.adicionarinimigo(criarheroi("mago de fogo", 5));
        onda5.adicionarinimigo(criarheroi("mago de gelo", 5));
        onda5.adicionarinimigo(criarheroi("mago de vento", 5));
        onda5.adicionarinimigo(criarchefe("arqueiro sagrado"));
        onda5.adicionarinimigo(criarchefe("lich"));
        onda5.adicionarinimigo(criarchefe("guardiao do mundo"));
        this.ondas.add(onda5);

        onda onda6 = new onda(6);
        onda6.adicionarinimigo(criarchefe("deusa"));
        this.ondas.add(onda6);
    }

    private heroi criarheroi(String tipo, int level) {
        if (tipo.equals("guerreiro")) {
            if (level == 1) return new heroi("guerreiro", 1, 80, 20, 100, false, "machado de guerra", 5, false);
            if (level == 2) return new heroi("guerreiro", 2, 100, 30, 250, false, "machado de guerra", 5, false);
            if (level == 3) return new heroi("guerreiro", 3, 150, 50, 500, false, "machado de guerra", 5, false);
            if (level == 4) return new heroi("guerreiro", 4, 200, 80, 1000, false, "machado de guerra", 5, false);
            if (level == 5) return new heroi("guerreiro", 5, 250, 100, 2000, false, "machado de guerra", 5, false);
        }
        if (tipo.equals("arqueiro")) {
            if (level == 1) return new heroi("arqueiro", 1, 40, 20, 100, true, "arco rapido", 5, false);
            if (level == 2) return new heroi("arqueiro", 2, 60, 35, 250, true, "arco rapido", 5, false);
            if (level == 3) return new heroi("arqueiro", 3, 100, 55, 500, true, "arco rapido", 5, false);
            if (level == 4) return new heroi("arqueiro", 4, 150, 90, 1000, true, "arco rapido", 5, false);
            if (level == 5) return new heroi("arqueiro", 5, 180, 120, 2000, true, "arco rapido", 5, false);
        }
        if (tipo.equals("cavaleiro")) {
            if (level == 1) return new heroi("cavaleiro", 1, 80, 40, 200, true, "espada de honra", 5, false);
            if (level == 2) return new heroi("cavaleiro", 2, 100, 50, 400, true, "espada de honra", 5, false);
            if (level == 3) return new heroi("cavaleiro", 3, 150, 60, 800, true, "espada de honra", 5, false);
            if (level == 4) return new heroi("cavaleiro", 4, 200, 90, 1600, true, "espada de honra", 5, false);
            if (level == 5) return new heroi("cavaleiro", 5, 250, 110, 3000, true, "espada de honra", 5, false);
        }
        if (tipo.equals("mago de fogo")) {
            if (level == 4) return new heroi("mago de fogo", 4, 120, 100, 1600, false, "explosao", 5, true);
            if (level == 5) return new heroi("mago de fogo", 5, 140, 130, 3000, false, "explosao", 5, true);
        }
        if (tipo.equals("mago de gelo")) {
            if (level == 4) return new heroi("mago de gelo", 4, 200, 80, 1600, false, "tempestade de gelo", 5, true);
            if (level == 5) return new heroi("mago de gelo", 5, 200, 110, 3000, false, "tempestade de gelo", 5, true);
        }
        if (tipo.equals("mago de vento")) {
            if (level == 4) return new heroi("mago de vento", 4, 150, 90, 1600, true, "tornado", 5, true);
            if (level == 5) return new heroi("mago de vento", 5, 180, 120, 3000, true, "tornado", 5, true);
        }
        return new heroi("desconhecido", 1, 1, 1, 0, false, "nada", 0, false);
    }

    private chefe criarchefe(String nome) {
        if (nome.equals("cavaleiro nobre")) {
            chefe c = new chefe("cavaleiro nobre", 200, 40, 500, false, "espada nobre", 10, false);
            c.setreduzfisico(true);
            c.setaumentodano(5);
            return c;
        }
        if (nome.equals("principe elfico")) {
            chefe c = new chefe("principe elfico", 200, 60, 1000, true, "arco magico", 10, true);
            c.setreduzmagico(true);
            return c;
        }
        if (nome.equals("santa")) {
            chefe c = new chefe("santa", 250, 40, 1500, false, "restauracao", 10, true);
            c.setreduzmagico(true);
            c.setcuraturno(15);
            return c;
        }
        if (nome.equals("heroi da profecia")) {
            chefe c = new chefe("heroi da profecia", 350, 100, 2000, false, "espada sagrada", 10, false);
            c.setreduzfisico(true);
            return c;
        }
        if (nome.equals("arquimago")) {
            chefe c = new chefe("arquimago", 350, 120, 3000, false, "aprimoramento", 10, true);
            c.setreduzmagico(true);
            return c;
        }
        if (nome.equals("arqueiro sagrado")) {
            chefe c = new chefe("arqueiro sagrado", 350, 150, 3500, true, "arco sagrado", 10, true);
            return c;
        }
        if (nome.equals("lich")) {
            chefe c = new chefe("lich", 400, 120, 3500, false, "maldicao", 10, true);
            c.setimunemagico(true);
            c.setamaldicoa(true);
            return c;
        }
        if (nome.equals("guardiao do mundo")) {
            chefe c = new chefe("guardiao do mundo", 500, 150, 5000, true, "elixir da vida", 50, false);
            return c;
        }
        if (nome.equals("deusa")) {
            chefe c = new chefe("deusa", 1000, 200, 10000, true, "coracao divino", 100, false);
            c.setignoradefesa(true);
            return c;
        }
        return new chefe("desconhecido", 1, 1, 0, false, "nada", 0, false);
    }

    public onda getonda(int numero) {
        if (numero >= 1 && numero <= ondas.size()) return ondas.get(numero - 1);
        return ondas.get(0);
    }
}