package za.co.wethinkcode.toyrobot;

public class ResetCommand extends Command {
    public ResetCommand() {
        super("reset");
    }


    @Override
    public boolean execute(Robot target) {
        target.getWorld().reset();
        target.setStatus("Ready");
        return true;
    }
}
