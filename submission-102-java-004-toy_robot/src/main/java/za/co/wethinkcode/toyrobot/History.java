package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;

public class History {
    private ArrayList commandsList;

    public History() {
        this.commandsList = new ArrayList();
    }

    public ArrayList getCommandsList() {
        return commandsList;
    }

    public void addCommand(Command command) {
        this.commandsList.add(command);
    }

    public void clearHistory() {
        this.commandsList.clear();
    }
}
