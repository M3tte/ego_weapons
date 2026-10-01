package net.m3tte.ego_weapons.client.renderer.delegatedEntityRendering;

import net.minecraft.client.renderer.IRenderTypeBuffer;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class RenderRequest {
    private static Deque<RenderRequest> queuedRenderRequests = new ArrayDeque<>();

    public abstract void render(IRenderTypeBuffer bufferIn);

    public void register(RenderRequest e) {
        queuedRenderRequests.add(e);
    }

    public static Deque<RenderRequest> getRenderRequests() {
        return queuedRenderRequests;
    }


}
