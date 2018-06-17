package se.uav.loggbok.util

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime

val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
        .create()

inline fun <reified T: Any> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)!!


//Very simple serializer/deserializer for java.time.LocalDateTime
//They will probably blow up for null values, for example, or maybe not be used at all
//For some reason, https://github.com/gkopff/gson-javatime-serialisers didn't work as it should
//so these are the alternative...

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(json!!.asJsonPrimitive.asString)
    }
}