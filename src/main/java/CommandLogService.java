import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommandLogService {

    private final ArrayList<String> movementCommands = new ArrayList<>();
    private final ArrayList<Integer> movementSpeeds = new ArrayList<>();
    private final ArrayList<Integer> movementDurations = new ArrayList<>();
    private final ArrayList<String> receivedCommands = new ArrayList<>();

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
    public void recordReceivedCommand(String rawCommand){
        receivedCommands.add(rawCommand);
    }
    public String writeLog(long programStartTime) {

        File logFile = new File("navigate_log.txt");

        long totalExecutionTimeSeconds = (System.currentTimeMillis() - programStartTime) / 1000;

        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String logWrittenTime = currentTime.format(timeFormatter);

        try (FileWriter writer = new FileWriter(logFile)) {

            writer.write("NAVIGATE COMMAND LOG" + System.lineSeparator());
            writer.write("Received commands:" + System.lineSeparator());

            for (int i = 0; i < receivedCommands.size(); i++) {
                writer.write((i + 1) + ". " + receivedCommands.get(i)  + System.lineSeparator() );
            }

            writer.write(System.lineSeparator());

            writer.write(
                    "Total program execution time: " + totalExecutionTimeSeconds + " seconds" + System.lineSeparator()
            );

            writer.write("Time log was written: " + logWrittenTime + System.lineSeparator()
            );

            return logFile.getAbsolutePath();

        } catch (IOException e) {
            System.out.println( "Unable to write command log: " + e.getMessage()
            );
            return null;
        }
    }
}

