package ServerModule.commands;

import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.exceptions.WrongAmountOfArgumentsException;

/**
 * 'exit' command. Closes the program.
 */
public class PrintFieldAscendingHeightCommand extends Command {
    private CollectionManager collectionManager;

    public PrintFieldAscendingHeightCommand(CollectionManager collectionManager) {
        super("print_field_ascending_height", " вывести значения поля height всех элементов в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            ResponseOutputer.append(collectionManager.ascendingHeight());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}

