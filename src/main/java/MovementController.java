import swiftbot.SwiftBotAPI;

public class MovementController extends SwiftBotComponent {

    private static final int RightTurnSpeed = 40;
    private static final int RightTurnDuration = 925;
    private static final int LeftTurnSpeed = 40;
    private static final int LeftTurnDuration = 850;


    public MovementController(SwiftBotAPI swiftBot) {
        super(swiftBot);
    }

    public void moveForward(int speed, int duration) {
    	getSwiftBot().move(speed, speed, duration * 1000);
    }
    public void moveBackward(int speed, int duration) {
    	getSwiftBot().move(-speed, -speed, duration * 1000);
    }
    public void turnRight (){
    	getSwiftBot().move(RightTurnSpeed, -RightTurnSpeed, RightTurnDuration);
    }
    public void turnLeft (){
    	getSwiftBot().move(-LeftTurnSpeed, LeftTurnSpeed, LeftTurnDuration);
    }
}