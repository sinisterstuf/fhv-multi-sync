package at.fhv.multisync.actions;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OpenView = "at.multisync.openview";
    public static final String CMD_OpenMessage = "at.multisync.openmsg";
    
}
