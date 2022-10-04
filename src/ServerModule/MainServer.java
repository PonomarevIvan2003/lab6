package ServerModule;

import ServerModule.commands.*;
import ServerModule.util.CollectionManager;
import ServerModule.util.CommandManager;
import ServerModule.util.FileManager;
import ServerModule.util.RequestManager;

import java.io.IOException;

public class MainServer {
    public static final int PORT = 8765;

    public static void main(String[] args) throws IOException {
        final String envVariable = "LAB5";
        FileManager fileManager = new FileManager(envVariable);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new RemoveGreaterCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new RemoveGreaterKeyCommand(collectionManager),
                new FilterByTransportCommand(collectionManager),
                new FilterLessThanNewnessCommand(collectionManager),
                new PrintFieldAscendingHeightCommand(collectionManager),
                new LoadCollectionCommand(collectionManager));
        RequestManager requestManager = new RequestManager(commandManager);
        Server server = new Server(PORT, requestManager);
        server.run();
        collectionManager.saveCollection();
    }
}
