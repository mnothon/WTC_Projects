package za.co.wethinkcode.toyrobot;

public class SprintCommand extends Command {

    public SprintCommand(String argument) {
        super("sprint", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        String finalString = "";
        for (int i = nrSteps; i > 0; i--) {
            Command forward = Command.create("forward " + i);
            target.handleCommand(forward);
            if (i == 1) {
                finalString = finalString + target.toString();
            } else {
                finalString = finalString + target.toString() + "\n";
            }
        }
        target.setIsPlayingMultipleCommands(true);
        target.setStatus(finalString);
        return true;
    }
}
