package com.example.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class JobViewModel : ViewModel() {
    private val _jobList = MutableStateFlow<List<Job>>(emptyList())
    val jobList: StateFlow<List<Job>> = _jobList

    private val _myJobList = MutableStateFlow<List<Job>>(emptyList())
    val myJobList: StateFlow<List<Job>> = _myJobList

    private val _favoriteJobs = MutableStateFlow<List<Job>>(emptyList())
    val favoriteJobs: StateFlow<List<Job>> = _favoriteJobs

    init {
        fetchJobs()
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            val jobs = withContext(Dispatchers.IO) {
                ApiService.fetchJobs()
            }
            _jobList.value = jobs
        }
    }

    fun getJobById(jobId: Int): Job? {
        return _jobList.value.firstOrNull { it.id == jobId }
    }

    fun addJob(job: Job) {
        _jobList.value = _jobList.value + job
    }

    fun addMyJob(job: Job) {
        _myJobList.value = _myJobList.value + job
    }

    fun removeMyJob(job: Job) {
        _myJobList.value = _myJobList.value - job
    }

    fun addFavoriteJob(job: Job) {
        _favoriteJobs.value = _favoriteJobs.value + job
    }

    fun removeFavoriteJob(job: Job) {
        _favoriteJobs.value = _favoriteJobs.value - job
    }

    fun updateJob(updatedJob: Job) {
        _jobList.value = _jobList.value.map { if (it.id == updatedJob.id) updatedJob else it }
        _myJobList.value = _myJobList.value.map { if (it.id == updatedJob.id) updatedJob else it }
        _favoriteJobs.value = _favoriteJobs.value.map { if (it.id == updatedJob.id) updatedJob else it }
    }
}