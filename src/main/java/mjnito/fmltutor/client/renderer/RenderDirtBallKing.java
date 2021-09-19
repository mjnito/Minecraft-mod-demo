package mjnito.fmltutor.client.renderer;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.client.model.ModelDirtBallKing;
import mjnito.fmltutor.entity.EntityDirtBallKing;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.Sys;

import java.util.Random;


public class RenderDirtBallKing extends RenderLiving {
    private static  final ResourceLocation ENTITY_DIRT_BALL_KING_TEXTURE=new ResourceLocation(FMLTutor.MODID+":textures/entity/"
    + EntityDirtBallKing.ID+"/"+EntityDirtBallKing.ID+".png");
    private static final ResourceLocation ENTITY_DIRT_BALL_KING_BLUE_TEXTURE=new ResourceLocation(FMLTutor.MODID+":textures/entity/"
            +EntityDirtBallKing.ID+"/"+EntityDirtBallKing.ID+"_blue.png");
    private static final ResourceLocation ENTITY_DIRT_BALL_KING_GREEN_TEXTURE=new ResourceLocation(FMLTutor.MODID+":textures/entity/"
            +EntityDirtBallKing.ID+"/"+EntityDirtBallKing.ID+"_green.png");

    public RenderDirtBallKing(RenderManager manager){
        super(manager,new ModelDirtBallKing(),0.8F);
    }


    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        byte color=((EntityDirtBallKing)entity).getColor();
        if(color==2){
            return ENTITY_DIRT_BALL_KING_GREEN_TEXTURE;
        }
        else if(color==1){
            return  ENTITY_DIRT_BALL_KING_BLUE_TEXTURE;
        }else
            return ENTITY_DIRT_BALL_KING_TEXTURE;
    }
}
