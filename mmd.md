classDiagram
direction BT
class ActionType {
<<enumeration>>
  + ActionType() 
  +  PLUS
  +  MINUS
  + valueOf(String) ActionType
  + values() ActionType[]
}
class BadMyEffect {
<<enumeration>>
  - BadMyEffect(PotionEffectType, int) 
  - BadMyEffect(PotionEffectType, int, MyEffect[]) 
  +  UNLUCKY
  +  WEAKNESS
  +  SLOW_DIGGING
  +  HUNGER
  - PotionEffectType type
  +  SLOWNESS
  +  BAD_OMEN
  - MyEffect[] dependencies
  +  SLOWNESS_2
  - int level
  +  SLOW_FALLING
  + additionSound() Sound
  + removalSound() Sound
  + getMyEffects() MyEffect[]
  + search(PotionEffect) Collection~BadMyEffect~
  + getEffectType() EffectType
  + values() BadMyEffect[]
  + getLevel() int
  + getType() PotionEffectType
  + getDependencies() MyEffect[]
  + valueOf(String) BadMyEffect
}
class Calculable {
<<Interface>>
  + calc(int) int
}
class CustomGameKilledEvent {
  + CustomGameKilledEvent(Component) 
  + HandlerList handlerList
  + Component reason
  + getReason() Component
  + setReason(Component) void
  + getHandlers() HandlerList
}
class DateFormatter {
<<Interface>>

}
class ESCommand {
  + ESCommand() 
  + String MOTP
  + onCommand(CommandSender, Command, String, String[]) boolean
}
class EffectArrayList {
  + EffectArrayList(Player) 
  - Player player
  + getOneRandom(EffectType, boolean) MyEffect?
  # getRandomFromList(EffectType, ArrayList~MyEffect~, boolean) MyEffect
  + getDependent(MyEffect, boolean) Collection~MyEffect~
  + getSimilar(MyEffect) Collection~MyEffect~
  + getOneRandom(EffectType) MyEffect?
  + calculateValue() int
  - grantEffect(MyEffect) void
  - revokeEffect(MyEffect) void
  + hasEffect(PotionEffect) boolean
  + reset() void
  + containsEffect(PotionEffect) boolean
  + getOneRandom() MyEffect
}
class EffectMap {
  + EffectMap() 
  + minus(UUID) void
  + prepare(Player[]) void
  # calculateValue(UUID) int
  + reset() void
  + clear(EffectMap) void
  + plus(UUID) void
  + clear() void
  + copyFrom(EffectMap) EffectMap
  + prepare(Player) boolean
  + create(UUID) EffectArrayList
}
class EffectSteal {
  + EffectSteal() 
  - long startTime
  - EffectStealTimer effectStealTimer
  - Registerator~Runnable~ gameEndHandlers
  - EffectMap effectMap
  + Component broadCastPrefix
  - boolean sendAdOnEnd
  - Function~EffectMap, UUID[]~ winnerGenerator
  - long endTime
  - DateFormatter dateFormatter
  - EffectSteal plugin
  - SubscribableType~Boolean~ running
  + isRunning() boolean
  + gameEnd(UUID?[]) void
  + hasPluginOpPermissions(CommandSender) boolean
  + setWinnerGenerator(Function~EffectMap, UUID[]~) void
  + get() EffectSteal
  + isSendAdOnEnd() boolean
  + setEndTime(long) void
  + onEnable() void
  + service() EffectStealService?
  + killGame(Component) void
  + getRunning() SubscribableType~Boolean~
  + getWinnerGenerator() Function~EffectMap, UUID[]~
  + onDisable() void
  + broadCast(Component) void
  + broadCast(String) void
  + getGameEndHandlers() Registerator~Runnable~
  + setSendAdOnEnd(boolean) void
  + getEndTime() long
  + getEffectMap() EffectMap
  + reportKill(Player, Player) void
  - subscribe() void
  + getStartTime() long
  + setStartTime(long) void
  + log(String) void
  + notifyGameEnd() void
  + serviceProvider() RegisteredServiceProvider~EffectStealService~?
}
class EffectStealActionEvent {
  + EffectStealActionEvent(UUID, MyEffect, ActionType) 
  - ActionType actionType
  - UUID player
  - MyEffect effect
  - HandlerList handlerList
  + getHandlerList() HandlerList
  + getPlayer() UUID
  + getEffect() MyEffect
  + getHandlers() HandlerList
  + getPlayerAsPlayer() Player
  + getActionType() ActionType
}
class EffectStealController {
  + EffectStealController() 
  - DateFormatter dateFormatter
  + killGameEarly(Component) void
  + getEndTime() long
  + addGameEndHandler(Runnable) void
  + killGameEarly() void
  + getEffectMap() EffectMap
  + getWinnerGenerator() Function~EffectMap, UUID[]~
  + toString() String
  + startNewGame(long, Runnable) void
  + isGameFinished() boolean
  + setEffectMap(EffectMap) EffectMap
  + getDateFormatter() DateFormatter
  + getStartTime() long
  + broadCast(Component) void
  + setWinnerGenerator(Function~EffectMap, UUID[]~) void
  + setDateFormatter(DateFormatter) void
}
class EffectStealListener {
  + EffectStealListener(EffectSteal) 
  - Map~UUID, Collection~PotionEffect~~ emap
  + onTimerTick(TimerTickEvent) void
  + onMilk(PlayerInteractEvent) void
  + onRespawn(PlayerPostRespawnEvent) void
  + onKill(PlayerDeathEvent) void
  + onEffect(EntityPotionEffectEvent) void
}
class EffectStealService {
<<Interface>>
  + killGameEarly(Component) void
  + startNewGame(long) void
  + setWinnerGenerator(Function~EffectMap, UUID[]~) void
  + isGameStarted() boolean
  + setEffectMap(EffectMap) EffectMap
  + getEffectMap() EffectMap
  + isGameFinished() boolean
  + killGameEarly() void
  + getWinnerGenerator() Function~EffectMap, UUID[]~
  + getGameEndHandlers() Registerator~Runnable~
  + setDateFormatter(DateFormatter) void
  + getStartTime() long
  + minus(UUID) void
  + startNewGame(long, Runnable) void
  + getEndTime() long
  + addGameEndHandler(Runnable) void
  + broadCast(Component) void
  + plus(UUID) void
  + getDateFormatter() DateFormatter
}
class EffectStealTimer {
  + EffectStealTimer(Date, DateFormatter) 
  - Date endDate
  - DateFormatter dateFormatter
  - Runnable runnable
  + getDateFormatter() DateFormatter
  + getEndDate() Date
  + startNew() BukkitTask
  + chancelAllTasks() void
}
class EffectType {
<<enumeration>>
  - EffectType(int) 
  +  NEUTRAL
  +  GOOD
  +  UNKNOWN
  - int calc
  +  BAD
  + calc(int) int
  + valueOf(String) EffectType
  + values() EffectType[]
}
class GameEndEvent {
  + GameEndEvent(UUID[]) 
  - HandlerList handlerList
  - UUID[] winner
  + getWinner() UUID[]
  + getHandlers() HandlerList
  + isWinnerPresent() boolean
  + getEffects() EffectArrayList?[]
  + getWinnerPlayer() OfflinePlayer[]?
}
class GameKilledEvent {
  + GameKilledEvent(Component) 
  - Component reason
  - HandlerList handlerList
  + getReason() Component
  + getHandlers() HandlerList
}
class GameStartEvent {
  + GameStartEvent(long) 
  - long endTime
  - HandlerList handlerList
  + getHandlers() HandlerList
  + getEndTime() long
}
class GoodMyEffect {
<<enumeration>>
  - GoodMyEffect(PotionEffectType, int) 
  - GoodMyEffect(PotionEffectType, int, MyEffect[]) 
  +  HEALTH
  +  JUMP_BOOST_2
  +  RESISTANCE_2
  +  HASTE_2
  +  NIGHT_VISION
  +  HASTE
  +  SLOW_FALLING
  - MyEffect[] dependencies
  +  SPEED
  +  LUCK
  +  HERO
  +  CONDUIT_POWER
  +  REGENERATION_2
  - PotionEffectType type
  +  FIRE_RESISTANCE
  - int level
  +  RESISTANCE
  +  SPEED_2
  +  STRENGTH
  +  WATER_BREATHING
  +  REGENERATION
  +  DOLPHINS_GRACE
  +  JUMP_BOOST
  + removalSound() Sound
  + getType() PotionEffectType
  + valueOf(String) GoodMyEffect
  + getDependencies() MyEffect[]
  + getMyEffects() MyEffect[]
  + getLevel() int
  + getEffectType() EffectType
  + search(PotionEffect) Collection~GoodMyEffect~
  + values() GoodMyEffect[]
  + additionSound() Sound
}
class MyEffect {
<<Interface>>
  + getEffectType() EffectType
  + revoke(Player) void
  + getDependencies() MyEffect[]
  + grant(Player, boolean) void
  + grant(Player) void
  + isSimilar(E) boolean
  + getOneNewRandom(MyEffect[]) MyEffect?
  + getType() PotionEffectType
  + removalSound() Sound
  + getLevel() int
  + revoke(Player, boolean) void
  + getMyEffects() MyEffect[]
  + additionSound() Sound
  + hasDependencies() boolean
}
class SingleWinnerGameEndEvent {
  + SingleWinnerGameEndEvent(UUID) 
  + HandlerList handlerList
  + getSingleWinnerPlayer() OfflinePlayer
  + getSingleEffects() EffectArrayList
  + getHandlers() HandlerList
}
class TimerTickEvent {
  + TimerTickEvent(Instant, Date, String) 
  - Date endDate
  - String actionBarMiniMessage
  - Instant now
  - HandlerList handlers
  + getEndDate() Date
  + getNow() Instant
  + getActionBarMiniMessage() String
  + setActionBarMiniMessage(String) void
  + getHandlerList() HandlerList
  + getHandlers() HandlerList
  + toString() String
  + between() Duration
  + getEndInstant() Instant
  + getActionBarAsComponent() Component
}
class UnknownEffect {
  + UnknownEffect() 
  + removalSound() Sound
  + additionSound() Sound
  + getType() PotionEffectType
  + getDependencies() MyEffect[]
  + getMyEffects() MyEffect[]
  + failSound() Sound
  + getEffectType() EffectType
  + getLevel() int
}

