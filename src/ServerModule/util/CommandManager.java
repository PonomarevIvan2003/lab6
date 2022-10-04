package ServerModule.util;

import ServerModule.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final Command helpCommand;
    private final Command infoCommand;
    private final Command showCommand;
    private final Command insertCommand;
    private final Command updateCommand;
    private final Command removeKeyCommand;
    private final Command clearCommand;
    private final Command saveCommand;
    private final Command executeScriptCommand;
    private final Command exitCommand;
    private final Command removeGreaterCommand;
    private final Command removeLowerCommand;
    private final Command removeGreaterKeyCommand;
    private final Command filterByTransportCommand;
    private final Command filterLessThanNewCommand;
    private final Command printFieldAscendingHeightCommand;
    private final Command loadCollection;


    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command insertCommand, Command updateCommand, Command removeKeyCommand, Command clearCommand, Command saveCommand, Command executeScriptCommand, Command exitCommand, Command removeGreaterCommand, Command removeLowerCommand, Command removeGreaterKeyCommand, Command filterByTransportCommand, Command filterLessThanNewCommand, Command printFieldAscendingHeightCommand, Command loadCollection) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.insertCommand = insertCommand;
        this.updateCommand = updateCommand;
        this.removeKeyCommand = removeKeyCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.removeGreaterKeyCommand = removeGreaterKeyCommand;
        this.filterByTransportCommand = filterByTransportCommand;
        this.filterLessThanNewCommand = filterLessThanNewCommand;
        this.printFieldAscendingHeightCommand = printFieldAscendingHeightCommand;
        this.loadCollection = loadCollection;

        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(insertCommand);
        commands.add(updateCommand);
        commands.add(removeKeyCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(removeGreaterCommand);
        commands.add(removeLowerCommand);
        commands.add(removeGreaterKeyCommand);
        commands.add(filterByTransportCommand);
        commands.add(filterLessThanNewCommand);
        commands.add(printFieldAscendingHeightCommand);
        commands.add(loadCollection);
    }

    /**
     * @return List of manager's com.serverModule.commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    public boolean loadCollection(String argument, Object objectArgument) {
        return loadCollection.execute(argument, objectArgument);
    }

    /**
     * Prints info about the all com.serverModule.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument, Object objectArgument) {
        if (helpCommand.execute(argument, objectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendTable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument, Object objectArgument) {
        return infoCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean show(String argument, Object objectArgument) {
        return showCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean insert(String argument, Object objectArgument) {
        return insertCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean update(String argument, Object objectArgument) {
        return updateCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeKey(String argument, Object objectArgument) {
        return removeKeyCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument, Object objectArgument) {
        return clearCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean save(String argument, Object objectArgument) {
        return saveCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument, Object objectArgument) {
        return exitCommand.execute(argument,objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String argument, Object objectArgument) {
        return executeScriptCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeGreater(String argument, Object objectArgument) {
        return removeGreaterCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeLower(String argument, Object objectArgument) {
        return removeLowerCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeGreaterKey(String argument, Object objectArgument){
        return removeGreaterKeyCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean filterByTransport(String argument, Object objectArgument){
        return filterByTransportCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean filterLessThanNew(String argument, Object objectArgument){
        return filterLessThanNewCommand.execute(argument, objectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printFieldAscendingHeight(String argument, Object objectArgument){
        return printFieldAscendingHeightCommand.execute(argument, objectArgument);
    }

}
