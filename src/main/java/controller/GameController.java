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
    private boolean bufffisico = false;
    private boolean buffmagico = false;
    private int turnosbuffaprimoramento = 0;
    private boolean maldicaolich = false;
    private int turnostempestade = 0;
    private int castarmeteoro = 0;
    private int danoreduzido = 0;

    @FXML public void initialize() {
        vidaatualplayer = player.getvidamaxima();
        ondaatual = gerenciadorondas.getonda(SelecaoOndaController.ondaselecionada);
        indexinimigo = 0;
        btnproximaonda.setVisible(false);
        btnirparaloja.setVisible(false);
        textlogbatalha.setText("batalha iniciada.\n");
        carregarproximoinimigo();
        atualizarinterface();
    }

    private void carregarproximoinimigo() {
        if (indexinimigo < ondaatual.getinimigos().size()) {
            inimigoatual = ondaatual.getinimigos().get(indexinimigo);
            vidamaximainimigo = inimigoatual.getvida();
            textlogbatalha.appendText("inimigo " + inimigoatual.getclasse() + " apareceu.\n");
        } else {
            textlogbatalha.appendText("onda concluida.\n");
            inimigoatual = null;
            btnproximaonda.setVisible(true);
            btnirparaloja.setVisible(true);
            desativarbotoes();
            for(String d : dropsdaonda) {
                if(d.contains("pocao") || d.contains("elixir") || d.contains("coracao")) player.addpocao(d);
                else if(d.contains("espada") || d.contains("arco") || d.contains("machado")) player.addarma(d);
                else player.addmagia(d);
            }
            dropsdaonda.clear();
        }
        atualizarinterface();
    }

    @FXML public void acaoataquebasico(ActionEvent e) { executarturno("ataque_basico"); }
    @FXML public void acaoataquemagico(ActionEvent e) { executarturno("ataque_especial"); }
    @FXML public void acaohabilidade1(ActionEvent e) { executarturno(player.getsloth1()); }
    @FXML public void acaohabilidade2(ActionEvent e) { executarturno(player.getsloth2()); }
    
    @FXML public void acaofugir(ActionEvent e) {
        if(player.getpocoes().isEmpty()) { textlogbatalha.appendText("sem pocoes.\n"); return; }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(player.getpocoes().get(0), player.getpocoes());
        dialog.showAndWait().ifPresent(pocao -> {
            player.getpocoes().remove(pocao);
            if(pocao.equals("pocao de vida")) curarplayer(50);
            else if(pocao.equals("pocao grande de vida")) curarplayer(150);
            else if(pocao.equals("pocao de dano fisico")) bufffisico = true;
            else if(pocao.equals("pocao de dano magico")) buffmagico = true;
            textlogbatalha.appendText("usou " + pocao + ".\n");
            turnoinimigos();
            fimdeturno();
        });
    }

    private void curarplayer(int valor) {
        if(!maldicaolich) vidaatualplayer = Math.min(player.getvidamaxima(), vidaatualplayer + valor);
    }

    private void executarturno(String acao) {
        if (inimigoatual == null || acao.equals("vazio")) return;
        heroi alvo = inimigoatual;
        boolean inimigoatacou = false;
        if (alvo.isatacaprimeiro()) { turnoinimigos(); inimigoatacou = true; }
        if (vidaatualplayer > 0) processaracaoplayer(acao);
        if (inimigoatual == alvo && inimigoatual != null && inimigoatual.getvida() > 0 && !inimigoatacou) turnoinimigos();
        fimdeturno();
    }

    private void processaracaoplayer(String acao) {
        String arma = player.getarmaequipada();
        int basearma = 20;
        if(arma.equals("machado de guerra")) basearma = 35;
        else if(arma.equals("arco rapido")) basearma = 25;
        else if(arma.equals("espada de honra")) basearma = 40;
        else if(arma.equals("espada nobre")) basearma = 60;
        else if(arma.equals("arco magico")) basearma = 55;
        else if(arma.equals("espada sagrada")) basearma = 80;
        else if(arma.equals("arco sagrado")) basearma = 75;

        double multf = 1.0 + (player.getatf() / 100.0);
        double multm = 1.0 + (player.getatm() / 100.0);
        if(bufffisico) multf += 0.3;
        if(buffmagico) multm += 0.3;
        if(turnosbuffaprimoramento > 0) { multf += 0.5; multm += 0.5; }

        int dano = 0;
        boolean magico = false;

        if(acao.equals("ataque_basico")) {
            dano = (int)(basearma * multf);
            if(arma.equals("arco magico")) dano = (int)(basearma * (multf + (player.getatm()/100.0)));
        } else if(acao.equals("ataque_especial")) {
            if(arma.equals("espada antiga")) { dano = (int)(basearma * multf * 2); }
            else if(arma.equals("machado de guerra")) { dano = (int)(basearma * multf * 1.5); }
            else if(arma.equals("arco rapido")) { dano = (int)(basearma * multf * 1.5); }
            else if(arma.equals("espada de honra")) { dano = (int)(basearma * multf); }
            else if(arma.equals("espada nobre")) { dano = (int)(basearma * multf); if(inimigoatual.getvida() < vidamaximainimigo * 0.3) dano *= 2; }
            else if(arma.equals("arco magico")) { dano = (int)(basearma * multf); }
            else if(arma.equals("espada sagrada")) { dano = (int)(basearma * multf); }
            else if(arma.equals("arco sagrado")) { dano = (int)(basearma * multf * 2); }
        } else if(acao.equals("bola de fogo")) { dano = (int)(25 * multm); magico = true; }
        else if(acao.equals("missil de mana")) { dano = (int)(35 * multm); magico = true; }
        else if(acao.equals("relampago")) { dano = (int)(50 * multm); magico = true; }
        else if(acao.equals("cura")) { curarplayer((int)(player.getvidamaxima() * 0.2)); textlogbatalha.appendText("curou.\n"); return; }
        else if(acao.equals("tempestade de fogo")) { dano = (int)(80 * multm); magico = true; }
        else if(acao.equals("meteoro")) { dano = (int)(100 * multm); magico = true; }
        else if(acao.equals("tornado")) { dano = (int)(50 * multm); magico = true; turnostempestade = 3; }
        else if(acao.equals("tempestade de gelo")) { dano = (int)(90 * multm); magico = true; danoreduzido = 30; }
        else if(acao.equals("explosao")) {
            if(castarmeteoro == 0) { castarmeteoro = 1; textlogbatalha.appendText("preparando explosao.\n"); return; }
            else { dano = (int)(120 * multm); magico = true; castarmeteoro = 0; }
        }
        else if(acao.equals("restauracao")) { curarplayer((int)(player.getvidamaxima() * 0.4)); textlogbatalha.appendText("restaurou.\n"); return; }
        else if(acao.equals("aprimoramento")) { turnosbuffaprimoramento = 3; textlogbatalha.appendText("aprimorado.\n"); return; }
        else if(acao.equals("maldicao")) { textlogbatalha.appendText("amaldicoado.\n"); return; }

        if(inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if(magico && c.isimunemagico()) dano = 0;
            if(magico && c.isreduzmagico()) dano /= 2;
            if(!magico && c.isreduzfisico()) dano /= 2;
        }

        inimigoatual.receberdano(dano);
        textlogbatalha.appendText("causou " + dano + " de dano.\n");
        verificarmorteinimigo();
    }

    private void verificarmorteinimigo() {
        if (inimigoatual.getvida() <= 0) {
            textlogbatalha.appendText("derrotado.\n");
            player.addpontos(inimigoatual.getpontos());
            
            String c = inimigoatual.getclasse();
            double r = Math.random() * 100;
            if(c.equals("guerreiro") && r < 5) dropsdaonda.add("machado de guerra");
            else if(c.equals("arqueiro") && r < 5) dropsdaonda.add("arco rapido");
            else if(c.equals("cavaleiro") && r < 5) dropsdaonda.add("espada de honra");
            else if(c.equals("mago de fogo") && r < 5) dropsdaonda.add("explosao");
            else if(c.equals("mago de gelo") && r < 5) dropsdaonda.add("tempestade de gelo");
            else if(c.equals("mago de vento") && r < 5) dropsdaonda.add("tornado");
            else if(c.equals("cavaleiro nobre") && r < 10) dropsdaonda.add("espada nobre");
            else if(c.equals("principe elfico") && r < 10) dropsdaonda.add("arco magico");
            else if(c.equals("santa") && r < 10) dropsdaonda.add("restauracao");
            else if(c.equals("heroi da profecia") && r < 10) dropsdaonda.add("espada sagrada");
            else if(c.equals("arquimago") && r < 10) dropsdaonda.add("aprimoramento");
            else if(c.equals("arqueiro sagrado") && r < 10) dropsdaonda.add("arco sagrado");
            else if(c.equals("lich") && r < 10) dropsdaonda.add("maldicao");
            else if(c.equals("guardiao do mundo") && r < 50) dropsdaonda.add("elixir da vida");
            else if(c.equals("deusa")) dropsdaonda.add("coracao divino");

            if (inimigoatual instanceof chefe) {
                textlogbatalha.appendText("onda limpa.\n");
                
                SelecaoPersonagemController.chefesderrotados[SelecaoPersonagemController.slotativo]++;
                if (SelecaoOndaController.ondaselecionada >= SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo]) {
                    SelecaoPersonagemController.ondasliberadas[SelecaoPersonagemController.slotativo] = SelecaoOndaController.ondaselecionada + 1;
                }
                SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
                SelecaoPersonagemController.salvardados();

                inimigoatual = null;
                btnproximaonda.setVisible(true);
                btnirparaloja.setVisible(true);
                desativarbotoes();
                for(String d : dropsdaonda) {
                    if(d.contains("pocao") || d.contains("elixir") || d.contains("coracao")) player.addpocao(d);
                    else if(d.contains("espada") || d.contains("arco") || d.contains("machado")) player.addarma(d);
                    else player.addmagia(d);
                }
                dropsdaonda.clear();
            } else {
                SelecaoPersonagemController.heroisderrotados[SelecaoPersonagemController.slotativo]++;
                SelecaoPersonagemController.salvardados();
                
                indexinimigo++;
                carregarproximoinimigo();
            }
        }
    }

    private void turnoinimigos() {
        if (inimigoatual == null) return;
        int danoi = inimigoatual.getdano();
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            c.aplicaraumentodano();
            danoi = c.getdano();
            if (c.isamaldicoa()) maldicaolich = true;
        } else if (inimigoatual.getclasse().contains("mago") && Math.random() < 0.25) {
            danoi += (danoi * 0.2);
        }
        
        if (danoreduzido > 0) { danoi -= (danoi * danoreduzido / 100); danoreduzido = 0; }
        
        double multdef = Math.min(0.8, player.getdefesa() / 100.0);
        int danofinal = (int)(danoi * (1.0 - multdef));
        
        if(player.getarmaequipada().equals("espada de honra")) inimigoatual.receberdano((int)(danofinal * 0.25));
        
        vidaatualplayer -= Math.max(1, danofinal);
        textlogbatalha.appendText("sofreu " + danofinal + ".\n");
        if (vidaatualplayer <= 0) {
            SelecaoPersonagemController.mortes[SelecaoPersonagemController.slotativo]++;
            SelecaoPersonagemController.niveis[SelecaoPersonagemController.slotativo] = player.getnivel();
            SelecaoPersonagemController.salvardados();
            trocartela("/view/morte.fxml");
        }
    }

    private void fimdeturno() {
        if (turnostempestade > 0 && inimigoatual != null) {
            inimigoatual.receberdano(50);
            turnostempestade--;
            verificarmorteinimigo();
        }
        if (turnosbuffaprimoramento > 0) turnosbuffaprimoramento--;
        if (inimigoatual instanceof chefe) {
            chefe c = (chefe) inimigoatual;
            if (c.getcuraturno() > 0) c.curar(c.getcuraturno());
        }
        atualizarinterface();
    }

    private void atualizarinterface() {
        labelonda.setText("onda " + SelecaoOndaController.ondaselecionada);
        labelyen.setText("pontos " + player.getpontosdemoniacos());
        labelvidaplayer.setText("hp " + Math.max(0, vidaatualplayer) + "/" + player.getvidamaxima());
        
        btnataquefisico.setText("atacar");
        btnataquemagico.setText("especial");
        btnhabilidade1.setText(player.getsloth1());
        btnhabilidade2.setText(player.getsloth2());
        btnfugir.setText("pocoes (" + player.getpocoes().size() + ")");
        
        barrahpplayer.setProgress((double) Math.max(0, vidaatualplayer) / player.getvidamaxima());
        barraxp.setProgress(0.0);
        if (inimigoatual != null) {
            labelnomeinimigo.setText(inimigoatual.getclasse());
            labelvidainimigo.setText(Math.max(0, inimigoatual.getvida()) + "/" + vidamaximainimigo);
            barrahpinimigo.setProgress((double) Math.max(0, inimigoatual.getvida()) / vidamaximainimigo);
        }
    }

    private void trocartela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) labelonda.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception ex) { }
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
    @FXML public void acaoirparaloja(ActionEvent e) { trocartela("/view/loja.fxml"); }
}