package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

public class GameController {
    @FXML private Label labelonda, labelvidaplayer, labelyen, labelnomeinimigo, labelvidainimigo;
    @FXML private ProgressBar barrahpplayer, barraxp, barrahpinimigo, barracargainimigo;
    @FXML private TextArea textlogbatalha;
    @FXML private Button btnproximaonda, btnirparaloja, btnataquefisico, btnataquemagico, btnfugir, btnhabilidade1, btnhabilidade2;

    private player player = model.player.getinstancia();
    private gerenciadordeondas gerenciadorondas = new gerenciadordeondas();
    private onda ondaatual;
    private heroi inimigoatual;
    private int indexinimigo, vidaatualplayer, vidamaximainimigo;
    private List<String> dropsdaonda = new ArrayList<>();
    private boolean playerbuffado = false;
    private int turnosbuff = 0;
    private boolean maldicaolich = false;
    private int turnostempestade = 0;
    private int danoreduzido = 0;
    
    private int usosmagia = 5;
    private int usosh1 = 3;
    private int usosh2 = 3;
    private int usosfuga = 2;

    @FXML
    public void initialize() {
        vidaatualplayer = player.getvidamaxima();
        ondaatual = gerenciadorondas.getonda(SelecaoOndaController.ondaselecionada);
        indexinimigo = 0;
        
        usosmagia = 5;
        usosh1 = 3;
        usosh2 = 3;
        usosfuga = 2;
        
        btnproximaonda.setVisible(false);
        btnirparaloja.setVisible(false);
        textlogbatalha.setText("a batalha comecou.\n");
        carregarproximoinimigo();
        atualizarinterface();
    }

    private void carregarproximoinimigo() {
        if (indexinimigo < ondaatual.getinimigos().size()) {
            inimigoatual = ondaatual.getinimigos().get(indexinimigo);
            vidamaximainimigo = inimigoatual.getvida();
            textlogbatalha.appendText("um " + inimigoatual.getclasse() + " apareceu.\n");
        } else {
            textlogbatalha.appendText("onda concluida.\n");
            inimigoatual = null;
            btnproximaonda.setVisible(true);
            btnirparaloja.setVisible(true);
            desativarbotoes();
        }
        atualizarinterface();
    }

    @FXML public void acaoataquebasico(ActionEvent e) { executarturno("espada antiga"); }
    
    @FXML public void acaoataquemagico(ActionEvent e) { 
        if (usosmagia > 0) {
            usosmagia--;
            executarturno("bola de fogo");
        } else {
            textlogbatalha.appendText("sem usos para a magia bola de fogo!\n");
        }
    }
    
    @FXML public void acaohabilidade1(ActionEvent e) { 
        if (!player.getsloth1().equals("vazio") && usosh1 > 0) {
            usosh1--;
            executarturno(player.getsloth1());
        } else if (usosh1 <= 0) {
            textlogbatalha.appendText("sem usos para " + player.getsloth1() + "!\n");
        }
    }
    
    @FXML public void acaohabilidade2(ActionEvent e) { 
        if (!player.getsloth2().equals("vazio") && usosh2 > 0) {
            usosh2--;
            executarturno(player.getsloth2());
        } else if (usosh2 <= 0) {
            textlogbatalha.appendText("sem usos para " + player.getsloth2() + "!\n");
        }
    }
    
    @FXML public void acaofugir(ActionEvent e) {
        if (usosfuga > 0) {
            usosfuga--;
            if (Math.random() < 0.5) {
                textlogbatalha.appendText("fuga bem sucedida! reiniciando onda.\n");
                trocartela("/view/gameplay.fxml");
            } else {
                textlogbatalha.appendText("falha ao fugir.\n");
                turnoinimigos();
                fimdeturno();
            }
        } else {
            textlogbatalha.appendText("ja nao tens mais tentativas de fuga!\n");
        }
    }

