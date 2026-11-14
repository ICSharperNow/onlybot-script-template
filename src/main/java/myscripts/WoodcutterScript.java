package myscripts;

import java.time.Duration;
import java.util.Optional;

import org.onlybot.scripting.Bot;
import org.onlybot.scripting.GameObject;
import org.onlybot.scripting.Script;
import org.onlybot.scripting.ScriptManifest;
import org.onlybot.scripting.Skill;

/**
 * A small but complete example: chop trees, drop the logs, repeat.
 * Read it top to bottom - every step is commented.
 */
@ScriptManifest(
        name = "Example Woodcutter",
        version = "1.0",
        author = "me",
        description = "Chops normal trees and drops the logs.",
        category = "Woodcutting")
public class WoodcutterScript implements Script {

    @Override
    public void onStart(Bot bot) {
        // A small info box drawn on the game screen.
        bot.ui().overlay(o -> o
                .title("Example Woodcutter")
                .showRuntime()
                .showXp(Skill.WOODCUTTING)
                .line("Logs in bag", () -> bot.inventory().count("Logs")));
    }

    @Override
    public long loop(Bot bot) {
        // 1. Nothing to do until we are in the game.
        if (!bot.isLoggedIn()) {
            return 2000;
        }

        // 2. Bag full? Drop every log.
        if (bot.inventory().isFull()) {
            int dropped = bot.inventory().dropAll("Logs");
            bot.log().info("Dropped " + dropped + " logs");
            return 600;
        }

        // 3. Already chopping or walking? Leave the character alone.
        if (bot.self().isAnimating() || bot.self().isMoving()) {
            return 600;
        }

        // 4. Find the closest tree we can chop, within 15 tiles.
        Optional<GameObject> tree = bot.objects()
                .named("Tree")
                .withAction("Chop down")
                .within(15)
                .nearest();

        if (tree.isEmpty()) {
            bot.log().warn("No tree nearby - stand next to some normal trees.");
            return 3000;
        }

        // 5. Click "Chop down", then wait (up to 5 seconds) until we start chopping.
        if (tree.get().interact("Chop down")) {
            bot.waits().until(() -> bot.self().isAnimating(), Duration.ofSeconds(5));
        }

        return 600;
    }
}
