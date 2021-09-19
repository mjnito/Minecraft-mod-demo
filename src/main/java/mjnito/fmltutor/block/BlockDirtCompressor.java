package mjnito.fmltutor.block;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.creativetab.TabFMLTutor;
import mjnito.fmltutor.network.FMLTutorGuiHandler;
import mjnito.fmltutor.tileentity.TileEntityDirtCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockDirtCompressor extends BlockContainer {
    private static final IProperty<EnumFacing> FACING= PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);

    public BlockDirtCompressor() {
        super(Material.ROCK);
        this.setUnlocalizedName(FMLTutor.MODID+".dirtCompressor");
        this.setCreativeTab(TabFMLTutor.Tab_FMLTutor);
        this.setRegistryName("dirt_compressor");
        this.setHarvestLevel("pickaxe",1);
        this.setHardness(3.5F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING,EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING,EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    @Override//鼠标右击时打开GUI
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            int x=pos.getX(),y = pos.getY(),z = pos.getZ();
            playerIn.openGui(FMLTutor.MODID, FMLTutorGuiHandler.DIRT_COMPRESSOR,worldIn,x,y,z);
        }
        return  true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return  new TileEntityDirtCompressor();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileEntity=worldIn.getTileEntity(pos);
        Capability<IItemHandler>itemHandlerCapability= CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

        IItemHandler up=tileEntity.getCapability(itemHandlerCapability,EnumFacing.UP);
        IItemHandler down=tileEntity.getCapability(itemHandlerCapability,EnumFacing.DOWN);
        IItemHandler side=tileEntity.getCapability(itemHandlerCapability,EnumFacing.NORTH);

        Block.spawnAsEntity(worldIn,pos,up.getStackInSlot(0));
        Block.spawnAsEntity(worldIn,pos,down.getStackInSlot(0));
        Block.spawnAsEntity(worldIn,pos,side.getStackInSlot(0));
        super.breakBlock(worldIn, pos, state);
    }
}
