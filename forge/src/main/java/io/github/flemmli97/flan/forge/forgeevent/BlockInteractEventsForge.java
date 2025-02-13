package io.github.flemmli97.flan.forge.forgeevent;

import io.github.flemmli97.flan.event.BlockInteractEvents;
import io.github.flemmli97.flan.event.ItemInteractEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

public class BlockInteractEventsForge {

    public static void startBreakBlocks(PlayerInteractEvent.LeftClickBlock event) {
        if (!(event.getLevel() instanceof ServerLevel))
            return;
        if (BlockInteractEvents.startBreakBlocks(event.getEntity(), event.getLevel(), event.getHand(), event.getPos(), event.getFace()) == InteractionResult.FAIL)
            event.setCanceled(true);
    }

    public static void breakBlocks(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel))
            return;
        if (!BlockInteractEvents.breakBlocks((Level) event.getLevel(), event.getPlayer(), event.getPos(), event.getState(), event.getLevel().getBlockEntity(event.getPos())))
            event.setCanceled(true);
    }

    public static void useBlocks(PlayerInteractEvent.RightClickBlock event) {
        InteractionResult res = BlockInteractEvents.useBlocks(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        if (res == InteractionResult.SUCCESS) {
            event.setCancellationResult(res);
            event.setCanceled(true);
            return;
        }
        if (res == InteractionResult.FAIL)
            event.setUseBlock(Event.Result.DENY);
        res = ItemInteractEvents.onItemUseBlock(new UseOnContext(event.getEntity(), event.getHand(), event.getHitVec()));
        if (res == InteractionResult.FAIL)
            event.setUseItem(Event.Result.DENY);
    }
}
