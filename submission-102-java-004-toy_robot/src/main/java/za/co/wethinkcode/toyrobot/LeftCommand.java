package za.co.wethinkcode.toyrobot;

public class LeftCommand extends Command {

    public LeftCommand() {
        super("left");
    }

    @Override
    public boolean execute(Robot target) {
        target.getWorld().updateDirection(false);

        if(isRunningReplay() == false){
            target.addCommandToHistory(this);
        }
        target.setStatus("Turned left.");
        return true;
    }
}
