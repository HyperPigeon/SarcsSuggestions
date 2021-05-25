package net.hyper_pigeon.sarcs_suggestions;

import net.hyper_pigeon.sarcs_suggestions.registry.SarcsSuggestionsBlocks;
import net.hyper_pigeon.sarcs_suggestions.registry.SarcsSuggestionsItems;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import net.fabricmc.api.ModInitializer;

public class SarcsSuggestions implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Sarcs Suggestions");
	public static LinkedList<ItemStack> LOST_AND_FOUND = new LinkedList<>();

	@Override
	public void onInitialize() {
		SarcsSuggestionsItems.init();
		SarcsSuggestionsBlocks.init();
	}
}
