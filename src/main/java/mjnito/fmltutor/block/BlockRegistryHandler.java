package mjnito.fmltutor.block;

import mjnito.fmltutor.tileentity.TileEntityDirtCompressor;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class BlockRegistryHandler {
    public static final BlockCompressedDirt BLOCK_COMPRESSED_DIRT = new BlockCompressedDirt();
    public static final BlockDirtCompressor BLOCK_DIRT_COMPRESSOR=new BlockDirtCompressor();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(BLOCK_COMPRESSED_DIRT);
        registry.register(BLOCK_DIRT_COMPRESSOR);
        TileEntity.register(TileEntityDirtCompressor.ID,TileEntityDirtCompressor.class);
    }
}
