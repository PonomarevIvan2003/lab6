package ServerModule.commands;


import ServerModule.util.ResponseOutputer;
import common.data.*;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.FlatNotFoundException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.FlatLite;

import java.time.LocalDateTime;

/**
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            long id = Long.parseLong(argument);
            int key = collectionManager.getKeyById(id);
            Flat oldFlat = collectionManager.getFromCollection(key);
            FlatLite flatLite = (FlatLite) objectArgument;

            if (oldFlat == null) throw new FlatNotFoundException();

            String name = flatLite.getName() == null ? oldFlat.getName() : flatLite.getName();
            Coordinates coordinates = flatLite.getCoordinates() == null ? oldFlat.getCoordinates() : flatLite.getCoordinates();
            LocalDateTime creationDate = oldFlat.getCreationDate();
            Long area = flatLite.getArea() == -1 ? oldFlat.getArea() : flatLite.getArea();
            Integer numberOfRooms = flatLite.getNumberOfRooms() == -1 ? oldFlat.getNumberOfRooms() : flatLite.getNumberOfRooms();
            Integer height = flatLite.getHeight() == -1 ? oldFlat.getHeight() : flatLite.getHeight();
            Boolean newness = flatLite.getNewness() == null ? oldFlat.getNewness() : flatLite.getNewness();
            Transport transport = flatLite.getTransport() == null ? oldFlat.getTransport() : flatLite.getTransport();
            House house = flatLite.getHouse() == null ? oldFlat.getHouse() : flatLite.getHouse();

            collectionManager.removeFromCollection(key);

            collectionManager.addToCollection(key, new Flat(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    area,
                    numberOfRooms,
                    height,
                    newness,
                    transport,
                    house
            ));
            ResponseOutputer.append("Квартира успешно изменена!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NumberFormatException exception) {
            ResponseOutputer.append("ID должен быть представлен числом!\n");
        } catch (FlatNotFoundException exception) {
            ResponseOutputer.append("Квартиры с таким ID в коллекции нет!\n");
        }
        return false;
    }
}
