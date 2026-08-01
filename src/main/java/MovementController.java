import swiftbot.SwiftBotAPI;

public class MovementController {

    private final SwiftBotAPI swiftBot;
    private static final int TurnSpeed = 40;
    private static final int TurnDuration = 850;

    public MovementController(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public void moveForward(int speed, int duration) {
        swiftBot.move(speed, speed, duration * 1000);
    }
    public void moveBackward(int speed, int duration) {
        swiftBot.move(-speed, -speed, duration * 1000);
    }
    public void turnRight (){
        swiftBot.move(TurnSpeed,-TurnSpeed,TurnDuration);
    }
    public void turnLeft (){
        swiftBot.move(-TurnSpeed, TurnSpeed, TurnDuration);
    }
}
