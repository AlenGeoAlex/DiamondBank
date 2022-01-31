## Diamond Bank
This is a custom plugin written for `Alexioe#8504`. Basically a plugin to deposit diamonds into a bank and withdraw them on need.

Although the plugin concept is simple. The plugin is done in as future proof and highly customizable. Every GUI in the menu is customizable from top-to-down. Even fillers can be added on the menu. Also The plugin is coded in a way the in-future anyone having simple Java Knoweldge (Check Below) can implement any storage support. The plugin overall took me around 10-12 hours. The plugin is also hooked with PlaceholderAPI

## PlaceholderAPI
The plugin is hooked in with PlaceholderAPI to show the amount of diamonds a player has.

| Placeholder | Description |
|---|---|
| %dbank_balance%| Displays the amount of diamonds a player has in his bank |

Currently i don't got an idea for any other placeholders. Feel free to ping me if you want something...😃
## Commands
| Command | Permmission | Description |
|---|---|---|
|dbank| dbank.bank|The default command to open up the menu| 
|dbank deposit|dbank.deposit |This command opens up the deposit GUI|
|dbank withdraw|dbank.withdraw |This command opens up the withdraw GUI|
|dbank set [Player] [amount]|dbank.admin.set |This command is an admin command which allow admins to set the amount of balance another player has. Currently the player need to be online. If needed Offline player support can be added|

## To Developers

If in future, you wish to expand the storage to MySQL or MongoDB, share this with the developer.

*To Dev* : You can just implement this interface located at `me.alen_alex.diamondbank.storage.core` to add the new storage. All this is called after the configuration loaded, So if you need you can request values from configuration too!

```JAVA
    /**
     * This is used to connect and register into the storage system.
     * NOTE: This must return false if the connection is unsuccessful
     * @return boolean Whether connection was successful or not
     */
    boolean connectToStorageSys();

    /**
     * Get the data of player as a future. If the data is not present, You should register it and return the data
     * see {@link me.alen_alex.diamondbank.storage.SQLite#getOrElseRegister(UUID)} for an example
     * @param playerID UUID of the Player
     * @return A newly or existing {@link PlayerData}
     */
    CompletableFuture<PlayerData> getOrElseRegister(@NotNull UUID playerID);

    /**
     * Just Return it as a string of name
     * @return
     */
    String storageType();

    /**
     * Prepare database tables if needed
     */
    void prepareDatabaseTables();

    /**
     * Closes databases
     */
    void closeDatabase();

    /**
     * Save data as async
     * @param playerData
     */
    void saveAsync(@NotNull PlayerData playerData);
    /**
     * Save data in sync
     * @param playerData
     */
    void saveSync(@NotNull PlayerData playerData);

    /**
     * Add a new transaction log to database
     * @param transaction
     */
    void addAsyncTransactionLog(@NotNull Transaction transaction);

    CompletableFuture<List<Transaction>> getPlayerTransaction(@NotNull UUID player);
```

An example class is `me.alen_alex.diamondbank.storage.SQLite`

