package model;

import java.util.ArrayList;
import java.util.List;

public class player {
    private static player instancia;
    private int vidamaxima = 100;
    private int atf = 10;
    private int atm = 10;
    private String sloth1 = "bola de fogo";
    private String sloth2 = "vazio";
    private int nivel = 1;
    private int yen = 0;
    private int xp = 0;
    private int pontosdemoniacos = 0;
    private List<String> inventario = new ArrayList<>();
    private List<String> magias = new ArrayList<>();

    public static player getinstancia() {
        if (instancia == null) {
            instancia = new player();
            instancia.magias.add("bola de fogo");
        }
        return instancia;
    }

    public int getvidamaxima() { return vidamaxima; }
    public int getatf() { return atf; }
    public int getatm() { return atm; }
    public String getsloth1() { return sloth1; }
    public String getsloth2() { return sloth2; }
    public void equiparslot1(String m) { this.sloth1 = m; }
    public void equiparslot2(String m) { this.sloth2 = m; }
    public int getnivel() { return nivel; }
    public int getyen() { return yen; }
    public int getxp() { return xp; }
    public int getpontosdemoniacos() { return pontosdemoniacos; }
    public void addpontos(int p) { this.pontosdemoniacos += p; }
    public List<String> getinventario() { return inventario; }
    public List<String> getmagias() { return magias; }

    public void additem(String item) { this.inventario.add(item); }
    public void addmagia(String magia) { if (!this.magias.contains(magia)) this.magias.add(magia); }

    public boolean gastaryen(int valor) {
        if (this.yen >= valor) {
            this.yen -= valor;
            return true;
        }
        return false;
    }
    public void ganharyen(int valor) { this.yen += valor; }
    public boolean ganharxp(int valor) {
        this.xp += valor;
        if (this.xp >= this.nivel * 100) {
            this.xp -= this.nivel * 100;
            this.nivel++;
            this.vidamaxima += 20;
            this.atf += 5;
            this.atm += 5;
            return true;
        }
        return false;
    }
}