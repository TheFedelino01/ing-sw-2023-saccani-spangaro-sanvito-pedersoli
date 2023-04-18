package polimi.ingsw.View.userView.text;

import org.fusesource.jansi.AnsiConsole;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.GameModelView.GameModelImmutable;
import polimi.ingsw.Model.Player;
import polimi.ingsw.Model.Tile;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class Console {

    private List<String> importantEvents; //events that needs to be showed always in screen
    public Console(){
        init();
    }

    public void init() {
        AnsiConsole.systemInstall();
        importantEvents= new ArrayList<>();
    }

    public void addImportantEvent(String imp){
        //Want to show only the first maxnum_of_last_event_tobe_showed important event happened
        if(importantEvents.size()+1>=DefaultValue.maxnum_of_last_event_tobe_showed){
            importantEvents.remove(0);
        }
        importantEvents.add(imp);
        show_important_events();
    }

    public List<String> getImportantEvents(){
        return new ArrayList<>(importantEvents);
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
        //show_alwaysOn_elements();
    }

    public void show_grabbedTile(String nickname, GameModelImmutable model) {
        StringBuilder ris = new StringBuilder("| ");
        for (Tile t : model.getHandOfCurrentPlaying()) {
            switch (t.getType()) {
                case CAT -> ris.append(ansi().bg(GREEN).a(t.toString()).bg(DEFAULT)).append(" | ");
                case TROPHY -> ris.append(ansi().bg(CYAN).a(t.toString()).bg(DEFAULT)).append(" | ");
                case PLANT -> ris.append(ansi().bg(MAGENTA).a(t.toString()).bg(DEFAULT)).append(" | ");
                case BOOK -> ris.append(ansi().bg(WHITE).a(t.toString()).bg(DEFAULT)).append(" | ");
                case ACTIVITY -> ris.append(ansi().bg(YELLOW).a(t.toString()).bg(DEFAULT)).append(" | ");
                case FRAME -> ris.append(ansi().bg(BLUE).a(t.toString()).bg(DEFAULT)).append(" | ");
            }
        }
        System.out.println(nickname + ": Player: " + model.getNicknameCurrentPlaying() + " has grabbed some tiles: " + ris);
    }


    public void show_playground(GameModelImmutable model) {
        System.out.println("GameID: [" + model.getGameId().toString() + "] \n" + model.getPg().toString());
    }

    public void showAllShelves(GameModelImmutable model) {
        int i = DefaultValue.col_shelves;

        for (Player p : model.getPlayers()) {
            System.out.print(ansi().cursor(DefaultValue.row_shelves, i - 3).toString() +
                    p.getNickname() + ": " + p.getShelf().toString(i));
            i += DefaultValue.displayShelfNextCol;
        }
        System.out.println(" ");
    }

    public void showCommonCards(GameModelImmutable gameModel){
        StringBuilder ris = new StringBuilder();
        ris.append(ansi().cursor(DefaultValue.row_commonCards,DefaultValue.col_commonCards));
        int i = 0;
        for(CommonCard c : gameModel.getCommonCards()) {
            ris.append(c.toString(c.getCommonType(), i));
            i+=2;
        }
        System.out.println(ris);
    }

    public void showGoalCards(Player toShow){
        System.out.println(toShow.getSecretGoal().toString(DefaultValue.col_goalCards));
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

    private void show_important_events(){
        StringBuilder ris = new StringBuilder();
        int i=0;
        ris.append(ansi().fg(GREEN).cursor(DefaultValue.row_important_events+i, 85).bold().a("Latest Events:").fg(DEFAULT).boldOff());
        for(String s : importantEvents){
            ris.append(ansi().fg(WHITE).cursor(DefaultValue.row_important_events+1+i, 86).a(s).fg(DEFAULT));
            i++;
        }

        System.out.println(ris);
    }

    private void show_alwaysOn_elements(){
        show_important_events();
    }

    public void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch(IOException | InterruptedException e){
            //per mac
            System.out.print("\033\143");

            //This might work too, but exec is deprecated
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}