package pro.mikey.mods.pop.client.pops;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.forgespi.Environment;
import pro.mikey.mods.pop.data.PopData;

import java.util.ArrayDeque;
import java.util.Deque;

public enum PopManager {
    INSTANCE;

    public static PopManager get() {
        if (Environment.get().getDist() != Dist.CLIENT) {
            throw new IllegalStateException("PopManager should only be called on the client side");
        }

        return INSTANCE;
    }

    private final Deque<PopData> pops = new ArrayDeque<>();

    public void addPop(PopData pop) {
        pops.add(pop);
    }

    public boolean hasNext() {
        return !pops.isEmpty();
    }

    public PopData next() {
        return pops.poll();
    }
}
