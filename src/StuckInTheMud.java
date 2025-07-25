import java.util.Arrays;
import java.util.Random;
import java.util.Iterator;

import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;


public class StuckInTheMud {

    private static final int DICE_AMOUNT = 4;
    private static final int MAX_SCORE = 100;
    private static int[] diceArray;
    private static CircularLinkedList<Player> playerCLL;
    private static SoundFile start, newRound, rolling, gameOver;
    private static Clip startClip, newRoundClip, rollingClip, gameOverClip;
    private static boolean isStartClipped, isNewRoundClipped, isRollingClipped, isGameOverClipped;

    public static void main(String[] args) {
        diceArray = clearDice(new int[DICE_AMOUNT]);

        playerCLL = new CircularLinkedList<>(
            new Player("Madame Margaret"),
            new Player("Vadrenaline Vladimir"),
            new Player("Rubber Rico")
        );

        playerCLL.add(new Player("Dadrenaline Dominic"));

        isStartClipped = isNewRoundClipped = isRollingClipped = isGameOverClipped = false;

        start = initializeSoundFile("Sounds/start-game.wav");
        newRound = initializeSoundFile("Sounds/new-round.wav");
        rolling = initializeSoundFile("Sounds/roll-dice.wav");
        gameOver = initializeSoundFile("Sounds/game-over.wav");


        if (start != null) {
            startClip = start.getClip();
            isStartClipped = true;
        }
        if (newRound != null) {
            newRoundClip = newRound.getClip();
            isNewRoundClipped = true;
        }
        if (rolling != null) {
            rollingClip = rolling.getClip();
            isRollingClipped = true;
        }
        if (gameOver != null) {
            gameOverClip = gameOver.getClip();
            isGameOverClipped = true;
        }

        if (ensurePlayableGame()){
            gameLoop();
        }

        System.out.println("Thanks for playing!");

    }


    public static void gameLoop() {
        Random r = new Random();
        Iterator<Player> playerItr = playerCLL.iterator();

        randomizePlayer(r.nextInt(playerCLL.getSize()) + 1, playerItr);

        int turns = 0;
        int highScore = 0;

        if (isStartClipped){
            System.out.print("""
                Stuck In The Mud
                
                Starting in:
                """);
            sleep(1000);

            startClip.start();

            for (int countdown  = 3; countdown > 0; countdown--) {
                System.out.println(countdown);
                sleep(1000);

            }
            System.out.println("LET THE GAME BEGIN!\n\n");

            sleep((startClip.getMicrosecondLength()/1000) - 5000);
            startClip.stop();
        } else {
            System.out.println("LET THE GAME BEGIN!\n\n");
        }

        for (Player player : playerCLL) {
            player = playerItr.next();

            sleep(300);

            if (turns % playerCLL.getSize() == 0) {
                System.out.print(
                        """
                        __________________
                        NEW ROUND STARTING
                        __________________
                        """
                );
                if (isNewRoundClipped) {
                    newRound.playSound(4);
                }
            }

            boolean noDiceStuck = true;

            System.out.printf("%s's turn!\n", player.getName());

            int roundTotal = 0;

            while (noDiceStuck) {
                System.out.printf("%s rolled ", player.getName());
                int dieIdx;
                int numOfDiceStuck = 0;

                for (dieIdx = 0; dieIdx < DICE_AMOUNT; dieIdx++) {
                    if ((diceArray[dieIdx] == 2 || diceArray[dieIdx] == 5)) {

                        numOfDiceStuck++;
                    } else {

                        diceArray[dieIdx] = r.nextInt(6) + 1;
                        if (!(diceArray[dieIdx] == 2 || diceArray[dieIdx] == 5)) {
                            roundTotal += diceArray[dieIdx];
                        } else {
                            numOfDiceStuck++;
                        }
                    }
                }
                sleep(500);

                System.out.printf("%s \n", Arrays.toString(diceArray));
                if (isRollingClipped){
                    rolling.playSound(1);
                }

                if (numOfDiceStuck == DICE_AMOUNT) {
                    player.addRoundTotal(roundTotal);
                    System.out.printf("%s earned %d points this round, totaling %d \n"
                        ,player.getName(), roundTotal, player.getScore()
                    );

                    if (player.getScore() > highScore) {
                        highScore = player.getScore();
                        System.out.println("...new high player!\n");
                    } else {
                        System.out.println();
                    }

                    if (isNewRoundClipped) {
                        newRound.playSound(1);
                    }
                    noDiceStuck = false;
                }

                sleep(1000);
            }

            if (player.getScore() >= MAX_SCORE){

                if (isGameOverClipped){
                    gameOverClip.start();
                }

                System.out.printf("\nTHE WINNER IS %s WITH A SCORE OF %d!\n", player.getName(), player.getScore());
                sleep(2000);

                return;
            }

            clearDice(diceArray);

            turns += 1;

            sleep(1000);
        }
    }


    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {

        }
    }


    private static int[] clearDice(int[] diceArray) {
        for (int i = 0; i < DICE_AMOUNT; i++) {
            diceArray[i] = 0;
        }
        return diceArray;
    }


    private static boolean ensurePlayableGame() {
        if (playerCLL.getSize() <= 1){
            System.out.println("""
                    Stuck In The Mud
                    
                    Can only play the game if there are two or more players...
                    """);
            if (isGameOverClipped){
                gameOver.playSound(1);
            }
            return false;
        } else {
            return true;
        }
    }


    private static void randomizePlayer(int totalPasses, Iterator<Player> itr) {
        for (int nextsCalled = 0; nextsCalled < totalPasses; nextsCalled++) {
            itr.next();
        }
    }


    private static SoundFile initializeSoundFile(String fileName) {
        SoundFile soundFile = null;
        try {
            soundFile = new SoundFile(fileName);
            return soundFile;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ignored) {

        }
        return soundFile;
    }

}
