package ServerModule.commands;


import common.exceptions.NumberOutOfBoundsException;
import common.exceptions.WrongAmountOfCoordinatesException;

import java.util.*;

public abstract class Command {
    private String name;
    private String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @param argument after command name.
     * @return execute status
     */
    public abstract boolean execute(String argument, Object objectArgument);

    /**
     * @return Name and usage way of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the command.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) &&
                Objects.equals(description, command.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }

}
