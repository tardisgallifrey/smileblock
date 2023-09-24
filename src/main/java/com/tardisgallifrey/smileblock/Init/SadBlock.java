package com.tardisgallifrey.smileblock.Init;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

//Create a new block type class that extends
//minecraft...Block class
//with several overriden methods
public class SadBlock extends Block {

    //Set a default facing direction variable
    //called FACING and it sets blocks to North
    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    //SadBlock constructor
    public SadBlock(Properties properties) {
        super(properties);
        //set Default block facing direction
        this.registerDefaultState(this.stateDefinition
                .any()
                .setValue(FACING, Direction.NORTH));
    }

    //create block State definition
    //Overrides same method in VANILLA
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        //method allows getting FACING into Block State
        builder.add(FACING);
    }

    //gets and sets Block state to face player
    //Overrides VANILLA method
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,
                context.getHorizontalDirection()
                        .getOpposite());
    }


    //method to check if player is holding
    //gunpowder and if so, explode sad_block

    //use is not from Minecraft.
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state,
                                          @NotNull Level world,
                                          @NotNull BlockPos pos,
                                          Player player,
                                          @NotNull InteractionHand hand,
                                          @NotNull BlockHitResult hit) {
        //get what player is holding in their hand
        ItemStack held = player.getItemInHand(hand);

        //IF this world is on the server, not a client,
        //AND the item held is gunpowder, then EXPLODE!
        if (!world.isClientSide() && held.getItem() == Items.GUNPOWDER) {
            //call explode method
            // for player at
            // X, Y, Z
            // TNT power is a 4.0 float
            // If we explode, the block is destroyed
            //shrink items in hand by 1 gunpowder??
            //return the Interaction Result of type CONSUME
            //
            world.explode(player, pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    0.2F,
                    true,
                    Explosion.BlockInteraction.DESTROY);
            held.shrink(1);

            return InteractionResult.CONSUME;
        }

        //This only works now if we prefix
        //with ISadblockInit class
        //we createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder);turn every thing about
        //interaction with sad_block to world
        //method is deprecated
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void wasExploded(Level world,
                            BlockPos pos,
                            @NotNull Explosion explosion) {
        world.explode(null,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                4.0F,
                true,
                Explosion.BlockInteraction.DESTROY);

        super.wasExploded(world,
                pos,
                explosion);
    }

    @Override
    public boolean canSustainPlant(@NotNull BlockState state,
                                   @NotNull BlockGetter world,
                                   BlockPos pos,
                                   @NotNull Direction facing,
                                   IPlantable plantable) {
        //check to see if block is plantable for plants
        Block plant = plantable.getPlant(world,
                pos.relative(facing)).getBlock();

        //If the "plant" is a CACTUS, return true
        //else, return the block and let
        //minecraft decide, which should be FALSE
        if (plant == Blocks.CACTUS) {
            return true;
        } else {

            return super.canSustainPlant(state,
                    world,
                    pos,
                    facing,
                    plantable);
        }
    }


    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return true;
    }


    //use randomTick and isRandomlyTicking to
    //do things when the randomTick is checked
    //by Minecraft
    //method is deprecated
    @Override
    public void randomTick(@NotNull BlockState state,
                           ServerLevel world,
                           BlockPos pos,
                           @NotNull RandomSource rand) {

        //get the block state
        BlockState above = world.getBlockState(pos.above());

        //if the block state of what is above is air??
        //then make that block a CACTUS??
        if (above.isAir()) {
            world.setBlockAndUpdate(pos.above(),
                    Blocks.CACTUS.defaultBlockState());
        }
    }
}//End SadBlock Class
