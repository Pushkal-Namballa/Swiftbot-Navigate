import java.awt.image.BufferedImage;
import swiftbot.SwiftBotAPI;

public class QrCommandParser {

    private final SwiftBotAPI swiftBot;
    private int speed;
    private int duration;
    private String errorMessage;
    private String command;
    private static final int MAX_COMMANDS = 10;
    private String[] sequenceCommands;
    private int[] sequenceSpeeds;
    private int[] sequenceDurations;
    private  int commandCount;
    private boolean retraceCommand;
    private int retraceCount;

    public QrCommandParser(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public String scanRawCommand() {
        System.out.println("Scanning for a QR code...");
        while (true) {
            try {
                BufferedImage image = swiftBot.getQRImage();

                if (image == null) {
                    System.out.println("Camera did not capture an image. Trying again...");
                    pauseBeforeRetry();
                    continue;
                }

                String decodedMessage = swiftBot.decodeQRImage(image);

                if (decodedMessage != null && !decodedMessage.isBlank()) {
                    return decodedMessage;
                }
                pauseBeforeRetry();

            } catch (Exception e) {
                System.out.println("Unable to scan the QR code. Trying again...");
                pauseBeforeRetry();
            }
        }
    }

    private void pauseBeforeRetry() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public int getCommandCount() {
        return commandCount;
    }

    public String getCommand(int index) {
        return sequenceCommands[index];
    }

    public int getSpeed(int index) {
        return sequenceSpeeds[index];
    }

    public int getDuration(int index) {
        return sequenceDurations[index];
    }
        public String getErrorMessage() {
            return errorMessage;
    }
public boolean isRetraceCommand(){
return retraceCommand; }

public  int getRetraceCount(){
    return retraceCount;}



    public boolean parseMovementCommand(String rawCommand) {
        speed = 0;
        duration = 0;
        errorMessage = "";
        command = null;

     if (rawCommand == null || rawCommand.isBlank()) {
         errorMessage = "Invalid command: QR code contained no command";
         return false;
     }
        String[] parts = rawCommand.trim().split(",", -1);

      if (parts.length != 3) {
          errorMessage = "Invalid  command: Key Letter, speed, duration.";
       return false;}

        String parsedCommand = parts[0].trim().toUpperCase();

        if (!parsedCommand.equals("F") && !parsedCommand.equals("B") && !parsedCommand.equals("L")
                && !parsedCommand.equals("R")) {
            errorMessage = "Command must be F, B, L or R.";
            return false;
        }

        int parsedSpeed;
        int parsedDuration;

        try {
            parsedSpeed = Integer.parseInt(parts[1].trim());
            parsedDuration = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid command: speed and duration must be whole numbers.";
            return false;
        } if (parsedSpeed < 1 || parsedSpeed > 100) {
            errorMessage = "Invalid speed: enter a value from 1 to 100.";
            return false;
        } if (parsedDuration < 1 || parsedDuration > 6) {
            errorMessage = "Invalid duration: enter a value from 1 to 6 seconds.";
            return false;
        }command = parsedCommand;
        speed = parsedSpeed;
        duration = parsedDuration;

        return true;
    }
    public boolean parseMovementCommands(String rawCommand) {
        commandCount = 0;

        if (rawCommand == null || rawCommand.isBlank()) {
            errorMessage = "Invalid command: QR code contained no command.";
            return false;
        }

        String[] rawCommands = rawCommand.trim().split(";", -1);

        if (rawCommands.length > MAX_COMMANDS) {
            errorMessage = "Invalid sequence: a QR code can contain no more than 10 commands.";
            return false;
        }
        if (rawCommands.length == 1) {
            String[] firstCommandParts =
                    rawCommands[0].trim().split(",", -1);

            String firstCommandLetter =
                    firstCommandParts[0].trim().toUpperCase();

            if (firstCommandLetter.equals("T")) {
                return parseRetraceCommand(rawCommands[0]);
            }
        }

        for (int i = 0; i < rawCommands.length; i++) {
            String[] commandParts =
                    rawCommands[i].trim().split(",", -1);

            String commandLetter =
                    commandParts[0].trim().toUpperCase();

            if (commandLetter.equals("T")) {
                errorMessage =
                        "Invalid sequence: T cannot be used in a multiple-command sequence.";
                return false;
            }
        }

        sequenceCommands = new String[rawCommands.length];
        sequenceSpeeds = new int[rawCommands.length];
        sequenceDurations = new int[rawCommands.length];

        for (int i = 0; i < rawCommands.length; i++) {
            if (!parseMovementCommand(rawCommands[i])) {
                errorMessage = "Command " + (i + 1) + ": " + errorMessage;
                return false;
            }

            sequenceCommands[i] = command;
            sequenceSpeeds[i] = speed;
            sequenceDurations[i] = duration;
        }

        commandCount = rawCommands.length;
        return true;

        }
    private boolean parseRetraceCommand(String rawCommand) {
        String[] parts = rawCommand.trim().split(",", -1);

        if (parts.length != 2) {
            errorMessage = "Invalid T command: use T,number.";
            return false;
        }

        int parsedRetraceCount;

        try {
            parsedRetraceCount = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            errorMessage = "Invalid T command: number must be a whole number.";
            return false;
        }

        if (parsedRetraceCount < 1) {
            errorMessage = "Invalid T command: number must be at least 1.";
            return false;
        }

        retraceCommand = true;
        retraceCount = parsedRetraceCount;
        return true;
    }
    }
