package tests;

import main.java.interpreter.BrainfuckInterpreter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nick on 09.06.16.
 */
public class BrainfuckCommandsFactoryTest {

    BrainfuckInterpreter bfInterpreter = new BrainfuckInterpreter();

    @Test
    public void makeIncrementDPCommandTest() throws Exception {
        bfInterpreter.setPointer(0);
        bfInterpreter.execute(">");
        assertEquals("Increment data pointer command doesn't work", 1, bfInterpreter.getPointer());
    }

    @Test
    public void makeDecrementDpCommandTest() throws Exception {
        bfInterpreter.setPointer(1);
        bfInterpreter.execute("<");
        assertEquals("Decrement data pointer command doesn't work", 0, bfInterpreter.getPointer());
    }

    @Test
    public void makeIncrementCommandTest() throws Exception {
        bfInterpreter.setValue((byte) 0);
        bfInterpreter.execute("+");
        assertEquals("Increment command doesn't work", 1, bfInterpreter.getPointer());
    }

    @Test
    public void makeDecrementCommandTest() throws Exception {
        bfInterpreter.setValue((byte) 1);
        bfInterpreter.execute("-");
        assertEquals("Decrement command doesn't work", 0, bfInterpreter.getPointer());
    }


    @Test
    public void makeCycleByStringTest() throws Exception {
        bfInterpreter.setPointer(0);
        bfInterpreter.setValue((byte) 0);
        bfInterpreter.setPointer(1);
        bfInterpreter.setValue((byte) 5);
        bfInterpreter.execute("[<+>-]");
        bfInterpreter.setPointer(0);
        assertEquals("Cycle making command doesn't work.", 5, bfInterpreter.getValue());
    }

}