package com.example.stockmanager;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Product> __deletionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Product> __updateAdapterOfProduct;

  private final SharedSQLiteStatement __preparedStmtOfUpdateQuantity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllProducts;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `products` (`id`,`name`,`category`,`buyingPrice`,`sellingPrice`,`quantity`,`sku`,`minStock`,`supplier`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategory());
        }
        statement.bindDouble(4, entity.getBuyingPrice());
        statement.bindDouble(5, entity.getSellingPrice());
        statement.bindLong(6, entity.getQuantity());
        if (entity.getSku() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSku());
        }
        statement.bindLong(8, entity.getMinStock());
        if (entity.getSupplier() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSupplier());
        }
      }
    };
    this.__deletionAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `products` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`name` = ?,`category` = ?,`buyingPrice` = ?,`sellingPrice` = ?,`quantity` = ?,`sku` = ?,`minStock` = ?,`supplier` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategory());
        }
        statement.bindDouble(4, entity.getBuyingPrice());
        statement.bindDouble(5, entity.getSellingPrice());
        statement.bindLong(6, entity.getQuantity());
        if (entity.getSku() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getSku());
        }
        statement.bindLong(8, entity.getMinStock());
        if (entity.getSupplier() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSupplier());
        }
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateQuantity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE products SET quantity = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllProducts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM products";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfProduct.insertAndReturnId(product);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Product> products) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProduct.insert(products);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateQuantity(final int id, final int newQty) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateQuantity.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, newQty);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateQuantity.release(_stmt);
    }
  }

  @Override
  public void deleteAllProducts() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllProducts.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllProducts.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Product>> getAllProducts() {
    final String _sql = "SELECT * FROM products ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
          final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final double _tmpBuyingPrice;
            _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpSku;
            if (_cursor.isNull(_cursorIndexOfSku)) {
              _tmpSku = null;
            } else {
              _tmpSku = _cursor.getString(_cursorIndexOfSku);
            }
            final int _tmpMinStock;
            _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
            final String _tmpSupplier;
            if (_cursor.isNull(_cursorIndexOfSupplier)) {
              _tmpSupplier = null;
            } else {
              _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
            }
            _item = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Product> getAllProductsList() {
    final String _sql = "SELECT * FROM products ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
      final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
      final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
      final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Product _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final double _tmpBuyingPrice;
        _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
        final double _tmpSellingPrice;
        _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpSku;
        if (_cursor.isNull(_cursorIndexOfSku)) {
          _tmpSku = null;
        } else {
          _tmpSku = _cursor.getString(_cursorIndexOfSku);
        }
        final int _tmpMinStock;
        _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
        final String _tmpSupplier;
        if (_cursor.isNull(_cursorIndexOfSupplier)) {
          _tmpSupplier = null;
        } else {
          _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
        }
        _item = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Product getProductById(final int id) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
      final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
      final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
      final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
      final Product _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final double _tmpBuyingPrice;
        _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
        final double _tmpSellingPrice;
        _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpSku;
        if (_cursor.isNull(_cursorIndexOfSku)) {
          _tmpSku = null;
        } else {
          _tmpSku = _cursor.getString(_cursorIndexOfSku);
        }
        final int _tmpMinStock;
        _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
        final String _tmpSupplier;
        if (_cursor.isNull(_cursorIndexOfSupplier)) {
          _tmpSupplier = null;
        } else {
          _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
        }
        _result = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Product getProductBySku(final String sku) {
    final String _sql = "SELECT * FROM products WHERE sku = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sku == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sku);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
      final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
      final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
      final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
      final Product _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final double _tmpBuyingPrice;
        _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
        final double _tmpSellingPrice;
        _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpSku;
        if (_cursor.isNull(_cursorIndexOfSku)) {
          _tmpSku = null;
        } else {
          _tmpSku = _cursor.getString(_cursorIndexOfSku);
        }
        final int _tmpMinStock;
        _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
        final String _tmpSupplier;
        if (_cursor.isNull(_cursorIndexOfSupplier)) {
          _tmpSupplier = null;
        } else {
          _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
        }
        _result = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Product>> searchProducts(final String search) {
    final String _sql = "SELECT * FROM products WHERE name LIKE '%' || ? || '%' OR category LIKE '%' || ? || '%' OR sku LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (search == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, search);
    }
    _argIndex = 2;
    if (search == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, search);
    }
    _argIndex = 3;
    if (search == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, search);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
          final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final double _tmpBuyingPrice;
            _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpSku;
            if (_cursor.isNull(_cursorIndexOfSku)) {
              _tmpSku = null;
            } else {
              _tmpSku = _cursor.getString(_cursorIndexOfSku);
            }
            final int _tmpMinStock;
            _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
            final String _tmpSupplier;
            if (_cursor.isNull(_cursorIndexOfSupplier)) {
              _tmpSupplier = null;
            } else {
              _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
            }
            _item = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getProductCount() {
    final String _sql = "SELECT COUNT(*) FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getLowStockCount() {
    final String _sql = "SELECT COUNT(*) FROM products WHERE quantity < 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getLowStockCountWithThreshold(final int threshold) {
    final String _sql = "SELECT COUNT(*) FROM products WHERE quantity < ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, threshold);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Product>> getLowStockProducts() {
    final String _sql = "SELECT * FROM products WHERE quantity < 5 ORDER BY quantity ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
          final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final double _tmpBuyingPrice;
            _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpSku;
            if (_cursor.isNull(_cursorIndexOfSku)) {
              _tmpSku = null;
            } else {
              _tmpSku = _cursor.getString(_cursorIndexOfSku);
            }
            final int _tmpMinStock;
            _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
            final String _tmpSupplier;
            if (_cursor.isNull(_cursorIndexOfSupplier)) {
              _tmpSupplier = null;
            } else {
              _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
            }
            _item = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Product>> getLowStockProductsWithThreshold(final int threshold) {
    final String _sql = "SELECT * FROM products WHERE quantity < ? ORDER BY quantity ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, threshold);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<List<Product>>() {
      @Override
      @Nullable
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBuyingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "buyingPrice");
          final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfSku = CursorUtil.getColumnIndexOrThrow(_cursor, "sku");
          final int _cursorIndexOfMinStock = CursorUtil.getColumnIndexOrThrow(_cursor, "minStock");
          final int _cursorIndexOfSupplier = CursorUtil.getColumnIndexOrThrow(_cursor, "supplier");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final double _tmpBuyingPrice;
            _tmpBuyingPrice = _cursor.getDouble(_cursorIndexOfBuyingPrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpSku;
            if (_cursor.isNull(_cursorIndexOfSku)) {
              _tmpSku = null;
            } else {
              _tmpSku = _cursor.getString(_cursorIndexOfSku);
            }
            final int _tmpMinStock;
            _tmpMinStock = _cursor.getInt(_cursorIndexOfMinStock);
            final String _tmpSupplier;
            if (_cursor.isNull(_cursorIndexOfSupplier)) {
              _tmpSupplier = null;
            } else {
              _tmpSupplier = _cursor.getString(_cursorIndexOfSupplier);
            }
            _item = new Product(_tmpName,_tmpCategory,_tmpBuyingPrice,_tmpSellingPrice,_tmpQuantity,_tmpSku,_tmpMinStock,_tmpSupplier);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalInventoryValue() {
    final String _sql = "SELECT SUM(sellingPrice * quantity) FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalCostValue() {
    final String _sql = "SELECT SUM(buyingPrice * quantity) FROM products";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"products"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