    private void executarturno(String acao) {
        if (inimigoatual == null || acao.equals("vazio")) return;
        
        heroi alvo = inimigoatual;
        boolean inimigoatacou = false;
        
        if (alvo.isatacaprimeiro()) {
            turnoinimigos();
            inimigoatacou = true;
        }

        if (vidaatualplayer > 0) {
            processaracaoplayer(acao);
        }

        if (inimigoatual == alvo && inimigoatual != null && inimigoatual.getvida() > 0 && !inimigoatacou) {
            turnoinimigos();
        }

        fimdeturno();
    }

    private void processaracaoplayer(String acao) {
        int dano = 0;
        boolean magico = false;

        if (acao.equals("espada antiga")) { dano = 10 + player.getatf(); }
        else if (acao.equals("bola de fogo")) { dano = 25; magico = true; }
        else if (acao.equals("missil de mana")) { dano = 35; magico = true; }
        else if (acao.equals("relampago")) { dano = 50; magico = true; }
        else if (acao.equals("cura")) { 
            if (!maldicaolich) vidaatualplayer = Math.min(player.getvidamaxima(), vidaatualplayer + (player.getvidamaxima() / 5)); 
            textlogbatalha.appendText("curou vida.\n"); 
            return; 
        }
        else if (acao.equals("tempestade de fogo")) { dano = 80; magico = true; }
        else if (acao.equals("meteoro")) { dano = 100; magico = true; }
        else if (acao.equals("tornado")) { dano = 50; magico = true; turnostempestade = 3; }
        else if (acao.equals("tempestade de gelo")) { dano = 90; magico = true; danoreduzido = 30; }
        else if (acao.equals("explosao")) { dano = 120; magico = true; }
        else if (acao.equals("restauracao")) { 
            if (!maldicaolich) vidaatualplayer = Math.min(player.getvidamaxima(), vidaatualplayer + (int)(player.getvidamaxima() * 0.4)); 
            textlogbatalha.appendText("restaurou vida.\n"); 
            return; 
        }
        else if (acao.equals("aprimoramento")) { playerbuffado = true; turnosbuff = 3; textlogbatalha.appendText("status aprimorados.\n"); return; }
        else if (acao.equals("maldicao")) { textlogbatalha.appendText("inimigo amaldicoado.\n"); return; }

        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if (magico && c.isimunemagico()) dano = 0;
            if (magico && c.isreduzmagico()) dano /= 2;
            if (!magico && c.isreduzfisico()) dano /= 2;
        }

        if (playerbuffado) dano += (dano / 2);

