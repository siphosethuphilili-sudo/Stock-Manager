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
public final class SaleDao_Impl implements SaleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Sale> __insertionAdapterOfSale;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSales;

  public SaleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSale = new EntityInsertionAdapter<Sale>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sales` (`saleId`,`productId`,`productName`,`quantitySold`,`totalPrice`,`date`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Sale entity) {
        statement.bindLong(1, entity.getSaleId());
        statement.bindLong(2, entity.getProductId());
        if (entity.getProductName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getProductName());
        }
        statement.bindLong(4, entity.getQuantitySold());
        statement.bindDouble(5, entity.getTotalPrice());
        statement.bindLong(6, entity.getDate());
      }
    };
    this.__preparedStmtOfDeleteAllSales = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sales";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Sale sale) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSale.insert(sale);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllSales() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSales.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllSales.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Sale>> getAllSales() {
    final String _sql = "SELECT * FROM sales ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sales"}, false, new Callable<List<Sale>>() {
      @Override
      @Nullable
      public List<Sale> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSaleId = CursorUtil.getColumnIndexOrThrow(_cursor, "saleId");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfQuantitySold = CursorUtil.getColumnIndexOrThrow(_cursor, "quantitySold");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<Sale> _result = new ArrayList<Sale>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Sale _item;
            final int _tmpProductId;
            _tmpProductId = _cursor.getInt(_cursorIndexOfProductId);
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final int _tmpQuantitySold;
            _tmpQuantitySold = _cursor.getInt(_cursorIndexOfQuantitySold);
            final double _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            _item = new Sale(_tmpProductId,_tmpProductName,_tmpQuantitySold,_tmpTotalPrice,_tmpDate);
            final int _tmpSaleId;
            _tmpSaleId = _cursor.getInt(_cursorIndexOfSaleId);
            _item.setSaleId(_tmpSaleId);
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
  public LiveData<Double> getTodaySales(final long startOfDay) {
    final String _sql = "SELECT SUM(totalPrice) FROM sales WHERE date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startOfDay);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sales"}, false, new Callable<Double>() {
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
  public LiveData<Double> getMonthSales(final long startOfMonth) {
    final String _sql = "SELECT SUM(totalPrice) FROM sales WHERE date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startOfMonth);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sales"}, false, new Callable<Double>() {
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
  public LiveData<Double> getTotalSalesRevenue() {
    final String _sql = "SELECT SUM(totalPrice) FROM sales";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sales"}, false, new Callable<Double>() {
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
  public LiveData<Integer> getTotalSalesCount() {
    final String _sql = "SELECT COUNT(*) FROM sales";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sales"}, false, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
