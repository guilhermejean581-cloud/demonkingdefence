package model;

public class Player {
    
    private int pontosdemoniacos;
    private int pontostemporais;
    private int turnosutilizados;

    public Player() {
        this.pontosdemoniacos = 0;
        this.pontostemporais = 0;
        this.turnosutilizados = 0;
    }

    public void morrerereiniciartimeline() {
        this.pontosdemoniacos = 0;
        this.turnosutilizados = 0;
        System.out.println("timeline reiniciada. retornando para a primeira onda.");
    }

    public void ganharpontosdemoniacos(int pontos) {
        this.pontosdemoniacos += pontos;
    }

    public void ganharpontostemporais(int pontos) {
        this.pontostemporais += pontos;
    }
}