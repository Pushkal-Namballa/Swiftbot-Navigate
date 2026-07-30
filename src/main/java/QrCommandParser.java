import java.awt.image.BufferedImage;
import swiftbot.SwiftBotAPI;

public class QrCommandParser {

    private final SwiftBotAPI swiftBot;
    private int speed;
    private int duration;
    private String errorMessage;
    private String command;

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
    public int getSpeed() {
        return speed;
    }

    public int getDuration() {
        return duration;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public String getCommand() {
        return command;
    }



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
}