EffectStealActionEvent  -->  ActionType 
BadMyEffect  ..>  MyEffect 
BadMyEffect  ..>  MyEffect : «create»
BadMyEffect "1" *--> "dependencies *" MyEffect 
CustomGameKilledEvent  -->  GameKilledEvent 
EffectArrayList  ..>  UnknownEffect : «create»
EffectMap  ..>  EffectArrayList : «create»
EffectMap  ..>  EffectStealActionEvent : «create»
EffectMap  ..>  MyEffect : «create»
EffectMap  ..>  UnknownEffect : «create»
EffectSteal "1" *--> "dateFormatter 1" DateFormatter 
EffectSteal  ..>  ESCommand : «create»
EffectSteal "1" *--> "effectMap 1" EffectMap 
EffectSteal  ..>  EffectMap : «create»
EffectSteal  ..>  EffectStealController : «create»
EffectSteal  ..>  EffectStealListener : «create»
EffectSteal "1" *--> "effectStealTimer 1" EffectStealTimer 
EffectSteal  ..>  EffectStealTimer : «create»
EffectSteal  ..>  GameEndEvent : «create»
EffectSteal  ..>  GameKilledEvent : «create»
EffectSteal  ..>  SingleWinnerGameEndEvent : «create»
EffectStealActionEvent "1" *--> "actionType 1" ActionType 
EffectStealActionEvent "1" *--> "effect 1" MyEffect 
EffectStealController "1" *--> "dateFormatter 1" DateFormatter 
EffectStealController  ..>  EffectStealService 
EffectStealController  ..>  GameStartEvent : «create»
EffectStealTimer "1" *--> "dateFormatter 1" DateFormatter 
EffectStealTimer  ..>  TimerTickEvent : «create»
EffectType  ..>  Calculable 
GameEndEvent  ..>  EffectArrayList : «create»
GoodMyEffect  ..>  MyEffect 
GoodMyEffect "1" *--> "dependencies *" MyEffect 
GoodMyEffect  ..>  MyEffect : «create»
SingleWinnerGameEndEvent  -->  GameEndEvent 
UnknownEffect  ..>  MyEffect 
UnknownEffect  ..>  MyEffect : «create»
