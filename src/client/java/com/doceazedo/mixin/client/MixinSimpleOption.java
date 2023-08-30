package com.doceazedo.mixin.client;

import com.doceazedo.callbacks.ClientOptionsChangeCallback;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 23/08/2023
 */
@Mixin(SimpleOption.class)
public class MixinSimpleOption<T> {
	@Inject(method = "setValue", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption$Callbacks;validate(Ljava/lang/Object;)Ljava/util/Optional;"))
	public void onChange(T value, CallbackInfo ci) {
		ClientOptionsChangeCallback.EVENT.invoker().onClientOptionsChange((SimpleOption<?>) (Object) this);
	}
}
