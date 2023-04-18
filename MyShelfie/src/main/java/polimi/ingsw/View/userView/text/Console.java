package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Scanner;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Console {

    public Console(){

    }

    public void init() {
        AnsiConsole.systemInstall();
    }


    public void show_allPlayers(GameModelImmutable model) {
        System.out.println("Current Players: \n" + model.toStringListPlayers());
    }

    public void show_titleMyShelfie() {
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().fg(YELLOW).a("""

                ███╗░░░███╗██╗░░░██╗        ░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
                ████╗░████║╚██╗░██╔╝        ██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
                ██╔████╔██║░╚████╔╝░        ╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
                ██║╚██╔╝██║░░╚██╔╝░░        ░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
                ██║░╚═╝░██║░░░██║░░░        ██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
                ╚═╝░░░░░╚═╝░░░╚═╝░░░        ╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝
                """).reset());

    }

    public void show_grabbedTile(String nickname, GameModelImmutable model) {
        StringBuilder ris = new StringBuilder("| ");
        for (Tile t : model.getHandOfCurrentPlaying()) {
            switch (t.getType()) {
                case CAT -> ris.append(ansi().fg(GREEN).a(t.toString()).fg(DEFAULT)).append(" | ");
                case TROPHY -> ris.append(ansi().fg(CYAN).a(t.toString()).fg(DEFAULT)).append(" | ");
                case PLANT -> ris.append(ansi().fg(MAGENTA).a(t.toString()).fg(DEFAULT)).append(" | ");
                case BOOK -> ris.append(ansi().fg(WHITE).a(t.toString()).fg(DEFAULT)).append(" | ");
                case ACTIVITY -> ris.append(ansi().fg(YELLOW).a(t.toString()).fg(DEFAULT)).append(" | ");
                case FRAME -> ris.append(ansi().fg(BLUE).a(t.toString()).fg(DEFAULT)).append(" | ");
            }
        }
        System.out.println(nickname + ": Player: " + model.getNicknameCurrentPlaying() + " has grabbed some tiles: " + ris);
    }


    public void show_playground(GameModelImmutable model) {
        System.out.println("GameID: [" + model.getGameId().toString() + "] \n" + model.getPg().toString());
    }

    public void showAllShelves(GameModelImmutable model) {
        int i = DefaultValue.displayShelfStartingCol;
        for (Player p : model.getPlayers()) {
            System.out.print(ansi().cursor(DefaultValue.displayShelfRow, i - 3).toString() +
                    p.getNickname() + ": " + p.getShelf().toString(i));
            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(" ");
    }

    public void showPlayerJoined(GameModelImmutable gameModel) throws IOException, InterruptedException {
        clearCMD();
        show_titleMyShelfie();
        System.out.println(ansi().cursor(10, 0).a("GameID: [" + gameModel.getGameId().toString() + "]\n").fg(DEFAULT));
        System.out.flush();
        //StringBuilder players = new StringBuilder();
        StringBuilder ris = new StringBuilder();

        int i = 0;
        for (Player p : gameModel.getPlayers()) {
            if (p.getReadyToStart()) {
                ris.append(ansi().cursor(12 + +i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" is ready!\n");
            } else {
                ris.append(ansi().cursor(12 + +i, 0)).append("[EVENT]: ").append(p.getNickname()).append(" has joined!\n");
            }
            i++;
        }
        System.out.println(ris);
        //TODO:
        // need to check if the player is ready or not, and
        // in case he's ready not show him this line, now everyone
        // will see it
        System.out.println(ansi().cursor(17, 0).fg(WHITE).a("> When you are ready to start, enter (y): \n"));
        System.out.flush();
    }

    public void show_Publisher() throws IOException, InterruptedException {
        clearCMD();
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println(ansi().cursor(1, 1).fg(YELLOW).a("""
                                                                                                           \s
                                                                                                           \s
                  ,----..                                                                                  \s
                 /   /   \\                                   ,--,                                          \s
                |   :     :  __  ,-.                 ,---, ,--.'|    ,---.                                 \s
                .   |  ;. /,' ,'/ /|             ,-+-. /  ||  |,    '   ,'\\                                \s
                .   ; /--` '  | |' | ,--.--.    ,--.'|'   |`--'_   /   /   |                               \s
                ;   | ;    |  |   ,'/       \\  |   |  ,"' |,' ,'| .   ; ,. :                               \s
                |   : |    '  :  / .--.  .-. | |   | /  | |'  | | '   | |: :                               \s
                .   | '___ |  | '   \\__\\/: . . |   | |  | ||  | : '   | .; :                               \s
                '   ; : .'|;  : |   ," .--.; | |   | |  |/ '  : |_|   :    |                               \s
                '   | '/  :|  , ;  /  /  ,.  | |   | |--'  |  | '.'\\   \\  /                                \s
                |   :    /  ---'  ;  :   .'   \\|   |/      ;  :    ;`----'                                 \s
                 \\   \\ .'         |  ,     .-./'---'       |  ,   /                                        \s
                  `---`            `--`---'                 ---`-'                                         \s
                  ,----..                                   ___                                            \s
                 /   /   \\                                ,--.'|_    ,--,                                  \s
                |   :     :  __  ,-.                      |  | :,' ,--.'|    ,---.        ,---,            \s
                .   |  ;. /,' ,'/ /|                      :  : ' : |  |,    '   ,'\\   ,-+-. /  | .--.--.   \s
                .   ; /--` '  | |' | ,---.     ,--.--.  .;__,'  /  `--'_   /   /   | ,--.'|'   |/  /    '  \s
                ;   | ;    |  |   ,'/     \\   /       \\ |  |   |   ,' ,'| .   ; ,. :|   |  ,"' |  :  /`./  \s
                |   : |    '  :  / /    /  | .--.  .-. |:__,'| :   '  | | '   | |: :|   | /  | |  :  ;_    \s
                .   | '___ |  | ' .    ' / |  \\__\\/: . .  '  : |__ |  | : '   | .; :|   | |  | |\\  \\    `. \s
                '   ; : .'|;  : | '   ;   /|  ," .--.; |  |  | '.'|'  : |_|   :    ||   | |  |/  `----.   \\\s
                '   | '/  :|  , ; '   |  / | /  /  ,.  |  ;  :    ;|  | '.'\\   \\  / |   | |--'  /  /`--'  /\s
                |   :    /  ---'  |   :    |;  :   .'   \\ |  ,   / ;  :    ;`----'  |   |/     '--'.     / \s
                 \\   \\ .'          \\   \\  / |  ,     .-./  ---`-'  |  ,   /         '---'        `--'---'  \s
                  `---`             `----'   `--`---'               ---`-'                                 \s
                """).reset());
    }

    public void clearCMD() throws IOException, InterruptedException {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch(IOException | InterruptedException e){
            //per mac
            System.out.print("\033\143");

            //This might work too, but exec is deprecated
            //Runtime.getRuntime().exec("clear");
        }
    }
}
