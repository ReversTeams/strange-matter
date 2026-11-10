package net.reversteam.strangematter.sound;

import net.reversteam.strangematter.StrangeMatterMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class StrangeMatterSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, StrangeMatterMod.MODID);

    // Research Tablet Sounds
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_TABLET_OPEN = registerSoundEvent("research_tablet_open");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_TABLET_NODE_HOVER = registerSoundEvent("research_tablet_node_hover");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_TABLET_NODE_CLICK = registerSoundEvent("research_tablet_node_click");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_TABLET_PAGE_TURN = registerSoundEvent("research_tablet_page_turn");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_TABLET_BUTTON = registerSoundEvent("button_noise");

    // Research Node Sounds
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_NODE_LOCKED_CLICK = registerSoundEvent("research_node_locked_click");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_NOTE_CREATE = registerSoundEvent("research_note_create");

    // Research Machine Sounds
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_OPEN = registerSoundEvent("research_machine_open");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_NOTE_INSERT = registerSoundEvent("research_machine_note_insert");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_NOTE_REJECT = registerSoundEvent("research_machine_note_reject");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_BEGIN_RESEARCH = registerSoundEvent("research_machine_begin_research");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_IDLE_LOOP = registerSoundEvent("research_machine_idle_loop");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_SUCCESS = registerSoundEvent("research_machine_success");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_FAILURE = registerSoundEvent("research_machine_failure");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_INSTABILITY_HIGH = registerSoundEvent("research_machine_instability_high");
    public static final java.util.function.Supplier<SoundEvent> RESEARCH_MACHINE_INSTABILITY_CRITICAL = registerSoundEvent("research_machine_instability_critical");

    // Energy Minigame Sounds
    public static final java.util.function.Supplier<SoundEvent> ENERGY_DIAL_TOGGLE = registerSoundEvent("energy_dial_toggle");
    public static final java.util.function.Supplier<SoundEvent> ENERGY_BUTTON_CLICK = registerSoundEvent("energy_button_click");
    public static final java.util.function.Supplier<SoundEvent> ENERGY_WAVE_ALIGN = registerSoundEvent("energy_wave_align");
    public static final java.util.function.Supplier<SoundEvent> ENERGY_WAVE_MISALIGN = registerSoundEvent("energy_wave_misalign");

    // General Minigame Sounds
    public static final java.util.function.Supplier<SoundEvent> MINIGAME_STABLE = registerSoundEvent("minigame_stable");
    public static final java.util.function.Supplier<SoundEvent> MINIGAME_UNSTABLE = registerSoundEvent("minigame_unstable");

    // Environmental Sounds
    public static final java.util.function.Supplier<SoundEvent> FIELD_SCANNER_SCAN = registerSoundEvent("field_scanner_scan");
    public static final java.util.function.Supplier<SoundEvent> GRAVITY_ANOMALY_LOOP = registerSoundEvent("gravity_anomaly_loop");
    public static final java.util.function.Supplier<SoundEvent> ENERGETIC_RIFT_LOOP = registerSoundEvent("energetic_rift_loop");
    public static final java.util.function.Supplier<SoundEvent> ECHOING_SHADOW_LOOP = registerSoundEvent("echoing_shadow_loop");
    public static final java.util.function.Supplier<SoundEvent> WARP_GATE_LOOP = registerSoundEvent("warp_gate_loop");
    public static final java.util.function.Supplier<SoundEvent> TEMPORAL_BLOOM_LOOP = registerSoundEvent("temporal_bloom_loop");
    public static final java.util.function.Supplier<SoundEvent> THOUGHTWELL_LOOP = registerSoundEvent("thoughtwell_loop");

    // Warp Gun sounds
    public static final java.util.function.Supplier<SoundEvent> WARP_GUN_SHOOT = registerSoundEvent("warp_gun_shoot");

    // Chrono Blister sounds
    public static final java.util.function.Supplier<SoundEvent> CHRONO_BLISTER_CHARGE = registerSoundEvent("chronoblister_charge");
    public static final java.util.function.Supplier<SoundEvent> CHRONO_BLISTER_FIRE = registerSoundEvent("chronoblister_fire");

    // Echo Vacuum sounds
    public static final java.util.function.Supplier<SoundEvent> ECHO_VACUUM_CHARGE_UP = registerSoundEvent("echo_vacuum_charge_up");
    public static final java.util.function.Supplier<SoundEvent> ECHO_VACUUM_FIRE_LOOP = registerSoundEvent("echo_vacuum_fire_loop");
    public static final java.util.function.Supplier<SoundEvent> ECHO_VACUUM_CHARGE_DOWN = registerSoundEvent("echo_vacuum_charge_down");
    public static final java.util.function.Supplier<SoundEvent> ECHO_VACUUM_CONTAIN = registerSoundEvent("echo_vacuum_contain");

    // Reality Forge sounds
    public static final java.util.function.Supplier<SoundEvent> REALITY_FORGE_CRAFT = registerSoundEvent("reality_forge_craft");
    public static final java.util.function.Supplier<SoundEvent> REALITY_FORGE_CRAFTING = registerSoundEvent("reality_forge_crafting");
    public static final java.util.function.Supplier<SoundEvent> REALITY_FORGE_INSERT = registerSoundEvent("reality_forge_insert");

    // Stasis Projector sounds
    public static final java.util.function.Supplier<SoundEvent> STASIS_PROJECTOR_ON = registerSoundEvent("stasis_projector_on");
    public static final java.util.function.Supplier<SoundEvent> STASIS_PROJECTOR_OFF = registerSoundEvent("stasis_projector_off");

    // Graviton Hammer sounds
    public static final java.util.function.Supplier<SoundEvent> GRAVITON_CHARGEUP = registerSoundEvent("graviton_chargeup");

    // Hoverboard sounds
    public static final java.util.function.Supplier<SoundEvent> HOVERBOARD_LOOP = registerSoundEvent("hoverboard_loop");
    public static final java.util.function.Supplier<SoundEvent> HOVERBOARD_JUMP = registerSoundEvent("hoverboard_jump");

    private static java.util.function.Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(StrangeMatterMod.MODID, name)));
    }
}
