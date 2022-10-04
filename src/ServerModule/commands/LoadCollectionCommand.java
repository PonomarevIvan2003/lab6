package ServerModule.commands;

import ServerModule.util.CollectionManager;
import ServerModule.util.ResponseOutputer;
import common.exceptions.WrongAmountOfParametersException;

import java.io.FileNotFoundException;


public class LoadCollectionCommand extends Command {
    private CollectionManager collectionManager;

    public LoadCollectionCommand(CollectionManager collectionManager) {
        super("loadCollection", "загружает коллекцию (эта команда недоступна пользователю)");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!(argument == null) || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.loadCollection();
            return true;
        } catch (WrongAmountOfParametersException | FileNotFoundException exception) {
            ResponseOutputer.append("Не должно быть передано название файла!\n");
        }
        return false;
    }
}
