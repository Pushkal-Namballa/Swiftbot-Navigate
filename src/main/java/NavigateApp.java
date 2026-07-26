import swiftbot.Button;
import swiftbot.SwiftBotAPI;
public class NavigateApp {

    private final SwiftBotAPI swiftBot = SwiftBotAPI.INSTANCE;
    private final ConsoleDisplay display = new ConsoleDisplay();
    private final QrCommandParser qrCommandParser = new QrCommandParser(swiftBot);

    public static void main(String[] args) {
        NavigateApp app = new NavigateApp();
        app.run();
    }

    private void run() {
        display.showWelcome();
        configureExitButton(); // Does not wait for execution.
        // does not pause and wait for the button to be pressed.
        // It registers instructions for what should happen later: While QR scanning is happening or until
        // the very last command in run, the X-button action remains active.
        String rawCommand = qrCommandParser.scanRawCommand();
        display.showRawCommand(rawCommand);

    }
    private void configureExitButton() {
        swiftBot.enableButton(Button.X, () -> {
            swiftBot.stopMove();
            display.showExitMessage();
            swiftBot.disableButton(Button.X);
        });
    }
}