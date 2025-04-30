package com.example.jobs

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object ApiService {
    private val client = OkHttpClient()

    fun fetchJobs(): List<Job> {
        val request = Request.Builder()
            .url("https://linkedin-job-api.p.rapidapi.com/job/search?keyword=Software%20developer&page=1")
            .get()
            .addHeader("x-rapidapi-key", "0191d5c996msh27fbd2ad3292110p126a52jsn803ece422f58")
            .addHeader("x-rapidapi-host", "linkedin-job-api.p.rapidapi.com")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ApiService", "API call failed with response code ${response.code}")
                throw IOException("Unexpected code $response")
            }
            val responseData = response.body?.string() ?: ""
            return parseJobs(responseData)
        }
    }

    private fun parseJobs(responseData: String): List<Job> {
        val jobs = mutableListOf<Job>()
        val jsonObject = JSONObject(responseData)
        val jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {
            val jobObject = jsonArray.getJSONObject(i)
            val job = Job(
                id = jobObject.getInt("jobPostingId"),
                title = jobObject.getString("title"),
                company = jobObject.getJSONObject("companyDetails").getString("name"),
                location = if (jobObject.has("location")) jobObject.getString("location") else "Location not specified",
                description = jobObject.getString("description"),
                logoUrl = jobObject.getJSONObject("companyDetails").getString("companyLogo")
            )
            jobs.add(job)
        }
        return jobs
    }
}