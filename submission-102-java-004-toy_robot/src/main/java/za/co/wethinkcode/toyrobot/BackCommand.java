package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class BackCommand extends Command {

    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        if (target.getWorld().updatePosition(-nrSteps) == IWorld.UpdateResponse.SUCCESS) {
//            setCommandsList(getName() + " " + getArgument());
            if(isRunningReplay() == false){
//                setCommandsList(this);
                target.addCommandToHistory(this);
            }
            target.setStatus("Moved back by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }
}
