package database

import app.cash.turbine.test
import com.mshabiola.database.dao.drawingdao.DrawingDao
import com.mshdabiola.model.data.Drawing
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DrawingTest : AbstractTest() {


    override fun insert() = runTest {
        val drawingDao by inject<DrawingDao>()
        drawingDao.insert(Drawing(id = 4172L, path = "Osbaldo"))

        assertEquals(1, drawingDao.getAll().first().size)

    }

    override fun delete() = runTest {
        val drawingDao by inject<DrawingDao>()
        drawingDao.insert(Drawing(id = 8, path = "Osbaldo8"))
        drawingDao.insert(Drawing(id = 6, path = "Osbaldo6"))
        drawingDao.insert(Drawing(id = 8, path = "Osbaldo r8"))
        drawingDao.delete(1)
        drawingDao.insert(Drawing(id = 4, path = "Osbaldo 4"))
        val drawing = drawingDao.getAll().first()
        println(drawing.joinToString())

        assertEquals(3, drawingDao.getAll().first().size)
    }

    override fun getOne() = runTest {
        val drawingDao by inject<DrawingDao>()
        val drawing = Drawing(id = 1, path = "Osbaldo8")
        drawingDao.insert(drawing)
        drawingDao.getOne(1)
            .catch {
                // it.printStackTrace()
            }
            .test {
                val drawing2 = awaitItem()
                assertEquals(drawing, drawing2)

                cancelAndIgnoreRemainingEvents()
            }
    }

    override fun getAll() = runTest {
        val drawingDao by inject<DrawingDao>()
        drawingDao.insert(Drawing(id = 8, path = "Osbaldo"))
        drawingDao.insert(Drawing(id = 6, path = "Osbaldo"))
        drawingDao.insert(Drawing(id = 8, path = "Osbaldo"))
        drawingDao.getAll()
            .test {
                val drawing = awaitItem()
                assertEquals(
                    listOf(
                        Drawing(id = 1, path = "Osbaldo"),
                        Drawing(id = 2, path = "Osbaldo"),
                        Drawing(id = 3, path = "Osbaldo")
                    ), drawing
                )
                cancelAndIgnoreRemainingEvents()
            }
    }


}