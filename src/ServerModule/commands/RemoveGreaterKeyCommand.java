package ServerModule.commands;


import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfArgumentsException;

public class RemoveGreaterKeyCommand extends Command{
    private CollectionManager collectionManager;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager){
        super("remove_greater_key null", "удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int key = Integer.parseInt(argument);
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeGreaterKey(key);
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
