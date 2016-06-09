package main.java.interpreter;

import java.util.ArrayList;
import java.util.List;

public class BrainfuckInterpreter {
    private byte[] dataArray;

    private int maxDataArraySize;
    private int pointer = 0;
    private boolean errorFlag = false;
    private List<Command> commandList = new ArrayList<>();
    private CommandsFactory bcFactory = new BrainfuckCommandsFactory();

    public BrainfuckInterpreter() {
        this.maxDataArraySize = 30000;
        dataArray = new byte[maxDataArraySize];
    }


    private void interpretLine(String input) {
        char[] inputCharArray = input.toCharArray();
        int i = 0;
        while (i < inputCharArray.length) {
            if (inputCharArray[i] == '[') {
                List<Command> subBCList =
                        bcFactory.makeCycleByString(inputCharArray, i);
                i += countOperators(subBCList) + 2;
                commandList.add(new Cycle(subBCList));
            } else {
                commandList.add(bcFactory.makeCommandBySymbol(inputCharArray[i]));
                i++;
            }
        }
    }

    public void execute(String line) {
        interpretLine(line);
        commandList.forEach(Command::execute);
    }

    public int getPointer() {
        return pointer;
    }

    public int getValue() {
        return dataArray[pointer];
    }

    public void setPointer(int pos) {
        pointer = pos;
    }

    public void setValue(byte value) {
        dataArray[pointer] = value;
    }

    class BrainfuckCommandsFactory implements CommandsFactory {
        @Override
        public Command makeCommandBySymbol(char symbol) {
            switch (symbol) {
                case '>': return new Command() {
                    @Override
                    public void execute() {
                        if (pointer < (maxDataArraySize - 1))
                            pointer++;
                        else
                            pointer = 0;
                    }
                };
                case '<': return new Command() {
                    @Override
                    public void execute() {
                        if (pointer > 0)
                            pointer--;
                        else
                            pointer = maxDataArraySize;
                    }
                };
                case '+': return new Command() {
                    @Override
                    public void execute() {
                        dataArray[pointer]++;
                    }
                };
                case '-': return new Command() {
                    @Override
                    public void execute() {
                        dataArray[pointer]--;
                    }
                };
                case '.': return new Command() {
                    @Override
                    public void execute() {
                        System.out.print((char) dataArray[pointer]);
                    }
                };
                case ']': System.out.println("ERROR! '[' expected."); break;
                default: System.out.println("ERROR! Cannot resolve symbol " + symbol);
            }
            errorFlag = true;
            return null;
        }

        @Override
        public List<Command> makeCycleByString(char[] commands, int currentPosition) {
            List<Command> cycleCommands = new ArrayList<>();
            int i = currentPosition + 1;
            int counter = 1;
            while ((i < commands.length) && (counter > 0)) {
                switch (commands[i]) {
                    case '[': {
                        counter++;
                        List<Command> subCCommands =
                                makeCycleByString(commands, i);
                        cycleCommands.add(new Cycle(subCCommands));
                        i += countOperators(subCCommands);
                        break;
                    }
                    case ']': counter--; break;
                    default: cycleCommands.add(makeCommandBySymbol(commands[i]));
                }
                i++;
            }
            if (counter > 0) {
                errorFlag = true;
                System.out.println("ERROR! ']' expected.");
                return null;
            } else if (counter < 0) {
                errorFlag = true;
                System.out.println("ERROR! '[' expected.");
                return null;
            } else {
                return cycleCommands;
            }
        }
    }

    class Cycle implements Command {
        private List<Command> commands;

        public Cycle(List<Command> commands) {
            this.commands = commands;
        }

        @Override
        public void execute() {
            while(dataArray[pointer] != 0)
                for (Command command : commands) {
                    command.execute();
                }
        }

        List<Command> getCommands() {
            return commands;
        }
    }

    private int countOperators(List<Command> commandsList) {
        int operatorsNum = 0;
        if (commandsList != null)
            for (Command command : commandsList) {
                if (command instanceof Cycle)
                    operatorsNum += countOperators(
                            ((Cycle) command).getCommands()) + 2; //Cycle took 2 operators
                else
                    operatorsNum++;
            }
        return operatorsNum;
    }
}
