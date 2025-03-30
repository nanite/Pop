package pro.mikey.mods.pop.client.pops;

import pro.mikey.mods.pop.data.AnimStage;

public class AnimTracker {
    private AnimStage stage = AnimStage.IN;

    // Ticks
    private final int inDuration;
    private final int idleDuration;
    private final int outDuration;

    private long startTime;
    private int duration = 0;
    private boolean isDone = false;

    public AnimTracker(int duration, int inDuration, int outDuration) {
        this.inDuration = inDuration;
        this.outDuration = outDuration;
        // Compute idle duration
        this.idleDuration = duration - inDuration - outDuration;
    }

    public AnimTracker(int duration) {
        // default to 200ms
        this(duration, 200, 200);
    }

    public void onRenderFrame() {
        if (startTime == 0) {
            startTime = System.nanoTime();
        }

        long currentTime = System.nanoTime();
        duration = (int) ((currentTime - startTime) / 1_000_000); // ms

        // IN
        if (stage == AnimStage.IN) {
            if (duration >= inDuration) {
                stage = AnimStage.IDLE;
            }
        } else if (stage == AnimStage.IDLE) {
            if (duration >= inDuration + idleDuration) {
                stage = AnimStage.OUT;
            }
        } else if (stage == AnimStage.OUT) {
            if (duration >= inDuration + idleDuration + outDuration) {
                isDone = true;
            }
        }
    }

    public int currentStageCompletion() {
        if (stage == AnimStage.IN) {
            return duration * 100 / inDuration;
        } else if (stage == AnimStage.IDLE) {
            return (duration - inDuration) * 100 / idleDuration;
        } else if (stage == AnimStage.OUT) {
            return (duration - inDuration - idleDuration) * 100 / outDuration;
        }

        return 0;
    }

    public int totalDuration() {
        return inDuration + idleDuration + outDuration;
    }

    public AnimStage getStage() {
        return stage;
    }

    public int duration() {
        return duration;
    }

    public boolean isDone() {
        return isDone;
    }
}
