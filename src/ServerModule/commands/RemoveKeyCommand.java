package ServerModule.commands;


import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Flat;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.FlatNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoveKeyCommand extends Command{
    private CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key null", "удалить элемент из коллекции по его ключу");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int key = Integer.parseInt(argument);
            Flat o = collectionManager.getFromCollection(key);
            if (o == null) throw new FlatNotFoundException();
            collectionManager.removeFromCollection(key);
            ResponseOutputer.append("Квартира успешно удалена!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (FlatNotFoundException exception) {
            ResponseOutputer.append("Квартира не найден!\n");
        }
        return false;
    }
}
