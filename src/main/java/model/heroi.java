package model;

public class heroi {
    protected String classe;
    protected int level;
    protected int vida;
    protected int vidamaxima;
    protected int dano;
    protected int pontos;
    protected boolean atacaprimeiro;
    protected String drop;
    protected int chancedrop;
    protected boolean danomagico;

    public heroi(String classe, int level, int vida, int dano, int pontos, boolean atacaprimeiro, String drop, int chancedrop, boolean danomagico) {
        this.classe = classe;
        this.level = level;
        this.vida = vida;
        this.vidamaxima = vida;
        this.dano = dano;
        this.pontos = pontos;
        this.atacaprimeiro = atacaprimeiro;
        this.drop = drop;
        this.chancedrop = chancedrop;
        this.danomagico = danomagico;
    }

    public String getclasse() { return classe; }
    public int getlevel() { return level; }
    public int getvida() { return vida; }
    public int getdano() { return dano; }
    public int getpontos() { return pontos; }
    public boolean isatacaprimeiro() { return atacaprimeiro; }
    public String getdrop() { return drop; }
    public int getchancedrop() { return chancedrop; }
    public boolean isdanomagico() { return danomagico; }
    public void receberdano(int d) { this.vida -= d; }
    public void curar(int cura) { this.vida = Math.min(this.vidamaxima, this.vida + cura); }
}