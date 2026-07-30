import swiftbot.Button;
import swiftbot.SwiftBotAPI;
public class NavigateApp {

    private final SwiftBotAPI swiftBot = SwiftBotAPI.INSTANCE;
    private final ConsoleDisplay display = new ConsoleDisplay();
    private final QrCommandParser qrCommandParser = new QrCommandParser(swiftBot);
    private final MovementController movementController = new MovementController(swiftBot);

    public static void main(String[] args) {
        NavigateApp app = new NavigateApp();
        app.run();
    }

    private void run() {
        display.showWelcome();
        configureExitButton();

        String rawCommand = qrCommandParser.scanRawCommand();
        display.showRawCommand(rawCommand);

        boolean isValid = qrCommandParser.parseMovementCommand(rawCommand);

        if (isValid) {
            System.out.println("Command: " + qrCommandParser.getCommand());
            System.out.println("Speed: " + qrCommandParser.getSpeed());
            System.out.println("Duration: " + qrCommandParser.getDuration());

            if (qrCommandParser.getCommand().equals("F")) {
                movementController.moveForward(qrCommandParser.getSpeed(), qrCommandParser.getDuration());

            } else if (qrCommandParser.getCommand().equals("b")) {
                movementController.moveBackward(qrCommandParser.getSpeed(), qrCommandParser.getDuration());
            } else {
                System.out.println("Movement for this command has not been implemented yet.");
            }

        } else {
            System.out.println(qrCommandParser.getErrorMessage());
        }
    }
    private void configureExitButton() {
        swiftBot.enableButton(Button.X, () -> {
            swiftBot.stopMove();
            display.showExitMessage();
            swiftBot.disableButton(Button.X);
        });
    }
}