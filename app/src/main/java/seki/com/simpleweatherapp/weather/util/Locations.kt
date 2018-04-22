package seki.com.simpleweatherapp.weather.util

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import seki.com.simpleweatherapp.weather.domain.db.Location
import java.io.BufferedReader
import java.io.StringReader

object Locations {

    private val mLocalsOrg: MutableMap<String, String> = mutableMapOf()

    val locals: Map<String, String>
        get() = mLocalsOrg

    init {
        setTestData()
    }

    private fun setTestData() {
        mLocalsOrg.put("さいたま", "110010")
        mLocalsOrg.put("東京", "110010")
        mLocalsOrg.put("長野", "200010")
        mLocalsOrg.put("仙台", "040010")
    }


    fun expandLocationXml(xmlString: String): List<Location> {

        BufferedReader(StringReader(xmlString)).use {
            val factory: XmlPullParserFactory =
                    XmlPullParserFactory.newInstance().apply { isNamespaceAware = true }

            val parser: XmlPullParser = factory.newPullParser().apply { setInput(it) }

            val locations = mutableListOf<Location>()

            var eventType: Int = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType != XmlPullParser.START_TAG) {
                    eventType = parser.next()
                    continue
                }
                val tagName: String = parser.name
                if (tagName != "pref") {
                    eventType = parser.next()
                    continue
                }

                // prefタグが見つかったため
                // 都道府県名と地域名、IDの取得処理をスタート！

                // 都道府県名を取得
                val prefName = (0 .. parser.attributeCount)
                        .firstOrNull { parser.getAttributeName(it) == "title" }
                        ?.let { parser.getAttributeValue(it) }
                        ?: ""

                // prefタグの子要素を検査して地域情報を見つける
                eventType = parser.next()
                while (!(eventType == XmlPullParser.END_TAG && parser.name == "pref")) {
                    if (eventType != XmlPullParser.START_TAG) {
                        eventType = parser.next()
                        continue
                    }

                    val tagNameChild = parser.name
                    if (tagNameChild != "city") {
                        eventType = parser.next()
                        continue
                    }

                    var cityName:String? = null
                    var cityId: String? = null
                    for (i in 0 .. (parser.attributeCount - 1)) {
                        when (parser.getAttributeName(i)) {
                            "title" -> cityName = parser.getAttributeValue(i)
                            "id"    -> cityId = parser.getAttributeValue(i)
                        }
                    }

                    if (prefName.isNotEmpty() && cityId != null && cityName != null) {
                        val location = Location(
                                id = cityId,
                                prefName = prefName,
                                cityName = cityName)

                        locations += location
                    }

                    eventType = parser.next()
                }

                eventType = parser.next()
            }

            return locations
        }

    }
}