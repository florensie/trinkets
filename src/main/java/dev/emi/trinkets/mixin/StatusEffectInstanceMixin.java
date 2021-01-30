package dev.emi.trinkets.mixin;

import dev.emi.trinkets.TrinketsStatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements TrinketsStatusEffectInstance {

	@Shadow private int duration;
	@Unique private boolean isTrinketEffect = false;

	@Inject(method = "updateDuration", cancellable = true, at = @At(value = "FIELD", ordinal = 0, target = "Lnet/minecraft/entity/effect/StatusEffectInstance;duration:I"))
	private void cancelUpdateDuration(CallbackInfoReturnable<Integer> info) {
		if (this.isTrinketEffect) {
			info.setReturnValue(this.duration);
		}
	}

	@Unique
	@Override
	public void trinkets$setTrinketEffect() {
		this.duration = 1;
		this.isTrinketEffect = true;
	}
}
