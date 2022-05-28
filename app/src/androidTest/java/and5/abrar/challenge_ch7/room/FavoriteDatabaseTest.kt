package and5.abrar.challenge_ch7.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDatabaseTest : TestCase() {

    private lateinit var db : FavoriteDatabase
    private lateinit var dao : FavoriteDuo
    @Before
    public override fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,FavoriteDatabase::class.java).build()
        dao = db.favoriteduo()
    }

    @After
    public override fun tearDown() {
    db.close()
    }

    @Test
    fun testData(){
        dao.getFavorite()
    }
}