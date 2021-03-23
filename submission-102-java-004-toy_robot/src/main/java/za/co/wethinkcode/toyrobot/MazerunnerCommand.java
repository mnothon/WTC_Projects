package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;

public class MazerunnerCommand extends Command{
    private String side;

    public MazerunnerCommand(String mazerun, String side) {
        super(mazerun, side);
        this.side = side;
    }


    /**
     * Facilitates the mazerun commands.
     * @param target
     * @return
     */
    @Override
    public boolean execute(Robot target) {
        switch (getSide()) {
            case "top":
                target.getWorld().getMazerunner().mazeRun(target, IWorld.Direction.UP);
                break ;
            case "right":
                target.getWorld().getMazerunner().mazeRun(target, IWorld.Direction.RIGHT);
                break ;
            case "bottom":
                target.getWorld().getMazerunner().mazeRun(target, IWorld.Direction.DOWN);
                break ;
            case "left":
                target.getWorld().getMazerunner().mazeRun(target, IWorld.Direction.LEFT);
                break ;
        }

        if (target.getWorld().getMazerunner().getFoundPath()) {
            target.setStatus("I am at the " + getSide().toLowerCase() + " edge. (Cost: "+target.getWorld().getMazerunner().getMazeRunCost()+" steps)");
        } else {
            target.setStatus("I am lost. (Cost: "+target.getWorld().getMazerunner().getMazeRunCost()+" steps)");
        }

        target.getWorld().getMazerunner().resetMazerunCost();
        return true;
    }


    public String getSide() {
        return side;
    }
}

