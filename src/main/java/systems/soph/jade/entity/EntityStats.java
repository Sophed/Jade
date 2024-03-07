package systems.soph.jade.entity;

@SuppressWarnings("unused")
public class EntityStats {

    int maxhealth;
    int defense;
    int intellect;
    int strength;
    int energy;

    public EntityStats(int health, int defense, int intellect, int strength, int energy) {
        this.maxhealth = health;
        this.defense = defense;
        this.intellect = intellect;
        this.strength = strength;
        this.energy = energy;
    }

    public int getMaxhealth() {
        return maxhealth;
    }
    public void setMaxhealth(int maxhealth) {
        this.maxhealth = maxhealth;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getIntellect() {
        return intellect;
    }
    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
