package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplayCommand extends Command {
    private String finalString = "";

    public ReplayCommand(){
        super("replay");
    }

    public ReplayCommand(String argument) {
        super("replay", argument);
    }

    public ReplayCommand(String argument, String argument2) {
        super("replay", "reversed", argument2);
    }

    @Override
    public boolean execute(Robot target) {
//        ArrayList<Command> commandsList = getCommandsList();
        ArrayList<Command> commandsList = target.getHistory();
        setRunningReplay(true);
        if (getArgument() == "") {
            doReplayNoArgs(commandsList, target);
            target.setStatus("replayed " + commandsList.size() + " commands.");
            setFinalString(getFinalString() + target.toString());
        } else if (getArgument2() == "") {
            String arg = getArgument();
            if (arg.equalsIgnoreCase("reversed")) {
                Collections.reverse(commandsList);
                doReplayNoArgs(commandsList, target);
                target.setStatus("replayed " + commandsList.size() + " commands.");
            } else {
                int argument = doReplayOneArg(commandsList, target, getArgument());
                target.setStatus("replayed " + argument + " commands.");
            }
            setFinalString(getFinalString() + target.toString());
        } else {
            int argument = doReplayOneArg(commandsList, target, getArgument2());
            target.setStatus("replayed " + argument + " commands.");
            setFinalString(getFinalString() + target.toString());
        }
        target.setIsPlayingMultipleCommands(true);
        target.setStatus(getFinalString());
        commandsList.clear();
        return true;
    }

    public int doReplayOneArg(ArrayList<Command> commandsList, Robot target, String arg) {
        int size = commandsList.size();
        int argument;
        if (isNumeric(arg)) {
            argument = Integer.parseInt(arg);
            if(argument > size) {
                return 0;
            }
            doOneArgForLoop(target, commandsList, argument, size);
            return argument;
        } else {
            String[] digits = arg.split("-", 2);
            if(isNumeric(digits[0]) && isNumeric(digits[1]) && digits.length == 2) {
                argument = Integer.parseInt(digits[0]) - Integer.parseInt(digits[1]);
                if (Integer.parseInt(digits[0])> size) {
                    return 0;
                }
                doOneArgWithHyphenForLoop(target, commandsList, digits);
                return argument;
            }
        }
        return 0;
    }

    public void doReplayNoArgs(ArrayList<Command> commandsList, Robot target) {
        for (int i = 0; i < commandsList.size(); i++) {
            target.handleCommand(commandsList.get(i));
            setFinalString(getFinalString() + target.toString() + "\n");
        }
    }

    public void doOneArgForLoop(Robot target, ArrayList<Command> commandsList, int argument, int size) {
        if (getArgument2() == "") {
            int start = size - argument;
            for (int i = start; i < size; i++) {
                target.handleCommand(commandsList.get(i));
                setFinalString(getFinalString() + target.toString() + "\n");
            }
        } else {
            for (int i = size - 1; i >= size - argument; i--) {
                target.handleCommand(commandsList.get(i));
                setFinalString(getFinalString() + target.toString() + "\n");
            }
        }
    }

    public void doOneArgWithHyphenForLoop(Robot target, ArrayList<Command> commandsList, String[] digits) {
        int firstNum = Integer.parseInt(digits[0]);
        int secondNum = Integer.parseInt(digits[1]);
        int start = commandsList.size() - firstNum;
        int end = start + (firstNum - secondNum);

        if (getArgument2() == "") {
            for (int i = start; i < end; i ++) {
                target.handleCommand(commandsList.get(i));
                setFinalString(getFinalString() + target.toString() + "\n");
            }
        } else {
            for (int i = end - 1; i >= start; i --) {
                target.handleCommand(commandsList.get(i));
                setFinalString(getFinalString() + target.toString() + "\n");
            }
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public String getFinalString() {
        return finalString;
    }

    public void setFinalString(String finalString) {
        this.finalString = finalString;
    }
}
