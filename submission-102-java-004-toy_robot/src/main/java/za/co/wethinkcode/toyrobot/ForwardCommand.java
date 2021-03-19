package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class ForwardCommand extends Command {
    @Override
        public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        if (target.getWorld().updatePosition(nrSteps) == IWorld.UpdateResponse.SUCCESS){
            if(isRunningReplay() == false){
                target.addCommandToHistory(this);
            }
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}

