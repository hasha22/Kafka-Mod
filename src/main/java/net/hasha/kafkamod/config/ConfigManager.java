package net.hasha.kafkamod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("kafkamod.json");

    public static KafkaModConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String raw = Files.readString(CONFIG_PATH);
                String stripped = stripComments(raw);
                return GSON.fromJson(stripped, KafkaModConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load ExhaustionMod config", e);
            }
        } else {
            KafkaModConfig defaultConfig = new KafkaModConfig();
            save(defaultConfig);
            return defaultConfig;
        }
    }
    public static void save(KafkaModConfig config) {
        String content = """
                {
                  // Exhaustion added per tick at the start of a sprint. Use values between 0.5 & 0.005. Base 0.05.
                  "baseTickCost": %s,

                  // Multiplier applied per tick sprinted - controls how sharply cost ramps up. Not recommended to change. Base 1.01.
                  "tickCostGrowth": %s,

                  // Sprint streak caps here, so exhaustion cost plateaus instead of growing forever. Base 200
                  "maxTicks": %d,

                  // How fast the streak decays per tick once the player stops sprinting. Base 2
                  "decayPerTick": %d
                }
                """.formatted(config.baseTickCost, config.tickCostGrowth, config.maxTicks, config.decayPerTick);

        try {
            Files.writeString(CONFIG_PATH, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save ExhaustionMod config", e);
        }
    }
    private static String stripComments(String raw) {
        StringBuilder result = new StringBuilder();
        for (String line : raw.lines().toList()) {
            String trimmed = line.trim();
            if (!trimmed.startsWith("//")) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }
}
