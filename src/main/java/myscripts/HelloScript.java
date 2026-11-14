package myscripts;

import org.onlybot.scripting.Bot;
import org.onlybot.scripting.Script;
import org.onlybot.scripting.ScriptManifest;

/**
 * The simplest possible OnlyBot script.
 * It writes a line to the client log every few seconds until you stop it.
 */
@ScriptManifest(
        name = "Hello Script",
        version = "1.0",
        author = "me",
        description = "Logs a message every few seconds.",
        category = "Other")
public class HelloScript implements Script {

    private int loops = 0;

    /** Runs once, when you press Start. */
    @Override
    public void onStart(Bot bot) {
        bot.log().info("Hello Script started. API version " + bot.apiVersion());
    }

    /**
     * Runs over and over until the script stops.
     * The number you return is how long to wait (in milliseconds) before the next run.
     * Return a negative number to stop the script.
     */
    @Override
    public long loop(Bot bot) {
        loops++;

        if (!bot.isLoggedIn()) {
            bot.log().info("Waiting for you to log in...");
            return 3000;
        }

        bot.log().info("Loop " + loops + ": I am " + bot.self().name()
                + " standing on " + bot.self().tile());
        return 3000;
    }

    /** Runs once, when the script stops. */
    @Override
    public void onStop(Bot bot) {
        bot.log().info("Hello Script stopped after " + loops + " loops.");
    }
}
