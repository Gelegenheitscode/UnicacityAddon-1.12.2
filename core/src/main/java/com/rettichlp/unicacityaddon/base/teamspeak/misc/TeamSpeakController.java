/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.rettichlp.unicacityaddon.base.teamspeak.misc;

import com.rettichlp.unicacityaddon.base.teamspeak.TeamSpeakAPI;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Channel;
import com.rettichlp.unicacityaddon.base.teamspeak.models.Server;
import com.rettichlp.unicacityaddon.base.teamspeak.models.User;
import com.rettichlp.unicacityaddon.base.teamspeak.util.ArgumentParser;
import com.rettichlp.unicacityaddon.base.teamspeak.util.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * This code was modified. The original code is available at: <a href="https://github.com/labymod-addons/teamspeak">https://github.com/labymod-addons/teamspeak</a>.
 * <p>
 * The following code is subject to the LGPL Version 2.1.
 *
 * @author jumpingpxl
 * @author RettichLP
 */
public class TeamSpeakController {

    private final TeamSpeakAPI teamSpeakAPI;
    private final List<Server> servers;
    private Server selectedServer;

    public TeamSpeakController(TeamSpeakAPI teamSpeakAPI) {
        this.teamSpeakAPI = teamSpeakAPI;
        this.servers = new ArrayList<>();
    }

    public Server getSelectedServer() {
        return this.selectedServer;
    }

    public void setSelectedServer(Server server) {
        System.out.println("SERVER=" + server);
        if (server == null) {
            this.selectedServer = null;
            return;
        }

        if (!this.servers.contains(server)) {
            throw new IllegalArgumentException("Server is not in the list of servers!");
        }

        this.selectedServer = server;
        this.teamSpeakAPI.clientNotifyRegister(server.getId());
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public Server getServer(int id) {
        for (Server server : this.servers) {
            if (server.getId() == id) {
                return server;
            }
        }

        return null;
    }

    public void refreshCurrentServer(int schandlerId) {
        this.teamSpeakAPI.request(Request.firstParamEquals(
                "use " + schandlerId,
                "selected",
                response -> {
                    this.teamSpeakAPI.query("whoami");
                    this.refreshCurrentServer0(schandlerId);
                }
        ));
    }

    private <T> T get(String[] arguments, String identifier, Class<T> clazz) {
        return ArgumentParser.parse(arguments, identifier, clazz);
    }

    public void refreshCurrentServer0(int schandlerId) {
        Server server = this.teamSpeakAPI.getServer(schandlerId);
        if (server == null) {
            return;
        }

        this.teamSpeakAPI.request(Request.unknown("channellist", channelListAnswer -> {
            String[] channels = channelListAnswer.split("\\|");
            for (String rawChannel : channels) {
                if (!rawChannel.startsWith("cid=")) {
                    return false;
                }
            }

            server.getChannels().clear();
            this.teamSpeakAPI.controller().setSelectedServer(server);
            for (String rawChannel : channels) {
                String[] channel = rawChannel.split(" ");
                Integer channelId = this.get(channel, "cid", Integer.class);
                String channelName = this.get(channel, "channel_name", String.class);
                Integer channelPid = this.get(channel, "pid", Integer.class);
                if (channelId != null) {
                    Channel defaultChannel = server.addChannel(channelId);
                    if (channelName != null) {
                        defaultChannel.setName(channelName);
                    }
                    if (channelPid != null) {
                        defaultChannel.setPid(channelPid);
                    }
                }
            }

            this.teamSpeakAPI.request(Request.unknown("clientlist -voice", clientListAnswer -> {
                String[] clients = clientListAnswer.split("\\|");
                for (String rawClient : clients) {
                    if (!rawClient.startsWith("clid=")) {
                        return false;
                    }
                }

                for (String rawClient : clients) {
                    String[] client = rawClient.split(" ");
                    Integer clientId = this.get(client, "clid", Integer.class);
                    Integer channelId = this.get(client, "cid", Integer.class);
                    if (clientId != null && channelId != null) {
                        Channel channel = server.getChannel(channelId);
                        if (channel != null) {
                            User user = new User(clientId);

                            this.teamSpeakAPI.request(Request.firstParamStartsWith(
                                    "clientvariable clid=" + clientId + " client_description",
                                    "clid=" + clientId,
                                    clientProperties -> {
                                        System.out.println("CL_ID=" + clientId);
                                        System.out.println("PROPS=" + clientProperties);
                                        String[] arguments = clientProperties.split(" ");
                                        String description = ArgumentParser.parse(arguments, "client_description", String.class);

                                        user.setDescription(description);
                                        System.out.println(description);
                                    }));

                            channel.addUser(user);
                        }
                    }
                }

                return true;
            }));

            return true;
        }));
    }
}
