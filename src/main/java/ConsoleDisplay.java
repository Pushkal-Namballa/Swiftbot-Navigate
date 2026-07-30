public class ConsoleDisplay {
    public void showWelcome() {
        System.out.println("NAVIGATE");
        System.out.println("SwiftBot navigation system started.");
        System.out.println("Press X to exit.");
    }

    public void showExitMessage() {
        System.out.println("Navigation system terminated safely.");

    }
    public void showRawCommand(String rawCommand){
        System.out.println("Raw QR command: [" + rawCommand + "]");
    }


}
