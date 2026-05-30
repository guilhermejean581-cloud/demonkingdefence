package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class SelecaoPersonagemController {

    @FXML private Button btnSlot1;
    @FXML private Button btnSlot2;
    @FXML private Button btnSlot3;
    @FXML private Button btnSlot4;

    public static String[] nomes = {"", "", "", ""};
    public static int[] ondasLiberadas = {1, 1, 1, 1};
    public static int[] mortes = {0, 0, 0, 0};
    public static int[] heroisDerrotados = {0, 0, 0, 0};
    public static int[] chefesDerrotados = {0, 0, 0, 0};
    public static int slotAtivo = 0;

    @FXML
    public void initialize() {
        carregarDados();
        atualizarBotoes();
    }

    public static void carregarDados() {
        try {
            File arquivo = new File("save_personagens.txt");
            if (arquivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(arquivo));
                for (int i = 0; i < 4; i++) {
                    String linhaNome = br.readLine();
                    if (linhaNome != null) nomes[i] = linhaNome;
                    String linhaOnda = br.readLine();
                    if (linhaOnda != null) ondasLiberadas[i] = Integer.parseInt(linhaOnda);
                    String linhaMortes = br.readLine();
                    if (linhaMortes != null) mortes[i] = Integer.parseInt(linhaMortes);
                    String linhaHerois = br.readLine();
                    if (linhaHerois != null) heroisDerrotados[i] = Integer.parseInt(linhaHerois);
                    String linhaChefes = br.readLine();
                    if (linhaChefes != null) chefesDerrotados[i] = Integer.parseInt(linhaChefes);
                }
                br.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void salvarDados() {
        try {
            File arquivo = new File("save_personagens.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
            for (int i = 0; i < 4; i++) {
                bw.write(nomes[i]); bw.newLine();
                bw.write(String.valueOf(ondasLiberadas[i])); bw.newLine();
                bw.write(String.valueOf(mortes[i])); bw.newLine();
                bw.write(String.valueOf(heroisDerrotados[i])); bw.newLine();
                bw.write(String.valueOf(chefesDerrotados[i])); bw.newLine();
            }
            bw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void atualizarBotoes() {
        btnSlot1.setText(!nomes[0].isEmpty() ? nomes[0] + " - nv. " + ondasLiberadas[0] : "criar personagem");
        btnSlot2.setText(!nomes[1].isEmpty() ? nomes[1] + " - nv. " + ondasLiberadas[1] : "criar personagem");
        btnSlot3.setText(!nomes[2].isEmpty() ? nomes[2] + " - nv. " + ondasLiberadas[2] : "criar personagem");
        btnSlot4.setText(!nomes[3].isEmpty() ? nomes[3] + " - nv. " + ondasLiberadas[3] : "criar personagem");
    }

    @FXML public void acaoSlot1(ActionEvent e) { processarSlot(0, e); }
    @FXML public void acaoSlot2(ActionEvent e) { processarSlot(1, e); }
    @FXML public void acaoSlot3(ActionEvent e) { processarSlot(2, e); }
    @FXML public void acaoSlot4(ActionEvent e) { processarSlot(3, e); }

    private void processarSlot(int index, ActionEvent event) {
        if (nomes[index].isEmpty()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("novo personagem");
            dialog.setContentText("nome:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                nomes[index] = result.get();
                salvarDados();
                atualizarBotoes();
            }
        } else {
            slotAtivo = index;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @FXML
    public void acaodeletar(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("deletar");
        dialog.setContentText("digite o numero do slot (1-4) para deletar:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int slot = Integer.parseInt(result.get()) - 1;
                if (slot >= 0 && slot < 4) {
                    nomes[slot] = ""; ondasLiberadas[slot] = 1;
                    mortes[slot] = 0; heroisDerrotados[slot] = 0; chefesDerrotados[slot] = 0;
                    salvarDados();
                    atualizarBotoes();
                }
            } catch (Exception e) {}
        }
    }

    @FXML public void acaoVoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}