package main.java.interpreter;

import java.util.List;

public interface CommandsFactory {
    Command makeCommandBySymbol(char symbol);
    List<Command> makeCycleByString(char[] commands, int currentPosition);
}
