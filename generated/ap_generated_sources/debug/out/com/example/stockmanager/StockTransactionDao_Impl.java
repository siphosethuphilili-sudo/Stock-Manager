package com.example.stockmanager;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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
public final class StockTransactionDao_Impl implements StockTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StockTransaction> __insertionAdapterOfStockTransaction;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTransactions;

  public StockTransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStockTransaction = new EntityInsertionAdapter<StockTransaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `stock_transactions` (`id`,`productId`,`productName`,`type`,`quantity`,`unitPrice`,`totalAmount`,`timestamp`,`note`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final StockTransaction entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductName());
        }
        if (entity.getType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getType());
        }
        statement.bindLong(5, entity.getQuantity());
        statement.bindDouble(6, entity.getUnitPrice());
        statement.bindDouble(7, entity.getTotalAmount());
        statement.bindLong(8, entity.getTimestamp());
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNote());
        }
      }
    };
    this.__preparedStmtOfDeleteAllTransactions = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM stock_transactions";
        return _query;
      }
    };
  }

  @Override
  public void insert(final StockTransaction transaction) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStockTransaction.insert(transaction);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllTransactions() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTransactions.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllTransactions.release(_stmt);
    }
  }

  @Override
  public LiveData<List<StockTransaction>> getAllTransactions() {
    final String _sql = "SELECT * FROM stock_transactions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"stock_transactions"}, false, new Callable<List<StockTransaction>>() {
      @Override
      @Nullable
      public List<StockTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<StockTransaction> _result = new ArrayList<StockTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockTransaction _item;
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new StockTransaction(_tmpProductId,_tmpProductName,_tmpType,_tmpQuantity,_tmpUnitPrice,_tmpTotalAmount,_tmpTimestamp,_tmpNote);
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
  public List<StockTransaction> getAllTransactionsList() {
    final String _sql = "SELECT * FROM stock_transactions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
      final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
      final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final List<StockTransaction> _result = new ArrayList<StockTransaction>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final StockTransaction _item;
        final int _tmpProductId;
        _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
        final String _tmpProductName;
        if (_cursor.isNull(_cursorIndexOfProductName)) {
          _tmpProductName = null;
        } else {
          _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
        }
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final double _tmpUnitPrice;
        _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
        final double _tmpTotalAmount;
        _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        final String _tmpNote;
        if (_cursor.isNull(_cursorIndexOfNote)) {
          _tmpNote = null;
        } else {
          _tmpNote = _cursor.getString(_cursorIndexOfNote);
        }
        _item = new StockTransaction(_tmpProductId,_tmpProductName,_tmpType,_tmpQuantity,_tmpUnitPrice,_tmpTotalAmount,_tmpTimestamp,_tmpNote);
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
  public LiveData<List<StockTransaction>> getTransactionsForProduct(final int productId) {
    final String _sql = "SELECT * FROM stock_transactions WHERE productId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, productId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"stock_transactions"}, false, new Callable<List<StockTransaction>>() {
      @Override
      @Nullable
      public List<StockTransaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<StockTransaction> _result = new ArrayList<StockTransaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StockTransaction _item;
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new StockTransaction(_tmpProductId,_tmpProductName,_tmpType,_tmpQuantity,_tmpUnitPrice,_tmpTotalAmount,_tmpTimestamp,_tmpNote);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
