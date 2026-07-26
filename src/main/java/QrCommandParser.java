import java.awt.image.BufferedImage;
import swiftbot.SwiftBotAPI;

public class QrCommandParser {

    private final SwiftBotAPI swiftBot;

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
}