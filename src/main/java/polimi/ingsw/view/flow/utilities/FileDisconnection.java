package polimi.ingsw.view.flow.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import polimi.ingsw.model.DefaultValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * FileDisconnection class
 * FileDisconnection is the class that manages the disconnection of the player
 * It reads and writes the game id from and to a file using json (JSONParser)
 */
public class FileDisconnection {
    private final String path;

    /**
     * Init class
     */
    public FileDisconnection() {
        path = System.getProperty("user.home") + "/AppData/Roaming/.MyShelfie";
    }

    /**
     * Returns the game id from the file
     * @param nickname
     * @return
     */
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
        } catch (ParseException | IOException ex) {
            return -1;
        }
        assert gameId != null;
        if (LocalDateTime.parse(time).isBefore(LocalDateTime.now().plusSeconds(DefaultValue.twelveHS)))
            return Integer.parseInt(gameId);
        else
            return -1;
    }

    /**
     * Creates the file and writes the game id in it
     * @param nickname
     * @param gameId
     */
    @SuppressWarnings("unchecked")
    public void setLastGameId(String nickname, int gameId) {
        JSONObject data = new JSONObject();
        data.put(DefaultValue.gameIdData, Integer.toString(gameId));
        data.put(DefaultValue.gameIdTime, LocalDateTime.now().toString());
        //if the directory doesn't exist, create it
        new File(path).mkdirs();

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
