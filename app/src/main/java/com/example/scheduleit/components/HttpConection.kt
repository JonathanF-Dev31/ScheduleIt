package com.example.scheduleit.components

import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONArray
import org.json.JSONObject

class HttpConection {

    suspend fun getChatGPTResponse(message: String): String {
        val apiKey = "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"
        val url = URL("https://api.openai.com/v1/chat/completions")

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Authorization", "Bearer $apiKey")
        connection.doOutput = true

        val jsonPayload = JSONObject().apply {
            put("model", "gpt-3.5-turbo")

            val messagesArray = JSONArray()
            val userMessage = JSONObject().apply {
                put("role", "user")
                put("content", message)
            }
            messagesArray.put(userMessage)

            put("messages", messagesArray)
        }.toString()

        connection.outputStream.use { outputStream ->
            val input = jsonPayload.toByteArray()
            outputStream.write(input)
        }

        val response = StringBuilder()

        try {
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader(connection.inputStream).use { reader ->
                    reader.forEachLine { response.append(it) }
                }
            } else {
                InputStreamReader(connection.errorStream).use { reader ->
                    reader.forEachLine { response.append(it) }
                }
                throw Exception("Error: $responseCode ${connection.responseMessage}")
            }
        } catch (e: Exception) {
            throw Exception("Error during API call: ${e.message}")
        }

        return response.toString()
    }
}
