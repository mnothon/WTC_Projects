package za.co.wethinkcode.toyrobot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus("I can understand these commands:\n" +
                "OFF                 - Shut down robot\n" +
                "HELP                - provide information about commands\n" +
                "FORWARD             - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
                "BACK                - backwards by specified number of steps, e.g. 'BACK 10'\n" +
                "LEFT                - turn left 90 degrees\n" +
                "RIGHT               - turn right 90 degrees\n" +
                "SPRINT              - move forward 'N' times by specified number of steps, each time moving forward 'N-1' steps\n" +
                "REPLAY              - replay all commands until that point.\n" +
                "REPLAY x-y          - replay all commands from the end from command x(inclusive) to command y(exclusive)\n" +
                "REPLAY REVERSED     - replays all commands in reverse\n" +
                "REPLAY REVERSED x-y - replay all commands from the end from command x(inclusive) to command y(exclusive) in reverse\n" +
                "REPLAY x            - replay last x commands\n" +
                "REPLAY REVERSED x   - replay last x commands in reverse\n" +
                "MAZERUN             - robot navigates its way around the obstacles to the top\n" +
                "MAZERUN TOP         - robot navigates its way around the obstacles to the top\n" +
                "MAZERUN RIGHT       - robot navigates its way around the obstacles to the right\n" +
                "MAZERUN LEFT        - robot navigates its way around the obstacles to the left\n" +
                "MAZERUN BOTTOM      - robot navigates its way around the obstacles to the bottom\n" +
                "RESET               - resets the robot position to the centre, resets robot direction to UP, clears world of all obstacles");
        return true;
    }
}
