package at.fhv.multisync;

import java.io.PrintStream;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		layout.setFixed(true);

		// initialize the console
		MessageConsole console = new MessageConsole("SyncLog", null);
		ConsolePlugin.getDefault().getConsoleManager()
				.addConsoles(new IConsole[] { console });

		// redirect output stream
		final MessageConsoleStream stream = console.newMessageStream();
		final PrintStream print = new PrintStream(stream);
		System.setErr(print);
		System.setOut(print);
	}
}
