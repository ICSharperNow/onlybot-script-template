# OnlyBot Script Template

A ready-to-use IntelliJ IDEA project for writing your own OnlyBot scripts.
Open it, press one button, and your script shows up in the OnlyBot client.

## What you need

1. **OnlyBot** installed, and the launcher started at least once.
2. **IntelliJ IDEA** (free): https://www.jetbrains.com/idea/download

Nothing else. No manual Java setup, no config files to edit.

## Get started

1. On this page click **Use this template > Create a new repository**
   (or **Code > Download ZIP** and unzip it).
2. In IntelliJ choose **Open** and pick the project folder.
3. If IntelliJ asks for a JDK, choose **Download JDK**, version **21**.
   Wait for the loading bar at the bottom to finish.
4. Press the green **Run** arrow at the top right. It is already set to **Build and Install**.
5. In the OnlyBot client, open the script list. Your scripts have a **Local** tag.

## Write your own script

Scripts live in `src/main/java/myscripts`. Two examples are included:

| File | What it does |
|---|---|
| `HelloScript.java` | Writes a line to the log every few seconds. The safest first test. |
| `WoodcutterScript.java` | Chops trees and drops the logs. Shows the find, click, wait pattern. |

To add a script, copy one of them, rename it, and change the text inside `@ScriptManifest(...)`.
Press **Run** again. That is all: the script list is generated for you from `@ScriptManifest`.

```java
@ScriptManifest(name = "My Script", version = "1.0", author = "me",
        description = "What it does", category = "Other")
public class MyScript implements Script {

    @Override
    public long loop(Bot bot) {
        if (!bot.isLoggedIn()) return 2000;   // not in game yet, check again in 2 seconds
        bot.log().info("Hello from " + bot.self().name());
        return 1000;                          // run loop again in about 1 second
    }
}
```

`loop` runs again and again. The number it returns is how long to wait, in milliseconds,
before it runs again. Return `-1` to stop the script.

## How it works

| Step | What happens |
|---|---|
| Compile | Your code is compiled against the OnlyBot client installed on your PC. |
| Script list | `echo-scripts.json` is generated from every class that has `@ScriptManifest`. |
| Pack | Everything goes into one file, `my-onlybot-scripts.jar`. |
| Install | The jar is copied to OnlyBot's `local-scripts` folder. |

Prefer a terminal? `gradlew installScript` does the same thing.

## Troubleshooting

| Problem | Fix |
|---|---|
| "Could not find the OnlyBot client" | Start the OnlyBot launcher once so it downloads the client. If OnlyBot is installed somewhere unusual, set `onlybotDir` in `gradle.properties`. |
| Red underlines everywhere | Click the Gradle elephant icon (right edge), then the reload button. |
| Script is not in the list | Does the class have `@ScriptManifest(...)` above it? Close and reopen the script list. |
| No Local scripts appear at all | Your OnlyBot account may need local-script access. Ask OnlyBot support. |
| Build broke after an OnlyBot update | Run again. The error points at the line that needs changing. |

## Good to know

This is a community template and is not made by OnlyBot. It was written for scripting API version 1.
