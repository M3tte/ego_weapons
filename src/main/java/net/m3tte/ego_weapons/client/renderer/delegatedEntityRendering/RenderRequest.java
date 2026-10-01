package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import net.minecraft.client.renderer.IRenderTypeBuffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public abstract class RenderRequest {
    private static final HashMap<RenderBatches, Deque<RenderRequest>> queuedRenderRequests = new HashMap<>();

    public abstract void render(IRenderTypeBuffer bufferIn);

    public void register(RenderBatches batch, RenderRequest e) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());

        queuedRenderRequests.get(batch).add(e);
    }

    public static Deque<RenderRequest> getRenderRequests(RenderBatches batch) {
        if (!queuedRenderRequests.containsKey(batch))
            queuedRenderRequests.put(batch, new ArrayDeque<>());

        return queuedRenderRequests.getOrDefault(batch, null);
    }


}
