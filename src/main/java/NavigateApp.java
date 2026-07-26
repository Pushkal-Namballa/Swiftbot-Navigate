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
        configureExitButton();
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