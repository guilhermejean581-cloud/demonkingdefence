package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.ConexaoDB;
import model.player;
import java.io.IOException;

public class SelecaoPersonagemController {

    @FXML private Button btnslot1;
    @FXML private Button btnslot2;
    @FXML private Button btnslot3;
    @FXML private Button btnslot4;
    @FXML private Button btnslot5;
    @FXML private Button btndeletar1;
    @FXML private Button btndeletar2;
    @FXML private Button btndeletar3;
    @FXML private Button btndeletar4;
    @FXML private Button btndeletar5;

    public static String[] nomes = {"", "", "", "", ""};
    public static int[] ondasliberadas = {1, 1, 1, 1, 1};
    public static int[] mortes = {0, 0, 0, 0, 0};
    public static int[] heroisderrotados = {0, 0, 0, 0, 0};
    public static int[] chefesderrotados = {0, 0, 0, 0, 0};
    public static int[] niveis = {1, 1, 1, 1, 1}; 
    public static int[] idsbanco = {-1, -1, -1, -1, -1};
    public static int slotativo = 0;

    @FXML
    public void initialize() {
        carregardados();
        atualizarbotoes();
    }

    public static void carregardados() {
        for(int i=0; i<5; i++) { 
            nomes[i]=""; ondasliberadas[i]=1; mortes[i]=0; 
            heroisderrotados[i]=0; chefesderrotados[i]=0; niveis[i]=1; idsbanco[i]=-1; 
        }

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("select * from save_personagem order by slot_id")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int slot = rs.getInt("slot_id");
                if (slot >= 0 && slot <= 4) {
                    idsbanco[slot] = rs.getInt("id");
                    nomes[slot] = rs.getString("nome");
                    niveis[slot] = rs.getInt("nivel");
                    ondasliberadas[slot] = rs.getInt("ondas_liberadas");
                    mortes[slot] = rs.getInt("mortes");
                    heroisderrotados[slot] = rs.getInt("herois_derrotados");
                    chefesderrotados[slot] = rs.getInt("chefes_derrotados");
                }
            }
        } catch (Exception e) { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erro");
            alert.setHeaderText("erro ao conectar no banco de dados");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void salvardados() {
        if (slotativo < 0 || slotativo > 4 || idsbanco[slotativo] == -1) return;

        int id = idsbanco[slotativo];
        player p = player.getinstancia();

        try (Connection conn = ConexaoDB.conectar()) {
            String sqlsave = "update save_personagem set nivel=?, ondas_liberadas=?, mortes=?, herois_derrotados=?, chefes_derrotados=? where id=?";
            try(PreparedStatement stmt = conn.prepareStatement(sqlsave)) {
                stmt.setInt(1, niveis[slotativo]);
                stmt.setInt(2, ondasliberadas[slotativo]);
                stmt.setInt(3, mortes[slotativo]);
                stmt.setInt(4, heroisderrotados[slotativo]);
                stmt.setInt(5, chefesderrotados[slotativo]);
                stmt.setInt(6, id);
                stmt.executeUpdate();
            }

            String sqlstatus = "update player_status set hp=?, vida_maxima=?, atf=?, atm=?, defesa=?, pontos_demoniacos=?, slot_h1=?, slot_h2=?, arma_equipada=?, ouro=?, pontos_distribuir=?, especiais_desbloqueados=? where personagem_id=?";
            try(PreparedStatement stmt = conn.prepareStatement(sqlstatus)) {
                stmt.setInt(1, p.gethp());
                stmt.setInt(2, p.getvidamaxima());
                stmt.setInt(3, p.getatf());
                stmt.setInt(4, p.getatm());
                stmt.setInt(5, p.getdefesa());
                stmt.setInt(6, p.getpontosdemoniacos());
                stmt.setString(7, p.getsloth1());
                stmt.setString(8, p.getsloth2());
                stmt.setString(9, p.getarmaequipada());
                stmt.setInt(10, p.getouro());
                stmt.setInt(11, p.getpontosdistribuir());
                stmt.setString(12, String.join(",", p.getespeciaisdesbloqueados()));
                stmt.setInt(13, id);
                stmt.executeUpdate();
            }

            try(PreparedStatement stmt = conn.prepareStatement("delete from player_inventario where personagem_id=?")) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            String sqlinv = "insert into player_inventario (personagem_id, tipo_item, nome_item) values (?, ?, ?)";
            try(PreparedStatement stmt = conn.prepareStatement(sqlinv)) {
                for(String arma : p.getarmas()) {
                    stmt.setInt(1, id); stmt.setString(2, "ARMA"); stmt.setString(3, arma); stmt.executeUpdate();
                }
                for(String magia : p.getmagias()) {
                    stmt.setInt(1, id); stmt.setString(2, "MAGIA"); stmt.setString(3, magia); stmt.executeUpdate();
                }
                for(String pocao : p.getpocoes()) {
                    stmt.setInt(1, id); stmt.setString(2, "POCAO"); stmt.setString(3, pocao); stmt.executeUpdate();
                }
            }

        } catch (Exception e) { }
    }

    private void atualizarbotoes() {
        Button[] slotbotoes = {btnslot1, btnslot2, btnslot3, btnslot4, btnslot5};
        Button[] deletebotoes = {btndeletar1, btndeletar2, btndeletar3, btndeletar4, btndeletar5};

        for (int i = 0; i < 5; i++) {
            if (!nomes[i].isEmpty()) {
                slotbotoes[i].setText(nomes[i] + "  -  nivel: " + niveis[i] + "  -  onda: " + ondasliberadas[i]);
                deletebotoes[i].setVisible(true);
            } else {
                slotbotoes[i].setText("criar personagem");
                deletebotoes[i].setVisible(false);
            }
        }
    }

    @FXML public void acaoslot1(ActionEvent e) { manipularslot(0, e); }
    @FXML public void acaoslot2(ActionEvent e) { manipularslot(1, e); }
    @FXML public void acaoslot3(ActionEvent e) { manipularslot(2, e); }
    @FXML public void acaoslot4(ActionEvent e) { manipularslot(3, e); }
    @FXML public void acaoslot5(ActionEvent e) { manipularslot(4, e); }

    @FXML public void acaodeletar1(ActionEvent e) { deletarslot(0); }
    @FXML public void acaodeletar2(ActionEvent e) { deletarslot(1); }
    @FXML public void acaodeletar3(ActionEvent e) { deletarslot(2); }
    @FXML public void acaodeletar4(ActionEvent e) { deletarslot(3); }
    @FXML public void acaodeletar5(ActionEvent e) { deletarslot(4); }

    private void manipularslot(int index, ActionEvent event) {
        if (nomes[index].isEmpty()) {
            criarpersonagemnoslot(index, event);
        } else {
            entrarnoslot(index, event);
        }
    }

    private void criarpersonagemnoslot(int index, ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("novo personagem");
        dialog.setHeaderText(null);
        dialog.setContentText("nome:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().isEmpty()) {
            String novonome = result.get();
            try (Connection conn = ConexaoDB.conectar()) {
                String sqlinsert = "insert into save_personagem (slot_id, nome, nivel, ondas_liberadas) values (?, ?, 1, 1)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlinsert, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, index);
                    stmt.setString(2, novonome);
                    stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int novoid = rs.getInt(1);
                        idsbanco[index] = novoid;

                        try(PreparedStatement ststatus = conn.prepareStatement("insert into player_status (personagem_id) values (?)")) {
                            ststatus.setInt(1, novoid);
                            ststatus.executeUpdate();
                        }
                        try(PreparedStatement stinv = conn.prepareStatement("insert into player_inventario (personagem_id, tipo_item, nome_item) values (?, ?, ?)")) {
                            stinv.setInt(1, novoid); stinv.setString(2, "ARMA"); stinv.setString(3, "espada antiga"); stinv.executeUpdate();
                            stinv.setInt(1, novoid); stinv.setString(2, "MAGIA"); stinv.setString(3, "bola de fogo"); stinv.executeUpdate();
                        }
                    }
                }
                carregardados();
                atualizarbotoes();
            } catch(Exception ex) { 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("erro");
                alert.setHeaderText("erro ao criar personagem");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void deletarslot(int index) {
        if (idsbanco[index] == -1) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmar");
        alert.setHeaderText(null);
        alert.setContentText("deseja realmente deletar " + nomes[index] + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = ConexaoDB.conectar();
                 PreparedStatement stmt = conn.prepareStatement("delete from save_personagem where id = ?")) {
                stmt.setInt(1, idsbanco[index]);
                stmt.executeUpdate();
                carregardados();
                atualizarbotoes();
            } catch(Exception ex) { 
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("erro");
                err.setHeaderText("erro ao deletar");
                err.setContentText(ex.getMessage());
                err.showAndWait();
            }
        }
    }

    private void entrarnoslot(int index, ActionEvent event) {
        slotativo = index;
        carregardadosplayer(idsbanco[index]);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/selecaoonda.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { }
    }

    private void carregardadosplayer(int id) {
        player p = player.getinstancia();
        p.limpardados();
        p.setidbanco(id);
        try (Connection conn = ConexaoDB.conectar()) {
            try(PreparedStatement stmt = conn.prepareStatement("select * from player_status where personagem_id = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    p.sethp(rs.getInt("hp"));
                    p.setatf(rs.getInt("atf"));
                    p.setatm(rs.getInt("atm"));
                    p.setdefesa(rs.getInt("defesa"));
                    p.setpontosdemoniacos(rs.getInt("pontos_demoniacos"));
                    p.setouro(rs.getInt("ouro"));
                    p.setpontosdistribuir(rs.getInt("pontos_distribuir"));
                    p.equiparslot1(rs.getString("slot_h1"));
                    p.equiparslot2(rs.getString("slot_h2"));
                    p.setarmaequipada(rs.getString("arma_equipada"));
                    String esp = rs.getString("especiais_desbloqueados");
                    if (esp != null && !esp.isEmpty()) {
                        for (String s : esp.split(",")) {
                            if (!s.trim().isEmpty()) p.desbloquarespecial(s.trim());
                        }
                    }
                }
            }
            try(PreparedStatement stmt = conn.prepareStatement("select * from player_inventario where personagem_id = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    String tipo = rs.getString("tipo_item").toUpperCase();
                    String nome = rs.getString("nome_item");
                    if(tipo.equals("ARMA")) p.addarma(nome);
                    else if(tipo.equals("MAGIA")) p.addmagia(nome);
                    else if(tipo.equals("POCAO")) p.addpocao(nome);
                }
            }
            p.setnivel(niveis[slotativo]);
        } catch(Exception e) { }
    }

    @FXML public void acaovoltar(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/menu.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) { }
    }
}