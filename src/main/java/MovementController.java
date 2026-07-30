import swiftbot.SwiftBotAPI;

public class MovementController {

    private final SwiftBotAPI swiftBot;

    public MovementController(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public void moveForward(int speed, int duration) {
        swiftBot.move(speed, speed, duration * 1000);
    }
    public void moveBackward(int speed, int duration) {
        swiftBot.move(-speed, -speed, duration * 1000);
    }
}
