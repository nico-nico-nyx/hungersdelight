package niconiconyx.hungersdelight.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

import niconiconyx.hungersdelight.HungersDelight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigBuilder {

    public static void Init(String mod_id, String modname, Class<?> config) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithModifiers(Modifier.PRIVATE)
                .setPrettyPrinting()
                .create();

        Path path = FabricLoader.getInstance().getConfigDir().resolve(mod_id + ".json");
        Logger logger = HungersDelight.LOGGER;

        try{
            gson.fromJson(Files.newBufferedReader(path), config);
        } catch (Exception e) {
            logger.error(modname+": Could not load config; reverting to defaults");
            try
            {
                if (!Files.exists(path)){
                    Files.createFile(path);
                    Files.write(path, gson.toJson(((Class) config).newInstance()).getBytes());
                }
            } catch (Exception e2)
            {
                logger.error(modname + ": Could not save config. Printing stack trace");
                e.printStackTrace();
            }
        }
    }
}
