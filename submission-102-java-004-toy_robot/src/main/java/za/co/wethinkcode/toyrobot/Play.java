package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;

import java.util.Scanner;

public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        IWorld world;

        scanner = new Scanner(System.in);
        Robot robot;
//        System.out.println(args[0]);

        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);

        if (args.length > 0) {
            switch (args[0]) {
                case "turtle":
                    world = new TurtleWorld();
                    break ;
                default:
                    world = new TextWorld();
            }
            robot.setWorld(world);
        } else {
            world = new TextWorld();
            robot.setWorld(world);
        }

        System.out.println("Hello Kiddo!");

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
