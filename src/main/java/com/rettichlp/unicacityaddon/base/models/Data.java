package com.rettichlp.unicacityaddon.base.models;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugPurity;
import com.rettichlp.unicacityaddon.base.enums.faction.DrugType;
import com.rettichlp.unicacityaddon.base.enums.faction.Equip;
import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    private List<Armament> armamentList;
    private Integer bankBalance;
    private Boolean carOpen;
    private Integer cashBalance;
    private List<CoordlistEntry> coordlist;
    private Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap;
    private Map<Equip, Integer> equipMap;
    private Long firstAidDate;
    private Map<Integer, HouseData> houseDataMap;
    private Integer jobBalance;
    private Integer jobExperience;
    private Integer payDayTime;
    private Long plantFertilizeTime;
    private Long plantWaterTime;
    private Integer serviceCount;
    private Integer timer;
    private List<TodolistEntry> todolist;

    public Data() {
        this.armamentList = new ArrayList<>();
        this.bankBalance = 0;
        this.carOpen = false;
        this.cashBalance = 0;
        this.coordlist = new ArrayList<>();
        this.drugInventoryMap = new HashMap<>();
        this.equipMap = new HashMap<>();
        this.firstAidDate = 0L;
        this.houseDataMap = new HashMap<>();
        this.jobBalance = 0;
        this.jobExperience = 0;
        this.payDayTime = 0;
        this.plantFertilizeTime = 0L;
        this.plantWaterTime = 0L;
        this.serviceCount = 0;
        this.timer = 0;
        this.todolist = new ArrayList<>();
    }

    public List<Armament> getArmamentList() {
        return armamentList != null ? armamentList : new ArrayList<>();
    }

    public void setArmamentList(List<Armament> armamentList) {
        this.armamentList = armamentList;
        save();
    }

    public int getBankBalance() {
        return bankBalance != null ? bankBalance : 0;
    }

    public void setBankBalance(Integer bankBalance) {
        this.bankBalance = bankBalance;
        save();
    }

    public boolean isCarOpen() {
        return carOpen != null ? carOpen : false;
    }

    public void setCarOpen(boolean carOpen) {
        this.carOpen = carOpen;
        save();
    }

    public int getCashBalance() {
        return cashBalance != null ? cashBalance : 0;
    }

    public void setCashBalance(Integer cashBalance) {
        this.cashBalance = cashBalance;
        save();
    }

    public List<CoordlistEntry> getCoordlist() {
        return coordlist != null ? coordlist : new ArrayList<>();
    }

    public void setCoordlist(List<CoordlistEntry> coordlist) {
        this.coordlist = coordlist;
        save();
    }

    public Map<DrugType, Map<DrugPurity, Integer>> getDrugInventoryMap() {
        return drugInventoryMap != null ? drugInventoryMap : new HashMap<>();
    }

    public void setDrugInventoryMap(Map<DrugType, Map<DrugPurity, Integer>> drugInventoryMap) {
        this.drugInventoryMap = drugInventoryMap;
        save();
    }

    public Map<Equip, Integer> getEquipMap() {
        return equipMap != null ? equipMap : new HashMap<>();
    }

    public void setEquipMap(Map<Equip, Integer> equipMap) {
        this.equipMap = equipMap;
        save();
    }

    public long getFirstAidDate() {
        return firstAidDate != null ? firstAidDate : 0;
    }

    public void setFirstAidDate(Long firstAidDate) {
        this.firstAidDate = firstAidDate;
        save();
    }

    public Map<Integer, HouseData> getHouseDataMap() {
        return houseDataMap != null ? houseDataMap : new HashMap<>();
    }

    public void setHouseDataMap(Map<Integer, HouseData> houseDataMap) {
        this.houseDataMap = houseDataMap;
        save();
    }

    public int getJobBalance() {
        return jobBalance != null ? jobBalance : 0;
    }

    public void setJobBalance(Integer jobBalance) {
        this.jobBalance = jobBalance;
        save();
    }

    public int getJobExperience() {
        return jobExperience != null ? jobExperience : 0;
    }

    public void setJobExperience(Integer jobExperience) {
        this.jobExperience = jobExperience;
        save();
    }

    public int getPayDayTime() {
        return payDayTime != null ? payDayTime : 0;
    }

    public void setPayDayTime(Integer payDayTime) {
        this.payDayTime = payDayTime;
        save();
    }

    public long getPlantFertilizeTime() {
        return plantFertilizeTime != null ? plantFertilizeTime : 0;
    }

    public void setPlantFertilizeTime(Long plantFertilizeTime) {
        this.plantFertilizeTime = plantFertilizeTime;
        save();
    }

    public long getPlantWaterTime() {
        return plantWaterTime != null ? plantWaterTime : 0;
    }

    public void setPlantWaterTime(Long plantWaterTime) {
        this.plantWaterTime = plantWaterTime;
        save();
    }

    public int getServiceCount() {
        return serviceCount != null ? serviceCount : 0;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
        save();
    }

    public int getTimer() {
        return timer != null ? timer : 0;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
        save();
    }

    public List<TodolistEntry> getTodolist() {
        return todolist != null ? todolist : new ArrayList<>();
    }

    public void setTodolist(List<TodolistEntry> todolist) {
        this.todolist = todolist;
        save();
    }

    /**
     * Adds a <code>Armament</code> object, created by the given values, to the <code>armamentList</code>
     *
     * @param name   name of the <code>Armament</code> pattern
     * @param weapon {@link Weapon} of the <code>Armament</code> pattern
     * @param amount amount of ammunition of the <code>Armament</code> pattern
     * @see Armament
     * @see Weapon
     */
    public void addArmamentPattern(String name, Weapon weapon, int amount) {
        List<Armament> newArmamentList = getArmamentList();
        newArmamentList.add(new Armament(name, weapon, amount));
        armamentList = newArmamentList;
        save();
    }

    /**
     * Removes the <code>Armament</code> object with the given name of the <code>armamentList</code>
     *
     * @param name name of the <code>Armament</code> pattern
     * @see Armament
     * @see Weapon
     */
    public boolean removeArmamentPattern(String name) {
        List<Armament> newArmamentList = getArmamentList();
        boolean success = newArmamentList.removeIf(armament -> armament.getName().equalsIgnoreCase(name));
        armamentList = newArmamentList;
        save();
        return success;
    }

    /**
     * Adds the given value <code>i</code> to the <code>bankBalance</code>
     *
     * @param i Amount of money to be added to the <code>bankBalance</code>
     */
    public void addBankBalance(int i) {
        bankBalance = getBankBalance() + i;
        save();
    }

    /**
     * Removes the given value <code>i</code> from the <code>bankBalance</code>
     *
     * @param i Amount of money to be removed from the <code>bankBalance</code>
     */
    public void removeBankBalance(int i) {
        bankBalance = getBankBalance() - i;
        save();
    }

    /**
     * Adds the given value <code>i</code> to the <code>cashBalance</code>
     *
     * @param i Amount of money to be added to the <code>cashBalance</code>
     */
    public void addCashBalance(int i) {
        cashBalance = getCashBalance() + i;
        save();
    }

    /**
     * Removes the given value <code>i</code> from the <code>cashBalance</code>
     *
     * @param i Amount of money to be removed from the <code>cashBalance</code>
     */
    public void removeCashBalance(int i) {
        cashBalance = getCashBalance() - i;
        save();
    }

    /**
     * Adds the position of the <code>UPlayer</code> with the given name to the <code>coordlist</code>
     *
     * @param name     Name of the position to be added to the <code>coordlist</code>
     * @param blockPos Position to be added to the <code>coordlist</code>
     * @see BlockPos
     * @see UPlayer
     */
    public void addCoordToCoordlist(String name, BlockPos blockPos) {
        List<CoordlistEntry> newCoordlistEntryList = getCoordlist();
        newCoordlistEntryList.add(new CoordlistEntry(name, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        coordlist = newCoordlistEntryList;
        save();
    }

    /**
     * Removes the position with the given name from the <code>coordlist</code>
     *
     * @param name Name of the position to be removed from the <code>coordlist</code>
     */
    public boolean removeCoordFromCoordlist(String name) {
        List<CoordlistEntry> newCoordlistEntryList = getCoordlist();
        boolean success = newCoordlistEntryList.removeIf(coordlistEntry -> coordlistEntry.getName().equalsIgnoreCase(name));
        coordlist = newCoordlistEntryList;
        save();
        return success;
    }

    /**
     * Adds the given <code>amount</code> of {@link DrugType} with its {@link DrugPurity} to the <code>drugInventoryMap</code>
     *
     * @param drugType   {@link DrugType} to be added to the <code>drugInventoryMap</code>
     * @param drugPurity {@link DrugPurity} of the {@link DrugType}
     * @param amount     Amount of the {@link DrugType}
     * @see DrugType
     * @see DrugPurity
     */
    public void addDrugToInventory(DrugType drugType, DrugPurity drugPurity, int amount) {
        UnicacityAddon.LOGGER.info("DrugInventoryInteraction: Added amount {} of DrugType {} with DrugPurity {} to inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = getDrugInventoryMap().getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, oldAmount + amount);
            Map<DrugType, Map<DrugPurity, Integer>> newdrugInventoryMap = getDrugInventoryMap();
            newdrugInventoryMap.put(drugType, drugPurityIntegerMap);
            drugInventoryMap = newdrugInventoryMap;
            save();
        }
    }

    /**
     * Removes the given <code>amount</code> of {@link DrugType} with its {@link DrugPurity} from the <code>drugInventoryMap</code>
     *
     * @param drugType   {@link DrugType} to be removed from the <code>drugInventoryMap</code>
     * @param drugPurity {@link DrugPurity} of the {@link DrugType}
     * @param amount     Amount of the {@link DrugType}
     * @see DrugType
     * @see DrugPurity
     */
    public void removeDrugFromInventory(DrugType drugType, DrugPurity drugPurity, int amount) {
        UnicacityAddon.LOGGER.info("DrugInventoryInteraction: Removed amount {} of DrugType {} with DrugPurity {} from inventory", amount, drugType, drugPurity);
        if (drugType != null) {
            Map<DrugPurity, Integer> drugPurityIntegerMap = getDrugInventoryMap().getOrDefault(drugType, new HashMap<>());
            int oldAmount = drugPurityIntegerMap.getOrDefault(drugPurity, 0);
            drugPurityIntegerMap.put(drugPurity, Math.max(oldAmount - amount, 0));
            Map<DrugType, Map<DrugPurity, Integer>> newdrugInventoryMap = getDrugInventoryMap();
            newdrugInventoryMap.put(drugType, drugPurityIntegerMap);
            drugInventoryMap = newdrugInventoryMap;
            save();
        }
    }

    /**
     * Adds the given {@link Equip} to the <code>equipMap</code>
     *
     * @param equip {@link Equip} to be added to the <code>equipMap</code>
     * @see Equip
     */
    public void addEquipToEquipMap(Equip equip) {
        Map<Equip, Integer> newEquipMap = getEquipMap();
        newEquipMap.put(equip, newEquipMap.getOrDefault(equip, 0) + 1);
        equipMap = newEquipMap;
        save();
    }

    /**
     * Returns the {@link HouseData} of the given <code>houseNumber</code> or creates them
     *
     * @param houseNumber Number of the house, from which the {@link HouseData} needs to be extracted
     * @see HouseData
     */
    public HouseData getHouseData(int houseNumber) {
        HouseData houseData = getHouseDataMap().getOrDefault(houseNumber, new HouseData(houseNumber));
        updateHouseData(houseNumber, houseData);
        return houseData;
    }

    /**
     * Updates the <code>houseDataMap</code> with the given <code>houseNumber</code> and its {@link HouseData}
     *
     * @param houseNumber Number of the house to be updated
     * @param houseData   {@link HouseData} of the house
     * @see HouseData
     */
    public void updateHouseData(int houseNumber, HouseData houseData) {
        Map<Integer, HouseData> newHouseDataMap = getHouseDataMap();
        newHouseDataMap.put(houseNumber, houseData);
        houseDataMap = newHouseDataMap;
        save();
    }

    /**
     * Removes the <code>houseNumber</code> with its associated {@link HouseData} from the <code>houseDataMap</code>
     *
     * @param houseNumber Number of the house to be removed
     * @see HouseData
     */
    public void removeHouseData(int houseNumber) {
        Map<Integer, HouseData> newHouseDataMap = getHouseDataMap();
        newHouseDataMap.remove(houseNumber);
        houseDataMap = newHouseDataMap;
        save();
    }

    /**
     * Sends a message which contains information about all house banks of registered {@link HouseData}
     *
     * @see UPlayer
     * @see HouseData
     * @see Message
     */
    public void sendAllHouseBankMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Hauskassen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getBankITextComponent()));
        p.sendEmptyMessage();
    }

    /**
     * Sends a message which contains information about all drug storages of registered {@link HouseData}
     *
     * @see UPlayer
     * @see HouseData
     * @see Message
     */
    public void sendAllDrugStorageMessage() {
        UPlayer p = AbstractionLayer.getPlayer();

        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Drogenlager:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        getHouseDataMap().values().forEach(houseData -> p.sendMessage(houseData.getStorageITextComponent()));
        p.sendEmptyMessage();
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobBalance</code>
     *
     * @param i Amount of money to be added to the <code>jobBalance</code>
     */
    public void addJobBalance(int i) {
        jobBalance = getJobBalance() + i;
        save();
    }

    /**
     * Adds the given value <code>i</code> to the <code>jobExperience</code>
     *
     * @param i Amount of experience to be added to the <code>jobExperience</code>
     */
    public void addJobExperience(int i) {
        jobExperience = getJobExperience() + i;
        save();
    }

    /**
     * Adds the given value <code>i</code> to the <code>payDayTime</code> and sends reminder message if necessary
     *
     * @param i Amount of minutes to be added to the <code>jobExperience</code>
     */
    public void addPayDayTime(int i) {
        UPlayer p = AbstractionLayer.getPlayer();
        payDayTime = getPayDayTime() + i;
        switch (payDayTime) {
            case 55:
                p.sendInfoMessage("Du hast in 5 Minuten deinen PayDay.");
                break;
            case 57:
                p.sendInfoMessage("Du hast in 3 Minuten deinen PayDay.");
                break;
            case 59:
                p.sendInfoMessage("Du hast in 1 Minute deinen PayDay.");
                break;
        }
        save();
    }

    /**
     * Adds the given value <code>i</code> to the <code>serviceCount</code>
     *
     * @param i Amount of services to be added to the <code>serviceCount</code>
     */
    public void addServiceCount(int i) {
        serviceCount = getServiceCount() + i;
        save();
    }

    /**
     * Removes the given value <code>i</code> from the <code>serviceCount</code>
     *
     * @param i Amount of services to be removed from the <code>serviceCount</code>
     */
    public void removeServiceCount(int i) {
        if (getServiceCount() > 0)
            serviceCount = getServiceCount() - i;
        save();
    }

    private void save() {
        FileManager.saveData();
    }
}