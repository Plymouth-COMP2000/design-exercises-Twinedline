package com.example.resturantmanagmentapp;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReservationDao_Impl implements ReservationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reservation> __insertionAdapterOfReservation;

  private final EntityDeletionOrUpdateAdapter<Reservation> __deletionAdapterOfReservation;

  public ReservationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReservation = new EntityInsertionAdapter<Reservation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `reservations` (`localId`,`id`,`customerName`,`numberOfPeople`,`date`,`time`,`location`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Reservation entity) {
        statement.bindLong(1, entity.localId);
        statement.bindLong(2, entity.id);
        if (entity.customerName == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.customerName);
        }
        if (entity.numberOfPeople == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.numberOfPeople);
        }
        if (entity.date == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.date);
        }
        if (entity.time == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.time);
        }
        if (entity.location == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.location);
        }
      }
    };
    this.__deletionAdapterOfReservation = new EntityDeletionOrUpdateAdapter<Reservation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reservations` WHERE `localId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Reservation entity) {
        statement.bindLong(1, entity.localId);
      }
    };
  }

  @Override
  public void insert(final Reservation reservation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfReservation.insert(reservation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Reservation reservation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfReservation.handle(reservation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Reservation> getAll() {
    final String _sql = "SELECT * FROM reservations";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfLocalId = CursorUtil.getColumnIndexOrThrow(_cursor, "localId");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCustomerName = CursorUtil.getColumnIndexOrThrow(_cursor, "customerName");
      final int _cursorIndexOfNumberOfPeople = CursorUtil.getColumnIndexOrThrow(_cursor, "numberOfPeople");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final List<Reservation> _result = new ArrayList<Reservation>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Reservation _item;
        _item = new Reservation();
        _item.localId = _cursor.getInt(_cursorIndexOfLocalId);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        if (_cursor.isNull(_cursorIndexOfCustomerName)) {
          _item.customerName = null;
        } else {
          _item.customerName = _cursor.getString(_cursorIndexOfCustomerName);
        }
        if (_cursor.isNull(_cursorIndexOfNumberOfPeople)) {
          _item.numberOfPeople = null;
        } else {
          _item.numberOfPeople = _cursor.getString(_cursorIndexOfNumberOfPeople);
        }
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _item.date = null;
        } else {
          _item.date = _cursor.getString(_cursorIndexOfDate);
        }
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _item.time = null;
        } else {
          _item.time = _cursor.getString(_cursorIndexOfTime);
        }
        if (_cursor.isNull(_cursorIndexOfLocation)) {
          _item.location = null;
        } else {
          _item.location = _cursor.getString(_cursorIndexOfLocation);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
