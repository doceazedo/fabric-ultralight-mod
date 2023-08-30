package com.doceazedo.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.option.SimpleOption;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 23/08/2023
 */
public interface ClientOptionsChangeCallback {
	Event<ClientOptionsChangeCallback> EVENT = EventFactory.createArrayBacked(ClientOptionsChangeCallback.class,
			(listeners) -> (option) -> {
				for (ClientOptionsChangeCallback listener : listeners) {
					listener.onClientOptionsChange(option);
				}
			});

	void onClientOptionsChange(SimpleOption<?> option);
}
