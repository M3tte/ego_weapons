# ..v1.01.xx
+ Fixed previous issue with red damage not being functional while "Lore Accurate Damage Types" > 0.
+ Fixed crash during friendly fire override when firing arrows at player entities.
+ ~ Reworked status and stagger effects to allow for targeting non players and improved their feedback.
+ Added /EGODamage command that allows for dealing highly customizable damage
  + The above commands (EGODamage, stagger and status) give feedback how many entities were affected and a different message if no valid entities were found.
+ All statuses should now have respective translation names
+ **Overhauled nothing there AI.**
  + Will now swap targets after certain skills.
  + Skills now correctly apply statuses.
  + New skills added
  + Stagger Animation added
+ All abilities now have consolidated logic for when they can be executed.
  + This means most abilities cannot be used while stunned or in an animation.
  + Abilities such as solemn lament are an exception to this
+ Added correct translations to (hopefully) all potion effects added by the mod.
+ Implemented **Decentralized Status Icons**.
  + The mod will now resolve status icons for descriptions where defined. This is "probably" slightly less performant than simply using unicode but bypasses the normal unicode limit.


# ..v1.01.19
+ Added Udjat Khopesh + Translations
+ Added LCA Khopesh + Translations
+ Added LCA Rifle + Translations
+ Added Gun Descriptions. Ammo Type and Capacity are now displayed correctly.
+ Added Descriptions for reload abilities for firearms
+ Added "Click" on empty ammo for certain weapons
+ Unified ammo effect system to more easily support many ammo types.
+ Refined some descriptions and implemented missing effects.
+ Added EGOAttackContext to more easily unify the data needed for attacks
+ Modified the wearable renderer system to more easily accompany many different renderers with less bloat.
+ Renderer system now has a renderer registry.
+ Added Udjat Suit
+ Added LCA Suit + Glow effects
+ Added shader rendering.
+ Added diffraction shader
+ Added post shaders for udjat
+ Added post shaders for red mist
+ Added post shaders for black silence
+ Added space shader
+ Added inticrate system for secondary weapons
  + When a weapon is held in the offhand and the mainhand weapon has no reload ability or ability. Upon ability use the offhand weapon is used. Otherwise a key is available to toggle to the offhand ability.
+ Added descriptions for holding certain keys in item descriptions
+ Added arayashiki
+ Reworked commands to work with datapacks
+ Reworked Item Description system and added updated descriptions to a lot of items.
+ Fixed and adjusted broken MAO passive
+ Fixed dialogue gamerule handling.
+ Fixed ammo types
+ Rebalanced and buffed justitia special
+ Moved potency only effects like haste or offense up to their own supercclass
+ Added more post shaders for staggering and nothing theres roaring
+ Added post shader for the terror status and revamped it.
+ Hit effect for the SHELL status only triggers on damage sources that are caused by an entity and strong enough.
+ Added true stun for great split vertical
+ Eased imitation decay to take 2x longer than normal skills (30s per stack)
+ Added new gamerule for converting damage types into lore accurate variants (white actually dealing sanity damage)
+ Added new gamerule for pale damage actually dealing percent damage to players

### TODO:
- [x] Go through effects and add names
- [x] Add Cheseds Latency
- [x] Round Defensive Value to one decimal
- [x] Go over Udjat skills and passives
- [x] Add Udjat Enhanced Weapon Ability [Khopesh + Gun]
- [x] Create better animations for Arayasiki
- [x] Add post shader for black silence
- [x] Add post shader for Cheseds Latency
- [x] Fix Udjat voiceline
- [x] Properly unequip the rifle for udjat skills
- [x] Fix funky attack speed with offhand attack
- [x] Ardor blossom kill msgs
- [x] Udjat kill msgs
- [x] Fix udjats offhand timing its messed up
- [x] IMPORTANT - FIX MESSY TIMINGS WITH DELAYED EFFECTS / Partially done
- [x] Add option for lore accurate damage types (white deals sanity)
- [x] Rename gamerules to be more sensible
- [x] Fix / rework mimicry armor ability effect being triggered with bleed
- [x] Enhance justitia special - drain light and light lock and more damage
- [x] Rework aggro for nothing there maybe other stuff as well (vfx to panic)
- [x] Add more attacks to nothing there, mainly situational
- [x] Increase sanity drain for terror
- [x] Rework Shell and Terror into count potency effects
- [x] Link skill usage with epic fight stuns
- [x] Give nothing there shell and imitation gain on kill or when landing certain attacks. Enhanced unblockable goodbye at enough
- [x] Add command for dealing damage
- [x] Add target mark of the udjat
- [x] Add stagger animation for nothing there