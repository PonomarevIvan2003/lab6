package ServerModule.commands;

import ServerModule.util.ResponseOutputer;

import common.data.Flat;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.FlatLite;

import java.time.LocalDateTime;


/**
 * 'remove_greater' command. Removes elements greater than user entered.
 */
public class RemoveLowerCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            FlatLite flat = (FlatLite) objectArgument;
            Flat newFlat = new Flat(
                    collectionManager.generateId(),
                    flat.getName(),
                    flat.getCoordinates(),
                    LocalDateTime.now(),
                    flat.getArea(),
                    flat.getNumberOfRooms(),
                    flat.getHeight(),
                    flat.getNewness(),
                    flat.getTransport(),
                    flat.getHouse()
            );
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeLower(newFlat);
            ResponseOutputer.append("Удалено фильмов: " + (collectionSize - collectionManager.collectionSize()));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        }
        return false;
    }
}