package ServerModule.commands;

import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Flat;
import common.exceptions.WrongAmountOfArgumentsException;
import common.util.FlatLite;

import java.time.LocalDateTime;

/**
 * 'info' command. Prints information about the collection.
 */
public class InsertCommand extends Command {
    private CollectionManager collectionManager;

    public InsertCommand(CollectionManager collectionManager) {
        super("insert null {element}", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            int key = Integer.parseInt(argument);
            FlatLite flatLite = (FlatLite) objectArgument;
            collectionManager.addToCollection(key, new Flat(
                    collectionManager.generateId(),
                    flatLite.getName(),
                    flatLite.getCoordinates(),
                    LocalDateTime.now(),
                    flatLite.getArea(),
                    flatLite.getNumberOfRooms(),
                    flatLite.getHeight(),
                    flatLite.getNewness(),
                    flatLite.getTransport(),
                    flatLite.getHouse()
            ));
            ResponseOutputer.append("Успешно добавлено в коллекцию!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        }
        return false;
    }
}