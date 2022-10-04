package ServerModule.commands;

import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Transport;
import common.exceptions.WrongAmountOfArgumentsException;

/**
 * 'exit' command. Closes the program.
 */
public class FilterByTransportCommand extends Command {
    private CollectionManager collectionManager;

    public FilterByTransportCommand(CollectionManager collectionManager) {
        super("filter_by_transport transport", "вывести элементы, значение поля transport которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            Transport transport = (Transport) objectArgument;
            ResponseOutputer.append(collectionManager.filterByTransport(transport));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}

