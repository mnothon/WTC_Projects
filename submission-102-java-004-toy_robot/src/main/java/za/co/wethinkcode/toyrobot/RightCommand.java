package za.co.wethinkcode.toyrobot;

public class RightCommand extends Command{

    public RightCommand() {
        super("right");
    }


    /**
     * Executes the right command by calling the updateDirection method in the AbstractWorld class and passing a
     * boolean, true, to indicate that robot should turn right. Then sets the the robot status with the appropriate status.
     * @param target
     * @return true to indicate command has been executed.
     */
    @Override
    public boolean execute(Robot target) {
        target.getWorld().updateDirection(true);

        if(target.isRunningReplay() == false){
            target.addCommandToHistory(this);
        }
        target.setStatus("Turned right.");
        return true;
    }
}