        inimigoatual.receberdano(dano);
        textlogbatalha.appendText("voce usou " + acao + " causando " + dano + " de dano.\n");
        verificarmorteinimigo();
    }

    private void verificarmorteinimigo() {
        if (inimigoatual.getvida() <= 0) {
            textlogbatalha.appendText(inimigoatual.getclasse() + " derrotado.\n");
            
            player.addpontos(inimigoatual.getpontos());
            player.ganharyen(inimigoatual.getpontos());
            
            int xpganho = 50 * SelecaoOndaController.ondaselecionada;
            if (player.ganharxp(xpganho)) {
                textlogbatalha.appendText("subiu de nivel! nivel: " + player.getnivel() + "\n");
            }
            
            if (Math.random() * 100 < inimigoatual.getchancedrop()) {
                dropsdaonda.add(inimigoatual.getdrop());
                textlogbatalha.appendText("item encontrado: " + inimigoatual.getdrop() + "\n");
            }

            if (inimigoatual instanceof chefe) {
                textlogbatalha.appendText("chefe derrotado. onda concluida!\n");
                for (String d : dropsdaonda) {
                    if (d.equals("elixir da vida") || d.equals("coracao divino") || d.contains("pocao")) player.additem(d);
                    else player.addmagia(d);
                }
                dropsdaonda.clear();
                inimigoatual = null;
                btnproximaonda.setVisible(true);
                btnirparaloja.setVisible(true);
                desativarbotoes();
            } else {
                indexinimigo++;
                carregarproximoinimigo();
            }
        }
    }

    private void turnoinimigos() {
        if (inimigoatual == null) return;
        
        int danoinimigo = inimigoatual.getdano();
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            c.aplicaraumentodano();
            danoinimigo = c.getdano();
            if (c.isamaldicoa()) maldicaolich = true;
        } else {
            if (inimigoatual.getclasse().contains("mago")) {
                if (Math.random() < 0.25) {
                    danoinimigo += (danoinimigo * 0.2);
                    textlogbatalha.appendText("ataque critico!\n");
                }
            }
        }

        if (danoreduzido > 0) {
            danoinimigo -= (danoinimigo * danoreduzido / 100);
            danoreduzido = 0;
        }

        vidaatualplayer -= Math.max(1, danoinimigo);
        textlogbatalha.appendText("inimigo causou " + danoinimigo + " de dano.\n");
        
        if (vidaatualplayer <= 0) {
            trocartela("/view/morte.fxml");
        }
    }

    private void fimdeturno() {
        if (turnostempestade > 0 && inimigoatual != null) {
            inimigoatual.receberdano(50);
            textlogbatalha.appendText("tornado causou 50 de dano.\n");
            turnostempestade--;
            verificarmorteinimigo();
        }
        if (turnosbuff > 0) {
            turnosbuff--;
            if (turnosbuff == 0) playerbuffado = false;
        }
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if (c.getcuraturno() > 0) {
                c.curar(c.getcuraturno());
                textlogbatalha.appendText("chefe curou hp.\n");
            }
        }
        atualizarinterface();
    }

    private void atualizarinterface() {
        labelonda.setText("onda: " + SelecaoOndaController.ondaselecionada);
        labelyen.setText("pontos: " + player.getpontosdemoniacos());
        labelvidaplayer.setText("hp: " + Math.max(0, vidaatualplayer) + " / " + player.getvidamaxima());
        
        btnataquemagico.setText("bola de fogo (" + usosmagia + "/5)");
        
        if (!player.getsloth1().equals("vazio")) btnhabilidade1.setText(player.getsloth1() + " (" + usosh1 + ")");
        else btnhabilidade1.setText("vazio");
        
        if (!player.getsloth2().equals("vazio")) btnhabilidade2.setText(player.getsloth2() + " (" + usosh2 + ")");
        else btnhabilidade2.setText("vazio");
        
        btnfugir.setText("fugir (" + usosfuga + "/2)");
        
        barrahpplayer.setProgress((double) Math.max(0, vidaatualplayer) / player.getvidamaxima());
        
        if (inimigoatual != null) {
            labelnomeinimigo.setText("inimigo: " + inimigoatual.getclasse());
            labelvidainimigo.setText("hp: " + Math.max(0, inimigoatual.getvida()) + " / " + vidamaximainimigo);
            barrahpinimigo.setProgress((double) Math.max(0, inimigoatual.getvida()) / vidamaximainimigo);
        }
        barraxp.setProgress((double) player.getxp() / (player.getnivel() * 100));
        barracargainimigo.setProgress(0.0);
    }

    private void trocartela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) labelonda.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        }
    }

    private void desativarbotoes() {
        btnataquefisico.setDisable(true);
        btnataquemagico.setDisable(true);
        btnfugir.setDisable(true);
        btnhabilidade1.setDisable(true);
        btnhabilidade2.setDisable(true);
    }

    @FXML public void acaoproximaonda(ActionEvent e) { 
        if (SelecaoOndaController.ondaselecionada < 6) {
            SelecaoOndaController.ondaselecionada++;
            trocartela("/view/gameplay.fxml");
        }
    }
    
    @FXML public void acaoirparaloja(ActionEvent e) { 
        trocartela("/view/loja.fxml"); 
    }
}