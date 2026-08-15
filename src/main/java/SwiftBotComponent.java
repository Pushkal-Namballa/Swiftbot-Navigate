import swiftbot.SwiftBotAPI;

public abstract class SwiftBotComponent {

    private final SwiftBotAPI swiftBot;

    protected SwiftBotComponent(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    protected final SwiftBotAPI getSwiftBot() {
        return swiftBot;
    }
}
