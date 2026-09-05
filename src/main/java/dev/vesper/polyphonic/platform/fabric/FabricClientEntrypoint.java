package dev.vesper.polyphonic.platform.fabric;

//? fabric {

import dev.vesper.polyphonic.Polyphonic;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Polyphonic.onInitializeClient();
	}

}
//?}
