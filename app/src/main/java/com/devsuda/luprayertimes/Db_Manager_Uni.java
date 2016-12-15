package com.devsuda.luprayertimes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_Manager_Uni extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "uniDB_5";
	private static final int DATABASE_VERSION = 1;
	private static final String DAY = "day";
	private static final String DATE = "date";
	private static final String DATE_H = "date_H";
	private static final String SHEHRI = "shehri";
	private static final String SUNRISE = "sunrise";

	private static String TABLE_NAME = null;

	private static final String A_FAJR = "fajr_Azan";
	private static final String A_ZHOR = "zhor_Azan";
	private static final String A_ASOR_S = "asor_Azan_S";
	private static final String A_ASOR_H = "asor_Azan_H";
	private static final String A_ISHA = "isha_Azan";

	private static final String I_FAJR = "fajr_Igama";
	private static final String I_ZHOR = "zhor_Igama";
	private static final String I_ASOR = "asor_Igama";
	private static final String I_MAGRIB = "magrib_Igama";
	private static final String I_ISHA = "isha_Igama";

	private SQLiteDatabase database;

	private final Context context;

	// database path
	private static String DATABASE_PATH;

	/** constructor */
	public Db_Manager_Uni(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = ctx;
		DATABASE_PATH = context.getFilesDir().getParentFile().getPath()
				+ "/databases/";

	}

	/**
	 * checking the current month
	 */
	public String monthOfYear() {
		int current_month = Calendar.getInstance().get(Calendar.MONTH);
		int i = current_month;
		switch (i) {
		case (0):
			TABLE_NAME = "jan_Prayers";
			break;
		case (1):
			TABLE_NAME = "feb_Prayers";
			break;
		case (2):
			TABLE_NAME = "march_Prayers";
			break;
		case (3):
			TABLE_NAME = "april_Prayers";
			break;
		case (4):
			TABLE_NAME = "may_Prayers";
			break;
		case (5):
			TABLE_NAME = "june_Prayers";
			break;
		case (6):
			TABLE_NAME = "july_Prayers";
			break;
		case (7):
			TABLE_NAME = "aug_Prayers";
			break;
		case (8):
			TABLE_NAME = "sep_Prayers";
			break;
		case (9):
			TABLE_NAME = "oct_Prayers";
			break;
		case (10):
			TABLE_NAME = "nov_Prayers";
			break;
		case (11):
			TABLE_NAME = "dec_Prayers";
			break;
		}
		return TABLE_NAME;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void create() throws IOException {
		boolean check = checkDataBase();

		SQLiteDatabase db_Read = null;

		// Creates empty database default system path
		db_Read = this.getWritableDatabase();
		db_Read.close();
		try {
			if (!check) {
				copyDataBase();
			}
		} catch (IOException e) {
			throw new Error("Error copying database");
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;

			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring byte-stream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	/** open the database */
	public void open() throws SQLException {
		String myPath = DATABASE_PATH + DATABASE_NAME;
		database = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/** close the database */
	@Override
	public synchronized void close() {
		if (database != null)
			database.close();
		super.close();
	}

	/** retrieves a particular day prayers */
	public Cursor getPrayersTimeOnDay(long rowId) throws SQLException {
		String currentMonthTable = monthOfYear();
		Cursor mCursor = database.query(true, currentMonthTable, new String[] {
				DAY, DATE, DATE_H, SHEHRI, A_FAJR, I_FAJR, SUNRISE, A_ZHOR,
				I_ZHOR, A_ASOR_S, A_ASOR_H, I_ASOR, I_MAGRIB, A_ISHA, I_ISHA },
				DAY + " = " + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
