package za.co.wethinkcode.toyrobot;

public abstract class Command {
    private final String name;
    private String argument;
    private String argument2;
    private static boolean runningReplay;


    /**
     * Abstract method to execute command
     * @param target
     * @return
     */
    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
        this.argument2 = "";
        this.runningReplay = false;
    }


    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
        this.argument2 = "";
        this.runningReplay = false;
    }


    public Command(String name, String argument, String argument2) {
        this(name);
        this.argument = argument.trim();
        this.argument2 = argument2.trim();
        this.runningReplay = false;
    }


    public String getName() {
        return name;
    }


    public String getArgument() {
        return this.argument;
    }


    public String getArgument2() {
        return this.argument2;
    }


    public static boolean isRunningReplay() {
        return runningReplay;
    }


    public static void setRunningReplay(boolean runningReplay) {
        Command.runningReplay = runningReplay;
    }


    /**
     * Checks all the possible commands to see which ones are valid
     * @param instruction - the user input
     * @return
     */
    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ", 3);
        int numberOfArgs = args.length;
        switch (numberOfArgs) {
            case 1:
                return checkForOneArg(args, instruction);
            case 2:
                return checkForTwoArgs(args, instruction);
            case 3:
                return checkForThreeArgs(args, instruction);
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }


    /**
     * Check commands for one arg
     * @param args
     * @param instruction
     * @return
     */
    public static Command checkForOneArg(String[] args, String instruction) {
        switch (args[0].toLowerCase()) {
            case "shutdown":
            case "off":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "right":
                return new RightCommand();
            case "left":
                return new LeftCommand();
            case "replay":
                return new ReplayCommand();
            case "reset":
                return new ResetCommand();
            case "mazerun":
                return new MazerunnerCommand("mazerun", "top");
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }


    /**
     * Check commands for two args
     * @param args
     * @param instruction
     * @return
     */
    public static Command checkForTwoArgs(String[] args, String instruction) {
        switch (args[0]) {
            case "back":
                return new BackCommand(args[1]);
            case "sprint":
                return new SprintCommand(args[1]);
            case "forward":
                return new ForwardCommand(args[1]);
            case "replay":
                return new ReplayCommand(args[1]);
            case "mazerun":
                Command command = checkMazerunCommands(args[1]);
                if (command != null){
                    return command;
                } else {
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
                }
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }


    /**
     * Check commands for mazerun
     * @param arg
     * @return
     */
    public static Command checkMazerunCommands(String arg) {
        switch (arg.toLowerCase()) {
            case "top":
                return new MazerunnerCommand("mazerun", "top");
            case "right":
                return new MazerunnerCommand("mazerun", "right");
            case "left":
                return new MazerunnerCommand("mazerun", "left");
            case "bottom":
                return new MazerunnerCommand("mazerun", "bottom");
            default:
                return null;
        }
    }


    /**
     * Check commands for three commands
     * @param args
     * @param instruction
     * @return
     */
    public static Command checkForThreeArgs(String[] args, String instruction) {
        if(args[0].equalsIgnoreCase("replay") && args[1].equalsIgnoreCase("reversed")) {
            return new ReplayCommand(args[1], args[2]);
        } else {
            throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}

