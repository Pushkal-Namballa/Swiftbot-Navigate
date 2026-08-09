import java.util.ArrayList;

public class CommandLogService {

    private final ArrayList<String> movementCommands = new ArrayList<>();
    private final ArrayList<Integer> movementSpeeds = new ArrayList<>();
    private final ArrayList<Integer> movementDurations = new ArrayList<>();

    public void recordMovement(String command, int speed, int duration) {
        movementCommands.add(command);
        movementSpeeds.add(speed);
        movementDurations.add(duration);

    }
    public int getMovementCount() {
        return movementCommands.size();
    }
    public String getMovementCommand(int index) {
        return movementCommands.get(index);
    }

    public int getMovementSpeed(int index) {
        return movementSpeeds.get(index);
    }

    public int getMovementDuration(int index) {
        return movementDurations.get(index);
    }
}

