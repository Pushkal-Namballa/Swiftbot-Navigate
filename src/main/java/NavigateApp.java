import swiftbot.Button;
import swiftbot.SwiftBotAPI;
public class NavigateApp {

    private final SwiftBotAPI swiftBot = SwiftBotAPI.INSTANCE;
    private final ConsoleDisplay display = new ConsoleDisplay();
    private final QrCommandParser qrCommandParser = new QrCommandParser(swiftBot);
    private final MovementController movementController = new MovementController(swiftBot);
    private final CommandLogService commandLogService = new CommandLogService();

    public static void main(String[] args) {
        NavigateApp app = new NavigateApp();
        app.run();
    }

    private void run() {
        display.showWelcome();
        configureExitButton();

        while (true) {
            String rawCommand = qrCommandParser.scanRawCommand();
            display.showRawCommand(rawCommand);

            boolean isValid =
                    qrCommandParser.parseMovementCommands(rawCommand);

            if (!isValid) {
                System.out.println(qrCommandParser.getErrorMessage());
                System.out.println("Please scan a new QR command.");
                continue;
            }if (qrCommandParser.isRetraceCommand()) {
                int retraceCount = qrCommandParser.getRetraceCount();

                int historySizeBeforeRetrace =
                        commandLogService.getMovementCount();

                if (retraceCount > historySizeBeforeRetrace) {
                    System.out.println("Cannot retrace " + retraceCount + " movement(s). Only " + historySizeBeforeRetrace
                                    + " movement(s) have been executed.");
                    System.out.println("Please scan a new QR command.");

                } else {
                    int newestHistoryIndex =
                            historySizeBeforeRetrace - 1;

                    int oldestHistoryIndex =
                            historySizeBeforeRetrace - retraceCount;

                    System.out.println(
                            "Retrace request accepted: "
                                    + retraceCount + " movement(s)."
                    ); for (int i = newestHistoryIndex; i >= oldestHistoryIndex; i--) {

                        String command =
                                commandLogService.getMovementCommand(i);

                        int speed =
                                commandLogService.getMovementSpeed(i);

                        int duration =
                                commandLogService.getMovementDuration(i);

                        System.out.println("Retracing movement: " + command);
                        System.out.println("Speed: " + speed);
                        System.out.println("Duration: " + duration);

                        if (command.equals("F")) {
                            movementController.moveForward(speed, duration);

                        } else if (command.equals("B")) {
                            movementController.moveBackward(speed, duration);

                        } else if (command.equals("R")) {
                            movementController.turnRight();
                            movementController.moveForward(speed, duration);
                            System.out.println("Right turn completed.");

                        } else if (command.equals("L")) {
                            movementController.turnLeft();
                            movementController.moveForward(speed, duration);
                            System.out.println("Left turn completed.");
                        }

                        commandLogService.recordMovement(command, speed, duration);
                    }

                    System.out.println("Retrace completed.");

                }

            } else {
            for (int i = 0; i < qrCommandParser.getCommandCount(); i++) {

                String command = qrCommandParser.getCommand(i);
                int speed = qrCommandParser.getSpeed(i);
                int duration = qrCommandParser.getDuration(i);

                System.out.println("Executing command " + (i + 1));
                System.out.println("Command: " + command);
                System.out.println("Speed: " + speed);
                System.out.println("Duration: " + duration);

                if (command.equals("F")) {
                    movementController.moveForward(speed, duration);

                } else if (command.equals("B")) {
                    movementController.moveBackward(speed, duration);

                } else if (command.equals("R")) {
                    movementController.turnRight();
                    movementController.moveForward(speed, duration);
                    System.out.println("Right turn completed.");

                } else if (command.equals("L")) {
                    movementController.turnLeft();
                    movementController.moveForward(speed, duration);
                    System.out.println("Left turn completed.");
                }
                commandLogService.recordMovement(command,speed,duration);
            }
            }
        }
    }
    private void configureExitButton() {
        swiftBot.enableButton(Button.X, () -> {
            swiftBot.stopMove();
            display.showExitMessage();
            swiftBot.disableButton(Button.X);
            System.exit(5);
        });
    }
}