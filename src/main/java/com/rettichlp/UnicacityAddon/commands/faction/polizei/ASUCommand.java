package com.rettichlp.UnicacityAddon.commands.faction.polizei;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.faction.polizei.WantedReason;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/e1e4cc90a852a24fbb552413eb478097f865c6f3/src/main/java/de/fuzzlemann/ucutils/commands/faction/police/ASUCommand.java">UCUtils by paulzhng</a>
 */
public class ASUCommand extends CommandBase {

    private final Timer timer = new Timer();

    @Override @Nonnull public String getName() {
        return "asu";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/asu [Spieler...] [Grund] (-v/-b/-fsa/-wsa)";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 2) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        Set<Flag> flags = getFlags(args);
        int reasonIndex = args.length - flags.size() - 1;

        List<String> players = Arrays.asList(args).subList(0, reasonIndex);
        String reason = args[reasonIndex];

        WantedReason wantedReason = null;
        for (WantedReason wanted : WantedReason.values()) {
            if (wanted.getReason().equals(reason)) {
                wantedReason = wanted;
            }
        }

        if (wantedReason == null) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Der Wantedgrund wurde nicht gefunden." + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        String wantedReasonString = wantedReason.getReason().replace('-', ' ');
        int wantedReasonAmount = wantedReason.getAmount();

        for (Flag flag : flags) {
            wantedReasonString = flag.modifyWantedReasonString(wantedReasonString);
            wantedReasonAmount = flag.modifyWantedReasonAmount(wantedReasonAmount);
        }

        giveWanteds(p, wantedReasonString, wantedReasonAmount, players);
    }

    private void giveWanteds(UPlayer issuer, String reason, int amount, List<String> players) {
        int maxAmount = Math.min(amount, 69);

        if (players.size() > 14) {
            timer.scheduleAtFixedRate(new TimerTask() {
                private int i;

                @Override
                public void run() {
                    if (i >= players.size()) {
                        cancel();
                        return;
                    }

                    String player = players.get(i++);

                    issuer.sendChatMessage("/su " + maxAmount + " " + player + " " + reason);
                    System.out.println("/su " + maxAmount + " " + player + " " + reason);
                }
            }, 0, TimeUnit.SECONDS.toMillis(1));
        } else{
            for (String player : players) {
                issuer.sendChatMessage("/su " + amount + " " + player + " " + reason);
                System.out.println("/su " + amount + " " + player + " " + reason);
            }
        }
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
            return tabCompletions;
        } else {
            List<String> tabCompletions = Arrays.stream(WantedReason.values()).map(WantedReason::getReason).sorted().collect(Collectors.toList());
            tabCompletions.addAll(ForgeUtils.getOnlinePlayers());

            String input = args[args.length - 1].toLowerCase().replace('-', ' ');
            tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));

            tabCompletions.addAll(Arrays.stream(Flag.values()).map(Flag::getFlagArgument).sorted().collect(Collectors.toList()));

            return tabCompletions;
        }
    }

    private Set<Flag> getFlags(String[] args) {
        Set<Flag> flags = new HashSet<>();

        for (String arg : args) {
            Flag flag = Flag.getFlag(arg);

            if (flag != null)
                flags.add(flag);
        }

        return flags;
    }

    private enum Flag {
        TRIED("-v", "Versuchte/r/s ", "", "x/2"),
        SUBSIDY("-b", "Beihilfe bei der/dem ", "", "x-10"),
        DRIVERS_LICENSE_WITHDRAWAL("-fsa", "", " + Führerscheinabnahme", "x"),
        WEAPONS_LICENSE_WITHDRAWAL("-wsa", "", " + Waffenscheinabnahme", "x");

        private final String flagArgument;
        private final String prependReason;
        private final String postponeReason;
        private final String wantedModification;

        Flag(String flagArgument, String prependReason, String postponeReason, String wantedModification) {
            this.flagArgument = flagArgument;
            this.prependReason = prependReason;
            this.postponeReason = postponeReason;
            this.wantedModification = wantedModification;
        }

        static Flag getFlag(String string) {
            for (Flag flag : Flag.values()) {
                if (flag.flagArgument.equalsIgnoreCase(string)) return flag;
            }

            return null;
        }

        private String getFlagArgument() {
            return flagArgument;
        }

        public String modifyWantedReasonString(String wantedReasonString) {
            return prependReason + wantedReasonString + postponeReason;
        }

        public int modifyWantedReasonAmount(int wantedReasonAmount) {
            return (int) new MathUtils(wantedModification.replace("x", String.valueOf(wantedReasonAmount))).evaluate();
        }
    }
}