package model;

public class chefe extends heroi {
    private boolean reduzfisico;
    private boolean reduzmagico;
    private int curaturno;
    private boolean imunemagico;
    private boolean ignoradefesa;
    private boolean amaldicoa;
    private int aumentodano;

    public chefe(String classe, int vida, int dano, int pontos, boolean atacaprimeiro, String drop, int chancedrop, boolean danomagico) {
        super(classe, 99, vida, dano, pontos, atacaprimeiro, drop, chancedrop, danomagico);
        this.reduzfisico = false;
        this.reduzmagico = false;
        this.curaturno = 0;
        this.imunemagico = false;
        this.ignoradefesa = false;
        this.amaldicoa = false;
        this.aumentodano = 0;
    }

    public void setreduzfisico(boolean v) { this.reduzfisico = v; }
    public void setreduzmagico(boolean v) { this.reduzmagico = v; }
    public void setcuraturno(int v) { this.curaturno = v; }
    public void setimunemagico(boolean v) { this.imunemagico = v; }
    public void setignoradefesa(boolean v) { this.ignoradefesa = v; }
    public void setamaldicoa(boolean v) { this.amaldicoa = v; }
    public void setaumentodano(int v) { this.aumentodano = v; }

    public boolean isreduzfisico() { return reduzfisico; }
    public boolean isreduzmagico() { return reduzmagico; }
    public int getcuraturno() { return curaturno; }
    public boolean isimunemagico() { return imunemagico; }
    public boolean isignoradefesa() { return ignoradefesa; }
    public boolean isamaldicoa() { return amaldicoa; }
    public void aplicaraumentodano() { this.dano += aumentodano; }
}