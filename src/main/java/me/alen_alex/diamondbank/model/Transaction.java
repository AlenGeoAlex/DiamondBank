package me.alen_alex.diamondbank.model;

import me.alen_alex.diamondbank.enums.TransactionWay;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Transaction {

    private final UUID transactionID;
    private final UUID uuid;
    private final int amount;
    private final TransactionWay way;
    private final long time;

    public Transaction(UUID uuid, int amount, TransactionWay way) {
        this.transactionID = UUID.randomUUID();
        this.uuid = uuid;
        this.amount = amount;
        this.way = way;
        this.time = System.currentTimeMillis();
    }

    private Transaction(String tID,String playerID,int amount,String way,long time){
        this.transactionID = UUID.fromString(tID);
        this.uuid = UUID.fromString(playerID);
        this.amount = amount;
        this.way = TransactionWay.valueOf(way);
        this.time = time;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionWay getWay() {
        return way;
    }

    public long getTime() {
        return time;
    }

    public UUID getTransactionID() {
        return transactionID;
    }

    public static Transaction of(@NotNull String transactionID,@NotNull String pID,int amount,String way,long time){
        return new Transaction(transactionID,pID,amount,way,time);
    }

}
