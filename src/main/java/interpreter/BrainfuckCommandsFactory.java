package main.java.interpreter;

import java.util.List;

/**
 * Created by nick on 09.06.16.
 */
public class BrainfuckCommandsFactory implements CommandsFactory {
    @Override
    public Command makeCommandBySymbol(char symbol) {
        return null;
    }

    @Override
    public List<Command> makeCycleByString(String commands) {
        return null;
    }
}
