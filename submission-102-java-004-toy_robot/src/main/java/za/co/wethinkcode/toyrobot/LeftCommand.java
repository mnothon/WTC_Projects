package za.co.wethinkcode.toyrobot;

public class LeftCommand extends Command {

    public LeftCommand() {
        super("left");
    }


    /**
     * Executes the left command by calling the updateDirection method in the AbstractWorld class and passing a
     * boolean, false, to indicate that robot should turn left. Then sets the the robot status with the appropriate status.
     * @param target
     * @return true to indicate command has been executed.
     */
    @Override
    public boolean execute(Robot target) {
        target.getWorld().updateDirection(false);

        if(target.isRunningReplay() == false){
            target.addCommandToHistory(this);
        }
        target.setStatus("Turned left.");
        return true;
    }
}
