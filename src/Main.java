import java.util.Random;

public class Main {public class Main {
    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {250, 260, 270, 200, 150};
    public static int[] heroesDamage = {20, 15, 10, 5, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer", "Golem"};
    public static int roundNumber = 0;
    public static boolean isThorStunned = false;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        if (isThorStunned) {
            System.out.println("Thor stunned the boss. The boss skips this round.");
            isThorStunned = false;
        } else {
            bossAttack();
        }
        heroesAttack();
        applyMedicHealing();
        applyGolemProtection();
        applyLuckyDodge();
        applyWitcherResurrection();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2,3,4
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void applyMedicHealing() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Healer") && heroesHealth[i] > 0) {
                for (int j = 0; j < heroesHealth.length; j++) {
                    if (heroesHealth[j] > 0 && heroesHealth[j] < 100) {
                        heroesHealth[j] += 30; // Произвольное значение лечения
                        System.out.println("Healer healed " + heroesAttackType[j]);
                        break;
                    }
                }
            }
        }
    }

    public static void applyGolemProtection() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Golem") && heroesHealth[i] > 0) {
                int golemDamage = bossDamage / 5;
                heroesHealth[i] -= golemDamage;
                System.out.println("Golem takes 1/5 of the boss's damage.");
            }
        }
    }

    public static void applyLuckyDodge() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Lucky") && heroesHealth[i] > 0) {
                Random random = new Random();
                if (random.nextBoolean()) {
                    System.out.println("Lucky dodged the boss's attack.");
                    return;
                }
            }
        }
    }

    public static void applyWitcherResurrection() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Witcher") && heroesHealth[i] > 0) {
                if (bossHealth <= 0) {
                    System.out.println("Witcher tries to resurrect the first fallen hero.");
                    for (int j = 0; j < heroesHealth.length; j++) {
                        if (heroesHealth[j] <= 0) {
                            heroesHealth[j] = 100; 
                            System.out.println("Witcher resurrected a fallen hero.");
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}}

