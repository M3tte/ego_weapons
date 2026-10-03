package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public abstract class RenderRequest {
    private static final HashMap<RenderBatches, Deque<RenderRequest>> queuedRenderRequests = new HashMap<>();

    public abstract void render(IRenderTypeBuffer bufferIn, MatrixStack stack);

    public void add(RenderBatches batch, RenderRequest e) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());

        queuedRenderRequests.get(batch).add(e);
    }

    public static Deque<RenderRequest> getRenderRequests(RenderBatches batch) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());

        return queuedRenderRequests.getOrDefault(batch, null);
    }

    public static boolean registerRequest(RenderBatches batch, RenderRequest req) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());

        return queuedRenderRequests.get(batch).add(req);
    }

    public static void clearRenderBatch(RenderBatches batch) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());
        else {
            queuedRenderRequests.get(batch).clear();
        }
    }

    public static boolean isBatchEmpty(RenderBatches batch) {
        if (!queuedRenderRequests.containsKey(batch)) {
            queuedRenderRequests.put(batch, new ArrayDeque<>());
            return true;
        }
        else {
            return queuedRenderRequests.get(batch).isEmpty();
        }
    }

}
