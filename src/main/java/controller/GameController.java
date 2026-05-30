package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;

public class GameController {
    @FXML private Label labelonda, labelvidaplayer, labelyen, labelnomeinimigo, labelvidainimigo;
    @FXML private ProgressBar barrahpplayer, barraxp, barrahpinimigo, barracargainimigo;
    @FXML private TextArea textlogbatalha;
    @FXML private Button btnproximaonda, btnirparaloja, btnataquefisico, btnataquemagico, btnfugir, btnhabilidade1, btnhabilidade2;

    private Player player = Player.getInstancia();
    private GerenciadorDeOndas gerenciadorondas = new GerenciadorDeOndas();
    private Onda ondaatual;
    private Heroi inimigoatual;
    private int indexinimigo, vidaatualplayer, vidamaximainimigo, cargacspecialinimigo;
    private final int maxcargainimigo = 3;

    @FXML
    public void initialize() {
        vidaatualplayer = player.getVidamaxima();
        ondaatual = gerenciadorondas.getOnda(SelecaoOndaController.ondaSelecionada);
        indexinimigo = 0;
        
        btnproximaonda.setVisible(false);
        btnirparaloja.setVisible(false);
        
        textlogbatalha.setText("a batalha comecou.\n");
        carregarproximoinimigo();
        atualizarinterface();
    }

    private void carregarproximoinimigo() {
        if (indexinimigo < ondaatual.getInimigos().size()) {
            inimigoatual = ondaatual.getInimigos().get(indexinimigo);
            vidamaximainimigo = inimigoatual.getVida();
            cargacspecialinimigo = 0;
            textlogbatalha.appendText("um " + inimigoatual.getClasse() + " apareceu.\n");
        } else {
            textlogbatalha.appendText("onda concluida.\n");
            inimigoatual = null;
            btnproximaonda.setVisible(true);
            btnirparaloja.setVisible(true);
            desativarBotoes();
        }
        atualizarinterface();
    }

    @FXML
    public void acaoataquebasico(ActionEvent e) { atacarinimigo(10 + (player.getAtf() * 2), "espada antiga"); }

    @FXML
    public void acaoataquemagico(ActionEvent e) { 
        if (player.isTemMagia()) atacarinimigo(15 + (player.getAtm() * 2), "bola de fogo");
        else textlogbatalha.appendText("magia nao desbloqueada.\n");
    }

    @FXML
    public void acaohabilidade1(ActionEvent e) {
        if (!player.getSlotH1().equals("vazio")) atacarinimigo(30 + (player.getAtf() * 3), player.getSlotH1());
        else textlogbatalha.appendText("slot vazio.\n");
    }

    @FXML
    public void acaohabilidade2(ActionEvent e) {
        if (!player.getSlotH2().equals("vazio")) atacarinimigo(40 + (player.getAtm() * 3), player.getSlotH2());
        else textlogbatalha.appendText("slot vazio.\n");
    }

    @FXML
    public void acaofugir(ActionEvent e) {
        if (Math.random() < 0.5) {
            textlogbatalha.appendText("fuga bem sucedida! reiniciando onda.\n");
            trocarTela(e, "/view/gameplay.fxml");
        } else {
            textlogbatalha.appendText("falha ao fugir!\n");
            turnoinimigos();
        }
    }

    private void atacarinimigo(int dano, String nome) {
        if (inimigoatual == null) return;
        inimigoatual.receberDano(dano);
        textlogbatalha.appendText("voce causou " + dano + " de dano com " + nome + ".\n");
        
        if (inimigoatual.getVida() <= 0) {
            int xpGanhos = 50 * SelecaoOndaController.ondaSelecionada;
            int yenGanhos = 30 * SelecaoOndaController.ondaSelecionada;
            if (player.ganharxp(xpGanhos)) textlogbatalha.appendText("subiu de nivel! Nivel: " + player.getNivel() + "\n");
            player.ganharyen(yenGanhos);
            indexinimigo++;
            carregarproximoinimigo();
        } else {
            turnoinimigos();
        }
        atualizarinterface();
    }

    private void turnoinimigos() {
        if (inimigoatual != null) {
            int dano = inimigoatual.getDano() - (10 / 2); // DF simplificado
            if (cargacspecialinimigo >= maxcargainimigo) { dano *= 2; cargacspecialinimigo = 0; } 
            else { cargacspecialinimigo++; }
            vidaatualplayer -= Math.max(1, dano);
            if (vidaatualplayer <= 0) trocarTela(new ActionEvent(), "/view/morte.fxml");
        }
    }

    private void atualizarinterface() {
        labelonda.setText("onda: " + SelecaoOndaController.ondaSelecionada);
        labelyen.setText("yen: " + player.getYen());
        labelvidaplayer.setText("hp: " + Math.max(0, vidaatualplayer) + " / " + player.getVidamaxima());
        barraxp.setProgress((double) player.getXp() / (player.getNivel() * 100));
        btnhabilidade1.setText(player.getSlotH1());
        btnhabilidade2.setText(player.getSlotH2());
        if (inimigoatual != null) {
            labelnomeinimigo.setText("inimigo: " + inimigoatual.getClasse());
            labelvidainimigo.setText("hp: " + Math.max(0, inimigoatual.getVida()) + " / " + vidamaximainimigo);
            barrahpinimigo.setProgress(Math.max(0, inimigoatual.getVida()) / (double) vidamaximainimigo);
        }
    }

    private void trocarTela(ActionEvent e, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException ex) { ex.printStackTrace(); }
    }

    private void desativarBotoes() {
        btnataquefisico.setDisable(true);
        btnataquemagico.setDisable(true);
        btnfugir.setDisable(true);
        btnhabilidade1.setDisable(true);
        btnhabilidade2.setDisable(true);
    }

    @FXML public void acaoproximaonda(ActionEvent e) { 
        if (SelecaoOndaController.ondaSelecionada < 5) {
            SelecaoOndaController.ondaSelecionada++;
            trocarTela(e, "/view/gameplay.fxml");
        }
    }
    @FXML public void acaoirparaloja(ActionEvent e) { trocarTela(e, "/view/loja.fxml"); }
}