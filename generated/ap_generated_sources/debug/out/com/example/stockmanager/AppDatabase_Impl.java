package com.example.stockmanager;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProductDao _productDao;

  private volatile SaleDao _saleDao;

  private volatile StockTransactionDao _stockTransactionDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `category` TEXT, `buyingPrice` REAL NOT NULL, `sellingPrice` REAL NOT NULL, `quantity` INTEGER NOT NULL, `sku` TEXT, `minStock` INTEGER NOT NULL, `supplier` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sales` (`saleId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `productName` TEXT, `quantitySold` INTEGER NOT NULL, `totalPrice` REAL NOT NULL, `date` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `stock_transactions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `productName` TEXT, `type` TEXT, `quantity` INTEGER NOT NULL, `unitPrice` REAL NOT NULL, `totalAmount` REAL NOT NULL, `timestamp` INTEGER NOT NULL, `note` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '054ac822338ba3b05c553c087b549250')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `sales`");
        db.execSQL("DROP TABLE IF EXISTS `stock_transactions`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(9);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("buyingPrice", new TableInfo.Column("buyingPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("sellingPrice", new TableInfo.Column("sellingPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("sku", new TableInfo.Column("sku", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("minStock", new TableInfo.Column("minStock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("supplier", new TableInfo.Column("supplier", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.example.stockmanager.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsSales = new HashMap<String, TableInfo.Column>(6);
        _columnsSales.put("saleId", new TableInfo.Column("saleId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("productName", new TableInfo.Column("productName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("quantitySold", new TableInfo.Column("quantitySold", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("totalPrice", new TableInfo.Column("totalPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSales.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSales = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSales = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSales = new TableInfo("sales", _columnsSales, _foreignKeysSales, _indicesSales);
        final TableInfo _existingSales = TableInfo.read(db, "sales");
        if (!_infoSales.equals(_existingSales)) {
          return new RoomOpenHelper.ValidationResult(false, "sales(com.example.stockmanager.Sale).\n"
                  + " Expected:\n" + _infoSales + "\n"
                  + " Found:\n" + _existingSales);
        }
        final HashMap<String, TableInfo.Column> _columnsStockTransactions = new HashMap<String, TableInfo.Column>(9);
        _columnsStockTransactions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("productName", new TableInfo.Column("productName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("unitPrice", new TableInfo.Column("unitPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("totalAmount", new TableInfo.Column("totalAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStockTransactions.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStockTransactions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStockTransactions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStockTransactions = new TableInfo("stock_transactions", _columnsStockTransactions, _foreignKeysStockTransactions, _indicesStockTransactions);
        final TableInfo _existingStockTransactions = TableInfo.read(db, "stock_transactions");
        if (!_infoStockTransactions.equals(_existingStockTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "stock_transactions(com.example.stockmanager.StockTransaction).\n"
                  + " Expected:\n" + _infoStockTransactions + "\n"
                  + " Found:\n" + _existingStockTransactions);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "054ac822338ba3b05c553c087b549250", "740561474b058df6c35bdf89b01b8b7a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "products","sales","stock_transactions");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `sales`");
      _db.execSQL("DELETE FROM `stock_transactions`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SaleDao.class, SaleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StockTransactionDao.class, StockTransactionDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public SaleDao saleDao() {
    if (_saleDao != null) {
      return _saleDao;
    } else {
      synchronized(this) {
        if(_saleDao == null) {
          _saleDao = new SaleDao_Impl(this);
        }
        return _saleDao;
      }
    }
  }

  @Override
  public StockTransactionDao transactionDao() {
    if (_stockTransactionDao != null) {
      return _stockTransactionDao;
    } else {
      synchronized(this) {
        if(_stockTransactionDao == null) {
          _stockTransactionDao = new StockTransactionDao_Impl(this);
        }
        return _stockTransactionDao;
      }
    }
  }
}
