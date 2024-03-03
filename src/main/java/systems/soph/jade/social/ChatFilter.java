package systems.soph.jade.social;

import java.util.List;

public class ChatFilter {

    static List<String> blockList = List.of(
            "nigger",
            "n1gger",
            "nigg3r",
            "n1gg3r",
            "nigga",
            "n1gga",
            "niga",
            "fag",
            "f@g",
            "f4g",
            "tranny",
            "tr@nny",
            "tr4nny",
            "trany",
            "tr@ny",
            "tr4ny",
            "trannie",
            "tr@nnie",
            "tr4nnie",
            "retard",
            "r3tard",
            "ret4rd",
            "ret@rd",
            "autist",
            "kys",
            "kill yourself",
            "kill urself",
            "hang yourself",
            "hang urself"
    );

    public static boolean filter(String message) {
        for (String word : blockList) {
            if (message.contains(word)) {
                return true;
            }
        }
        return false;
    }

}
