package main.java.interpreter;

import java.util.List;

public interface CommandsFactory {
    Command makeCommandBySymbol(char symbol);
    List<Command> makeCycleByString(String commands);
}