```JAVA
    @Override
    public boolean connectToStorageSys() {
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            handler.getPlugin().getLogger().severe("SQLite JDBC class is not found!, Contact the developer");
            return false;
        }

        final File databaseFile = new File(handler.getPlugin().getDataFolder()+File.separator+"data","database.db");

        if(!databaseFile.getParentFile().exists())
            databaseFile.getParentFile().mkdirs();

        try {
            storageConnection = DriverManager.getConnection("jdbc:sqlite:"+databaseFile.getAbsolutePath());
        } catch (SQLException e) {
            handler.getPlugin().getLogger().severe("Unable to connect to database, Check stacktrace for more info");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public CompletableFuture<PlayerData> getOrElseRegister(@NotNull UUID playerID) {
        return CompletableFuture.supplyAsync(new Supplier<PlayerData>() {
            @Override
            public PlayerData get() {
                PreparedStatement preparedStatement = null;
                PlayerData playerData = null;
                try {
                    preparedStatement = storageConnection.prepareStatement("SELECT diamond FROM tbl_bank WHERE `uuid` = ?;");
                    preparedStatement.setString(1,playerID.toString());


                    ResultSet set = preparedStatement.executeQuery();

                    if(set == null){
                        preparedStatement.close();
                        return PlayerData.of(playerID);
                    }

                    if(set.next()) {
                        playerData = PlayerData.of(playerID, set.getInt("diamond"));
                    } else {

                        final PreparedStatement ps2 = storageConnection.prepareStatement("INSERT INTO tbl_bank VALUES (?,?);");
                        ps2.setString(1,playerID.toString());
                        ps2.setInt(2,0);

                        ps2.executeUpdate();
                        ps2.close();
                        playerData = PlayerData.of(playerID);
                    }

                    set.close();
                } catch (Exception e) {
                    handler.getPlugin().getLogger().warning("An unknown error occurred while fetching player data from database, A new raw data will be provided instead");
                    e.printStackTrace();
                    playerData = PlayerData.of(playerID);
                } finally {
                    try {
                        if (preparedStatement != null && !preparedStatement.isClosed()) {
                            preparedStatement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return playerData;
            }
        });
    }

    @Override
    public String storageType() {
        return "SQLite";
    }

    @Override
    public void prepareDatabaseTables() {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = storageConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tbl_bank (`uuid` VARCHAR(36) NOT NULL UNIQUE, `diamond` INTEGER DEFAULT 0);");
            ps.execute();

            ps2 = storageConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tbl_transactions(`t_id` VARCHAR(36) NOT NULL UNIQUE, `player` VARCHAR(36) NOT NULL, `amount` INTEGER NOT NULL, `at` VARCHAR(14) NOT NULL, `t_way` VARCHAR(20) NOT NULL);");
            ps2.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null)
                    ps.close();
                if(ps2 != null)
                    ps2.close();;
            } catch (SQLException ignored) {}
        }
    }

    @Override
    public void closeDatabase() {
        try {
            if(storageConnection != null && !storageConnection.isClosed()) {
                storageConnection.close();
                handler.getPlugin().getLogger().info("Closed database!");
            }
        }catch (Exception e){
            handler.getPlugin().getLogger().warning("Database is not connected to close! Skipping");
        }
    }

    @Override
    public void saveAsync(@NotNull PlayerData playerData) {
        handler.getPlugin().getServer().getScheduler().runTaskAsynchronously(handler.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {

                    final PreparedStatement ps = storageConnection.prepareStatement("UPDATE tbl_bank SET `diamond` = ?;");
                    ps.setInt(1,playerData.getPlayerDiamond());

                    ps.executeUpdate();
                    ps.close();
                }catch (Exception e){
                    handler.getPlugin().getLogger().severe("Unable to save data for "+playerData.getPlayerUUID()+". Check the stacktrace below for more info!");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void saveSync(@NotNull PlayerData playerData) {
        try {
            final PreparedStatement ps = storageConnection.prepareStatement("UPDATE tbl_bank SET `diamond` = ?;");
            ps.setInt(1,playerData.getPlayerDiamond());

            ps.executeUpdate();
            ps.close();
        }catch (Exception e){
            handler.getPlugin().getLogger().severe("Unable to save data for "+playerData.getPlayerUUID()+". Check the stacktrace below for more info!");
            e.printStackTrace();
        }
    }

    @Override
    public void addAsyncTransactionLog(@NotNull Transaction transaction) {
        handler.getPlugin().getServer().getScheduler().runTaskAsynchronously(handler.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    final PreparedStatement ps = storageConnection.prepareStatement("INSERT INTO `tbl_transactions` VALUES (?,?,?,?,?);");

                    if(ps == null)
                        return;

                    ps.setString(1,transaction.getTransactionID().toString());
                    ps.setString(2,transaction.getUuid().toString());
                    ps.setInt(3,transaction.getAmount());
                    ps.setString(4, String.valueOf(transaction.getTime()));
                    ps.setString(5,transaction.getWay().name());



                    ps.executeUpdate();
                    ps.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public CompletableFuture<List<Transaction>> getPlayerTransaction(@NotNull UUID player) {
        return CompletableFuture.supplyAsync(new Supplier<List<Transaction>>() {
            @Override
            public List<Transaction> get() {
                final List<Transaction> transactionList = new ArrayList<>();

                try {
                    PreparedStatement ps = storageConnection.prepareStatement("SELECT * FROM tbl_transactions WHERE `player` = ?;");
                    if(ps == null)
                        return transactionList;

                    ps.setString(1,player.toString());

                    ResultSet set = ps.executeQuery();

                    if(set == null)
                        return transactionList;

                    while (set.next()){
                        transactionList.add(Transaction.of(
                                set.getString("t_id"),
                                set.getString("player"),
                                set.getInt("amount"),
                                set.getString("way"),
                                set.getLong("at")
                        ));
                    }
                }catch (Exception ignored){}

                return transactionList;
            }
        });
    }
```