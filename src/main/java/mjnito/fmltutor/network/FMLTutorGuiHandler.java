package mjnito.fmltutor.network;


import mjnito.fmltutor.client.network.GuiDirtCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class FMLTutorGuiHandler implements IGuiHandler {
    public static final int DIRT_COMPRESSOR=1;


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID==DIRT_COMPRESSOR){
            return new ContainerDirtCompressor(player,world,x,y,z);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID==DIRT_COMPRESSOR){
            return new GuiDirtCompressor(player,world,x,y,z);
        }
        return null;
    }
}
