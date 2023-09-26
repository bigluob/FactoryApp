package com.camc.media.data.model

class Image(var path: String, var name: String, var time: Long) {
    override fun equals(o: Any?): Boolean {
        try {
            val other = o as Image?
            return path.equals(other!!.path, ignoreCase = true)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return super.equals(o)
    }
}