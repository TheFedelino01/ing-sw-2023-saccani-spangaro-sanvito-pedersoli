package polimi.ingsw.view.userView.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import polimi.ingsw.model.DefaultValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

public class FileDisconnection {
    private String path;

    public FileDisconnection() {
        path = System.getProperty("user.dir");
    }

    public int getLastGameId(String nickname) {
        //game data related to the player is stored in a json file named after the nickname the player had in that game
        String gameId = null;
        String time = null;
        JSONParser parser = new JSONParser();
        File file = new File(path + "/" + nickname + ".json");

        try (InputStream is = new FileInputStream(file);
             Reader reader = new InputStreamReader(Objects.requireNonNull(is, "Couldn't find json file"), StandardCharsets.UTF_8)) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            gameId = (String) obj.get(DefaultValue.gameIdData);
            time = (String) obj.get(DefaultValue.gameIdTime);
        } catch (ParseException | FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            return -1;
        }
        assert gameId != null;
        if (LocalDateTime.parse(time).isBefore(LocalDateTime.now().plusSeconds(DefaultValue.twelveHS)))
            return Integer.parseInt(gameId);
        else
            return -1;
    }

    @SuppressWarnings("unchecked")
    public void setLastGameId(String nickname, int gameId) {
        JSONObject data = new JSONObject();
        data.put(DefaultValue.gameIdData, Integer.toString(gameId));
        data.put(DefaultValue.gameIdTime, LocalDateTime.now().toString());

        File file = new File(path + "/" + nickname + ".json");
        try {
            //if the file does not exist, create it
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try (FileWriter fileWriter = new FileWriter(path + "/" + nickname + ".json")) {
            fileWriter.write(data.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
