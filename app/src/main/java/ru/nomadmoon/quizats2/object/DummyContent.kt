package ru.nomadmoon.quizats2.`object`

import ru.nomadmoon.quizats2.quizdata
import ru.nomadmoon.quizats2.quizmetadata
import ru.nomadmoon.quizats2.quizresult
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */

object MainObject {
    var arrayOfAnswers = ArrayList<quizresult>()
    var arrayOfQuestions: ArrayList<quizdata> = ArrayList()
    var currentQuizDir: String = ""
    var currentQuizMeta: quizmetadata = quizmetadata("Тест не выбран", "Загрузите тест из файла или выберите тест из уже загруженных. Для вызова меню проведите пальцем с левого края экрана или нажмите кнопку ≡ в левом верхнем углу.", -1, -1, true)

}


object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 3

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    fun clearItems() {
        ITEMS.clear()
        ITEM_MAP.clear()
    }


    private fun createDummyItem(position: Int): DummyItem {
        return DummyItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val dirId: String) {
        override fun toString(): String = content
    }
}
