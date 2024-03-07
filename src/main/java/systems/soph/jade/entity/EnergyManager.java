package systems.soph.jade.entity;

import org.bukkit.entity.Entity;

import java.util.HashMap;

public class EnergyManager {

    private static final HashMap<Entity, Double> values = new HashMap<>();

    public static int getPretty(Entity entity) {
        if (!values.containsKey(entity)) return 0;
        return (int) values.get(entity).doubleValue();
    }

    public static void addEntity(Entity entity, double value) {
        values.put(entity, value);
    }
    public static void removeEntity(Entity entity) {
        values.remove(entity);
    }
    public static double getEntityValue(Entity entity) {
        if (!values.containsKey(entity)) return 0;
        return values.get(entity);
    }
    public static void setEntityValue(Entity entity, double value) {
        values.put(entity, value);
    }

}
