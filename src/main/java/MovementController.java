import swiftbot.SwiftBotAPI;

public class MovementController {

    private final SwiftBotAPI swiftBot;
    private static final int RightTurnSpeed = 40;
    private static final int RightTurnDuration = 925;
    private static final int LeftTurnSpeed = 40;
    private static final int LeftTurnDuration = 850;


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
        swiftBot.move(RightTurnSpeed ,-RightTurnSpeed,RightTurnDuration);
    }
    public void turnLeft (){
        swiftBot.move(-LeftTurnSpeed, LeftTurnSpeed, LeftTurnDuration);
    }
}
