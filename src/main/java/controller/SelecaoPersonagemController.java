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
    public static int[] ondasliberadas = {1, 1, 1, 1};
    public static int[] mortes = {0, 0, 0, 0};
    public static int[] heroisderrotados = {0, 0, 0, 0};
    public static int[] chefesderrotados = {0, 0, 0, 0};
    public static int[] niveis = {1, 1, 1, 1}; 
    public static int slotativo = 0;

    @FXML
    public void initialize() {
        carregardados();
        atualizarbotoes();
    }

    public static void carregardados() {
        try {
            File arquivo = new File("save_personagens.txt");
            if (arquivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(arquivo));
                for (int i = 0; i < 4; i++) {
                    String linhanome = br.readLine();
                    if (linhanome != null) nomes[i] = linhanome;
                    String linhaonda = br.readLine();
                    if (linhaonda != null) ondasliberadas[i] = Integer.parseInt(linhaonda);
                    String linhamortes = br.readLine();
                    if (linhamortes != null) mortes[i] = Integer.parseInt(linhamortes);
                    String linhaherois = br.readLine();
                    if (linhaherois != null) heroisderrotados[i] = Integer.parseInt(linhaherois);
                    String linhachefes = br.readLine();
                    if (linhachefes != null) chefesderrotados[i] = Integer.parseInt(linhachefes);
                    String linhanivel = br.readLine();
                    if (linhanivel != null && !linhanivel.isEmpty()) niveis[i] = Integer.parseInt(linhanivel);
                }
                br.close();
            }
        } catch (Exception e) { }
    }

    public static void salvardados() {
        try {
            File arquivo = new File("save_personagens.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
            for (int i = 0; i < 4; i++) {
                bw.write(nomes[i]); bw.newLine();
                bw.write(String.valueOf(ondasliberadas[i])); bw.newLine();
                bw.write(String.valueOf(mortes[i])); bw.newLine();
                bw.write(String.valueOf(heroisderrotados[i])); bw.newLine();
                bw.write(String.valueOf(chefesderrotados[i])); bw.newLine();
                bw.write(String.valueOf(niveis[i])); bw.newLine();
            }
            bw.close();
        } catch (IOException e) { }
    }

    private void atualizarbotoes() {
        btnSlot1.setText(!nomes[0].isEmpty() ? nomes[0] + " - nv. " + niveis[0] + " - onda " + ondasliberadas[0] : "criar personagem");
        btnSlot2.setText(!nomes[1].isEmpty() ? nomes[1] + " - nv. " + niveis[1] + " - onda " + ondasliberadas[1] : "criar personagem");
        btnSlot3.setText(!nomes[2].isEmpty() ? nomes[2] + " - nv. " + niveis[2] + " - onda " + ondasliberadas[2] : "criar personagem");
        btnSlot4.setText(!nomes[3].isEmpty() ? nomes[3] + " - nv. " + niveis[3] + " - onda " + ondasliberadas[3] : "criar personagem");
    }

    @FXML public void acaoSlot1(ActionEvent e) { processarslot(0, e); }
    @FXML public void acaoSlot2(ActionEvent e) { processarslot(1, e); }
    @FXML public void acaoSlot3(ActionEvent e) { processarslot(2, e); }
    @FXML public void acaoSlot4(ActionEvent e) { processarslot(3, e); }

    private void processarslot(int index, ActionEvent event) {
        if (nomes[index].isEmpty()) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("novo personagem");
            dialog.setHeaderText(null);
            dialog.setContentText("nome:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                nomes[index] = result.get();
                niveis[index] = 1;
                ondasliberadas[index] = 1;
                salvardados();
                atualizarbotoes();
            }
        } else {
            slotativo = index;
            model.player.getinstancia().setnivel(niveis[index]); 
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) { }
        }
    }

    @FXML
    public void acaodeletar(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("deletar");
        dialog.setHeaderText(null);
        dialog.setContentText("digite o nome do personagem para deletar:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String nomedigitado = result.get();
            for (int i = 0; i < 4; i++) {
                if (nomes[i].equals(nomedigitado)) {
                    nomes[i] = ""; ondasliberadas[i] = 1; niveis[i] = 1;
                    mortes[i] = 0; heroisderrotados[i] = 0; chefesderrotados[i] = 0;
                    salvardados();
                    atualizarbotoes();
                    break;
                }
            }
        }
    }

    @FXML public void acaoVoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) { }
    }
}