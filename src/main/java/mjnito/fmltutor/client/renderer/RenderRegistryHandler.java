package mjnito.fmltutor.client.renderer;

import mjnito.fmltutor.entity.EntityDirtBall;
import mjnito.fmltutor.entity.EntityDirtBallKing;
import mjnito.fmltutor.item.ItemRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderRegistryHandler {

    public static void register(){
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtBallKing.class,manager -> {
            return new RenderDirtBallKing(manager);
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtBall.class,manager -> {
            RenderItem renderItem= Minecraft.getMinecraft().getRenderItem();
            return new RenderSnowball<EntityDirtBall>(manager, ItemRegistryHandler.DIRT_BALL,renderItem);
        });
    }
}
