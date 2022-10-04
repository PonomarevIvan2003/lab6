package ServerModule.commands;

import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.exceptions.WrongAmountOfArgumentsException;

/**
 * Класс, описывающий команду clear
 */
public class ClearCommand extends Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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
            collectionManager.clearCollection();
            ResponseOutputer.append("Коллекция очищена!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}
