package com.rettichlp.unicacityaddon.base;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.manager.FactionService;
import com.rettichlp.unicacityaddon.base.manager.FileService;
import com.rettichlp.unicacityaddon.base.manager.NameTagService;
import com.rettichlp.unicacityaddon.base.manager.NavigationService;
import com.rettichlp.unicacityaddon.base.manager.TokenService;
import com.rettichlp.unicacityaddon.base.manager.WebService;

public class Services {

    private final FactionService factionService;
    private final FileService fileService;
    private final NameTagService nametagService;
    private final NavigationService navigationService;
    private final TokenService tokenService;
    private final WebService webService;

    public Services(UnicacityAddon unicacityAddon) {
        this.factionService = new FactionService(unicacityAddon);
        this.fileService = new FileService(unicacityAddon);
        this.nametagService = new NameTagService(unicacityAddon);
        this.navigationService = new NavigationService(unicacityAddon);
        this.tokenService = new TokenService(unicacityAddon);
        this.webService = new WebService(unicacityAddon);
    }

    public FactionService factionService() {
        return factionService;
    }

    public FileService fileService() {
        return fileService;
    }

    public NameTagService nametagService() {
        return nametagService;
    }

    public NavigationService navigationService() {
        return navigationService;
    }

    public TokenService tokenService() {
        return tokenService;
    }

    public WebService webService() {
        return webService;
    }
}
