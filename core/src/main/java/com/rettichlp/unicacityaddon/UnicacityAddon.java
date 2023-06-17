package com.rettichlp.unicacityaddon;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.DefaultAddonPlayer;
import com.rettichlp.unicacityaddon.base.Services;
import com.rettichlp.unicacityaddon.base.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.config.DefaultUnicacityAddonConfiguration;
import com.rettichlp.unicacityaddon.base.io.api.API;
import com.rettichlp.unicacityaddon.base.nametags.AddonTag;
import com.rettichlp.unicacityaddon.base.nametags.DutyTag;
import com.rettichlp.unicacityaddon.base.nametags.FactionInfoTag;
import com.rettichlp.unicacityaddon.base.nametags.HouseBanTag;
import com.rettichlp.unicacityaddon.base.nametags.NoPushTag;
import com.rettichlp.unicacityaddon.base.nametags.OutlawTag;
import com.rettichlp.unicacityaddon.base.services.FileService;
import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.commands.UnicacityCommand;
import com.rettichlp.unicacityaddon.controller.DeadBodyController;
import com.rettichlp.unicacityaddon.controller.GuiController;
import com.rettichlp.unicacityaddon.controller.ScreenshotController;
import com.rettichlp.unicacityaddon.controller.SoundController;
import com.rettichlp.unicacityaddon.controller.TabListController;
import com.rettichlp.unicacityaddon.controller.TransportController;
import com.rettichlp.unicacityaddon.controller.WorldInteractionController;
import com.rettichlp.unicacityaddon.core.generated.DefaultReferenceStorage;
import com.rettichlp.unicacityaddon.hudwidgets.AmmunitionHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.BombHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.CarHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.EmergencyServiceHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.HearthHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.InventoryHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.JobHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.MoneyHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PayDayHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.PlantHudWidget;
import com.rettichlp.unicacityaddon.hudwidgets.TimerHudWidget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.models.addon.annotation.AddonMain;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>Hello Labymod addon reviewers and others,</h2>
 * <p>
 * I know the guidelines for publication. Nevertheless, I use the session token of users, access the file system and
 * only return messages in German. Below I describe why I do this.
 * <p>
 * <h3>Session token ({@link API})</h3>
 * An important function of the addon is to collect statistics and make data available to all players. In order to offer
 * a high level of user-friendliness, an update should not have to be created due to small changes. That's why I use an
 * API through which I make some data available. I use a private server for this. This provides data for:
 * <ul>
 *     <li>addon groups <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/player">API</a></li>
 *     <li>banners <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/banner">API</a></li>
 *     <li>blacklist reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blacklistreason/LEMILIEU">API</a> (unauthorized)</li>
 *     <li>blackmarket locations <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/blackmarket">API</a></li>
 *     <li>broadcasts <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/broadcast/queue">API</a></li>
 *     <li>events <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/event">API</a></li>
 *     <li>house bans <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/houseban?advanced=false">API</a> (unauthorized for <code>advanced=true</code>)</li>
 *     <li>house ban reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/housebanreason">API</a></li>
 *     <li>users <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/mgmt/users">API</a></li>
 *     <li>navi points <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/navipoint">API</a></li>
 *     <li>revives <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/revive">API</a> (unauthorized)</li>
 *     <li>statistics <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/statistic/RettichLP">API</a></li>
 *     <li>wanted reasons <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/wantedreason">API</a></li>
 *     <li>yasin <a href="http://rettichlp.de:8888/unicacityaddon/v1/dhgpsklnag2354668ec1d905xcv34d9bdee4b877/yasin">API</a></li>
 * </ul>
 * This data can change constantly and can therefore not be entered statically in the code.
 * <p>
 * Why i need the session token for this? For example, the number of revives should only be seen by medics, as well as
 * the name of the person who entered a house ban (advanced house ban view). For editing any data, a certain faction and
 * rank in this faction is required.
 * <p>
 * I can read the faction and rank from the Unicacity website
 * (<a href="https://unicacity.de/fraktionen">https://unicacity.de/fraktionen</a>). But in order to be able to assign
 * the faction information to a player, I need his UUID. I could pass these as parameters in the api call, but you could
 * mess that up by calling the endpoint with a different UUID that isn't your own. I needed a way to pass the UUID so
 * that it cannot (so easily) be falsified. For this I use the session token, because I can use it to read the UUID via
 * the Mojang API and nobody else knows the session token.
 * <p>
 * A more detailed overview of how the authorization works can be found
 * <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/autorisierung/">here</a> and an overview of all data I
 * store can be found <a href="https://wiki.unicacityaddon.rettichlp.de/api/function/daten-und-speicherung/">here</a>.
 * The session token is never saved ore logged. Only my specially generated token is saved in a database. If necessary I
 * can give access to the server code and give an insight into all stored data.
 * <p>
 * <h3>File storage access ({@link FileService})</h3>
 * The addon uses data that is not important for all players. That's why they are not stored on my server via the API,
 * but locally on the player's computer. This data contains, for example, the current account balance or the time until
 * the next payday. This is used to be able to display Hud-Widgets immediately after joining the server and not to wait
 * until a specific message is in the chat or to execute a command that supplies the said data. The data is saved in the
 * Minecraft folder under an extra folder called <code>unicacityaddon</code>.
 * <p>
 * <h3>Language file</h3>
 * The addon is for a German speaking server. Thus, all messages that are sent in response to commands or events are
 * also in German. A random survey has shown that many players play in English, but still want German messages in the
 * chat. Only the configuration is also available in English.
 *
 * @author RettichLP
 */
@AddonMain
@Accessors(fluent = true)
@Getter
@NoArgsConstructor
public class UnicacityAddon extends LabyAddon<DefaultUnicacityAddonConfiguration> {

    private AddonPlayer player;
    private Services services;
    private API api;
    private TeamSpeakAPI teamSpeakAPI;
    private List<Command> commands;

    @Override
    public void load() {
        this.player = new DefaultAddonPlayer(this);
        this.services = new Services(this);
        this.api = new API(this);
        this.teamSpeakAPI = new TeamSpeakAPI(this);
        this.commands = new ArrayList<>();

        this.logger().info("Loaded UnicacityAddon");
    }

    @Override
    protected void enable() {
        this.api.sync(this.player);
        this.registerSettingCategory();
        this.registerTags();
        this.registerHudWidgets();
        this.registerListeners();
        this.registerCommands();

        new Thread(() -> {
            try {
                this.teamSpeakAPI.initialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        this.logger().info("Enabled UnicacityAddon");
    }

    @Override
    protected Class<DefaultUnicacityAddonConfiguration> configurationClass() {
        return DefaultUnicacityAddonConfiguration.class;
    }

    public GuiController guiController() {
        return controller().getGuiController();
    }

    public DeadBodyController deadBodyController() {
        return controller().getDeadBodyController();
    }

    public ScreenshotController screenshotController() {
        return controller().getScreenshotController();
    }

    public SoundController soundController() {
        return controller().getSoundController();
    }

    public TabListController tabListController() {
        return controller().getTabListController();
    }

    public TransportController transportController() {
        return controller().getTransportController();
    }

    public WorldInteractionController worldInteractionController() {
        return controller().getWorldInteractionController();
    }

    private DefaultReferenceStorage controller() {
        return this.referenceStorageAccessor();
    }

    private void registerTags() {
        TagRegistry tagRegistry = this.labyAPI().tagRegistry();
        tagRegistry.register(
                "unicacityaddon_addontag",
                PositionType.ABOVE_NAME,
                AddonTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_nopushtag",
                PositionType.ABOVE_NAME,
                NoPushTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_housebantag",
                PositionType.BELOW_NAME,
                HouseBanTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_outlawtag",
                PositionType.LEFT_TO_NAME,
                OutlawTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_factioninfotag",
                PositionType.RIGHT_TO_NAME,
                FactionInfoTag.create(this)
        );

        tagRegistry.register(
                "unicacityaddon_dutytag",
                PositionType.RIGHT_TO_NAME,
                DutyTag.create(this)
        );
    }

    private void registerHudWidgets() {
        HudWidgetRegistry registry = this.labyAPI().hudWidgetRegistry();
        registry.register(new AmmunitionHudWidget("ammunition", this));
        registry.register(new BombHudWidget("bomb", this));
        registry.register(new CarHudWidget("car", this));
        registry.register(new EmergencyServiceHudWidget("service", this));
        registry.register(new HearthHudWidget("hearth", this));
        registry.register(new InventoryHudWidget("inventory", this));
        registry.register(new JobHudWidget("job", this));
        registry.register(new MoneyHudWidget("money", this));
        registry.register(new PayDayHudWidget("payday", this));
        registry.register(new PlantHudWidget("plant", this));
        registry.register(new TimerHudWidget("timer", this));
    }

    private void registerListeners() {
        AtomicInteger registeredListenerCount = new AtomicInteger();
        Set<Class<?>> listenerClassSet = this.services.util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.listener");
        listenerClassSet.stream()
                .filter(listenerClass -> listenerClass.isAnnotationPresent(UCEvent.class))
                .forEach(listenerClass -> {
                    try {
                        this.registerListener(listenerClass.getConstructor(UnicacityAddon.class).newInstance(this));
                        registeredListenerCount.getAndIncrement();
                    } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                             InstantiationException e) {
                        this.logger().warn("Can't register listener: {}", listenerClass.getSimpleName());
                        e.printStackTrace();
                    }
                });
        this.logger().info("Registered {}/{} listeners", registeredListenerCount, listenerClassSet.size());
    }

    private void registerCommands() {
        AtomicInteger registeredCommandCount = new AtomicInteger();
        AtomicInteger deactivatedCommandCount = new AtomicInteger();
        Set<Class<?>> commandClassSet = this.services.util().getAllClassesFromPackage("com.rettichlp.unicacityaddon.commands");
        commandClassSet.remove(UnicacityCommand.class);
        commandClassSet.stream()
                .filter(commandClass -> commandClass.isAnnotationPresent(UCCommand.class))
                .forEach(commandClass -> {
                    UCCommand ucCommand = commandClass.getAnnotation(UCCommand.class);
                    if (ucCommand.deactivated()) {
                        deactivatedCommandCount.getAndIncrement();
                    } else {
                        try {
                            Command command = (Command) commandClass.getConstructor(UnicacityAddon.class, UCCommand.class).newInstance(this, ucCommand);
                            this.commands.add(command);
                            this.registerCommand(command);
                            registeredCommandCount.getAndIncrement();
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                                 InstantiationException e) {
                            this.logger().warn("Can't register command: {}", commandClass.getSimpleName());
                            e.printStackTrace();
                        }
                    }
                });
        this.logger().info("Registered {}/{} commands, {} skipped (deactivated)", registeredCommandCount, commandClassSet.size() - deactivatedCommandCount.get(), deactivatedCommandCount.get());
    }
}