package at.htlleonding.dungeonsandportals.Model;

public class Player {
    //region <fields>
    private int id;
    private String name;
    private int health;
    private int mana;
    private int killedMobs;
    //endregion
    //region <Getter and Setter>
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getKilledMobs() {
        return killedMobs;
    }
    //endregion

    public Player(int id, String name, int health, int mana, int killedMobs) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.killedMobs = killedMobs;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
