package za.co.wethinkcode.toyrobot;

public class BackCommand extends Command {

    public BackCommand(String argument) {
        super("back", argument);
    }


    /**
     * Executes the back command by calling the updatePosition method in the AbstractWorld class and passing the
     * number of step the robot should move back. Then sets the the robot status with the appropriate status.
     * @param target
     * @return true to indicate command has been executed.
     */
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());

        switch (target.getWorld().updatePosition(-nrSteps)) {
            case SUCCESS:
                if(target.isRunningReplay() == false){
                    target.addCommandToHistory(this);
                }
                target.setStatus("Moved back by "+nrSteps+" steps.");
                break;
            case FAILED_OBSTRUCTED:
                target.setStatus("Sorry, there is an obstacle in the way.");
            default:
                target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }
}
