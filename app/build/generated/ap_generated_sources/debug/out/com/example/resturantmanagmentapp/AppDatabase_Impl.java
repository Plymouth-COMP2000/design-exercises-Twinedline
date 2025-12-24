package com.example.resturantmanagmentapp;

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
  private volatile MenuItemDao _menuItemDao;

  private volatile ReservationDao _reservationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `menu_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `price` REAL NOT NULL, `description` TEXT, `category` TEXT, `imageUrl` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reservations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `customerName` TEXT, `date` TEXT, `time` TEXT, `numberOfPeople` TEXT, `location` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '794908960c25131c2bfb497fce067dfc')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `menu_items`");
        db.execSQL("DROP TABLE IF EXISTS `reservations`");
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
        final HashMap<String, TableInfo.Column> _columnsMenuItems = new HashMap<String, TableInfo.Column>(6);
        _columnsMenuItems.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMenuItems = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMenuItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMenuItems = new TableInfo("menu_items", _columnsMenuItems, _foreignKeysMenuItems, _indicesMenuItems);
        final TableInfo _existingMenuItems = TableInfo.read(db, "menu_items");
        if (!_infoMenuItems.equals(_existingMenuItems)) {
          return new RoomOpenHelper.ValidationResult(false, "menu_items(com.example.resturantmanagmentapp.MenuItem).\n"
                  + " Expected:\n" + _infoMenuItems + "\n"
                  + " Found:\n" + _existingMenuItems);
        }
        final HashMap<String, TableInfo.Column> _columnsReservations = new HashMap<String, TableInfo.Column>(6);
        _columnsReservations.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReservations.put("customerName", new TableInfo.Column("customerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReservations.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReservations.put("time", new TableInfo.Column("time", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReservations.put("numberOfPeople", new TableInfo.Column("numberOfPeople", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReservations.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReservations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReservations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReservations = new TableInfo("reservations", _columnsReservations, _foreignKeysReservations, _indicesReservations);
        final TableInfo _existingReservations = TableInfo.read(db, "reservations");
        if (!_infoReservations.equals(_existingReservations)) {
          return new RoomOpenHelper.ValidationResult(false, "reservations(com.example.resturantmanagmentapp.Reservation).\n"
                  + " Expected:\n" + _infoReservations + "\n"
                  + " Found:\n" + _existingReservations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "794908960c25131c2bfb497fce067dfc", "b23f457f2ff9a89fd3087c94431068d6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "menu_items","reservations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `menu_items`");
      _db.execSQL("DELETE FROM `reservations`");
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
    _typeConvertersMap.put(MenuItemDao.class, MenuItemDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReservationDao.class, ReservationDao_Impl.getRequiredConverters());
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
  public MenuItemDao menuItemDao() {
    if (_menuItemDao != null) {
      return _menuItemDao;
    } else {
      synchronized(this) {
        if(_menuItemDao == null) {
          _menuItemDao = new MenuItemDao_Impl(this);
        }
        return _menuItemDao;
      }
    }
  }

  @Override
  public ReservationDao reservationDao() {
    if (_reservationDao != null) {
      return _reservationDao;
    } else {
      synchronized(this) {
        if(_reservationDao == null) {
          _reservationDao = new ReservationDao_Impl(this);
        }
        return _reservationDao;
      }
    }
  }
}
