package com.example.assetmanagement.utils

/**
 * Class for events consumption in Live Data
 */
class Event<out T>(private var data: T) {

    var isEventHandled = false
        private set

    init {
        isEventHandled = false
    }

    // get data and handle the event
    fun getDataAndHandleEvent(): T {
        isEventHandled = true
        return data
    }

    // get data
    fun getData(): T {
        return data
    }

    // in case you want to handle the event without using the data
    fun handleEvent() {
        isEventHandled = true
    }

}