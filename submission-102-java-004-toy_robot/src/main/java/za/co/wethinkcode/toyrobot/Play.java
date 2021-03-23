package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.maze.*;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;

import java.util.Scanner;

public class Play {
    static Scanner scanner;

    /**
     * Method that controls the entire game, takes in commandline arguments that specify which World the program
     * will use and which maze to use as well.
     * Game ends when this method is done running.
     * @param args - arg[0] : Which world is being used, either turtle or text
     *             - arg[1] : Which maze is being used.
     */
    public static void main(String[] args) {
        IWorld world;

        scanner = new Scanner(System.in);
        Robot robot;

        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);

        System.out.println("Hello Kiddo!");

        if (args.length == 1 || args.length == 2) {
            world = handleOneArg(args[0], args);
        } else {
            System.out.println("Loaded RandomMaze.");
            world = new TextWorld(new RandomMaze());
        }
        robot.setWorld(world);

        System.out.println(robot.toString());

        Command command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }
            System.out.println(robot.toString());
        } while (shouldContinue);
    }


    public static void printRobot(Robot target){
        System.out.println(target);
    }


    /**
     * Handle 2 args for passing into maze
     * @param arg
     * @return
     */
    public static Maze handleTwoArgs(String arg) {

        switch (arg.toLowerCase()) {
            case "emptymaze":
                System.out.println("Loaded EmptyMaze.");
                return new EmptyMaze();
            case "simplemaze":
                System.out.println("Loaded SimpleMaze.");
                return new SimpleMaze();
            case "designedmaze":
                System.out.println("Loaded DesignedMaze.");
                return new DesignedMaze();
            default:
                System.out.println("Loaded RandomMaze.");
                return new RandomMaze();
        }
    }


    public static IWorld handleOneArg(String arg, String[] args) {
        Maze maze;

        if (args.length > 1) {
            maze = handleTwoArgs(args[1]);
        } else {
            maze = new RandomMaze();
        }
        switch (arg.toLowerCase()) {
            case "turtle":
                return new TurtleWorld(maze);
            default:
                return new TextWorld(maze);
        }
    }


    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}
