package za.co.wethinkcode.toyrobot;

public class RightCommand extends Command{

    public RightCommand() {
        super("right");
    }

    @Override
    public boolean execute(Robot target) {
        target.getWorld().updateDirection(true);

        if(isRunningReplay() == false){
            target.addCommandToHistory(this);
        }
        target.setStatus("Turned right.");
        return true;
    }
}
