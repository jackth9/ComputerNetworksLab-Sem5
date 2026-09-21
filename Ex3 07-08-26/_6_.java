import java.util.Random;

public class _6_ {
    //Simulating stop and wait ARQ protocol
    public static void main(String[] args) throws InterruptedException {
        int totalFrames = 5;
        Random rand = new Random();

        for (int frame = 0; frame < totalFrames; frame++) {
            boolean ackReceived = false;
            int attempts = 1;

            while (!ackReceived) {
                System.out.println("[Sender] Sending Frame " + frame + " (Attempt " + attempts + ")");
                Thread.sleep(800);

                // Simulate 30% packet or ACK loss
                if (rand.nextInt(10) < 3) {
                    System.out.println("[Network] Frame " + frame + " lost or timed out!");
                    attempts++;
                } else {
                    System.out.println("[Receiver] Frame " + frame + " received successfully.");
                    System.out.println("[Receiver] Sending ACK " + frame);
                    ackReceived = true;
                }
            }
            System.out.println("--- Transaction Completed for Frame " + frame + " ---\n");
        }
    }
}