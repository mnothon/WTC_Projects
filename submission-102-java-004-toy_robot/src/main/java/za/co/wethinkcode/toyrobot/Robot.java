package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

import java.util.ArrayList;

public class Robot {

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private String status;
    private String name;
    private String platform;
    private History history;
    private boolean isPlayingMultipleCommands;
    private IWorld world;
    private boolean runningReplay;


    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.isPlayingMultipleCommands = false;
        this.platform = "text";
        this.history = new History();
        this.runningReplay = false;
    }


    public Robot(String name, String platform) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.isPlayingMultipleCommands = false;
        this.platform = platform;
        this.history = new History();
        this.runningReplay = false;
    }


    public void setWorld(IWorld world) {
        this.world = world;
    }


    public IWorld getWorld() {
        return world;
    }


    public String getStatus() {
        return this.status;
    }


    public ArrayList getHistory() {
        return this.history.getCommandsList();
    }


    public void addCommandToHistory(Command command) {
        this.history.addCommand(command);
    }


    public boolean handleCommand(Command command) {
        return command.execute(this);
    }


    @Override
    public String toString() {
       if (getIsPlayingMultipleCommands()) {
           setIsPlayingMultipleCommands(false);
           return this.status;
       } else {
           return "[" + getWorld().getPosition().getX() + "," + getWorld().getPosition().getY() + "] "
                   + this.name + "> " + this.status;
       }
    }


    public boolean isRunningReplay() {
        return this.runningReplay;
    }


    public boolean getIsPlayingMultipleCommands() {
        return isPlayingMultipleCommands;
    }


    public void setIsPlayingMultipleCommands(boolean isPlayingMultipleCommands) {
        this.isPlayingMultipleCommands = isPlayingMultipleCommands;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }
}