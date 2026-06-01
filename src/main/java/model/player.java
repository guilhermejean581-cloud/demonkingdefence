package model;

import java.util.ArrayList;
import java.util.List;

public class player {
    private static player instancia;
    private int hp = 10;
    private int vidamaxima = 100;
    private int atf = 1;
    private int atm = 1;
    private int defesa = 1;
    private int nivel = 1;
    
    private int yen = 0; 
    private int pontosdemoniacos = 0;
    private int pontosdistribuir = 0;
    
    private String sloth1 = "bola de fogo";
    private String sloth2 = "vazio";
    private String armaequipada = "espada antiga";
    private List<String> armas = new ArrayList<>();
    private List<String> magias = new ArrayList<>();
    private List<String> pocoes = new ArrayList<>();

    public static player getinstancia() {
        if (instancia == null) {
            instancia = new player();
            instancia.armas.add("espada antiga");
            instancia.magias.add("bola de fogo");
        }
        return instancia;
    }

    public int gethp() { return hp; }
    public void sethp(int v) { this.hp = v; this.vidamaxima = v * 10; }
    public int getvidamaxima() { return vidamaxima; }
    public int getatf() { return atf; }
    public void setatf(int v) { this.atf = v; }
    public int getatm() { return atm; }
    public void setatm(int v) { this.atm = v; }
    public int getdefesa() { return defesa; }
    public void setdefesa(int v) { this.defesa = v; }
    public int getnivel() { return nivel; }
    public void setnivel(int v) { this.nivel = v; }
    
    public int getyen() { return yen; }
    public void ganharyen(int v) { this.yen += v; }
    public boolean gastaryen(int v) {
        if(this.yen >= v) { this.yen -= v; return true; }
        return false;
    }
    
    public int getpontosdemoniacos() { return pontosdemoniacos; }
    public void addpontos(int v) { this.pontosdemoniacos += v; }
    public boolean gastarpontos(int v) {
        if(this.pontosdemoniacos >= v) { this.pontosdemoniacos -= v; return true; }
        return false;
    }

    public int getpontosdistribuir() { return pontosdistribuir; }
    public void addpontosdistribuir(int v) { this.pontosdistribuir += v; }
    public boolean gastarpontodistribuir() {
        if(this.pontosdistribuir > 0) { this.pontosdistribuir--; return true; }
        return false;
    }

    public String getsloth1() { return sloth1; }
    public void equiparslot1(String m) { this.sloth1 = m; }
    public String getsloth2() { return sloth2; }
    public void equiparslot2(String m) { this.sloth2 = m; }
    public String getarmaequipada() { return armaequipada; }
    public void setarmaequipada(String a) { this.armaequipada = a; }
    
    public List<String> getarmas() { return armas; }
    public void addarma(String a) { if(!armas.contains(a)) armas.add(a); }
    public List<String> getmagias() { return magias; }
    public void addmagia(String m) { if(!magias.contains(m)) magias.add(m); }
    public List<String> getpocoes() { return pocoes; }
    public void addpocao(String p) { pocoes.add(p); }
}